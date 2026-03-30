package Engine.Panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import Engine.Core.GameManager;

public class PauseMenu extends JLayeredPane {
    private GameManager manager;

    public PauseMenu(GameManager manager) {
        this.manager = manager;

        JPanel overlay_panel = new JPanel();
        overlay_panel.setBackground(new Color(0, 0, 0, 0));
        overlay_panel.setOpaque(true);

        JButton quit_button = new JButton("Quit");
        quit_button.setPreferredSize(new Dimension(200, 400));
        quit_button.addActionListener(e -> {
            System.exit(1);
        });


        add(quit_button);
        add(overlay_panel, JLayeredPane.PALETTE_LAYER);
    }
}
