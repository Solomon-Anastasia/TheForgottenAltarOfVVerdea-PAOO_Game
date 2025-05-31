package paoo.game.object;

import paoo.game.entity.Entity;

import java.util.List;

/**
 * Represents an interactive chest object in the game that can contain loot items.
 * The chest has different opening requirements based on the current game level
 * and displays different sprites when opened/closed.
 *
 * <p>Opening requirements by level:
 * <ul>
 * <li>Level 1: Player must have at least 10 carrots in inventory</li>
 * <li>Level 2: All goblins must be defeated</li>
 * <li>Level 3: Boss must be defeated (no goblins remaining)</li>
 * </ul>
 */
public class ObjChest extends Entity {
    /**
     * List of items that can be obtained from this chest
     */
    private final List<Entity> loots;

    /**
     * Flag indicating whether the chest has been opened
     */
    private boolean opened = false;

    /**
     * Constructs a new chest object with the specified loot items.
     * @param loots     the list of items that can be obtained from this chest
     */
    public ObjChest(List<Entity> loots) {
        super();

        down1 = setup("/objects/chest1");
        down2 = setup("/objects/chest2");
        image1 = down1;
        image2 = down2;

        this.loots = loots;

        name = "Chest";
        collision = true;
        renderPriority = 0;
        description = "[Chest]\nAn old chest!";
        setSize(48, 48);
    }

    /**
     * Handles the interaction with the chest when the player activates it.
     * Checks level-specific requirements before allowing the chest to be opened.
     *
     * <p>The method performs the following actions:
     * <ul>
     * <li>Cancels any ongoing player attacks</li>
     * <li>Switches to dialog state</li>
     * <li>Checks if chest is already opened</li>
     * <li>Validates level-specific opening requirements</li>
     * <li>Distributes loot items to player inventory if requirements are met</li>
     * <li>Updates chest sprite to opened state</li>
     * <li>Triggers game end condition if diamond is obtained on level 3</li>
     * </ul>
     *
     * <p>Level-specific requirements:
     * <ul>
     * <li>Level 1: Requires 10 carrots in inventory</li>
     * <li>Level 2: All goblins must be defeated</li>
     * <li>Level 3: Boss must be defeated (triggers game end if diamond obtained)</li>
     * </ul>
     */
    public void interact() {
        gamePanel.getPlayer().setAttackCanceled(true);
        gamePanel.setGameState(gamePanel.getDIALOG_STATE());

        if (opened) {
            gamePanel.getUi().setCurrentDialogue("It's empty!");
            return;
        }

        int level = gamePanel.getKeyHandler().getCurrentLevel();
        int index = gamePanel.getPlayer().searchItemInInventory("Carrot");
        if (level == 1 && gamePanel.getPlayer().getInventory().get(index).getAmount() < 10) {
            gamePanel.getUi().setCurrentDialogue("You need 10 carrots!");
            return;
        }

        boolean goblinsExist = false;
        for (Entity monster : gamePanel.getMonster()) {
            if (monster != null) {
                goblinsExist = true;
                break;
            }
        }

        if (level == 2 && goblinsExist) {
            gamePanel.getUi().setCurrentDialogue("There are still goblins!");
            return;
        }

        if (level == 3 && goblinsExist) {
            gamePanel.getUi().setCurrentDialogue("Defeat the boss!");
            return;
        }

        gamePanel.playSE(5);

        StringBuilder obtainedItems = new StringBuilder("You open the chest and find:\n");
        boolean atLeastOneObtained = false;

        for (Entity loot : loots) {
            boolean canTake = gamePanel.getPlayer().canObtainItem(loot);

            if (canTake) {
                gamePanel.getPlayer().addItemToInventory(loot);
                obtainedItems.append("- ").append(loot.getName()).append("\n");
                atLeastOneObtained = true;
            } else {
                obtainedItems.append("- ").append(loot.getName()).append(" (Can't carry)\n");
            }
        }

        if (!atLeastOneObtained) {
            obtainedItems = new StringBuilder("You open the chest...\nBut you can't carry any of the items!");
        } else {
            down1 = image2;
            opened = true;

            if (level == 3 && gamePanel.getPlayer().searchItemInInventory("Diamond") != 1) {
                gamePanel.stopMusic();
                gamePanel.playSE(8);
                gamePanel.setGameState(gamePanel.getGAME_END_STATE());
            }
        }

        gamePanel.getUi().setCurrentDialogue(obtainedItems.toString().trim());
    }
}
