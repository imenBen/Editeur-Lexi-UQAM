package utils;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BackgroundColorAction extends StyledEditorKit.StyledTextAction {
    private Color bg;

    public BackgroundColorAction(String nm, Color bg) {
        super(nm);
        this.bg = bg;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JEditorPane editor = getEditor(ae);
        if (editor == null) return;
        // définir un attribut vide
        SimpleAttributeSet attr = new SimpleAttributeSet();
        if(this.bg != null) attr.addAttribute(HTML.Attribute.BGCOLOR, getHTMLColor(bg));
        // définir le tag (balise)
        MutableAttributeSet outerAttr = new SimpleAttributeSet();
        outerAttr.addAttribute(HTML.Tag.FONT, attr);
        // Envoyez la commande pour changer la couleur de fond à l'éditeur
        setCharacterAttributes(editor, outerAttr, false);
    }

    public String getHTMLColor(Color color) {
        if (color == null) return "";
        return "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
    }
}