package model;

/*
 * décorateur pour le font italique
 */

public class Italic extends Decorator{
    
    public Italic(DocumentElement g){
        super.glyph = g;
        super.setTagname("i");
        super.setAttribute("");
    }

    @Override
    public int getChildSize() {
        return 1;
    }

   

    @Override
    public boolean isSingleTag() { return false; }
   
}


