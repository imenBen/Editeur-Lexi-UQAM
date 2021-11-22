package utils;

import model.DocumentElememtFactory;
import model.*;
import model.Character;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.ArrayList;
import java.util.List;

public class HTMLtoDocumentElementParser implements Parser<DocumentElement> {
    @Override
    public DocumentElement parse(String html) {
        Document document = Jsoup.parse(html.replaceAll("\\s{2,}"," "));
        Element body = document.body();
        body.insertChildren(body.childNodeSize()-2, new Element(Tag.valueOf("p"), ""));
        return getParse(body);
    }

    public DocumentElement getParse(Element elem){
        //si c'est null retourner null
        if(elem == null) return null;

       // Obtenir document element Factory et générer document elemennt
        DocumentElememtFactory myDecouementElementFactory = new DocumentElememtFactory();
        DocumentElement documentElementSpanElement = isDecorator(elem.tagName())? getDecorateeGlyphs(elem): null;
        DocumentElement documentElement = myDecouementElementFactory.getDocumentElementInstance(elem.tagName(), documentElementSpanElement);

       // Définir les propriétés du glyphe du document element
        try {
            documentElement.setAttribute(elem.attributes().toString().replace("\"", "\\\"").replaceAll(" style=\\\\?[\\\"'].*\\\\?['\\\"]", ""));
            documentElement.setContent(elem.ownText());
            
        } catch(Exception ex) {}

        //Insérer un élément enfant
        if(!(documentElement instanceof Character || documentElement instanceof Decorator)) {
            ArrayList<DocumentElement> childGlyphs = getChildListParse(elem.childNodes());
            for (DocumentElement glyph : childGlyphs) {
                try { if (documentElement.getChildSize() > 1 ) ((CompositeElement) documentElement).insert(glyph); }
                catch (Exception ex) {}
            }
        }
        System.out.println(documentElement.toString());
        //retourner un document element
        return documentElement;
    }

    public ArrayList<DocumentElement> getChildListParse(List<Node> elems){
        //Parcourez chaque enfant et générez un document element à enregistrer dans la liste
        ArrayList<DocumentElement> glyphList = new ArrayList<>();

        for (Node node : elems) {
            //Traitement des nœuds de texte
            if(node instanceof TextNode){
                //Construire une chaîne en tant que nœud d'élément  basé sur des caractères
                for (String s: preProcessedText(((TextNode) node).toString()).split("")) {
                    if(!((TextNode) node).isBlank()) {
                        Element textElement = new Element(Tag.valueOf("character"), "");
                        if(s.equals(" "))  s = "&nbsp;";    //espaces
                        textElement.text(s);
                        DocumentElement g = getParse(textElement);
                        glyphList.add(g);
                    }
                }
            }
            //Traitement d'autres nœuds
            else {
                DocumentElement g = getParse((Element) node);
                if (g != null) glyphList.add(g);
            }
        }
        return glyphList;
    }

    public DocumentElement getDecorateeGlyphs(Element elem){
        //span Document element
        DocumentElement glyphSpanElement = getParse(new Element(Tag.valueOf("span"), ""));
        //Mettez les documentelement de tous les éléments enfants de cet élément dans l'étendue pour former un ensemble de documentelements
        ArrayList<DocumentElement> childGlyphs = getChildListParse(elem.childNodes());
        for (DocumentElement glyph : childGlyphs) {
            try {if (glyphSpanElement.getChildSize() > 1 ) ((CompositeElement) glyphSpanElement).insert(glyph);   }
            catch (Exception ex) {}
        }

        return glyphSpanElement;
    }

    public String preProcessedText(String input){
        
        return input.replaceAll(" ", "").replaceAll("(&nbsp;)|(&#160;)|(&amp;nbsp;)", " ");
    }

    public boolean isDecorator(String tagName){
        List<String> normalFactory = List.of("span", "p", "img", "body", "character");
        List<String> decoratorFactory = List.of("b", "i", "u", "font");
        return decoratorFactory.contains(tagName);
    }
}