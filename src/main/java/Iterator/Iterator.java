package Iterator;

import model.DocumentElement;

public interface Iterator {
    boolean hasNext();
    DocumentElement next();
    boolean hasPrevious();
    DocumentElement previous();
    void remove();
}
