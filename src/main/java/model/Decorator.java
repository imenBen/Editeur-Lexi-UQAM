package model;


/*
 * une abstraction pour les décorateurs
 */
public abstract class Decorator extends DocumentElement{
    protected DocumentElement glyph;  //élément à décorer
    
    @Override
    public int getChildSize() { return glyph.getChildSize(); }

    @Override
    public void setAttribute(String attribute) { glyph.setAttribute(attribute); }
    @Override
    public void setContent(String content) { glyph.setContent(content); }

    @Override
    public String getAttribute() { return glyph.getAttribute(); }
    @Override
    public String getContent() { return glyph.getContent(); }
    @Override
    public String getTagname() { return glyph.getTagname(); }

    @Override
    public boolean isSingleTag() { return glyph.isSingleTag(); }
 
}
