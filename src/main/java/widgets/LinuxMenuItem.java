package widgets;

import java.awt.*;

public class LinuxMenuItem extends MenuItem{
    private final Color FOREGROUND = new Color(43, 41, 42, 255);
    private final Color BACKGROUND = new Color(211, 206, 205, 255);

    public LinuxMenuItem(){
        this.setOpaque(true);
        this.setBackground(BACKGROUND);
        this.setForeground(FOREGROUND);
    }

    public void setDescription(String description) {
        this.setText(description);
    }
}