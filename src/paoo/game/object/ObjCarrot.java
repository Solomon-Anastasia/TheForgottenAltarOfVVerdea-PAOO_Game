package paoo.game.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjCarrot extends SuperObject {
    public ObjCarrot() {
        name = "Carrot";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/objects/carrot_05.png")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        collision = true;
        setSize(16, 16);

        // Match collision to the size of image
        setSolidArea(0, 0, 16, 16);
    }
}
