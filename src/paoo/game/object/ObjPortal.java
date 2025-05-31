package paoo.game.object;

import paoo.game.entity.Entity;

/**
 * Represents a mystical portal object that serves as a transportation mechanism in the game.
 * The portal can only be activated when the player possesses a specific artifact (Pendant)
 * and provides teleportation functionality between game areas or levels.
 *
 * <p>Portal activation requirements:
 * <ul>
 * <li>Player must have a "Pendant" item in their inventory</li>
 * <li>Portal becomes interactive and ready for teleportation once activated</li>
 * <li>Without the pendant, the portal remains dormant and non-functional</li>
 * </ul>
 */
public class ObjPortal extends Entity {
    /**
     * Constructs a new portal object with default properties and appearance.
     * The portal is initialized as a collision-enabled entity with a mysterious
     * swirling light appearance.
     */
    public ObjPortal() {
        super();

        name = "Portal";
        collision = true;
        renderPriority = 0;

        // Load portal sprite (same image for both animation frames)
        down1 = setup("/objects/portal");
        down2 = setup("/objects/portal");
        image1 = down1;
        description = "[" + name + "]\nA mysterious portal swirling with light...";
    }

    /**
     * Handles the interaction with the portal when activated by the player.
     * The portal's behavior depends on whether the player possesses the required
     * pendant artifact and the current teleportation readiness state.
     *
     * <p>Interaction behavior:
     * <ul>
     * <li><b>With Pendant (first interaction):</b> Portal recognizes the pendant,
     *     glows in response, and prepares for teleportation</li>
     * <li><b>With Pendant (subsequent interactions):</b> Portal is ready to teleport,
     *     prompting the player to proceed</li>
     * <li><b>Without Pendant:</b> Portal remains inactive and suggests finding
     *     a powerful artifact to awaken it</li>
     * </ul>
     *
     * <p>The method performs the following actions:
     * <ul>
     * <li>Cancels any ongoing player attacks</li>
     * <li>Checks for pendant in player inventory</li>
     * <li>Updates teleportation readiness state if pendant is present</li>
     * <li>Displays appropriate dialogue based on portal state</li>
     * <li>Switches game to dialog state for user interaction</li>
     * </ul>
     */
    @Override
    public void interact() {
        // Cancel any ongoing player attack action
        gamePanel.getPlayer().setAttackCanceled(true);

        // Check if player has the required pendant artifact
        if (gamePanel.getPlayer().searchItemInInventory("Pendant") != -1) {
            // Player has pendant - check teleportation readiness
            if (!gamePanel.getPlayer().isTeleportReady()) {
                // First interaction with pendant - activate portal
                gamePanel.getUi().setCurrentDialogue("As you approach, the pendant glows faintly..." +
                        "\nThe portal responds to its presence.\nPress Enter to teleport.");
                gamePanel.getPlayer().setTeleportReady(true);
            } else {
                // Portal already activated - ready for teleportation
                gamePanel.getUi().setCurrentDialogue("The portal pulses with energy..." +
                        "\nPress Enter to proceed.");
            }
            gamePanel.setGameState(gamePanel.getDIALOG_STATE());

        } else {
            // Player lacks required pendant - portal remains dormant
            gamePanel.getUi().setCurrentDialogue("The portal remains dormant." +
                    "\nA powerful artifact might awaken it...");
            gamePanel.setGameState(gamePanel.getDIALOG_STATE());
        }
    }
}