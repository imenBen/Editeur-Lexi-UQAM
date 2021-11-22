package main;

import ui.LinuxWindowImp;
import ui.MainWindow;
import ui.WinWindowImp;
import ui.WindowImp;

public class Application
{
    //Implementeur d'environnement
    public static final WindowImp impl = new WinWindowImp();
    public static void main(String[] args)
    {
        //Créer mainWindow et utilisez Implementor comme paramètre
        MainWindow mainWindow = new MainWindow(impl);
        //Exécuter run() de mainWindow
        mainWindow.run();
    }
}
