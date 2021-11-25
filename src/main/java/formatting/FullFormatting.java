package formatting;

import model.Character;
import model.DocumentElement;
import model.Image;
import utils.parseArgs;

public class FullFormatting implements Formatting{
    private final String stylesheet = "style=\"font-family:'Microsoft is bold' !important;\"";
    private final String TYPE = "Full";
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
                startringTag = startringTag.replaceAll(" width=\\\\?[\\\"'].{1,3}\\\\?['\\\"] ", " width=\"600\" ");
                startringTag = startringTag.replaceAll(" src=\\\\?[\\\"']#*", " src=\"");
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
