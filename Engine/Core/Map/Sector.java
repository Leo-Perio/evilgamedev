package Engine.Core.Map;

import java.util.ArrayList;
import java.util.HashMap;

import Engine.Core.Map.Objects.Ceiling;
import Engine.Core.Map.Objects.Floor;
import Engine.Core.Map.Objects.Sprite;
import Engine.Core.Map.Objects.Wall;
import Engine.Core.Textures.Texture;

public class Sector {
    private HashMap<DIRECTIONS, Wall> walls;
    private ArrayList<Sprite> sprites;

    private Ceiling ceiling;
    private Floor floor;

    public enum DIRECTIONS {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    private Wall temp_wall;

    // Temporary Constructor
    public Sector(Wall wall) {
        this.temp_wall = wall;
    }

    public Sector() {
        this.temp_wall = null;

        this.walls = new HashMap<>();
        this.sprites = new ArrayList<>();
    }

    public void AddWall(DIRECTIONS dir, Wall wall) {walls.put(dir, wall);}
    public void AddSprite(Sprite sprite) {sprites.add(sprite);}
    public void CreateCeiling(Texture texture, double height) {this.ceiling = new Ceiling(texture, height);}
    public void CreateFloor(Texture texture, double height) {this.floor = new Floor(texture, height);}

    public ArrayList<Sprite> GetSprites() {return this.sprites;}
    public HashMap<DIRECTIONS, Wall> GetWalls() {return this.walls;}
    public Wall GetWall(DIRECTIONS dir) {
        return walls.get(dir);
    }
    
    public Ceiling GetCeiling() {return this.ceiling;}
    public Floor GetFloor() {return this.floor;}

    public double GetCeilingHeight() {return this.ceiling.GetHeight();}
    public double GetFloorHeight() {return this.floor.GetHeight();}
    public int GetWallColor() {return temp_wall.GetColor();}
}
