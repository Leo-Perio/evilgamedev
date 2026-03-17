package Engine.Entities;

import java.awt.image.BufferedImage;

public class Sprite {
    private BufferedImage texture;
    private double x;
    private double y;

    public Sprite(BufferedImage texture, double x, double y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public double GetX() {return x;}
    public double GetY() {return y;}
    public BufferedImage GetTexture() {return texture;}
}
