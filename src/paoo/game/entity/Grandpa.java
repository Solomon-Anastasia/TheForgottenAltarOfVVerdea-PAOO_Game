package paoo.game.entity;

import java.util.Random;

/**
 * Represents the Grandpa NPC character in the game.
 * Grandpa serves as a quest giver and provides important story information
 * about carrots, goblins, and the wizard Master Kael.
 * <p>
 * This NPC has simple AI movement patterns and provides multipart dialogue
 * to guide the player through early game objectives.
 */
public class Grandpa extends Entity {

    /**
     * Constructs a new Grandpa NPC with default properties.
     * Initializes movement speed, size, sprite images, and dialogue content.
     */
    public Grandpa() {
        super();

        direction = "down";
        speed = 1;
        height = 48 + 17; // Base tile size plus additional height
        width = 32 + 17;  // Base tile size plus additional width

        getNpcImage();
        setDialogue();
    }

    /**
     * Defines the AI behavior for Grandpa's movement.
     * Uses a simple random movement pattern that changes direction every 80 frames.
     * Each direction has a 25% probability of being selected.
     * <p>
     * Movement probabilities:
     * - Up: 1-25%
     * - Down: 26-50%
     * - Left: 51-75%
     * - Right: 76-100%
     */
    @Override
    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 80) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            // Simple AI movement logic
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    /**
     * Initializes all dialogue text for Grandpa's conversation sequence.
     * The dialogue provides story context and quest information including:
     * - Welcome message to the player (Ryo)
     * - Information about wild carrots in the eastern orchard
     * - Warning about goblins in the area
     * - Instructions for harvesting safely
     * - Direction to seek help from Master Kael the wizard
     * <p>
     * Total dialogue entries: 9 (indices 0-8)
     */
    public void setDialogue() {
        dialogues[0] = "Ah, Ryo... you're finally here. This old farm's\nmissed your spirit.";
        dialogues[1] = "I spotted some carrots growing wild near the\neastern orchard.";
        dialogues[2] = "Must be from the last planting season. They're\nnearly ripe, but you'll have to wait just a bit longer\nbefore you can collect them.";
        dialogues[3] = "Trouble is, goblins have started lurking around\nthat patch.";
        dialogues[4] = "Don't try to scare them off — they're feisty little\nbeasts, and they'll hurt you if you get too close.";
        dialogues[5] = "Stay hidden, move quickly,and gather as much as\nyou can once the carrots are ready.";
        dialogues[6] = "If they get to the harvest before you, we lose it.";
        dialogues[7] = "And we can't afford that now. Once you've got\nenough, head to Master Kael, the wizard near the\nforest's edge.";
        dialogues[8] = "He'll help you defend yourself... And maybe share \na few secrets of the island's past.";
    }

    /**
     * Handles dialogue interaction with the player.
     * Extends the base speak functionality by playing a sound effect
     * when dialogue is initiated.
     * <p>
     * Plays sound effect #4 when speaking.
     */
    @Override
    public void speak() {
        super.speak();
        gamePanel.playSE(4); // Play dialogue sound effect
    }

    /**
     * Loads all sprite images for Grandpa's animations.
     * Sets up walking animation frames for all four directions:
     * - Right: frames 008-011
     * - Left: frames 004-007
     * - Up: frames 020-023
     * - Down: frames 012-015
     * <p>
     * Each direction has 4 animation frames for smooth walking movement.
     * All images are loaded from the "/npc/" resource directory.
     */
    public void getNpcImage() {
        // Walking animations for right direction
        right1 = setup("/npc/grandpa008");
        right2 = setup("/npc/grandpa009");
        right3 = setup("/npc/grandpa010");
        right4 = setup("/npc/grandpa011");

        // Walking animations for left direction
        left1 = setup("/npc/grandpa004");
        left2 = setup("/npc/grandpa005");
        left3 = setup("/npc/grandpa006");
        left4 = setup("/npc/grandpa007");

        // Walking animations for up direction
        up1 = setup("/npc/grandpa020");
        up2 = setup("/npc/grandpa021");
        up3 = setup("/npc/grandpa022");
        up4 = setup("/npc/grandpa023");

        // Walking animations for down direction
        down1 = setup("/npc/grandpa012");
        down2 = setup("/npc/grandpa013");
        down3 = setup("/npc/grandpa014");
        down4 = setup("/npc/grandpa015");
    }
}