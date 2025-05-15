package paoo.game.object;

import paoo.game.panel.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjSeed extends SuperObject {

    public ObjSeed(GamePanel gamePanel) {
        super(gamePanel);
        name = "Seed";

        try {
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/objects/carrot_00.png")));
            image1 = utilityTool.scaleImage(image1, this.gamePanel.getTILE_SIZE(), this.gamePanel.getTILE_SIZE());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        collision = true;
        setSize(16, 16);

        // Match collision to the size of image
        setSolidArea(0, 0, 16, 16);
    }
}
