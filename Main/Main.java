package Main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Cursor;
import javax.swing.JFrame;

import Engine.Panels.GamePanel;

public class Main {
    public static void main(String[] args) {
        final String GAME_NAME = "Idk im a placeholder";
        JFrame frame = new JFrame(GAME_NAME);
        GamePanel gp = new GamePanel();

        File f = new File("Assets/Sprites/Goat.png");
        if (!f.exists()) System.exit(67);

        frame.add(gp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true); // Remove title bar

        GraphicsDevice d = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        d.setFullScreenWindow(frame);

        BufferedImage cursor_img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor invis = Toolkit.getDefaultToolkit().createCustomCursor(cursor_img, new Point(0, 0), "blank cursor");

        frame.getContentPane().setCursor(invis);
        frame.pack();
        frame.setVisible(true);
    }
}