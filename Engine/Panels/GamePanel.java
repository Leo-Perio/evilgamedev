package Engine.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import Engine.Core.GameManager;
import Engine.Core.Audio.AudioHandler;
import Engine.Core.Map.GameMap;
import Engine.Core.Map.Sector;
import Engine.Core.Map.Objects.Wall;
import Engine.Core.Map.Sector.DIRECTIONS;
import Engine.Core.Textures.TextureManager;
import Engine.Core.Utils.CONSTANTS;
import Engine.Panels.Utilities.PanelCursor;
import Engine.Player.*;

public class GamePanel extends JPanel implements Runnable {
    private int HEIGHT = CONSTANTS.HEIGHT;
    private int WIDTH = CONSTANTS.WIDTH;

    private int frames;
    long last_fps_time;
    
    private GameMap map;
    private GameManager manager;
    private Player player;
    private PlayerKeyHandler player_key;
    private PlayerMouseHandler player_mouse;
    private AudioHandler audio_handler;
    private TextureManager texture_manager;
    private Robot robot;

    private float pitch;
    private final float sensitivity;
    private int accum_dx;
    private boolean recentering;

    public GamePanel(AudioHandler audio_handler, GameManager manager) {
        this.texture_manager = new TextureManager();
        this.map = new GameMap(texture_manager);
        this.player = new Player(map);
        this.manager = manager;
        this.player_key = new PlayerKeyHandler(manager);
        this.audio_handler = audio_handler;

        this.sensitivity = 0.001f;
        this.pitch = 0;
        this.accum_dx = 0;
       
        this.recentering = false;

        try {
            this.robot = new Robot();
        } catch (AWTException e) {}

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setCursor(PanelCursor.GetCursor("INVISIBLE"));
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

            public void mouseDragged(MouseEvent e) {
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
        });

