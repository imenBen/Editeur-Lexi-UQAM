package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
import visitor.DocumentElementToLexiFILEVisitor;



public class EnregistrerCommand implements Command {
	//private Circuit circuit;
	private MainWindow mainWindow;
	private DocumentElement root;

	public EnregistrerCommand(MainWindow mainWindow, DocumentElement root) {
		
		this.mainWindow = mainWindow;
		this.root = root;
	}
	
	@Override
	public void execute() {		
		// Créer un sélecteur de fichier
        JFileChooser jFileChooser = new JFileChooser("f:");
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Lexi Document File", new String[] {"LEXI"}));
        jFileChooser.setDialogTitle("Specify a file to save");   
        
        int userSelection = jFileChooser.showSaveDialog(jFileChooser);
         
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = jFileChooser.getSelectedFile();
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
       
        
        
        
        SwingUtilities.updateComponentTreeUI(jFileChooser);
        // Si l'utilisateur sélectionne le fichier
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
      
        
	}
	
	
}

