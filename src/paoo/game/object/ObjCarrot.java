package paoo.game.object;

import paoo.game.panel.GamePanel;

import java.awt.image.BufferedImage;

public class ObjCarrot extends SuperObject {
    private BufferedImage[] stages;
    private BufferedImage harvestedImage;
    private int currentStage = 0;
    private long lastUpdateTime;

    // Waiting tipe for each stage
    // TODO: Uncomment
//    private static final long[] COOLDOWN_TIMES = {10_000, 10_000, 10_000, 10_000};
    private static final long[] COOLDOWN_TIMES = {1_000, 1_000, 1_000, 1_000};
    private boolean isHarvested = false;
    private boolean isCollected = false;

    public ObjCarrot(GamePanel gamePanel) {
        super(gamePanel);
        this.name = "Carrot";

        try {
            stages = new BufferedImage[5];
            stages[0] = setup("carrot_01");
            stages[1] = setup("carrot_02");
            stages[2] = setup("carrot_03");
            stages[3] = setup("carrot_04");
            stages[4] = setup("carrot_05");

            harvestedImage = setup("soil_03");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        lastUpdateTime = System.currentTimeMillis();
        collision = false;
    }

    public boolean isHarvested() {
        return isHarvested;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public boolean isReadyForHarvest() {
        return currentStage == 4;
    }

    public void collect() {
        isCollected = true;
    }

    public void update() {
        if (!isHarvested && currentStage < COOLDOWN_TIMES.length && System.currentTimeMillis() - lastUpdateTime > COOLDOWN_TIMES[currentStage]) {
            if (currentStage < stages.length - 1) {
                image1 = stages[currentStage];
                currentStage++;

                // Reset time
                lastUpdateTime = System.currentTimeMillis();
            }
        }
    }

    public void harvest() {
        if (isReadyForHarvest() && !isHarvested) {
            isHarvested = true;
            image1 = harvestedImage;
        }
    }
}
