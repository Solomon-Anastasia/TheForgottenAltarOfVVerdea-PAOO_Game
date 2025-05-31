package paoo.game.entity;

public class Wizard extends Entity {

    public Wizard() {
        super();

        direction = "right";
        speed = 0;
        height = 48 + 17;
        width = 48 + 17;

        getNpcImage();
        setDialogue();
    }

    public void setDialogue() {
        if (gamePanel.getKeyHandler().getCurrentLevel() == 1) {
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
                dialogues[7] = null;
                dialogues[8] = null;
            }
        } else if (gamePanel.getKeyHandler().getCurrentLevel() == 2) {
            boolean goblinsExist = false;
            for (Entity monster : gamePanel.getMonster()) {
                if (monster != null) {
                    goblinsExist = true;
                    break;
                }
            }

            if (goblinsExist) {
                dialogues[0] = "You’ve returned — and not empty-handed.\nThe island feels your steps more firmly now.";
                dialogues[1] = "But courage is not grown in gardens alone.\nAhead lies a forest once sacred, now spoiled.";
                dialogues[2] = "Goblins swarm its paths like thorns on vines.\nThey sense the altar’s fragments drawing near.";
                dialogues[3] = "You must face them. All of them. Only then\nwill the path to the mine open.";
                dialogues[4] = "Beware the trees — they whisper warnings.\nSome say the goblins laugh before they strike.";
                dialogues[5] = "There are relics hidden in the shadows:\na pickaxe forged for stone, and an artefact of light.";
                dialogues[6] = "The pickaxe I hold in trust. Prove your strength,\nand I shall grant it to you.";
                dialogues[7] = "Seek the artifact hidden deep within the woods.\nIt will call to your spirit — listen closely.";
                dialogues[8] = "Go now, Ryo. Let the forest remember what\nit means to tremble before a hero.";
            } else {
                dialogues[0] = "The goblins are gone — their shrieks silenced.";
                dialogues[1] = "In the cave beyond the glade, their lord awaits.";
                dialogues[2] = "He is cunning and cruel, bound to the altar’s\nshadow.";
                dialogues[3] = "Defeat him, and the village will finally breathe free.";
                dialogues[4] = "This is your final trial, Ryo. Go — end this darkness.";
                dialogues[5] = null;
                dialogues[6] = null;
                dialogues[7] = null;
                dialogues[8] = null;
            }
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
