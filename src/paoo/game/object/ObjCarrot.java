package paoo.game.object;

import paoo.game.entity.Entity;

import java.awt.image.BufferedImage;

/**
 * Represents a carrot crop that grows through multiple stages and can be harvested.
 *
 * <p>The carrot progresses through 5 visual stages of growth, with each stage having
 * its own timing cooldown. Once fully grown, the carrot can be harvested and collected
 * by the player. This class extends Entity and implements consumable item behavior.</p>
 *
 * <p>Growth stages:
 * <ul>
 * <li>Stage 0-3: Growing phases with 2-second intervals each</li>
 * <li>Stage 4: Fully grown and ready for harvest</li>
 * <li>Post-harvest: Shows soil image indicating harvested state</li>
 * </ul></p>
 */
public class ObjCarrot extends Entity {

    /**
     * Array of BufferedImages representing each growth stage of the carrot
     */
    private BufferedImage[] stages;

    /**
     * Image displayed after the carrot has been harvested
     */
    private BufferedImage harvestedImage;

    /**
     * Current growth stage of the carrot (0-4)
     */
    private int currentStage = 0;

    /**
     * Timestamp of the last stage update in milliseconds
     */
    private long lastUpdateTime;

    /**
     * Cooldown times in milliseconds for each growth stage.
     * Each stage waits 2 seconds before progressing to the next.
     */
    private static final long[] COOLDOWN_TIMES = {2_000, 2_000, 2_000, 2_000};

    /**
     * Flag indicating whether the carrot has been harvested
     */
    private boolean isHarvested = false;

    /**
     * Flag indicating whether the carrot has been collected by the player
     */
    private boolean isCollected = false;

    /**
     * Constructs a new carrot object with initial growth stage and properties.
     *
     * <p>Initializes all growth stage images, sets up entity properties such as
     * collision detection, stack ability, and consumable type. The carrot starts
     * at growth stage 0 and begins its growth timer immediately.</p>
     */
    public ObjCarrot() {
        super();

        this.name = "Carrot";
        direction = "down";

        stages = new BufferedImage[5];
        stages[0] = setup("/objects/carrot_01");
        stages[1] = setup("/objects/carrot_02");
        stages[2] = setup("/objects/carrot_03");
        stages[3] = setup("/objects/carrot_04");
        stages[4] = setup("/objects/carrot_05");
        harvestedImage = setup("/objects/soil_03");

        down1 = stages[0];
        down2 = stages[4];

        renderPriority = 0;
        lastUpdateTime = System.currentTimeMillis();
        collision = false;
        description = "[" + name + "]\nPlain carrot!";
        type = TYPE_CONSUMABLE;
        stackable = true;

        setSize(16, 16);
    }

    /**
     * Checks if the carrot has been harvested.
     *
     * @return true if the carrot has been harvested, false otherwise
     */
    public boolean isHarvested() {
        return isHarvested;
    }

    /**
     * Checks if the carrot has been collected by the player.
     *
     * @return true if the carrot has been collected, false otherwise
     */
    public boolean isCollected() {
        return isCollected;
    }

    /**
     * Determines if the carrot is ready for harvest.
     *
     * <p>A carrot is ready for harvest when it reaches the final growth stage (stage 4).</p>
     *
     * @return true if the carrot is at stage 4 and ready for harvest, false otherwise
     */
    public boolean isReadyForHarvest() {
        return currentStage == 4;
    }

    /**
     * Marks the carrot as collected by the player.
     *
     * <p>This method should be called when the player successfully picks up
     * the harvested carrot, preventing further interaction with this object.</p>
     */
    public void collect() {
        isCollected = true;
    }

    /**
     * Updates the carrot's growth state based on elapsed time.
     *
     * <p>This method should be called each frame to progress the carrot through
     * its growth stages. If the carrot hasn't been harvested and the cooldown
     * time for the current stage has elapsed, it advances to the next stage.</p>
     *
     * <p>The carrot automatically progresses through stages 0-4, with each stage
     * having a 2-second cooldown before advancing.</p>
     */
    public void update() {
        if (!isHarvested && currentStage < COOLDOWN_TIMES.length && System.currentTimeMillis() - lastUpdateTime > COOLDOWN_TIMES[currentStage]) {
            if (currentStage < stages.length - 1) {
                down1 = stages[currentStage];
                currentStage++;

                // Reset time
                lastUpdateTime = System.currentTimeMillis();
            }
        }
    }

    /**
     * Harvests the carrot if it's ready and hasn't been harvested yet.
     *
     * <p>This method can only be called successfully when the carrot is at stage 4
     * (fully grown) and hasn't been previously harvested. Upon successful harvest,
     * the carrot's visual representation changes to show harvested soil.</p>
     *
     * @see #isReadyForHarvest()
     */
    public void harvest() {
        if (isReadyForHarvest() && !isHarvested) {
            isHarvested = true;
            down1 = harvestedImage;
        }
    }
}