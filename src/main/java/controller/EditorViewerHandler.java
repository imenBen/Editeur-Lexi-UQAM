package controller;

import ui.MainWindow;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EditorViewerHandler implements DocumentListener {
    private boolean active = true;
    private MainWindow mainWindow;

    public EditorViewerHandler(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }
    @Override
    public void insertUpdate(DocumentEvent e) { if(active) mainWindow.loadFromEditorViewer(); }
    @Override
    public void removeUpdate(DocumentEvent e) { if(active) mainWindow.loadFromEditorViewer(); }
    @Override
    public void changedUpdate(DocumentEvent e) { if(active) mainWindow.loadFromEditorViewer(); }

    public void setActive(boolean active) { this.active = active; }
    public boolean getActive() { return this.active; }
}
