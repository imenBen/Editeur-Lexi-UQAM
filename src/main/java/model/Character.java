package model;


/*
 * Les éléments de type caractère
 */
public class Character extends DocumentElement{
   
    
    public Character(){ 
    	this.setTagname("character");
    	this.setAttribute("");
 
    }
    @Override
    public int getChildSize() {
        return 0;
    }

    @Override
    public boolean isSingleTag() { return true; }
   
}
