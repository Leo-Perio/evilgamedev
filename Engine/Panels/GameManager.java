package Engine.Panels;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameManager {
    private enum GameState {
        MENU,
        PLAYING,
        PAUSED,
        DEAD
    }

    private JFrame frame;
    private MainMenu main_menu;
    private PauseMenu pause_menu;
    private GamePanel game_panel;
    private GameState current_state;

    public GameManager(JFrame frame) {
        this.frame = frame;
        this.main_menu = new MainMenu(this);
        this.pause_menu = new PauseMenu(this);
        this.game_panel = new GamePanel();
        this.current_state = GameState.MENU;

        showPanel(main_menu);
    }

    public void setState(GameState state) {
        current_state = state;
        switch (current_state) {
            case MENU -> showPanel(main_menu);
            case PLAYING -> showPanel(game_panel);
            case PAUSED -> showPanel(pause_menu);
            case DEAD -> System.exit(1000011);
        }
    }

    public void showPanel(JPanel panel) {}
}
