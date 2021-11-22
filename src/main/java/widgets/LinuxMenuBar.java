package widgets;

import javax.swing.*;
import java.awt.*;

public class LinuxMenuBar extends MenuBar {
    private final Color BACKGROUND = new Color(211, 206, 205, 255);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(BACKGROUND);
        g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

    }

    @Override
    public void addMenu(Menu m) {
        this.add(m);
    }
}