package ui;


import controller.Command;
import controller.EditorViewerHandler;
import controller.EnregistrerCommand;
import controller.MainWindowHandler;
import controller.NouveauCommand;
import controller.OuvrirCommand;
import formatting.Formatting;
import formatting.FullFormatting;
//import formatting.FullFormatting;
//import formatting.Formatting;
import main.Application;
import model.DocumentElement;
import model.Root;
import utils.HTMLtoDocumentElementParser;
import visitor.CountCharacterVisitor;
import visitor.DrawDocumentElementVisitor;
//import visitor.CountCharacterVisitor;
//import visitor.DrawGlyphVisitor;
import widgets.Menu;
import widgets.MenuBar;
import widgets.MenuItem;
import widgets.*;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

public class MainWindow extends Window {
    // Frame
    private final JFrame frame;
    private final int width = 800;
    private final int height = 600;
    private final DialogWindow dialogWindow;

    // Les controlleurs
    private final MainWindowHandler mainWindowHandler = new MainWindowHandler(this);
    private final EditorViewerHandler editorViewerHandler = new EditorViewerHandler(this);
    private final WidgetFactory widgetFactory;

    // Intialisation de la zone d'édition
    private final JTextPane editorViewer = new JTextPane();
    private final JPanel statusBar = new JPanel(new BorderLayout());
    private final JLabel statusLabel = new JLabel();

    // formattage
    private Formatting formatting = new FullFormatting();

    // la racine du texte
    private DocumentElement root = new Root();

    // Déclaration des objets de la barre de menu
    private Menu fileMenu;
    private MenuItem newFileMenu;
    private MenuItem openFileMenu;
    private MenuItem saveFileMenu;
    private Menu editMenu;
    private MenuItem cutEditMenu;
    private MenuItem copyEditMenu;
    private MenuItem pasteEditMenu;
    private MenuItem insertImgEditMenu;
    private Menu fontStyleMenu;
    private MenuItem boldFontStyleMenu;
    private MenuItem italicFontStyleMenu;
    private MenuItem underlineFontStyleMenu;
    private Menu colorMenu;
    private MenuItem redBackgroundColorMenu;
    private MenuItem greenBackgroundColorMenu;
    private MenuItem blueBackgroundColorMenu;
    private MenuItem blackBackgroundColorMenu;
    private MenuItem noneBackgroundColorMenu;
    private MenuItem redForegroundColorMenu;
    private MenuItem greenForegroundColorMenu;
    private MenuItem blueForegroundColorMenu;
    private MenuItem whiteForegroundColorMenu;
    private MenuItem blackForegroundColorMenu;
    private Menu formatMenu;
    private MenuItem fullFormatMenu;
    private MenuItem plaintextFormatMenu;
    private Menu helpMenu;
    private MenuItem aboutHelpMenu;

