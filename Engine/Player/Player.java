package Engine.Player;

import Engine.Core.GameMap;

public class Player {
    public double pos_x = 3.5;
    public double pos_y = 3.5;

    public double dir_x = -1;
    public double dir_y = 0;

    public double plane_x = 0;
    public double plane_y = 1.20; // FOV

    private double move_speed = 0.05; // Subject to Change
    private final double rot_speed = 0.03;

    private GameMap map;

    public Player(GameMap map) {
        this.map = map;
    }

    /*
    * FORWARD:
    *	(x, y)
    * BACKWARD:
    *	(-x, -y)
    * LEFT:
    *	(-y, x)
    * RIGHT:
    *	(y, -x)
    **/
    
    public void Move(String direction) {
        // I hate whoever made linear algebra with a passion
        switch (direction) {
            case "FORWARD":
                if (map.get((int)(pos_x + dir_x * move_speed), (int)(pos_y)) == 0) pos_x += dir_x * move_speed;
                if (map.get((int)(pos_x), (int)(pos_y + dir_y * move_speed)) == 0) pos_y += dir_y * move_speed;
                break;
            case "BACKWARD":
                if (map.get((int)(pos_x - dir_x * move_speed), (int)(pos_y)) == 0) pos_x -= dir_x * move_speed;
                if (map.get((int)(pos_x), (int)(pos_y - dir_y * move_speed)) == 0) pos_y -= dir_y * move_speed;
                break;
            case "LEFT":
            	if (map.get((int)(pos_x - plane_x * move_speed), (int)(pos_y)) == 0) pos_x -= plane_x * move_speed;
                if (map.get((int)(pos_x), (int)(pos_y + plane_y * move_speed)) == 0) pos_y -= plane_y * move_speed;
                break;
            case "RIGHT":
            	if (map.get((int)(pos_x + plane_x * move_speed), (int)(pos_y)) == 0) pos_x += plane_x * move_speed;
                if (map.get((int)(pos_x), (int)(pos_y - plane_y * move_speed)) == 0) pos_y += plane_y * move_speed;
                break;
        }

        System.out.printf("Player is at (%.2f, %.2f)%n", pos_x, pos_y);
    }

    // Rotations

    /* 
    The rotation function involves calculating the rotation along a 2D plane
    using the rotation matrix:
    Rv = | cos(theta) -sin(theta) | * | x |
         | sin(theta)  cos(theta) | * | y |

    where Rv is the Rotation Vector
    so:
        Rv = | x cos(theta) - y sin(theta) |
             | x sin(theta) + y sin(theta) |

    finally:
        That is the logic behind the code below
        want more info on this?
        https://en.wikipedia.org/wiki/Rotation_matrix
    */

    public void Rotate(double angle) {
        // Rotate direction vector
        double old_dir_x = dir_x; // Temp variable to store the value of dir_x
        dir_x = dir_x * Math.cos(angle) - dir_y * Math.sin(angle);
        dir_y = old_dir_x * Math.sin(angle) + dir_y * Math.cos(angle);

        // Rotate camera plane
        double old_plane_x = plane_x; // Temp variable to store the value of plane_x
        plane_x = plane_x * Math.cos(angle) - plane_y * Math.sin(angle);
        plane_y = old_plane_x * Math.sin(angle) + plane_y * Math.cos(angle);
    }

    public void RotateLeft() {Rotate(-rot_speed);}
    public void RotateRight() {Rotate(rot_speed);}
}
