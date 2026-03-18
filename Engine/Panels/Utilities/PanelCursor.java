package Engine.Panels.Utilities;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.imageio.ImageIO;

public class PanelCursor {
    public static Cursor GetCursor(String type) {
        switch (type) {
            case "INVISIBLE":
                return Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor");
            case "VISIBLE":
                BufferedImage b_image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                try {
                    File img = new File("src/Assets/Textures/Cursors/Menu_Cursor.png");
                    byte[] bytes = Files.readAllBytes(img.toPath());
                    try (InputStream is = new ByteArrayInputStream(bytes)){
                        b_image = ImageIO.read(is);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Toolkit.getDefaultToolkit().createCustomCursor(b_image, new Point(0, 0), "Main");
            default:
                return null;
        }
    }
}