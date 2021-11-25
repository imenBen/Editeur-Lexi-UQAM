package formatting;

import model.DocumentElement;

import utils.parseArgs;

public interface Formatting {
    parseArgs parse(DocumentElement glyph);   //parses un document
    String getTYPE();  //Obtenir le type de formatage actuel
}
