package model;

import visitor.Visitor;

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
    
    @Override
    public void accept(Visitor visitor) { visitor.visit(this); System.out.println(visitor.getClass());}
   
}
