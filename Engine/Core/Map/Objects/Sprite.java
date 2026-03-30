package Engine.Core.Map.Objects;

import java.awt.image.BufferedImage;

import Engine.Core.Textures.Texture;

public class Sprite {
    private Texture texture;
    private double x;
    private double y;
    private double z;
    private double rotation;

    public Sprite(Texture texture, double x, double y, double z, double rotation) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;
    }

    public double GetX() {return x;}
    public double GetY() {return y;}
    public double GetRotationRadians() {return Math.toRadians(rotation);}
    public double GetRotationDegrees() {return Math.toDegrees(rotation);}
    public BufferedImage GetTexture() {return texture.GetImage();}
}