package model;

import Iterator.Iterator;
import visitor.Visitor;

/*
 * une abstraction pour l'élément paragraphe
 */
public class Paragraph extends CompositeElement{
    
    
    public Paragraph(){
    	super.setTagname("p");
    	super.setAttribute("");
    	super.setContent("");
    }

   
    public void accept(Visitor visitor) {
        Iterator iterator = this.getIterator();
        while (iterator.hasNext()) {
            iterator.next().accept(visitor);
        }
        visitor.visit(this);
    }
	

	
    
}
