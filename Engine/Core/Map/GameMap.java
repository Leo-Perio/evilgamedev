package Engine.Core.Map;

import Engine.Core.Map.Objects.Wall;
import Engine.Core.Textures.TextureManager;

public class GameMap {
    private Sector[][] map;
    private MapLoader map_loader;
    private TextureManager manager;

    public GameMap(TextureManager manager) {
        this.manager = manager;
        this.map_loader = new MapLoader(manager);

        this.map = map_loader.load("test_map");
    }
    
    public int GetWidth() {return map[0].length;}
    public int GetHeight() {return map.length;}
    public Sector GetSector(int x, int y) {return map[y][x];}
    public int get(int x, int y) {return map[y][x].GetWallColor();}
}
