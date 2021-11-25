package model;

import java.util.ArrayList;
import java.util.List;

import Iterator.GlyphListIterator;
import Iterator.Iterator;
import visitor.Visitor;
/*
 * un élément composite
 */
public abstract class CompositeElement extends DocumentElement{
	    private List<DocumentElement> children = new ArrayList<>();
	
	 
	    public void insert(DocumentElement g) {
	    	children.add(g);
	    }
	   
	    public void remove(int i) {
	    	children.remove(i);
	    }

	  
	    public DocumentElement getChild(int i) {
	        return children.get(i);
	    }
	
	    public int getChildSize() {
	        return children.size();
	    }
	
	    public Iterator getIterator() {
	        return new GlyphListIterator(this);
	    }
	    @Override
	    public String getContent() { return ""; }
	    
	    
	    @Override
	    public boolean isSingleTag() { return false; }
	    
	    @Override
	    public void accept(Visitor visitor) {
	        Iterator iterator = this.getIterator();
	        while (iterator.hasNext()) {
	            iterator.next().accept(visitor);
	        }
	        visitor.visit(this);
	    }
	   

		
	    
	    
	
	
     

   

}
