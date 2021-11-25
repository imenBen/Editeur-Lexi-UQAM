package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.CompositeElement;
import model.DocumentElement;
import model.Paragraph;
import model.Root;
import ui.MainWindow;
import utils.LEXIFILEtoGDocumentElementParser;



public class OuvrirCommand implements Command {
	//private Circuit circuit;
	private MainWindow mainWindow;
	private DocumentElement root;

	public OuvrirCommand(MainWindow mainWindow, DocumentElement root) {
		
		this.mainWindow = mainWindow;
		this.root =  root;
	}
	
	@Override
	public void execute() {		
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
                root =  (new LEXIFILEtoGDocumentElementParser().parse(opening_oosefile));
                mainWindow.setEditorContent(root);
                // Définir la mise en page
                //Map<String, Object> document = new Gson().fromJson(opening_oosefile, new TypeToken<Map<String, Object>>(){}.getType());
                // if(document.get("format").equals("Full")) mainWindow.setFormatting(new FullFormatting());
                //  else  mainWindow.setFormatting(new PlaintextFormatting());
            } catch (Exception evt) {
                System.out.println(evt.getMessage());
                mainWindow.showDialog(evt.getMessage(), "Erreur");
            }

        }
        // If the user cancelled the operation
        else
            mainWindow.showDialog("Annuler l'opération de sauvegarde du fichier", "OK");
        
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

