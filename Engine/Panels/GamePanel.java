package Engine.Panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Engine.Core.GameMap;
import Engine.Entities.Sprite;
import Engine.Player.Player;
import Engine.Player.PlayerKeyHandler;
import Engine.Player.PlayerMouseHandler;

public class GamePanel extends JPanel implements Runnable {
    private final int HEIGHT = 1080;
    private final int WIDTH = 1920;
    
    private GameMap map;
    private Player player;
    private PlayerKeyHandler player_key;
    private PlayerMouseHandler player_mouse;
    private Robot robot;

    private float pitch;
    private final float sensitivity;
    private int accum_dx;
    private boolean recentering;

    private Sprite test;

    public GamePanel() {
        this.map = new GameMap();
        this.player = new Player(map);
        this.player_key = new PlayerKeyHandler();

        this.sensitivity = 0.001f;
        this.pitch = 0;
        this.accum_dx = 0;
       
        this.recentering = false;

        try {
            this.robot = new Robot();
        } catch (AWTException e) {}

        try {
            File f = new File("Assets/Sprites/Goat.png");
            BufferedImage img = ImageIO.read(f);

            this.test = new Sprite(img, 6, 7);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(player_key);

        addMouseMotionListener(new MouseMotionListener() {
            
            public void mouseMoved(MouseEvent e) {
                if (recentering) {
                    recentering = false;
                    return;
                }

                int center_x = getWidth() / 2;
                int center_y = getHeight() / 2;
                
                int dx = e.getX() - center_x;
                int dy = e.getY() - center_y;

                accum_dx += dx;

                Point p = getLocationOnScreen();

                int screen_center_x = p.x + getWidth() / 2;
                int screen_center_y = p.y + getHeight() / 2;

                recentering = true;
                robot.mouseMove(screen_center_x, screen_center_y);

                /*
                This is to be used if we allow total mouse movement
                pitch += dy * sensitivity;

                if (pitch > 90) pitch = 90;
                if (pitch < -90) pitch = -90;
                */
            }
            public void mouseDragged(MouseEvent e) {}
        });

        new Thread(this).start();
    }

    public void run() {
       final int TARGET_FPS = 120;
       final double ns_per_frame = 1_000_000_000.0 / TARGET_FPS; // nanoseconds per frame

       long last_time = System.nanoTime();
       double delta = 0;

       while (true) {
        long start_time = System.nanoTime();
        
        delta += (start_time - last_time) / ns_per_frame;
        last_time = start_time;

        while (delta >= 1) {
            update(); // Logic 
            delta--;
        }
        
        repaint(); // Drawings
       }
    }

    private void update() {
        if (player_key.forward) player.Move("FORWARD");
        if (player_key.left) player.Move("LEFT");
        if (player_key.right) player.Move("RIGHT");
        if (player_key.backward) player.Move("BACKWARD");
        if (player_key.arrow_left) player.RotateLeft();
        if (player_key.arrow_right) player.RotateRight();

        player.Rotate(-accum_dx * sensitivity);
        accum_dx = 0;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        double[] zBuffer = new double[WIDTH];
        for (int x = 0; x < WIDTH; x++) {
            double camera_x = 2 * x / (double) WIDTH - 1;
            double ray_dir_x = player.dir_x + player.plane_x * camera_x;
            double ray_dir_y = player.dir_y + player.plane_y * camera_x;

            int map_x = (int)player.pos_x;
            int map_y = (int)player.pos_y;

            double delta_dist_x = Math.abs(1 / ray_dir_x);
            double delta_dist_y = Math.abs(1 / ray_dir_y);

            double side_dist_x, side_dist_y;
            int step_x = 0, step_y = 0;
            boolean hit = false;
            int side = 0;

            if (ray_dir_x < 0) {
                step_x = -1;
                side_dist_x = (player.pos_x - map_x) * delta_dist_x;
            } else {
                step_x = 1; 
                side_dist_x = (map_x + 1.0 - player.pos_x) * delta_dist_x;
            }

            if (ray_dir_y < 0) {
                step_y = -1;
                side_dist_y = (player.pos_y - map_y) * delta_dist_y;
            } else {
                step_y = 1;
                side_dist_y = (map_y + 1.0 - player.pos_y) * delta_dist_y;
            }

            while (!hit) {
                if (side_dist_x < side_dist_y) {
                    side_dist_x += delta_dist_x;
                    map_x += step_x;
                    side = 0;
                } else {
                    side_dist_y += delta_dist_y;
                    map_y += step_y;
                    side = 1;
                }

                if (map.get(map_x, map_y) > 0) hit = true; 
            }

            double perp_wall_dist;
            if (side == 0) perp_wall_dist = (map_x - player.pos_x + (1 - step_x) / 2) / ray_dir_x;
            else perp_wall_dist = (map_y - player.pos_y + (1 - step_y) / 2) / ray_dir_y;

            zBuffer[x] = perp_wall_dist; // Allows Sprites to hide behind walls

            int line_height = (int)(HEIGHT / perp_wall_dist);
            int draw_start = -line_height / 2 + HEIGHT / 2;
            int draw_end = line_height / 2 + HEIGHT / 2;

            int wall_type = map.get(map_x, map_y);
            switch(wall_type) {
                case 1:
                    g.setColor(Color.BLUE);
                    break;
                case 2:
                    g.setColor(Color.GREEN);
                    break;
                case 3:
                    g.setColor(Color.RED);
                    break;
                case 4:
                    g.setColor(Color.YELLOW);
                    break;
                default:
                    g.setColor(Color.GRAY);
                    break;
            }

            g.drawLine(x, draw_start, x, draw_end);
        }

    }
}
