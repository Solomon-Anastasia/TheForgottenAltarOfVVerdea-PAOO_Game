package paoo.game.entity;

import paoo.game.panel.GamePanel;

import java.util.Random;

public class Grandpa extends Entity {

    public Grandpa(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 1;

        height = 48 + 17;
        width = 32 + 17;

        getNpcImage();
        setDialogue();
    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 80) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            // Simple AI
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

    public void setDialogue() {
        dialogues[0] = "Ah, Ryo... you're finally here. This old farm’s\nmissed your spirit.";
        dialogues[1] = "I spotted some carrots growing wild near the\neastern orchard.";
        dialogues[2] = "Must be from the last planting season. They’re\nnearly ripe, but you’ll have to wait just a bit longer\nbefore you can collect them.";
        dialogues[3] = "Trouble is, goblins have started lurking around\nthat patch.";
        dialogues[4] = "Don't try to scare them off — they're feisty little\nbeasts, and they’ll hurt you if you get too close.";
        dialogues[5] = "Stay hidden, move quickly,and gather as much as\nyou can once the carrots are ready.";
        dialogues[6] = "If they get to the harvest before you, we lose it.";
        dialogues[7] = "And we can't afford that now. Once you've got\nenough, head to Master Kael, the wizard near the\nforest’s edge.";
        dialogues[8] = "He’ll help you defend yourself... And maybe share \na few secrets of the island’s past.";
    }

    public void speak() {
        super.speak();
    }

    public void getNpcImage() {
        // Walking animations
        right1 = setup("/npc/grandpa008");
        right2 = setup("/npc/grandpa009");
        right3 = setup("/npc/grandpa010");
        right4 = setup("/npc/grandpa011");

        left1 = setup("/npc/grandpa004");
        left2 = setup("/npc/grandpa005");
        left3 = setup("/npc/grandpa006");
        left4 = setup("/npc/grandpa007");

        up1 = setup("/npc/grandpa020");
        up2 = setup("/npc/grandpa021");
        up3 = setup("/npc/grandpa022");
        up4 = setup("/npc/grandpa023");

        down1 = setup("/npc/grandpa012");
        down2 = setup("/npc/grandpa013");
        down3 = setup("/npc/grandpa014");
        down4 = setup("/npc/grandpa015");
    }
}
