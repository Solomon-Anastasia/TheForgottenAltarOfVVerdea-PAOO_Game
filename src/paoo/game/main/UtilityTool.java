package paoo.game.main;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Utility class providing helper methods for image processing and manipulation.
 * This class contains static utility methods that can be used throughout the game
 * for common image operations.
 */
public class UtilityTool {
    /**
     * Scales a BufferedImage to the specified dimensions.
     *
     * <p>This method creates a new BufferedImage with the target width and height,
     * then draws the original image scaled to fit those dimensions. The scaling
     * uses bilinear interpolation for smooth results.</p>
     *
     * <p>The resulting image maintains ARGB color model with alpha channel support,
     * making it suitable for images with transparency.</p>
     *
     * @param original the original BufferedImage to be scaled
     * @param width    the target width in pixels for the scaled image
     * @param height   the target height in pixels for the scaled image
     * @return a new BufferedImage scaled to the specified dimensions
     * @throws IllegalArgumentException if width or height is less than or equal to 0
     * @throws NullPointerException     if the original image is null
     * @see BufferedImage
     * @see Graphics2D#drawImage(Image, int, int, int, int, java.awt.image.ImageObserver)
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
}