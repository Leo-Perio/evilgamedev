package Engine.Panels;

import java.awt.*;
import javax.swing.*;

import Engine.Core.GameManager;
import Engine.Panels.Utilities.PanelCursor;

public class LoadingMenu extends JPanel {
    private GameManager manager;
    private String[] states;

    public LoadingMenu(GameManager manager) {
        this.manager = manager;

        this.states = new String[] {"Loading.", "", "", ""};

        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setCursor(PanelCursor.GetCursor("VISIBLE"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel loading_text = new JLabel("Loading");
        loading_text.setPreferredSize(new Dimension(250, 100));
        loading_text.setAlignmentX(Component.CENTER_ALIGNMENT);
        loading_text.setForeground(Color.WHITE);
        loading_text.setFont(new Font("SansSerif", Font.BOLD, 15));

        add(Box.createVerticalGlue());
        add(loading_text);

        Load(loading_text);
    }

    private void Load(JLabel text) {
        int times = 0;

        while (times < 5) {
            String load = text.getText();
            for (int i = 0; i < 3; i++) {
                load += ".";
                text.setText(load);
            }
            text.setText("Loading");

            times++;
        }
    }
}
