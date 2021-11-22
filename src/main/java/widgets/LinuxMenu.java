package widgets;

import java.awt.*;

public class LinuxMenu extends Menu {
    private final Color FOREGROUND = new Color(43, 41, 42, 255);
    private final Color BACKGROUND = new Color(211, 206, 205, 255);
    private final Color SELECTED_BACKGROUND = new Color(0, 188, 212, 255);

    public LinuxMenu(){
        this.setOpaque(true);
        this.setBackground(BACKGROUND);
        this.setForeground(FOREGROUND);

        this.addChangeListener(e -> {
            if (e.getSource() instanceof Menu) {
                Menu item = (Menu) e.getSource();
                if (item.isSelected() || item.isArmed()) {
                    item.setBackground(SELECTED_BACKGROUND);
                } else {
                    item.setBackground(BACKGROUND);
                }
            }
        });
    }

    @Override
    public void setDescription(String description) {
        super.setText(description);
    }

    @Override
    public void addMenuItem(MenuItem m) {
        this.add(m);
    }
}
