package Engine.Panels;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Engine.Core.AudioHandler;

public class GameManager {
    public enum GameState {
        MENU,
        PLAYING,
        PAUSED,
        DEAD,
        CUTSCENE_FULL, // When a cutscene disables all player controls
        CUTSCENE_PARTIAL // When a cutscene allows skipping and or maybe chase sequences
    }

    private JFrame frame;
    private MainMenu main_menu;
    private PauseMenu pause_menu;
    private GamePanel game_panel;
    private GameState current_state;
    private AudioHandler audio_handler;

    public GameManager(JFrame frame) {
        this.frame = frame;
        this.audio_handler = new AudioHandler();

        this.main_menu = new MainMenu(this, audio_handler);
        this.pause_menu = new PauseMenu(this);
        this.game_panel = new GamePanel(audio_handler);
        this.current_state = GameState.MENU;

        showPanel(main_menu);
    }

    public void setState(GameState state) {
        current_state = state;
        switch (current_state) {
            case MENU: showPanel(main_menu); break;
            case PLAYING: showPanel(game_panel); audio_handler.StopMusic(); break;
            case PAUSED: showPanel(pause_menu); break;
            case DEAD: System.exit(1000011); break;
            case CUTSCENE_FULL: System.out.println("Controls disabled"); break;
            case CUTSCENE_PARTIAL: System.out.println("Controls disabled"); break;
        }
    }

    public void showPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
        panel.requestFocusInWindow();
    }
}
