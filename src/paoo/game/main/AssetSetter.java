package paoo.game.main;

import paoo.game.entity.Grandpa;
import paoo.game.entity.Wizard;
import paoo.game.monster.MON_Boss;
import paoo.game.monster.MON_Goblin;
import paoo.game.object.*;
import paoo.game.panel.GamePanel;

import java.util.List;

/**
 * Manages the placement and initialization of game assets including objects, NPCs, and monsters
 * across different game levels. This class is responsible for setting up the game world
 * by populating it with interactive elements based on the current level.
 * <p>
 * The AssetSetter configures different layouts for each level:
 * - Level 1: Tutorial/starting level with multiple carrots, goblins, and basic NPCs
 * - Level 2: Intermediate level with fewer resources and moderate enemy count
 * - Level 3+: Final level with boss encounter and rare items
 */
public class AssetSetter {
    /**
     * The main game panel that manages the game state and entities
     */
    private GamePanel gamePanel;

    /**
     * Constructs a new AssetSetter with the specified game panel.
     */

    public AssetSetter() {
        this.gamePanel = GamePanel.getInstance();
    }

    /**
     * Sets up and places all interactive objects in the game world based on the current level.
     * This includes chests with loot, consumable items like carrots, and level transition objects.
     * <p>
     * Level-specific object placement:
     * - Level 1: Treasure chest with sword and pendant, 10 carrots scattered across the map, portal to next level
     * - Level 2: Treasure chest with pickaxe and cup, cave entrance object
     * - Level 3+: Treasure chest containing rare diamond
     * <p>
     * Objects are positioned using tile-based coordinates multiplied by TILE_SIZE.
     */
    public void setObject() {
        int i = 0;

        if (gamePanel.getKeyHandler().getCurrentLevel() == 1) {
            // Chest
            gamePanel.getObjects()[i] = new ObjChest(List.of(new ObjSword(), new ObjPendant()));
            gamePanel.getObjects()[i].setWorldX(59 * gamePanel.getTILE_SIZE()); // Column
            gamePanel.getObjects()[i].setWorldY(39 * gamePanel.getTILE_SIZE()); // Row
            ++i;

            // Carrot
            gamePanel.getObjects()[i] = new ObjCarrot();
            gamePanel.getObjects()[i].setWorldX(73 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(28 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot();
            gamePanel.getObjects()[i].setWorldX(40 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(35 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot();
            gamePanel.getObjects()[i].setWorldX(30 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(35 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot();
            gamePanel.getObjects()[i].setWorldX(38 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(26 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot();
            gamePanel.getObjects()[i].setWorldX(59 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(28 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot();
            gamePanel.getObjects()[i].setWorldX(24 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(25 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot();
            gamePanel.getObjects()[i].setWorldX(30 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(28 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot();
            gamePanel.getObjects()[i].setWorldX(59 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(30 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot();
            gamePanel.getObjects()[i].setWorldX(66 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(30 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot();
            gamePanel.getObjects()[i].setWorldX(64 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(22 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjPortal();
            gamePanel.getObjects()[i].setWorldX(74 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(15 * gamePanel.getTILE_SIZE());
            ++i;
        } else if (gamePanel.getKeyHandler().getCurrentLevel() == 2) {
            gamePanel.getObjects()[i] = new ObjChest(List.of(new ObjPickaxe(), new ObjCup()));
            gamePanel.getObjects()[i].setWorldX(35 * gamePanel.getTILE_SIZE()); // Column
            gamePanel.getObjects()[i].setWorldY(27 * gamePanel.getTILE_SIZE()); // Row
            ++i;

            gamePanel.getObjects()[i] = new ObjCave();
            gamePanel.getObjects()[i].setWorldX(55 * gamePanel.getTILE_SIZE()); // Column
            gamePanel.getObjects()[i].setWorldY(6 * gamePanel.getTILE_SIZE()); // Row
            ++i;
        } else {
            gamePanel.getObjects()[i] = new ObjChest(List.of(new ObjDiamond()));
            gamePanel.getObjects()[i].setWorldX(46 * gamePanel.getTILE_SIZE()); // Column
            gamePanel.getObjects()[i].setWorldY(24 * gamePanel.getTILE_SIZE()); // Row
            ++i;
        }
    }

    /**
     * Places Non-Player Characters (NPCs) throughout the game world based on the current level.
     * NPCs provide dialogue, quests, or other interactive elements for the player.
     * <p>
     * Level-specific NPC placement:
     * - Level 1: Grandpa (tutorial/quest giver) and Wizard (magical services)
     * - Level 2: Single Wizard (continued magical services)
     * - Level 3+: No NPCs (boss encounter focus)
     * <p>
     * NPCs are positioned using tile-based coordinates for consistent placement.
     */
    public void setNpc() {
        int i = 0;

        if (gamePanel.getKeyHandler().getCurrentLevel() == 1) {
            gamePanel.getNpc()[i] = new Grandpa();
            gamePanel.getNpc()[i].setWorldX(gamePanel.getTILE_SIZE() * 37); // Start column
            gamePanel.getNpc()[i].setWorldY(gamePanel.getTILE_SIZE() * 25); // Start line
            ++i;

            gamePanel.getNpc()[i] = new Wizard();
            gamePanel.getNpc()[i].setWorldX(gamePanel.getTILE_SIZE() * 63);
            gamePanel.getNpc()[i].setWorldY(gamePanel.getTILE_SIZE() * 39);
            ++i;
        } else if (gamePanel.getKeyHandler().getCurrentLevel() == 2) {
            gamePanel.getNpc()[i] = new Wizard();
            gamePanel.getNpc()[i].setWorldX(gamePanel.getTILE_SIZE() * 35);
            gamePanel.getNpc()[i].setWorldY(gamePanel.getTILE_SIZE() * 35);
            ++i;
        }
    }

    /**
     * Spawns and positions monsters/enemies throughout the game world based on the current level.
     * Monster placement creates gameplay challenges and combat encounters for the player.
     * <p>
     * Level-specific monster placement:
     * - Level 1: 8 Goblins scattered across the starting area for basic combat training
     * - Level 2: 5 Goblins positioned strategically for intermediate challenge
     * - Level 3+: Single Boss monster for final encounter
     * <p>
     * Monsters are positioned using tile-based coordinates, often near valuable items
     * or strategic locations to create meaningful combat encounters.
     */
    public void setMonster() {
        int i = 0;

        if (gamePanel.getKeyHandler().getCurrentLevel() == 1) {
            gamePanel.getMonster()[i] = new MON_Goblin();
            gamePanel.getMonster()[i].setWorldX(48 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(26 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin();
            gamePanel.getMonster()[i].setWorldX(48 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(35 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin();
            gamePanel.getMonster()[i].setWorldX(60 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(28 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin();
            gamePanel.getMonster()[i].setWorldX(50 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(32 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin();
            gamePanel.getMonster()[i].setWorldX(35 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(35 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin();
            gamePanel.getMonster()[i].setWorldX(24 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(25 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin();
            gamePanel.getMonster()[i].setWorldX(59 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(30 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin();
            gamePanel.getMonster()[i].setWorldX(64 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(22 * gamePanel.getTILE_SIZE());
            ++i;
        } else if (gamePanel.getKeyHandler().getCurrentLevel() == 2) {
            gamePanel.getMonster()[i] = new MON_Goblin();
            gamePanel.getMonster()[i].setWorldX(28 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(23 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin();
            gamePanel.getMonster()[i].setWorldX(41 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(24 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin();
            gamePanel.getMonster()[i].setWorldX(42 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(34 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin();
            gamePanel.getMonster()[i].setWorldX(31 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(37 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin();
            gamePanel.getMonster()[i].setWorldX(50 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(17 * gamePanel.getTILE_SIZE());
            ++i;
        } else {
            gamePanel.getMonster()[i] = new MON_Boss();
            gamePanel.getMonster()[i].setWorldX(40 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(40 * gamePanel.getTILE_SIZE());
            ++i;
        }
    }
}
