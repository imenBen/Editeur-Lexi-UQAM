package visitor;

import model.Bold;
import model.Character;
import model.DocumentElement;
import model.Font;
import model.Image;
import model.Italic;
import model.Paragraph;
import model.Root;
import model.Span;
import model.Underline;

public class CountCharacterVisitor implements Visitor{
    private int characterCnt = 0;
    private int paragraphCnt = 0;
    @Override
    public void visit(Root root) {}
    @Override
    public void visit(Paragraph paragraph) {
        if(paragraph.getChildSize()!=0)
            paragraphCnt++;
        System.out.println(paragraphCnt);
        
        
        
    }
    @Override
    public void visit(Span span) {}
    @Override
    public void visit(Image image) {}
    @Override
    public void visit(Character character) {
       // if(!character.getContent().equals(""))
            characterCnt++;
            System.out.println("characterCnt"+characterCnt);
    }
    @Override
    public void visit(Bold bold) {}
    @Override
    public void visit(Italic italic) {}
    @Override
    public void visit(Underline underline) {}
    @Override
    public void visit(Font font) {}
    @Override
    public void visit(DocumentElement documentElement) {}

    public int getCharacter(){
        return characterCnt;
    }
    public int getParagraph() { return paragraphCnt; }
}
