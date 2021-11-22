package model;

/*
 * une abstraction pour les éléments d'un document. 
 */


public abstract class DocumentElement{
	
	private String tagname ;
    private String attribute ;
    private String content ;


    
    public void setAttribute(String attribute) {
    	this.attribute = attribute;
    }
    public String getAttribute() {
		return attribute;
	}
    public void setContent(String content) {
    	this.content = content;
    }
    public String getContent() {
		return content;
	}   
    protected void setTagname(String tagname) {
		this.tagname = tagname;
	}
	public String getTagname() {
		return tagname;
	}
	
	
	
	public abstract boolean isSingleTag(); 
    
    public abstract int getChildSize() ;
  
}
