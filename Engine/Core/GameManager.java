package Engine.Core;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import Engine.Core.Audio.AudioHandler;
import Engine.Panels.EditorPanel;
import Engine.Panels.GamePanel;
import Engine.Panels.LoadingMenu;
import Engine.Panels.MainMenu;
import Engine.Panels.PauseMenu;
import Engine.Panels.EditorPanels.EditorManager;

public class GameManager {
    public enum GameState {
        // Global States
        LOADING,
        MENU,
        // Gameplay states
        PLAYING,
        PAUSED,
        DEAD,
        CUTSCENE_FULL, // When a cutscene disables all player controls
        CUTSCENE_PARTIAL, // When a cutscene allows skipping and or maybe chase sequences
        // Editor States
        EDITOR
    }

    private JFrame frame;

    private MainMenu main_menu;
    private PauseMenu pause_menu;
    private LoadingMenu loading_menu;
    private GamePanel game_panel;
    private EditorPanel level_edit_panel;

    private GameState current_state;
    private AudioHandler audio_handler;
    private EditorManager level_manager;

    public GameManager(JFrame frame) {
        this.frame = frame;
        this.audio_handler = new AudioHandler();
        this.level_manager = new EditorManager();

        this.main_menu = new MainMenu(this, audio_handler);
        this.pause_menu = new PauseMenu(this);
        this.loading_menu = new LoadingMenu(this);
        this.game_panel = new GamePanel(audio_handler, this);
        this.level_edit_panel = new EditorPanel(this, level_manager);

        this.current_state = GameState.MENU;

        //showPanel(loading_menu);
        showPanel(main_menu);
    }

    public void setState(GameState state) {
        current_state = state;
        switch (current_state) {
            case MENU: 
                showPanel(main_menu); 
                break;
            case PLAYING: 
                showPanel(game_panel); 
                audio_handler.StopMusic(); 
                break;
            case PAUSED: 
                showPanel(pause_menu); break;
            case DEAD: 
                System.exit(1000011); 
                break;
            case CUTSCENE_FULL: 
                System.out.println("Controls disabled"); 
                break;
            case CUTSCENE_PARTIAL: 
                System.out.println("Controls disabled"); 
                break;
            case LOADING:
                showPanel(loading_menu);
                break;
            case EDITOR:
                showPanel(level_edit_panel);
                audio_handler.StopMusic();
                break;
        }
    }

    public void showPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
        panel.requestFocusInWindow();
    }

    public void showPanel(JLayeredPane panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
        panel.requestFocusInWindow();
    }
}
