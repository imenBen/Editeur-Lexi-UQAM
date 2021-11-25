package model;

import Iterator.Iterator;
import visitor.Visitor;

/*
 * une abstraction pour la racine d'un document 
 */

public class Root extends CompositeElement{
    
    public Root(){
    	 super.setTagname("body");
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
