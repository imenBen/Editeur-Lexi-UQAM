package model;
/*
 * décorateur pour le font italique
 */

import visitor.Visitor;

public class Underline extends Decorator{
    
    
    public Underline(DocumentElement g){
        super.glyph = g;
        super.setTagname("u");
        super.setAttribute("");
    }

    @Override
    public int getChildSize() {
        return 1;
    }

    

    @Override
    public boolean isSingleTag() { return false; }
    @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
