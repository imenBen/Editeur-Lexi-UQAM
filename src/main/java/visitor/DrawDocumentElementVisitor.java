package visitor;

import formatting.Formatting;
import model.Character;
import model.*;
import utils.parseArgs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class DrawDocumentElementVisitor implements Visitor{
    private Stack<parseArgs> parseStack = new Stack<>();
    private Formatting formatting;
    public DrawDocumentElementVisitor(Formatting formatting){
        this.formatting = formatting;
    }
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
    public void visit(Bold bold) {pushCompositeToStack(bold);}
    @Override
    public void visit(Italic italic) {pushCompositeToStack(italic);}
    @Override
    public void visit(Underline underline) {pushCompositeToStack(underline);}
    @Override
    public void visit(Font font) {pushCompositeToStack(font);}
    @Override
    public void visit(DocumentElement documentElement) {pushCompositeToStack(documentElement);}

    public void pushCompositeToStack(DocumentElement documentElement){
        parseArgs parseArgs = formatting.parse(documentElement);
        //Si le documentElement actuel a des éléments enfants et le nombre d'éléments enfants >= le nombre actuel d'éléments dans la pile
        if(documentElement.getChildSize()>0 && parseStack.size() >= documentElement.getChildSize()){
        	// Combinez d'abord les éléments enfants dans l'ordre (le nœud le plus ancien sera poussé avant le nœud le plus récent, 
        	//donc le nœud le plus récent sortira en premier, donc pop() + childParseStrings
            String childParseStrings = "";
            for(int i = 0; i < documentElement.getChildSize(); i++){
                childParseStrings = parseStack.pop().getFullTag() + childParseStrings;
            }
            // documentElement enveloppe childParseStrings
            childParseStrings = parseArgs.getStartingTag() + childParseStrings + parseArgs.getClosingTag();
            //Repousser le  documentElement dans la pile
            parseArgs pushBackParseArgs = new parseArgs();
            pushBackParseArgs.setFullTag(childParseStrings);
            parseStack.push(pushBackParseArgs);
        } else {
            pushLeafToStack(documentElement);
        }
    }

    public void pushLeafToStack(DocumentElement documentElement){
        parseArgs parseArgs = formatting.parse(documentElement);
        parseStack.push(parseArgs);
    }

    public String getParseString(){
        return parseStack.peek().getFullTag();
    }
}
