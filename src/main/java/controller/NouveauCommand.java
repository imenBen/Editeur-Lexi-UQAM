package controller;

import model.CompositeElement;
import model.Paragraph;
import model.Root;
import ui.MainWindow;



public class NouveauCommand implements Command {
	//private Circuit circuit;
	private MainWindow mainWindow;
	private Root root;

	public NouveauCommand(MainWindow mainWindow) {
		
		this.mainWindow = mainWindow;
		root = new Root();
	}
	
	@Override
	public void execute() {		
        ((CompositeElement)root).insert(new Paragraph());
        mainWindow.setEditorContent(root);
	}
}

