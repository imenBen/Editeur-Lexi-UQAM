package model;
/*
 * décorateur pour le font italique
 */

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
   
}
