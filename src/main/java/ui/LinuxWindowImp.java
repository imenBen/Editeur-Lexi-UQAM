package ui;

import widgets.LinuxWidgetFactory;
import widgets.WidgetFactory;
import widgets.WidgetFactoryProducer;
import widgets.WinWidgetFactory;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;

public class LinuxWindowImp implements WindowImp{
    private final String ENVIRONMENT = "Linux";
    private Font systemFont = new Font("Times", Font.PLAIN, 14);

    @Override
    public String getEnvironment() {
        return ENVIRONMENT;
    }

    @Override
    public JFrame drawFrame() {
        DefaultMetalTheme linux = new DefaultMetalTheme() {
            private final Color INACTIVE_BACKGROUND = new Color(60, 59, 57, 255);
            private final Color INACTIVE_FOREGROUND = new Color(159, 148, 146, 255);
            private final Color BACKGROUND = new Color(43, 41, 42, 255);
            private final Color FOREGROUND = new Color(244, 247, 230, 255);

            //inactive title color
            public ColorUIResource getWindowTitleInactiveBackground() {
                return new ColorUIResource(INACTIVE_BACKGROUND);
            }
            public ColorUIResource getWindowTitleInactiveForeground() {
                return new ColorUIResource(INACTIVE_FOREGROUND);
            }
            //start inActiveBumps
            public ColorUIResource getControlHighlight () {
                return new ColorUIResource(INACTIVE_BACKGROUND);
            }

            public ColorUIResource getControlDarkShadow () {
                return new ColorUIResource(INACTIVE_BACKGROUND);
            }

            public ColorUIResource getControl () {
                return new ColorUIResource(INACTIVE_FOREGROUND);
            }
            //end inActiveBumps

            //active title color
            public ColorUIResource getWindowTitleBackground() {
                return new ColorUIResource(BACKGROUND);
            }
            public ColorUIResource getWindowTitleForeground() {
                return new ColorUIResource(FOREGROUND);
            }
            //start ActiveBumps
            public ColorUIResource getPrimaryControlHighlight() {
                return new ColorUIResource(BACKGROUND);
            }
            public ColorUIResource getPrimaryControlDarkShadow() {
                return new ColorUIResource(BACKGROUND);
            }

            public ColorUIResource getPrimaryControl(){
                return new ColorUIResource(FOREGROUND);
            }

            //end ActiveBumps

            //font
            public FontUIResource getWindowTitleFont(){
                return new FontUIResource(systemFont);
            }
            public FontUIResource getControlTextFont(){
                return new FontUIResource(systemFont);
            }
            public FontUIResource getMenuTextFont(){
                return new FontUIResource(systemFont);
            }
            public FontUIResource getSubTextFont(){
                return new FontUIResource(systemFont);
            }
            public FontUIResource getSystemTextFont(){
                return new FontUIResource(systemFont);
            }
            public FontUIResource getUserTextFont(){
                return new FontUIResource(systemFont);
            }
        };

        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        Toolkit.getDefaultToolkit().setDynamicLayout(true);

        MetalLookAndFeel.setCurrentTheme(linux);

        // 設定JFrame
        try { UIManager.setLookAndFeel(new MetalLookAndFeel()); }
        catch(Exception ex) {}

        setUIFont();

        return new JFrame();
    }

    @Override
    public void setSystemFont(Font systemFont){
        this.systemFont = systemFont;
        setUIFont();
    }

    @Override
    public Font getSystemFont(){
        return this.systemFont;
    }

    @Override
    public WidgetFactory getWidgetFactory() {
        return WidgetFactoryProducer.getInstance().getFactory(ENVIRONMENT);
    }

    public void setUIFont() {
        UIManager.put("CheckBoxMenuItem.acceleratorFont", systemFont);
        UIManager.put("Button.font", systemFont);
        UIManager.put("ToggleButton.font", systemFont);
        UIManager.put("RadioButton.font", systemFont);
        UIManager.put("CheckBox.font", systemFont);
        UIManager.put("ColorChooser.font", systemFont);
        UIManager.put("ComboBox.font", systemFont);
        UIManager.put("Label.font", systemFont);
        UIManager.put("List.font", systemFont);
        UIManager.put("MenuBar.font", systemFont);
        UIManager.put("Menu.acceleratorFont", systemFont);
        UIManager.put("RadioButtonMenuItem.acceleratorFont", systemFont);
        UIManager.put("MenuItem.acceleratorFont", systemFont);
        UIManager.put("MenuItem.font", systemFont);
        UIManager.put("RadioButtonMenuItem.font", systemFont);
        UIManager.put("CheckBoxMenuItem.font", systemFont);
        UIManager.put("OptionPane.buttonFont", systemFont);
        UIManager.put("OptionPane.messageFont", systemFont);
        UIManager.put("Menu.font", systemFont);
        UIManager.put("PopupMenu.font", systemFont);
        UIManager.put("OptionPane.font", systemFont);
        UIManager.put("Panel.font", systemFont);
        UIManager.put("ProgressBar.font", systemFont);
        UIManager.put("ScrollPane.font", systemFont);
        UIManager.put("Viewport.font", systemFont);
        UIManager.put("TabbedPane.font", systemFont);
        UIManager.put("Slider.font", systemFont);
        UIManager.put("Table.font", systemFont);
        UIManager.put("TableHeader.font", systemFont);
        UIManager.put("TextField.font", systemFont);
        UIManager.put("Spinner.font", systemFont);
        UIManager.put("PasswordField.font", systemFont);
        UIManager.put("TextArea.font", systemFont);
        UIManager.put("TextPane.font", systemFont);
        UIManager.put("EditorPane.font", systemFont);
        UIManager.put("TabbedPane.smallFont", systemFont);
        UIManager.put("TitledBorder.font", systemFont);
        UIManager.put("ToolBar.font", systemFont);
        UIManager.put("ToolTip.font", systemFont);
        UIManager.put("Tree.font", systemFont);
        UIManager.put("FormattedTextField.font", systemFont);
        UIManager.put("IconButton.font", systemFont);
        UIManager.put("InternalFrame.optionDialogTitleFont", systemFont);
        UIManager.put("InternalFrame.paletteTitleFont", systemFont);
        UIManager.put("InternalFrame.titleFont", systemFont);
    }
}
