package paoo.game.object;

import paoo.game.panel.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjChest extends SuperObject {
    private GamePanel gamePanel;

    public ObjChest(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Chest";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/objects/chest.png")));
            utilityTool.scaleImage(image, this.gamePanel.getTILE_SIZE(), this.gamePanel.getTILE_SIZE());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        collision = true;

        // Match collision to the size of image
        setSize(48, 48);
    }
}