        new Thread(this).start();
    }

    public void run() {
       final int TARGET_FPS = CONSTANTS.FPS;
       final double ns_per_frame = 1_000_000_000.0 / TARGET_FPS; // nanoseconds per frame

       frames = 0;
       last_fps_time = System.currentTimeMillis();

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

        long frame_time = System.nanoTime() - start_time;
        long sleep_time = (long)(ns_per_frame - frame_time);

        if (sleep_time > 0) {
            try {Thread.sleep(sleep_time / 1_000_000);}
            catch (InterruptedException e) {}
        }
       }
    }

    private void update() {
        input();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        double fov = 2 * Math.atan(Math.sqrt(player.plane_x * player.plane_x + player.plane_y * player.plane_y));

        draw_sky(g, fov);
        //draw_floor_and_ceiling(g);
        raycast(g);
        
        frames++;
        
        if(System.currentTimeMillis() - last_fps_time >= 1000) {
            System.out.printf("FPS: %d%n", frames);
            frames = 0;
            last_fps_time += 1000;
        }
    }

    private void input() {
        player.Rotate(-accum_dx * sensitivity);
        accum_dx = 0;

        if (player_key.right && player_key.left || player_key.forward && player_key.backward) return;
        if (player_key.forward) player.Move("FORWARD");
        if (player_key.left) player.Move("LEFT");
        if (player_key.right) player.Move("RIGHT");
        if (player_key.backward) player.Move("BACKWARD");
        if (player_key.arrow_left) player.RotateLeft();
        if (player_key.arrow_right) player.RotateRight();
    }

    private void draw_sky(Graphics g, double fov) {
        BufferedImage sky_texture = texture_manager.GetTexture("skybox_placeholder").GetImage();

        for (int x = 0; x < WIDTH; x++) {
            double cam_x = 2 * x / (double) WIDTH - 1;
            
            double ray_angle = player.yaw + cam_x * (fov / 2);

            int texture_x = (int)((ray_angle / (2 * Math.PI)) * sky_texture.getWidth());
            texture_x = (texture_x % sky_texture.getWidth() + sky_texture.getWidth() % sky_texture.getWidth());
            g.drawImage(sky_texture, x, 0, x+1, HEIGHT/2, texture_x, 0, texture_x + 1, sky_texture.getHeight(), null);
        }
    }

    private void raycast(Graphics g) {
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

            if (map_x < 0 || map_x >= map.GetWidth() || map_y < 0 || map_y >= map.GetHeight()) {
                hit = true;
                break;
            }


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

            Sector sector = map.GetSector(map_x, map_y);

            Wall wall_hit = null;

            if (sector == null) {
                hit = true;
                break;
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

                if (map_x < 0 || map_x >= map.GetWidth() || map_y < 0 || map_y >= map.GetHeight()) {
                    hit = true;
                    break;
                }

                sector = map.GetSector(map_x, map_y);

                if (side == 0) wall_hit = (step_x > 0) ? sector.GetWall(DIRECTIONS.EAST) : sector.GetWall(DIRECTIONS.WEST);
                else wall_hit = (step_y > 0) ? sector.GetWall(DIRECTIONS.SOUTH) : sector.GetWall(DIRECTIONS.NORTH);

                if (wall_hit == null) continue;
                else hit = true;
            }

            if (wall_hit == null) continue;

            // distance from perpendicular walls
            double perp_wall_dist = (side == 0) 
                    ? (map_x - player.pos_x + (1 - step_x) / 2.0) / ray_dir_x 
                    : (map_y - player.pos_y + (1 - step_y) / 2.0) / ray_dir_y;

            // project wall heights
            double wall_top = wall_hit.GetHeight();
            double wall_bottom = sector.GetFloorHeight();
            double camera_z = player.z;

            int draw_start = (int)(HEIGHT / 2 - ((wall_top - camera_z) / perp_wall_dist) * HEIGHT);
            int draw_end = (int)(HEIGHT / 2 - ((wall_bottom - camera_z) / perp_wall_dist) * HEIGHT);

            BufferedImage wall_texture = wall_hit.GetTexture() != null ? wall_hit.GetTexture().GetImage() : null;

            if (wall_texture != null) {
                int texture_width = wall_texture.getWidth();
                int texture_height = wall_texture.getHeight();

                // x
                double hit_x;
                if (side == 0) hit_x = player.pos_y + perp_wall_dist * ray_dir_y;
                else hit_x = player.pos_x + perp_wall_dist * ray_dir_x;

                hit_x -= Math.floor(hit_x);

                int texture_x = (int)(hit_x * texture_width);

                for (int y = draw_start; y < draw_end; y++) {
                    double wall_height = draw_end - draw_start;
                    int texture_y = (int)((y - draw_start) / wall_height * texture_height);
                    int color = wall_texture.getRGB(texture_x, texture_y);

                    // Fog rendering
                    double fog = Math.min(perp_wall_dist / 10.0, 1.0);
                    Color c = new Color(color);
                    int red = (int)(c.getRed() * (1-fog));
                    int green = (int)(c.getGreen() * (1-fog));
                    int blue = (int)(c.getBlue() * (1-fog));

                    // Side-Darkening
                    if (side == 1) {
                        red *= 0.7;
                        green *= 0.7;
                        blue *= 0.7;
                    }

                    g.setColor(new Color(red, green, blue));
                    g.drawLine(x, y, x, y);
                }
            } else {
                Color wall_color = Color.GRAY;

                double fog = Math.min(perp_wall_dist / 10.0, 1.0);
                Color c = wall_color;

                int red = (int)   (c.getRed() * (1-fog));
                int green = (int) (c.getGreen() * (1-fog));
                int blue = (int)  (c.getBlue() * (1-fog));

                // Side-Darkening
                if (side == 1) {
                    red *= 0.7;
                    green *= 0.7;
                    blue *= 0.7;
                }

                g.setColor(new Color(red, green, blue));
                g.drawLine(x, draw_start, x, draw_end);
            }
            
        }
    }

    // This is AI generated for the lols
    private void draw_floor_and_ceiling(Graphics g) {
        BufferedImage floor_texture = texture_manager.GetTexture("placeholder").GetImage();
        BufferedImage ceiling_texture = texture_manager.GetTexture("placeholder").GetImage();

        int texWidth = floor_texture.getWidth();
        int texHeight = floor_texture.getHeight();

        for (int y = 0; y < HEIGHT; y++) {
            // Determine whether this row is floor or ceiling
            boolean isFloor = y > HEIGHT / 2;
            double p = isFloor ? (y - HEIGHT / 2.0) / (HEIGHT / 2.0) : (HEIGHT / 2.0 - y) / (HEIGHT / 2.0);

            for (int x = 0; x < WIDTH; x++) {
                // compute ray direction for current pixel
                double camera_x = 2 * x / (double) WIDTH - 1;
                double ray_dir_x = player.dir_x + player.plane_x * camera_x;
                double ray_dir_y = player.dir_y + player.plane_y * camera_x;

                // distance from player to the row in world space
                double row_distance = (isFloor ? player.z - 0 : 2 - player.z) / p;

                // world coordinates of the floor/ceiling pixel
                double world_x = player.pos_x + ray_dir_x * row_distance;
                double world_y = player.pos_y + ray_dir_y * row_distance;

                // get sector coordinates
                int sector_x = (int) world_x;
                int sector_y = (int) world_y;

                // clamp to map boundaries
                if (sector_x < 0 || sector_x >= map.GetWidth() || sector_y < 0 || sector_y >= map.GetHeight())
                    continue;

                Sector sector = map.GetSector(sector_x, sector_y);
                if (sector == null) continue;

                // relative coordinates inside the sector (0..1)
                double local_x = world_x - sector_x;
                double local_y = world_y - sector_y;

                // map to texture coordinates
                int tex_x = (int) (local_x * texWidth);
                int tex_y = (int) (local_y * texHeight);

                // clamp just in case
                tex_x = Math.max(0, Math.min(tex_x, texWidth - 1));
                tex_y = Math.max(0, Math.min(tex_y, texHeight - 1));

                // get pixel color
                //Color c = isFloor ? new Color(floor_texture.getRGB(tex_x, tex_y)) : new Color(ceiling_texture.getRGB(tex_x, tex_y));

                Color c = Color.GRAY;
                // optionally, add fog based on distance
                double fog = Math.min(row_distance / 10.0, 1.0);
                int red = (int) (c.getRed() * (1 - fog));
                int green = (int) (c.getGreen() * (1 - fog));
                int blue = (int) (c.getBlue() * (1 - fog));

                g.setColor(new Color(red, green, blue));
                g.drawLine(x, y, x, y);
            }
        }
    }
}