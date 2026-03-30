package Engine.Core.Map;

import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

import Engine.Core.Utils.FlexibleScanner;

import Engine.Core.Map.Sector;
import Engine.Core.Map.Objects.Wall;
import Engine.Core.Map.Sector.DIRECTIONS;
import Engine.Core.Textures.TextureManager;

public class MapLoader {
    private FlexibleScanner scanner;
    private TextureManager manager;
    private HashMap<String, File> maps;

    public MapLoader(TextureManager manager) {
        Instantiate(manager);
        FillHashMap();
    }

    private void Instantiate(TextureManager manager) {
        this.manager = manager;
        this.maps = new HashMap<>();
        this.scanner = new FlexibleScanner("");
    }

    private void FillHashMap() {
        maps.put("test_map", new File("src/Assets/Maps/test_map.bsp"));
    }
    
    public Sector[][] load(String name) {
        File map_file = maps.get(name);
        System.out.println(maps.get(name).getAbsolutePath());

        if (map_file == null || !map_file.exists()) throw new RuntimeException("Map file was not found");

        try {scanner.setInput(map_file);}
        catch (FileNotFoundException e) {}

        Sector[][] map = null;
        Sector current_sector = null;

        int x, y;

        int current_sector_x = 0, current_sector_y = 0; 

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) continue;

            String[] data = line.trim().split(" ");

            switch(data[0]) {
                case "MAP":
                    x = Integer.parseInt(data[1]);
                    y = Integer.parseInt(data[2]);

                    map = new Sector[x][y];

                    break;
                case "SECTOR":
                    current_sector = new Sector();

                    current_sector_x = Integer.parseInt(data[1]);
                    current_sector_y = Integer.parseInt(data[2]);

                    map[current_sector_x][current_sector_y] = current_sector;

                    break;
                case "CEILING":
                    current_sector.CreateCeiling(null, Double.parseDouble(data[1]));
                    break;
                case "FLOOR":
                    current_sector.CreateFloor(null, Double.parseDouble(data[1]));
                    break;
                case "WALL":
                    //current_sector.AddWall(DIRECTIONS.valueOf(data[1]), new Wall(manager.GetTexture("placeholder"), Integer.parseInt(data[2]),  Integer.parseInt(data[3]), DIRECTIONS.valueOf(data[1])));
                    current_sector.AddWall(DIRECTIONS.valueOf(data[1]), new Wall(null, Integer.parseInt(data[2]),  Integer.parseInt(data[3]), DIRECTIONS.valueOf(data[1])));
                    // Walls only appear on the side that their facing, a WONDERFUL renderer bug :c
                    
                    break;
                default:
                    break;
            }
        }
        return map;
    }
}
