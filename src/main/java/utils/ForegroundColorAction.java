package utils;

import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ForegroundColorAction extends StyledEditorKit.StyledTextAction {
    private Color fg;

    public ForegroundColorAction(String nm, Color fg) {
        super(nm);
        this.fg = fg;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JEditorPane editor = getEditor(ae);
        if (editor == null) return;
        // Définir un attribut vide
        MutableAttributeSet attr = new SimpleAttributeSet();
        // Envoyez la commande pour changer la couleur de fond à l'éditeur
        if(this.fg != null) StyleConstants.setForeground(attr, this.fg);
        setCharacterAttributes(editor, attr, false);
    }

    public String getHTMLColor(Color color) {
        if (color == null) return "";
        return "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
    }
}