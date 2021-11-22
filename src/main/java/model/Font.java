package model;
/*
 * décorateur pour le font :  font
 */


public class Font extends Decorator{
    
   
    public Font(DocumentElement g){
        super.glyph = g;
        super.setAttribute("");
        super.setTagname("font");
    }

    @Override
    public int getChildSize() {
        return 1;
    }

   
    @Override
    public boolean isSingleTag() { return false; }
   
}
