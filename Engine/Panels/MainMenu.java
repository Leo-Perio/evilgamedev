package Engine.Panels;

import java.awt.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Engine.Core.AudioHandler;
import Engine.Panels.GameManager.GameState;
import Engine.Panels.Utilities.PanelCursor;


public class MainMenu extends JPanel {
    private GameManager manager;
    private AudioHandler audio_handler; 

    public MainMenu(GameManager manager, AudioHandler audio_handler) {
        this.manager = manager;
        this.audio_handler = audio_handler;
        
        setBackground(Color.darkGray);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setCursor(PanelCursor.GetCursor("VISIBLE"));

        JButton start_button = new JButton("PLAY");
        start_button.setPreferredSize(new Dimension(400, 200));
        start_button.setMaximumSize(new Dimension(400, 200));
        start_button.addActionListener(e -> manager.setState(GameState.PLAYING));
        start_button.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton exit_button = new JButton("QUIT");
        exit_button.setPreferredSize(new Dimension(400, 200));
        exit_button.setMaximumSize(new Dimension(400, 200));
        exit_button.addActionListener(e -> System.exit(1));
        exit_button.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon picture = new ImageIcon("src/Assets/Sprites/Goat.PNG");
        Image scaled = picture.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        picture = new ImageIcon(scaled);

        JLabel background = new JLabel(picture);

        add(Box.createVerticalGlue());
        add(start_button);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(exit_button);
        add(Box.createVerticalGlue());

        add(background);

        audio_handler.PlayMusic(0);
    }
}
