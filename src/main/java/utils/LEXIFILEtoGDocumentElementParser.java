package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Character;
import model.CompositeElement;
import model.Decorator;
import model.DocumentElement;
import model.DocumentElememtFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LEXIFILEtoGDocumentElementParser implements Parser<DocumentElement> {

    @Override
    public DocumentElement parse(String oosefile_string) {
        Gson gson = new Gson();
        Map<String, Object> document = gson.fromJson(oosefile_string, new TypeToken<Map<String, Object>>(){}.getType()); //Type type = ;
        return getParse((Map<String, Object>)document.get("content"));
    }

    public DocumentElement getParse(Map<String, Object> entry){
    	//Si l'élément est vide, renvoie un DocumentElement vide
        if(entry == null) return null;

        //extraite les propriétés
        String tag = (String) entry.get("tag");
        String attribute = (String) entry.get("attribute");
        String content = (String) entry.get("content");
        ArrayList<Map<String, Object>> childs = (ArrayList<Map<String, Object>>)entry.get("child");

        //Instancier les factorys et créer l'élément
        DocumentElememtFactory myDocumentElementFactory = new DocumentElememtFactory();
        DocumentElement documentElementDecorateeElement = isDecorator(tag)? getParse((Map<String, Object>) entry.get("decoratee")): null;
        DocumentElement documentElementElement = myDocumentElementFactory.getDocumentElementInstance(tag, documentElementDecorateeElement);

        //définir les propriétés du documentElement
        try {
            documentElementElement.setAttribute(attribute.replace("\"", "\\\"").replaceAll(" style=\\\\?[\\\"'].*\\\\?['\\\"]", ""));
            documentElementElement.setContent(content);
        } catch(Exception ex) {}

        //Insérer un élément enfant
        if(!(documentElementElement instanceof Character || documentElementElement instanceof Decorator)) {
            for (Map<String, Object> child : childs) {
                DocumentElement childGlyph = getParse(child);
                try { if(childGlyph!=null) ((CompositeElement)documentElementElement).insert(childGlyph); }
                catch (Exception ex) {}
            }
        }

        //retourner document élément
        return documentElementElement;
    }

    public boolean isDecorator(String tagName){
        List<String> decoratorFactory = List.of("b", "i", "u", "font");
        return decoratorFactory.contains(tagName);
    }
}
