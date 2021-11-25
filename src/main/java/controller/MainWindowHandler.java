package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import formatting.FullFormatting;
import formatting.PlaintextFormatting;
import model.CompositeElement;
//import formatting.FullFormatting;
//import formatting.PlaintextFormatting;
import model.DocumentElement;
import model.Paragraph;
import model.Root;
import ui.MainWindow;
import utils.BackgroundColorAction;
import utils.ForegroundColorAction;
import utils.InsertImageAction;
import utils.LEXIFILEtoGDocumentElementParser;
//import visitor.GlyphToOOSEFILEVisitor;
import visitor.DocumentElementToLexiFILEVisitor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class MainWindowHandler implements ActionListener {
    MainWindow mainWindow;
    DocumentElement root;
    public MainWindowHandler(MainWindow mainWindow){ this.mainWindow = mainWindow; }
    // Lorsque le bouton MenuItem est enfoncé
    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        this.root = mainWindow.getRoot();
        switch (s) {
            //----------------------------------------Éditer----------------------------------------
            case "cut":
                new DefaultEditorKit.CutAction().actionPerformed(e); //editorViewer.cut();
                break;
            case "copy":
                new DefaultEditorKit.CopyAction().actionPerformed(e);
                break;
            case "paste":
                new DefaultEditorKit.PasteAction().actionPerformed(e);
                break;
            case "insertImg": {
                // Créer un sélecteur de fichier
                JFileChooser jFileChooser = new JFileChooser("f:");
                jFileChooser.setFileFilter(new FileNameExtensionFilter("jpg, jpeg, png, and gif", new String[] {"JPEG", "JPG", "PNG", "GIF"}));
                setFileChooserText("Insérer une image", "Insérer");
                SwingUtilities.updateComponentTreeUI(jFileChooser);
                // Si l'utilisateur sélectionne le fichier
                if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    // Définir le fichier sur le chemin du répertoire sélectionné
                    String absPath = jFileChooser.getSelectedFile().getAbsolutePath();
                    AttributeSet outerAttr = new InsertImageAction(absPath).actionPerformed(e);
                    mainWindow.insertEditorImage(outerAttr);
                } else {
                    mainWindow.showDialog("Annuler", "OK");
                }
                break;
            }

            //----------------------------------------Style----------------------------------------
            case "Bold":
                new StyledEditorKit.BoldAction().actionPerformed(e);
                break;
            case "Italic":
                new StyledEditorKit.ItalicAction().actionPerformed(e);
                break;
            case "Underline":
                new StyledEditorKit.UnderlineAction().actionPerformed(e);
                break;

            //---------------------------------------Couleurs----------------------------------------
            case "BG-Red":
                new BackgroundColorAction("BG-Red", Color.RED).actionPerformed(e);
                break;
            case "BG-Green":
                new BackgroundColorAction("BG-Green", Color.GREEN).actionPerformed(e);
                break;
            case "BG-Blue":
                new BackgroundColorAction("BG-Blue", Color.BLUE).actionPerformed(e);
                break;
            case "BG-Black":
                new BackgroundColorAction("BG-Black", Color.BLACK).actionPerformed(e);
                break;
            case "BG-None":
                new BackgroundColorAction("BG-None", null).actionPerformed(e);
                break;
            case "FG-Red":
                new ForegroundColorAction("FG-Red", Color.RED).actionPerformed(e);
                break;
            case "FG-Green":
                new ForegroundColorAction("FG-Green", Color.GREEN).actionPerformed(e);
                break;
            case "FG-Blue":
                new ForegroundColorAction("FG-Blue", Color.BLUE).actionPerformed(e);
                break;
            case "FG-White":
                new ForegroundColorAction("FG-White", Color.WHITE).actionPerformed(e);
                break;
            case "FG-Black":
                new ForegroundColorAction("FG-Black", Color.BLACK).actionPerformed(e);
                break;

            //----------------------------------------Fichier----------------------------------------
            case "new":
                root = new Root();
                ((CompositeElement)root).insert(new Paragraph());
                mainWindow.setEditorContent(root);
                break;
            case "open": {
                 // Créer un sélecteur de fichier
                JFileChooser jFileChooser = new JFileChooser("f:");
                jFileChooser.setFileFilter(new FileNameExtensionFilter("Lexi Document File", new String[] {"LEXI"}));
                setFileChooserText("Ouvrir les anciens fichiers", "Ouvrir");
                SwingUtilities.updateComponentTreeUI(jFileChooser);
                // Si l'utilisateur sélectionne le fichier
                if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    // Définir le chemin absolu vers le répertoire sélectionné
                    File file = new File(jFileChooser.getSelectedFile().getAbsolutePath().replaceAll("\\.lexi", "") + ".lexi");

                    try {
                        // Créer un lecteur de fichiers
                        InputStreamReader fileReader = new InputStreamReader (new FileInputStream(file),StandardCharsets.UTF_8);    //FileReader fileReader = new FileReader(file);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        // Lire ligne par ligne
                        String opening_oosefile = "";
                        String readline = "";
                        opening_oosefile = bufferedReader.readLine();
                        while ((readline = bufferedReader.readLine()) != null)
                            opening_oosefile = opening_oosefile + "\n" + readline;

                        //Définir sur editorViewer
                        root = new LEXIFILEtoGDocumentElementParser().parse(opening_oosefile);
                        mainWindow.setEditorContent(root);
                        // Définir la mise en page
                        Map<String, Object> document = new Gson().fromJson(opening_oosefile, new TypeToken<Map<String, Object>>(){}.getType());
                        if(document.get("format").equals("Full")) mainWindow.setFormatting(new FullFormatting());
                        else  mainWindow.setFormatting(new PlaintextFormatting());
                    } catch (Exception evt) {
                        System.out.println(evt.getMessage());
                        mainWindow.showDialog(evt.getMessage(), "Erreur");
                    }

                }
                // If the user cancelled the operation
                else
                    mainWindow.showDialog("Annuler l'opération de sauvegarde du fichier", "OK");
                break;

            }
            case "save": {
                // Créer un sélecteur de fichier
                JFileChooser jFileChooser = new JFileChooser("f:");
                jFileChooser.setFileFilter(new FileNameExtensionFilter("Lexi Document File", new String[] {"LEXI"}));
                setFileChooserText("Enregistrer le fichier", "Enregistrer");
                SwingUtilities.updateComponentTreeUI(jFileChooser);
                // Si l'utilisateur sélectionne le fichier
                if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    // Définir le chemin absolu vers le répertoire sélectionné
                    File file = new File(jFileChooser.getSelectedFile().getAbsolutePath().replaceAll("\\.lexi", "") + ".lexi");

                    try {
                        
                        OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);    //FileWriter fileWriter = new FileWriter(file, false);
                        BufferedWriter writer = new BufferedWriter(fileWriter);
                        // Écrire des données
                          DocumentElementToLexiFILEVisitor documentElementToLexiFILEVisitor = new DocumentElementToLexiFILEVisitor();
                          root.accept(documentElementToLexiFILEVisitor);  
                          String saving_oosefile = "{\"format\": \"" + mainWindow.getFormatting().getTYPE() + "\"," +
                                                   "\"content\": " + documentElementToLexiFILEVisitor.getParseString() + "}";
                          writer.write(saving_oosefile);
                        writer.flush();
                        writer.close();
                    } catch (Exception evt) {
                        evt.printStackTrace();
                        mainWindow.showDialog(evt.getMessage(), "Erreur");
                    }
                }
                // Si l'utilisateur annule l'opération
                else
                    mainWindow.showDialog("Annuler l'opération de sauvegarde du fichier", "OK");
                break;
            }

            //----------------------------------------Format----------------------------------------
            //Le bouton enfoncé est "full"
            case "full":
                mainWindow.setFormatting(new FullFormatting());
                break;
            //Le bouton enfoncé est "plaintext"
            case "plaintext":
                mainWindow.setFormatting(new PlaintextFormatting());
                break;

            //---------------------------------------aide----------------------------------------
            case "about":
                mainWindow.showDialog("Cet éditeur de document fonctionne sur l'environnement" + mainWindow.getEnvironment() , "À propos");
                break;
        }
    }

    public void setFileChooserText(String openDialogTitleText, String openButton){
        UIManager.put("FileChooser.openDialogTitleText", openDialogTitleText);
        UIManager.put("FileChooser.lookInLabelText", "");
        UIManager.put("FileChooser.openButtonText", openButton);
        UIManager.put("FileChooser.cancelButtonText", "Annuler");
        UIManager.put("FileChooser.fileNameLabelText", "Nom de fichier");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Type de fichier");
        UIManager.put("FileChooser.openButtonToolTipText", openButton+"ouvrir");
        UIManager.put("FileChooser.cancelButtonToolTipText","Annuler");
    }
}
