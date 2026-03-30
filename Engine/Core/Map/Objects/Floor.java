package Engine.Core.Map.Objects;

import Engine.Core.Textures.Texture;

public class Floor {
    private Texture texture;
    private double height;

    public Floor(Texture texture, double height) {
        this.texture = texture;
        this.height = height;
    }

    public double GetHeight() {return this.height;}
    public Texture GetTexture() {return this.texture;}
}
