package Engine.Core.Map.Objects;

import Engine.Core.Textures.Texture;

public class Ceiling {
    private Texture texture;
    private double height; // "How high it is from the ground"

    public Ceiling(Texture texture, double height) {
        this.texture = texture;
        this.height = height;
    }
    
    public double GetHeight() {return this.height;}
    public Texture GetTexture() {return this.texture;}
}
