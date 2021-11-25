package visitor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Character;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class DocumentElementToLexiFILEVisitor implements Visitor{
    private Stack<Map<String, Object>> parseStack = new Stack<>();
    private ArrayList<Map<String, Object>> childList;
    @Override
    public void visit(Root root) {pushCompositeToStack(root);}
    @Override
    public void visit(Paragraph paragraph) {pushCompositeToStack(paragraph);}
    @Override
    public void visit(Span span) {pushCompositeToStack(span);}
    @Override
    public void visit(Image image) {pushLeafToStack(image);}
    @Override
    public void visit(Character character) {pushLeafToStack(character);}
    @Override
    public void visit(Bold bold) {pushDecorateToStack(bold);}
    @Override
    public void visit(Italic italic) {pushDecorateToStack(italic);}
    @Override
    public void visit(Underline underline) {pushDecorateToStack(underline);}
    @Override
    public void visit(Font font) {pushDecorateToStack(font);}
    @Override
    public void visit(DocumentElement documentElement) {pushCompositeToStack(documentElement);}

    public Map<String, Object> generateGlyphElem(DocumentElement documentElement) {
    	// Combinez d'abord les éléments enfants dans l'ordre (le nœud le plus ancien sera poussé avant le nœud le plus récent, 
    	//donc le nœud le plus récent sortira en premier, donc pop() + childParseStrings
        childList = new ArrayList<>();
        for(int i = 0; i < documentElement.getChildSize(); i++){
            childList.add(0, parseStack.pop());
        }
        Map<String, Object> elem = new HashMap<>();
        elem.put("tag", documentElement.getTagname());
        elem.put("attribute", documentElement.getAttribute().replace("\\\"", "\""));
        elem.put("content", documentElement.getContent());
        return elem;
    }

    public void pushDecorateToStack(DocumentElement documentElement){
    	//Si le documentelement actuel a des éléments enfants et le nombre d'éléments enfants >= le nombre actuel d'éléments dans la pile
        if(documentElement.getChildSize()>0 && parseStack.size() >= documentElement.getChildSize()){
            Map<String, Object> elem = generateGlyphElem(documentElement);
            elem.put("decoratee", childList.get(0));
            elem.put("child", new ArrayList<>());
            // Repousse le documentelement actuel dans la pile
            parseStack.push(elem);
        } else {
            pushLeafToStack(documentElement);
        }
    }

    public void pushCompositeToStack(DocumentElement documentElement){
    	//Si le documentelement actuel a des éléments enfants et le nombre d'éléments enfants >= le nombre actuel d'éléments dans la pile
        if(documentElement.getChildSize()>0 && parseStack.size() >= documentElement.getChildSize()){
            Map<String, Object> elem = generateGlyphElem(documentElement);
            elem.put("decoratee", null);
            elem.put("child", childList);
         // Repousse le documentelement actuel dans la pile
            parseStack.push(elem);
        } else {
            pushLeafToStack(documentElement);
        }
    }

    public void pushLeafToStack(DocumentElement documentElement){
        Map<String, Object> elem = new HashMap<>();
        elem.put("tag", documentElement.getTagname());
        elem.put("attribute", documentElement.getAttribute().replace("\\\"", "\""));
        elem.put("content", documentElement.getContent());
        elem.put("decoratee", null);
        elem.put("child", new ArrayList<>());
        parseStack.push(elem);
    }

    public String getParseString(){
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(parseStack.peek());
    }
}
