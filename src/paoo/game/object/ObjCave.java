package paoo.game.object;

import paoo.game.entity.Entity;

/**
 * Represents a mystical cave entrance that serves as a teleportation gateway in the game.
 * The cave can only be accessed when the player possesses a specific artifact (Cup)
 * and provides passage to hidden or special areas within the game world.
 *
 * <p>Cave access requirements:
 * <ul>
 * <li>Player must have a "Cup" item in their inventory</li>
 * <li>Cave becomes interactive and ready for teleportation once activated</li>
 * <li>Without the cup, a mysterious force blocks entry to the cave</li>
 * </ul>
 */
public class ObjCave extends Entity {
    /**
     * Constructs a new cave entrance object with default properties and appearance.
     * The cave is initialized as a collision-enabled entity that blocks player
     * movement until properly activated.
     */
    public ObjCave() {
        super();

        name = "Cave";
        collision = true;
        renderPriority = 0;

        down1 = setup("/objects/cave");
        description = "A mysterious portal swirling with light...";
    }

    /**
     * Handles the interaction with the cave entrance when activated by the player.
     * The cave's behavior depends on whether the player possesses the required
     * cup artifact and the current teleportation readiness state.
     *
     * <p>Interaction behavior:
     * <ul>
     * <li><b>With Cup (first interaction):</b> Cave recognizes the artifact,
     *     activates the teleportation mechanism, and prepares for transport</li>
     * <li><b>With Cup (subsequent interactions):</b> Cave is ready to teleport,
     *     prompting the player to proceed</li>
     * <li><b>Without Cup:</b> Cave entrance is blocked by a swirling force,
     *     hinting that an important item is needed</li>
     * </ul>
     *
     * <p>The method performs the following actions:
     * <ul>
     * <li>Cancels any ongoing player attacks</li>
     * <li>Checks for cup artifact in player inventory</li>
     * <li>Updates teleportation readiness state if cup is present</li>
     * <li>Displays appropriate dialogue based on cave accessibility</li>
     * <li>Switches game to dialog state for user interaction</li>
     * </ul>
     */
    @Override
    public void interact() {
        gamePanel.getPlayer().setAttackCanceled(true);

        if (gamePanel.getPlayer().searchItemInInventory("Cup") != -1) {
            if (!gamePanel.getPlayer().isTeleportReady()) {
                gamePanel.getUi().setCurrentDialogue("You feel the cup vibrating...\nA strange force surrounds you.\nPress Enter to teleport.");
                gamePanel.getPlayer().setTeleportReady(true);
            } else {
                gamePanel.getUi().setCurrentDialogue("The cave hums in response to the cup.\nPress Enter to proceed.");
            }
            gamePanel.setGameState(gamePanel.getDIALOG_STATE());

        } else {
            gamePanel.getUi().setCurrentDialogue("A swirling force blocks the path...\nYou sense that something important is missing.");
            gamePanel.setGameState(gamePanel.getDIALOG_STATE());
        }
    }
}
