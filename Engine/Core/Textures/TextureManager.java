package Engine.Core.Textures;

import java.util.HashMap;
import javax.imageio.ImageIO;
import java.io.*;

public class TextureManager {
    private HashMap<String, Texture> TextureMap;

    public TextureManager() {
        TextureMap = new HashMap<>();

        try {init();}
        catch (IOException e) {}
    }

    public Texture GetTexture(String name) {
        return TextureMap.get(name);
    }

    // This function will populate the HashMap with Textures at runtime :)
    // Make sure textures are added here before trying to use them
    public void init() throws IOException{
        //TextureMap.put("skybox_placeholder", new Texture(ImageIO.read(new File("src/Assets/Sprites/Goat.PNG"))));
        TextureMap.put("skybox_placeholder", new Texture(ImageIO.read(new File("src/Assets/Textures/Skyboxes/Placeholder.png"))));
        TextureMap.put("placeholder", new Texture(ImageIO.read(new File("src/Assets/Textures/Placeholders/Placeholder.png"))));
    }
}
