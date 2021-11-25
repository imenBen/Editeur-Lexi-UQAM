package model;

import visitor.Visitor;

/*
 * décorateur pour le font gras
 */
public class Bold extends Decorator{

    public Bold(DocumentElement g){
    	super.glyph = g;
    	super.setAttribute("");
    	super.setTagname("b");
        
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
