package paoo.game.entity;

import paoo.game.panel.GamePanel;

public class Wizard extends Entity {
    public Wizard(GamePanel gamePanel) {
        super(gamePanel);

        direction = "right";
        speed = 0;

        height = 48 + 17;
        width = 48 + 17;

        getNpcImage();
        setDialogue();
    }

    public void setDialogue() {
        if (gamePanel.getPlayer().getNrCarrots() < 10) {
            // Initial interaction
            dialogues[0] = "So... you’re Ryo. The land has whispered your\nname for some time now.";
            dialogues[1] = "Your grandfather once stood where you do,\nyoung and stubborn, full of fire.";
            dialogues[2] = "I sense that same flame in you. Good. We’ll\nneed it for what’s to come.";
            dialogues[3] = "The goblins grow bolder. Their presence stirs\nthe shadows that once slept beneath this island.";
            dialogues[4] = "Long ago, a magical altar kept the balance...\nsealed the darkness away.";
            dialogues[5] = "But it was shattered — its pieces scattered across\nVerdea, waiting to be found.";
            dialogues[6] = "Before you can face what lies ahead, prove your\nresolve. Bring me 10 carrots from your harvest.";
            dialogues[7] = "Only then will I unlock the chest that holds your\nsword — forged in elderfire, bound by ancient wood.";
            dialogues[8] = "Return when you're ready. The island watches.\nAnd time grows short.";
        }
        // Follow-up interaction
        else {
            dialogues[0] = "Ah... I see you’ve returned with the carrots.";
            dialogues[1] = "You’ve shown determination — the kind needed to\nstand against the darkness.";
            dialogues[2] = "As promised, the chest is now yours to open.\nInside lies your sword.";
            dialogues[3] = "It’s old, but alive with enchantments. It will serve\nyou well against goblinkind.";
            dialogues[4] = "And take this — the first of three sacred artifacts.\nWith all three, the altar can be restored.";
            dialogues[5] = "Guard it well. Each piece carries a fragment of the\nisland’s forgotten magic.";
            dialogues[6] = "Now go, Ryo. The path ahead is long, but you\ndo not walk it alone.";
        }
    }

    public void update() {
        super.update();
        setDialogue();
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gamePanel.getUi().setCurrentDialogue(dialogues[dialogueIndex]);
        dialogueIndex++;

        gamePanel.playSE(4);
    }

    public void getNpcImage() {
        right1 = setup("/npc/wizard002");
        right2 = setup("/npc/wizard002");
        right3 = setup("/npc/wizard003");
        right4 = setup("/npc/wizard004");
        right5 = setup("/npc/wizard005");
        right6 = setup("/npc/wizard005");
    }
}
