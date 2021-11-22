package widgets;

public class WinWidgetFactory implements WidgetFactory{

    @Override
    public ScrollBar createScrollbar() {
        return new WinScrollBar();
    }

    @Override
    public MenuBar createMenuBar() {
        return new WinMenuBar();
    }

    @Override
    public Menu createMenu() {
        return new WinMenu();
    }

    @Override
    public MenuItem createMenuItem() {
        return new WinMenuItem();
    }
}