    //================================================== GUI ==================================================
    public MainWindow(WindowImp impl) {
        super(impl);                                //intialiser l'implémentation
        widgetFactory = super.getWidgetFactory();   //obtenir le widgetfactory selon le type de l'implémentation

        //-------------------- JFrame --------------------
        // JFrame
        frame = super.drawFrame();                  //construire le cadre selon les différents types d'implémentation
        frame.setTitle("Lexi UQAM Document Editor");
        frame.setBounds(100, 100, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // MenuBar
        frame.setJMenuBar(createMenuBar());

        // Layout
        frame.setLayout(new BorderLayout());
        frame.add(createScrollPane(editorViewer), BorderLayout.CENTER);
        frame.add(statusBar, BorderLayout.SOUTH);

        // Dialog Window
        dialogWindow = new DialogWindow(impl);

        //-------------------- Barre d'état --------------------
        statusLabel.setHorizontalAlignment(JLabel.RIGHT);
        statusLabel.setFont(super.getSystemFont());
        statusBar.setPreferredSize(new Dimension(statusBar.getWidth(), 30));
        statusBar.add(statusLabel);
        //-------------------- Zone de l'éditeur --------------------
        editorViewer.setContentType("text/html");
        editorViewer.setText("<html><head></head><body><p></p></body></html>");
        editorViewer.getDocument().addDocumentListener(editorViewerHandler);
        editorViewer.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) { insertBlankText(); }
        });

    }

    // 執行
    public void run(){
        //affichage du cadre de l'éditeur
        frame.pack();
        frame.setSize(width, height);
        frame.setVisible(true);
        loadFromEditorViewer();
    }


    //================================================== Contenu du editor viewer ==================================================
    // Insérer le contenu du texte dans le editorviewer
    public void drawIntoEditorViewer(){
        if(editorViewerHandler.getActive()) {
            Runnable doAssist = () -> {
                // desactiver le listener
                editorViewerHandler.setActive(false);
                // enregistrer la position du curseur
                int caretPosition = editorViewer.getCaretPosition();
                // instancier les Visiteur
                DrawDocumentElementVisitor drawGlyphVisitor = new DrawDocumentElementVisitor(formatting);
                CountCharacterVisitor countCharacterVisitor = new CountCharacterVisitor();
                System.out.println("rag name "+root.getTagname());
                root.accept(drawGlyphVisitor);      //traiter le texte
                root.accept(countCharacterVisitor); //Utilisez countCharacterVisitor pour calculer le nombre de mots
                System.out.println(root.getChildSize());
                // ecrire sur  editorViewer
                editorViewer.setText("<html><head></head>" + drawGlyphVisitor.getParseString() + "</html>");
                editorViewer.setCaretPosition(Math.min(caretPosition, editorViewer.getDocument().getLength()-1));
                // Afficher l'état actuel
                statusLabel.setText( countCharacterVisitor.getParagraph() + " Paragraphes　" + countCharacterVisitor.getCharacter() + " Caractères　|　Type de formattage：" + formatting.getTYPE() + "　　");
                // activer le listener
                editorViewerHandler.setActive(true);
            };
            SwingUtilities.invokeLater(doAssist);
        }
    }

    // Lire le texte saisi par l'utilisateur
    public void loadFromEditorViewer(){ setEditorContent(new HTMLtoDocumentElementParser().parse(editorViewer.getText())); }


    //================================================== GETTER SETTER ==================================================
    // Définir le contenu sur editorViewer
    public void setEditorContent(DocumentElement root){
        this.root = root;
        drawIntoEditorViewer();
    }
    // Définir le type du formattage
    public void setFormatting(Formatting formatting){
        // définir le formattage 
        this.formatting = formatting;
        drawIntoEditorViewer();
        // Réglage des boutons de menu
        boolean enabled = this.formatting.getTYPE().equals("Full");
        insertImgEditMenu.setEnabled(enabled);
        fontStyleMenu.setEnabled(enabled);
        colorMenu.setEnabled(enabled);
    }
    // get le formattage
    public Formatting getFormatting(){ return this.formatting; }
    // obtenir tout la racine du document 
    public DocumentElement getRoot() { return this.root; }
    public void setRoot() {  this.root= new HTMLtoDocumentElementParser().parse(editorViewer.getText()); }
    // afficher Dialog Window
    public void showDialog(String message, String title) {
        dialogWindow.showDialog(message, title);
    }


    //================================================= ACTION Inserer==================================================
    // Insérer une image
    public void insertEditorImage(AttributeSet outerAttr){
        try { editorViewer.getDocument().insertString(editorViewer.getCaretPosition()," ", outerAttr); }
        catch (BadLocationException e) { e.printStackTrace(); }
    }
    // Insérer un texte vide
    public void insertBlankText(){
        try {
            editorViewer.getDocument().insertString(editorViewer.getCaretPosition(),"&nbsp;", null);
            editorViewer.setCaretPosition(Math.max(0, editorViewer.getCaretPosition()-6));
        }
        catch (BadLocationException e) { e.printStackTrace(); }
    }


    // ==================================================Créer le  ScrollPane==================================================
    public JScrollPane createScrollPane(Component component){
        JScrollPane scrollPane = new JScrollPane();
        // Créer et définir le ScrollBar
        scrollPane.setVerticalScrollBar(widgetFactory.createScrollbar().setOriented(JScrollBar.VERTICAL));
        scrollPane.setHorizontalScrollBar(widgetFactory.createScrollbar().setOriented(JScrollBar.HORIZONTAL));
        // Décorer ScrollPane
        scrollPane.setViewportView(component);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    // ==================================================Créer une barre de menu==================================================
    public MenuBar createMenuBar() {
        MenuBar bar = widgetFactory.createMenuBar();

        // --------------------------------------------------Option de menu Créer [Fichier]--------------------------------------------------
        fileMenu = widgetFactory.createMenu();
        fileMenu.setDescription("Fichier");
        // Créer une sous-option de menu "Fichier"
        // --------------------
        newFileMenu =  widgetFactory.createMenuItem();
        newFileMenu.setDescription("Nouveau ");
        newFileMenu.setActionCommand("new");
        
        Command nouveau = new NouveauCommand(this);

        newFileMenu.addActionListener(e -> nouveau.execute());
        // --------------------
        openFileMenu = widgetFactory.createMenuItem();
        openFileMenu.setDescription("Ouvrir ");
        openFileMenu.setActionCommand("open");
        Command ouvrir = new OuvrirCommand(this, getRoot() );

        openFileMenu.addActionListener(e -> ouvrir.execute());
        // -------------------- d
        saveFileMenu = widgetFactory.createMenuItem();
        saveFileMenu.setDescription("Enregistrer");
        saveFileMenu.setActionCommand("save");
        Command enregistrer = new EnregistrerCommand(this,  getRoot());

        saveFileMenu.addActionListener(e -> enregistrer.execute());
        // Des listeners pour les options
        //newFileMenu.addActionListener(mainWindowHandler);
        //openFileMenu.addActionListener(mainWindowHandler);
        //saveFileMenu.addActionListener(mainWindowHandler);

        // Item ajoutés au menu 
        fileMenu.addMenuItem(newFileMenu);
        fileMenu.addMenuItem(openFileMenu);
        fileMenu.addMenuItem(saveFileMenu);

        // --------------------------------------------------Créer des options de menu [modifier]--------------------------------------------------
        editMenu = widgetFactory.createMenu();
        editMenu.setDescription("Éditer");
        // Créer une sous-option de menu "Éditer"
        // --------------------
        cutEditMenu = widgetFactory.createMenuItem();
        cutEditMenu.setDescription("Couper (ctrl+x)");
        cutEditMenu.setActionCommand("cut");
        // --------------------
        copyEditMenu = widgetFactory.createMenuItem();
        copyEditMenu.setDescription("Copier (ctrl+c)");
        copyEditMenu.setActionCommand("copy");
        // --------------------
        pasteEditMenu = widgetFactory.createMenuItem();
        pasteEditMenu.setDescription("Coller (ctrl+v)");
        pasteEditMenu.setActionCommand("paste");
        // --------------------
        insertImgEditMenu = widgetFactory.createMenuItem();
        insertImgEditMenu.setDescription("Insérer Image");
        insertImgEditMenu.setActionCommand("insertImg");

        // Des listeners pour les options
        cutEditMenu.addActionListener(mainWindowHandler);
        copyEditMenu.addActionListener(mainWindowHandler);
        pasteEditMenu.addActionListener(mainWindowHandler);
        insertImgEditMenu.addActionListener(mainWindowHandler);

        // Item ajoutés au menu 
        editMenu.addMenuItem(cutEditMenu);
        editMenu.addMenuItem(copyEditMenu);
        editMenu.addMenuItem(pasteEditMenu);
        editMenu.addMenuItem(insertImgEditMenu);

        // --------------------------------------------------Créer l'option de menu [Style]--------------------------------------------------
        fontStyleMenu = widgetFactory.createMenu();
        fontStyleMenu.setDescription("Style");
        // Créer une sous-option de menu "Style"
        // --------------------
        boldFontStyleMenu = widgetFactory.createMenuItem();
        boldFontStyleMenu.setDescription("Gras");
        boldFontStyleMenu.setActionCommand("Bold");
        boldFontStyleMenu.setFont(new Font(boldFontStyleMenu.getFont().getFontName(), Font.BOLD, 12));
        // --------------------
        italicFontStyleMenu = widgetFactory.createMenuItem();
        italicFontStyleMenu.setDescription("Italique");
        italicFontStyleMenu.setActionCommand("Italic");
        italicFontStyleMenu.setFont(new Font(italicFontStyleMenu.getFont().getFontName(), Font.ITALIC, 12));
        // --------------------
        underlineFontStyleMenu = widgetFactory.createMenuItem();
        underlineFontStyleMenu.setDescription("Souligné");
        underlineFontStyleMenu.setActionCommand("Underline");
        Map attributes = underlineFontStyleMenu.getFont().getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        underlineFontStyleMenu.setFont(underlineFontStyleMenu.getFont().deriveFont(attributes));

        // Des listeners pour les options
        boldFontStyleMenu.addActionListener(mainWindowHandler);
        italicFontStyleMenu.addActionListener(mainWindowHandler);
        underlineFontStyleMenu.addActionListener(mainWindowHandler);

        // Item ajoutés au menu 
        fontStyleMenu.addMenuItem(boldFontStyleMenu);
        fontStyleMenu.addMenuItem(italicFontStyleMenu);
        fontStyleMenu.addMenuItem(underlineFontStyleMenu);

        // --------------------------------------------------Créer l'option de menu [Couleurs]--------------------------------------------------
        colorMenu = widgetFactory.createMenu();
        colorMenu.setDescription("Couleurs");
        // Créer une sous-option de menu "Style"
        // --------------------
        redBackgroundColorMenu = widgetFactory.createMenuItem();
        redBackgroundColorMenu.setDescription("Arrière Plan Rouge");
        redBackgroundColorMenu.setActionCommand("BG-Red");
        redBackgroundColorMenu.setForeground(Color.WHITE);
        redBackgroundColorMenu.setBackground(Color.RED);
        // --------------------
        greenBackgroundColorMenu = widgetFactory.createMenuItem();
        greenBackgroundColorMenu.setDescription("Arrière Plan Vert");
        greenBackgroundColorMenu.setActionCommand("BG-Green");
        greenBackgroundColorMenu.setForeground(Color.WHITE);
        greenBackgroundColorMenu.setBackground(Color.GREEN);
        // --------------------
        blueBackgroundColorMenu = widgetFactory.createMenuItem();
        blueBackgroundColorMenu.setDescription("Arrière Plan Bleu");
        blueBackgroundColorMenu.setActionCommand("BG-Blue");
        blueBackgroundColorMenu.setForeground(Color.WHITE);
        blueBackgroundColorMenu.setBackground(Color.BLUE);
        // --------------------
        blackBackgroundColorMenu = widgetFactory.createMenuItem();
        blackBackgroundColorMenu.setDescription("Arrière Plan Noir");
        blackBackgroundColorMenu.setActionCommand("BG-Black");
        blackBackgroundColorMenu.setForeground(Color.WHITE);
        blackBackgroundColorMenu.setBackground(Color.BLACK);
        // --------------------
        noneBackgroundColorMenu = widgetFactory.createMenuItem();
        noneBackgroundColorMenu.setDescription("Arrière Plan sans Couleur");
        noneBackgroundColorMenu.setActionCommand("BG-None");
        // --------------------
        redForegroundColorMenu = widgetFactory.createMenuItem();
        redForegroundColorMenu.setDescription("Font Rouge");
        redForegroundColorMenu.setActionCommand("FG-Red");
        redForegroundColorMenu.setForeground(Color.RED);
        // --------------------
        greenForegroundColorMenu = widgetFactory.createMenuItem();
        greenForegroundColorMenu.setDescription("Font Vert");
        greenForegroundColorMenu.setActionCommand("FG-Green");
        greenForegroundColorMenu.setForeground(Color.GREEN);
        // --------------------
        blueForegroundColorMenu = widgetFactory.createMenuItem();
        blueForegroundColorMenu.setDescription("Font Bleu");
        blueForegroundColorMenu.setActionCommand("FG-Blue");
        blueForegroundColorMenu.setForeground(Color.BLUE);
        // --------------------
        whiteForegroundColorMenu = widgetFactory.createMenuItem();
        whiteForegroundColorMenu.setDescription("Font Blanc");
        whiteForegroundColorMenu.setActionCommand("FG-White");
        // --------------------
        blackForegroundColorMenu = widgetFactory.createMenuItem();
        blackForegroundColorMenu.setDescription("Font Noire");
        blackForegroundColorMenu.setActionCommand("FG-Black");

        // Des listeners pour les options
        redBackgroundColorMenu.addActionListener(mainWindowHandler);
        greenBackgroundColorMenu.addActionListener(mainWindowHandler);
        blueBackgroundColorMenu.addActionListener(mainWindowHandler);
        blackBackgroundColorMenu.addActionListener(mainWindowHandler);
        noneBackgroundColorMenu.addActionListener(mainWindowHandler);
        redForegroundColorMenu.addActionListener(mainWindowHandler);
        greenForegroundColorMenu.addActionListener(mainWindowHandler);
        blueForegroundColorMenu.addActionListener(mainWindowHandler);
        whiteForegroundColorMenu.addActionListener(mainWindowHandler);
        blackForegroundColorMenu.addActionListener(mainWindowHandler);

        // Item ajoutés au menu
        colorMenu.addMenuItem(redBackgroundColorMenu);
        colorMenu.addMenuItem(greenBackgroundColorMenu);
        colorMenu.addMenuItem(blueBackgroundColorMenu);
        colorMenu.addMenuItem(blackBackgroundColorMenu);
        colorMenu.addMenuItem(noneBackgroundColorMenu);
        colorMenu.addMenuItem(redForegroundColorMenu);
        colorMenu.addMenuItem(greenForegroundColorMenu);
        colorMenu.addMenuItem(blueForegroundColorMenu);
        colorMenu.addMenuItem(whiteForegroundColorMenu);
        colorMenu.addMenuItem(blackForegroundColorMenu);

        // --------------------------------------------------Créer l'option de menu [Format]-------------------------------------------------
        formatMenu = widgetFactory.createMenu();
        formatMenu.setDescription("Format");
        // Créer une sous-option de menu "Format"
        // --------------------
        fullFormatMenu = widgetFactory.createMenuItem();
        fullFormatMenu.setDescription("Avancé");
        fullFormatMenu.setActionCommand("full");
        // --------------------
        plaintextFormatMenu = widgetFactory.createMenuItem();
        plaintextFormatMenu.setDescription("Basique");
        plaintextFormatMenu.setActionCommand("plaintext");

        // Des listeners pour les options
        fullFormatMenu.addActionListener(mainWindowHandler);
        plaintextFormatMenu.addActionListener(mainWindowHandler);

        // Item ajoutés au menu
        formatMenu.addMenuItem(fullFormatMenu);
        formatMenu.addMenuItem(plaintextFormatMenu);

        // --------------------------------------------------Créer l'option de menu [Aide]--------------------------------------------------
        helpMenu = widgetFactory.createMenu();
        helpMenu.setDescription("Aide");
        // Créer une sous-option de menu "Aide"
        // --------------------
        aboutHelpMenu = widgetFactory.createMenuItem();
        aboutHelpMenu.setDescription("À propos");
        aboutHelpMenu.setActionCommand("about");

        // Des listeners pour les options
        aboutHelpMenu.addActionListener(mainWindowHandler);

        // Item ajoutés au menu
        helpMenu.addMenuItem(aboutHelpMenu);

        // --------------------------------------------------items ajouté au menu principal--------------------------------------------------
        bar.addMenu(fileMenu);
        bar.addMenu(editMenu);
        bar.addMenu(fontStyleMenu);
        bar.addMenu(colorMenu);
        bar.addMenu(formatMenu);
        bar.addMenu(helpMenu);
        return bar;
    }
}
