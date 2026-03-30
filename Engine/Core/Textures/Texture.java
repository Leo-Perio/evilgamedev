package Engine.Core.Textures;

import java.awt.image.BufferedImage;

public class Texture {
    private BufferedImage image;

    public Texture(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage GetImage() {
        return this.image;
    }
}
