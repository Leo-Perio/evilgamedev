package Main;

import javax.swing.JFrame;

import Engine.Core.Utils.CONSTANTS;
import Engine.Panels.GameManager;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame(CONSTANTS.GAME_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(CONSTANTS.WIDTH, CONSTANTS.HEIGHT);
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        new GameManager(frame);
        frame.setVisible(true);
    }
}