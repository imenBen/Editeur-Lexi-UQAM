package widgets;

public class LinuxWidgetFactory implements WidgetFactory{

    @Override
    public ScrollBar createScrollbar() {
        return new LinuxScrollBar();
    }

    @Override
    public MenuBar createMenuBar() {
        return new LinuxMenuBar();
    }

    @Override
    public Menu createMenu() {
        return new LinuxMenu();
    }

    @Override
    public MenuItem createMenuItem() {
        return new LinuxMenuItem();
    }
}
