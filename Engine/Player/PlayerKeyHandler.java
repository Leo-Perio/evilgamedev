package Engine.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerKeyHandler implements KeyListener {
    public boolean forward, backward, left, right, arrow_left, arrow_right;

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_W:
                forward = true;
                break;
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_S:
                backward = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_LEFT:
                arrow_right = true;
                break;
            case KeyEvent.VK_RIGHT:
                arrow_left = true;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(1);
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_W:
                forward = false;
                break;
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_S:
                backward = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_LEFT:
                arrow_right = false;
                break;
            case KeyEvent.VK_RIGHT:
                arrow_left = false;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(1);
                break;
        }
    }

    public void keyTyped(KeyEvent e) {return;}
}
