package Engine.Panels;

import java.awt.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Engine.Core.GameManager;
import Engine.Core.Audio.AudioHandler;
import Engine.Core.GameManager.GameState;
import Engine.Core.Utils.CONSTANTS;
import Engine.Panels.Utilities.PanelCursor;

public class MainMenu extends JPanel {
    private GameManager manager;
    private AudioHandler audio_handler; 
    private Image picture;

    private final int BUTTON_WIDTH  = 400;
    private final int BUTTON_HEIGHT = 100;

    public MainMenu(GameManager manager, AudioHandler audio_handler) {
        this.manager = manager;
        this.audio_handler = audio_handler;
        
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setCursor(PanelCursor.GetCursor("VISIBLE"));
        setOpaque(true);

        JLabel game_name = new JLabel("Five Nights at Leo's");
        game_name.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        game_name.setAlignmentX(Component.CENTER_ALIGNMENT);
        game_name.setForeground(Color.WHITE);
        game_name.setFont(new Font("SansSerif", Font.BOLD, 48));

        JLabel build_version = new JLabel(String.format("%s DEV BUILD %d.%d.0", CONSTANTS.ENGINE_VERSION_TYPE, CONSTANTS.ENGINE_VERSION_MAJOR, CONSTANTS.ENGINE_VERSION_MINOR));
        build_version.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        build_version.setAlignmentX(Component.CENTER_ALIGNMENT);
        build_version.setForeground(Color.WHITE);
        build_version.setFont(new Font("SansSerif", Font.BOLD, 12));

        JButton start_button = new JButton("PLAY");
        StyleComponent(start_button);
        start_button.addActionListener(e -> manager.setState(GameState.PLAYING));
        
        JButton level_editor = new JButton("LEVEL EDITOR");
        StyleComponent(level_editor);
        level_editor.addActionListener(e -> manager.setState(GameState.EDITOR));

        JButton exit_button = new JButton("QUIT");
        StyleComponent(exit_button);
        exit_button.addActionListener(e -> System.exit(1));

        ImageIcon temp_picture = new ImageIcon("src/Assets/Sprites/Goat.PNG");
        picture = temp_picture.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);

        add(Box.createVerticalGlue());
        add(game_name);
        add(Box.createRigidArea(new Dimension(0, 100)));
        add(start_button);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(level_editor);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(exit_button);
        add(Box.createVerticalGlue());
        add(build_version);

        audio_handler.PlayMusic(0);
    }

    private void StyleComponent(JButton button) {
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 24));
        button.setBackground(new Color(40, 43, 41));
        button.setBorder(null);
        button.setFocusable(false);

        button.getModel().addChangeListener(e -> {
            if (button.getModel().isPressed()) button.setBackground(new Color(45, 45, 45));
            else if (button.getModel().isRollover()) button.setBackground(new Color(50, 50, 50));
            else button.setBackground(new Color(40, 43, 41));
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(picture, 0, 0, getWidth(), getHeight(), this);
    }
}
