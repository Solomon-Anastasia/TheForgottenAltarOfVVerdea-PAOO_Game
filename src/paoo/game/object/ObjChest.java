package paoo.game.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjChest extends SuperObject {
    public ObjChest() {
        name = "Chest";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/objects/chest.png")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
