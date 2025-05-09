package paoo.game.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjSeed extends SuperObject {
    public ObjSeed() {
        name = "Seed";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/objects/carrot_00.png")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        collision = true;
        setSize(16, 16);

        // Match collision to the size of image
        setSolidArea(0, 0, 16, 16);
    }
}
