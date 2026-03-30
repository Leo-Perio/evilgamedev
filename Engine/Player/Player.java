package Engine.Player;

import Engine.Core.Map.GameMap;
import Engine.Core.Map.Sector;

public class Player {
    public double pos_x = 1.5, pos_y = 1.5;
    public double z = 0.5; 

    public double dir_x = -1, dir_y = 0;
    public double yaw = Math.PI;

    public double plane_x = 0, plane_y = 1.20;

    public double pitch = 0;

    private double move_speed = 0.03;
    private final double rot_speed = 0.03;

    private GameMap map; // Reference to map for collision detection

    public Player(GameMap map) { 
        this.map = map; 
    }

    /*
     * FORWARD: (x, y)
     * BACKWARD: (-x, -y)
     * LEFT: (-y, x)
     * RIGHT: (y, -x)
     */

    public void Move(String dir) {
        double next_x = pos_x, next_y = pos_y;

        switch(dir) {
            case "FORWARD":
                next_x += dir_x * move_speed;
                next_y += dir_y * move_speed;
                break;
            case "BACKWARD":
                next_x -= dir_x * move_speed;
                next_y -= dir_y * move_speed;
                break;
            case "LEFT":
                next_x -= plane_x * move_speed;
                next_y -= plane_y * move_speed;
                break;
            case "RIGHT":
                next_x += plane_x * move_speed;
                next_y += plane_y * move_speed;
                break;
        }

        Sector sector = map.GetSector((int)next_x, (int)next_y);
        if (sector != null) {
            double floor_h = sector.GetFloor() != null ? sector.GetFloor().GetHeight() : 0;
            double ceiling_h = sector.GetCeiling() != null ? sector.GetCeiling().GetHeight() : 1;

            if (z >= floor_h && z <= ceiling_h) {
                pos_x = next_x;
                pos_y = next_y;
            }
        }
    }

    /*
     * Rotate function: rotates player using 2D rotation matrix
     * Rv = | cos(theta) -sin(theta) | * | x |
     *      | sin(theta)  cos(theta) |   | y |
     */
    public void Rotate(double angle) {
        double two_pi = 2 * Math.PI;
        double cos_angle = Math.cos(angle);
        double sin_angle = Math.sin(angle);

        yaw += angle;
        if (yaw < 0) yaw += two_pi;
        if (yaw >= two_pi) yaw -= two_pi;

        // Rotate direction vector
        double old_dir_x = dir_x;
        dir_x = dir_x * cos_angle - dir_y * sin_angle;
        dir_y = old_dir_x * sin_angle + dir_y * cos_angle;

        // Rotate camera plane
        double old_plane_x = plane_x;
        plane_x = plane_x * cos_angle - plane_y * sin_angle;
        plane_y = old_plane_x * sin_angle + plane_y * cos_angle;
    }

    public void RotateLeft() { Rotate(-rot_speed); }
    public void RotateRight() { Rotate(rot_speed); }

    // Placeholder functions for future features
    public void Zoom() {}
    public void Sprint() {}
    public void Jump(double height) { z += height; }
}