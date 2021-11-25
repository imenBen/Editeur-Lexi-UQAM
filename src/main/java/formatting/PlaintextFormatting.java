package formatting;

import model.Character;
import model.DocumentElement;
import model.Image;
import utils.parseArgs;

public class PlaintextFormatting implements Formatting{
    private final String stylesheet = "style=\"background-color: #FFFFFF !important; color: #000000 !important; font-weight: normal !important; margin: 0; font-style: normal !important; text-decoration:none !important; font-family:'Times New Roman', 'New detailed body' !important;\"";
    private final String TYPE = "Plaintext";
    @Override
    public parseArgs parse(DocumentElement documentElement) {
        parseArgs parseArgs = new parseArgs();
        String startringTag = "";
        String closingTag = "";
        if(documentElement instanceof Character) {
            startringTag = documentElement.getContent();
            parseArgs.setFullTag(startringTag);
        } else {
            startringTag = "<" + documentElement.getTagname() + " " + documentElement.getAttribute().replace("\\\"", "\"") + " " + stylesheet + " >";

            closingTag = "</" + documentElement.getTagname() + ">";
            if(documentElement instanceof Image) {
                startringTag = startringTag.replaceAll(" width=\\\\?[\\\"'].{1,3}\\\\?['\\\"] ", " width=\"100\" ");
                startringTag = startringTag.replaceAll(" src=\\\\?[\\\"']#*", " src=\"##");
            }
            if(documentElement.isSingleTag()) closingTag = "";
            parseArgs.setStartingTag(startringTag);
            parseArgs.setClosingTag(closingTag);
        }
        return parseArgs;
    }

    @Override
    public String getTYPE() {
        return TYPE;
    }
}
