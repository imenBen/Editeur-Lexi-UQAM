package Iterator;

import model.CompositeElement;
import model.DocumentElement;

public class GlyphListIterator implements Iterator {
    private int position = 0;
    private CompositeElement glyphComposite;
    public GlyphListIterator(CompositeElement glyphComposite){
        this.glyphComposite = glyphComposite;
    }

    @Override
    public boolean hasNext() {
        if(position >= glyphComposite.getChildSize() || glyphComposite.getChild(position) == null)
            return false;
        return true;
    }

    @Override
    public DocumentElement next() {
        return glyphComposite.getChild(position++);
    }

    @Override
    public boolean hasPrevious() {
        if(position-1 >= glyphComposite.getChildSize() || glyphComposite.getChild(position-1) == null)
            return false;
        return true;
    }

    @Override
    public DocumentElement previous() { return glyphComposite.getChild(--position); }

    @Override
    public void remove() {
        glyphComposite.remove(position-1);
        position -= 1;
    }
}
