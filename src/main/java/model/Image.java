package model;

import visitor.Visitor;

/*
 * une abstraction pour les éléments de type image
 */
public class Image extends DocumentElement{
   
    public Image(){
    	this.setTagname("img");
    	this.setAttribute("");
    	this.setContent("");
    }

    @Override
    public int getChildSize() {
        return 0;
    }

   
    @Override
    public boolean isSingleTag() { return true; }
    
    @Override
    public void accept(Visitor visitor) { visitor.visit(this); }
   
}
