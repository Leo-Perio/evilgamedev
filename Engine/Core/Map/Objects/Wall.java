package Engine.Core.Map.Objects;

import Engine.Core.Textures.Texture;
import Engine.Core.Map.Sector.DIRECTIONS;

public class Wall {
    private Texture texture;
    private double height;
    private int color; // This is temporary
    private int wall_type; // Won't be used until we make doors

    private double x;
    private double y;
    private double z;

    private DIRECTIONS dir;

    private boolean double_sided;

    public Wall(Texture texture, int color, double height, DIRECTIONS dir) {
        this.texture = texture;
        this.color = color;
        this.dir = dir;
        this.height = height;
        this.double_sided = true;
    }

    public Wall(Texture texture, int color, int wall_type, double height, double x, double y, double z, DIRECTIONS dir) {
        this.texture = texture;
        this.color = color;
        this.wall_type = wall_type;
        this.height = height;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dir = dir;
    }

    public int GetColor() {return this.color;}
    public int GetWallType() {return this.wall_type;}
    public double GetHeight() {return this.height;}
    public boolean IsDoubleSided() {return this.double_sided;}
    public Texture GetTexture() {return this.texture;}

    public double GetX() {return this.x;}
    public double GetY() {return this.y;}
    public double GetZ() {return this.z;}

    public DIRECTIONS GetDirection() {return this.dir;}
}
