package paoo.game.object;

import paoo.game.panel.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjHeart extends SuperObject {
    public ObjHeart(GamePanel gamePanel) {
        super(gamePanel);

        name = "Heart";

        try {
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/objects/life_1.png")));
            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/objects/life_2.png")));
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/objects/life_3.png")));

            image1 = utilityTool.scaleImage(image1, this.gamePanel.getTILE_SIZE() - 10, this.gamePanel.getTILE_SIZE() - 10);
            image2 = utilityTool.scaleImage(image2, this.gamePanel.getTILE_SIZE() - 10, this.gamePanel.getTILE_SIZE() - 10);
            image3 = utilityTool.scaleImage(image3, this.gamePanel.getTILE_SIZE() - 10, this.gamePanel.getTILE_SIZE() - 10);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
