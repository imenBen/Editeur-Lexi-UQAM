package model;

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
  
}
