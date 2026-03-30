package Engine.Panels.EditorPanels;

import java.awt.*;

import javax.swing.JPanel;

import Engine.Panels.Utilities.COLORS;

public class SidebarPanel extends JPanel{
    public SidebarPanel() {
        setBackground(COLORS.SIDEBAR_BACK);
        setPreferredSize(new Dimension(350, 0));
    }
}


