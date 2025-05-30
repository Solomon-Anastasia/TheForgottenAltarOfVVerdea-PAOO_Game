package paoo.game.main;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

/**
 * Handles collision detection for entities within the game world.
 * This class provides methods to check collisions between entities and various game elements
 * including tiles, objects, other entities, and the player.
 */
public class CollisionChecker {
    /**
     * The game panel containing game state and entities
     */
    private GamePanel gamePanel;

    /**
     * Constructs a new CollisionChecker with the specified game panel.
     *
     * @param gamePanel the game panel containing game state and entities
     */
    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Checks for tile collisions for the specified entity based on its movement direction.
     * This method examines tiles in the entity's path and sets the collision flag if
     * the entity would collide with solid tiles.
     * <p>
     * The method checks two tile layers and considers the entity's solid area bounds
     * to determine which tiles need to be examined based on the movement direction.
     *
     * @param entity the entity to check for tile collisions
     */
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX / gamePanel.getTILE_SIZE();
        int entityRightCol = entityRightWorldX / gamePanel.getTILE_SIZE();
        int entityTopRow = entityTopWorldY / gamePanel.getTILE_SIZE();
        int entityBottomRow = entityBottomWorldY / gamePanel.getTILE_SIZE();

        int tileNum1;
        int tileNum2;
        int layers = 2;

        // Only check 2 tiles touching the player
        switch (entity.getDirection()) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gamePanel.getTILE_SIZE();

                for (int i = 0; i < layers; ++i) {
                    tileNum1 = gamePanel.getTileManager().getMapTileNumber()[gamePanel.getCurrentMap()][i][entityLeftCol][entityTopRow];
                    tileNum2 = gamePanel.getTileManager().getMapTileNumber()[gamePanel.getCurrentMap()][i][entityRightCol][entityTopRow];

                    if (gamePanel.getTileManager().getTile(tileNum1).isCollision() ||
                            gamePanel.getTileManager().getTile(tileNum2).isCollision()) {
                        entity.setCollisionOn(true);
                    }
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gamePanel.getTILE_SIZE();

                for (int i = 0; i < layers; ++i) {
                    tileNum1 = gamePanel.getTileManager().getMapTileNumber()[gamePanel.getCurrentMap()][i][entityLeftCol][entityBottomRow];
                    tileNum2 = gamePanel.getTileManager().getMapTileNumber()[gamePanel.getCurrentMap()][i][entityRightCol][entityBottomRow];

                    if (gamePanel.getTileManager().getTile(tileNum1).isCollision() ||
                            gamePanel.getTileManager().getTile(tileNum2).isCollision()) {
                        entity.setCollisionOn(true);
                    }
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gamePanel.getTILE_SIZE();

                for (int i = 0; i < layers; ++i) {
                    tileNum1 = gamePanel.getTileManager().getMapTileNumber()[gamePanel.getCurrentMap()][i][entityLeftCol][entityTopRow];
                    tileNum2 = gamePanel.getTileManager().getMapTileNumber()[gamePanel.getCurrentMap()][i][entityLeftCol][entityBottomRow];

                    if (gamePanel.getTileManager().getTile(tileNum1).isCollision() ||
                            gamePanel.getTileManager().getTile(tileNum2).isCollision()) {
                        entity.setCollisionOn(true);
                    }
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gamePanel.getTILE_SIZE();

                for (int i = 0; i < layers; ++i) {
                    tileNum1 = gamePanel.getTileManager().getMapTileNumber()[gamePanel.getCurrentMap()][i][entityRightCol][entityTopRow];
                    tileNum2 = gamePanel.getTileManager().getMapTileNumber()[gamePanel.getCurrentMap()][i][entityRightCol][entityBottomRow];

                    if (gamePanel.getTileManager().getTile(tileNum1).isCollision() ||
                            gamePanel.getTileManager().getTile(tileNum2).isCollision()) {
                        entity.setCollisionOn(true);
                    }
                }
            }
        }
    }

    /**
     * Checks for collisions between an entity and game objects.
     * This method tests if the entity will intersect with any objects in its path
     * and handles both collision detection and object interaction.
     *
     * @param entity the entity to check for object collisions
     * @param player true if the entity is the player (allows object pickup), false otherwise
     * @return the index of the intersected object, or -1 if no collision occurred
     */
    public int checkObject(Entity entity, boolean player) {
        // Suppose the player isn't touching any object
        int index = -1;

        for (int i = 0; i < gamePanel.getObjects().length; ++i) {
            if (gamePanel.getObjects()[i] != null) {
                // Get entity's solid area position
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                // Get object's solid area position
                gamePanel.getObjects()[i].getSolidArea().x = gamePanel.getObjects()[i].getWorldX() + gamePanel.getObjects()[i].getSolidArea().x;
                gamePanel.getObjects()[i].getSolidArea().y = gamePanel.getObjects()[i].getWorldY() + gamePanel.getObjects()[i].getSolidArea().y;

                switch (entity.getDirection()) {
                    case "up" -> entity.getSolidArea().y -= entity.getSpeed();
                    case "down" -> entity.getSolidArea().y += entity.getSpeed();
                    case "left" -> entity.getSolidArea().x -= entity.getSpeed();
                    case "right" -> entity.getSolidArea().x += entity.getSpeed();
                }

                if (entity.getSolidArea().intersects(gamePanel.getObjects()[i].getSolidArea())) {
                    if (gamePanel.getObjects()[i].isCollision()) {
                        entity.setCollisionOn(true);
                    }

                    // Only player can get object
                    if (player) {
                        index = i;
                    }
                }

                // Reset solid area
                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();

                gamePanel.getObjects()[i].getSolidArea().x = gamePanel.getObjects()[i].getSolidAreaDefaultX();
                gamePanel.getObjects()[i].getSolidArea().y = gamePanel.getObjects()[i].getSolidAreaDefaultY();
            }
        }
        return index;
    }

    /**
     * Checks for collisions between an entity and other entities (NPCs or monsters).
     * This method determines if the entity will collide with any entities in the target array
     * during its next movement.
     *
     * @param entity the entity to check for collisions
     * @param target the array of target entities to check against
     * @return the index of the collided entity, or -1 if no collision occurred
     */
    public int checkEntity(Entity entity, Entity[] target) {
        // Suppose the entity isn't touching any object
        int index = -1;

        for (int i = 0; i < target.length; ++i) {
            if (target[i] != null) {
                // Get entity's solid area position
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                // Get object's solid area position
                target[i].getSolidArea().x = target[i].getWorldX() + target[i].getSolidArea().x;
                target[i].getSolidArea().y = target[i].getWorldY() + target[i].getSolidArea().y;

                switch (entity.getDirection()) {
                    case "up" -> entity.getSolidArea().y -= entity.getSpeed();
                    case "down" -> entity.getSolidArea().y += entity.getSpeed();
                    case "left" -> entity.getSolidArea().x -= entity.getSpeed();
                    case "right" -> entity.getSolidArea().x += entity.getSpeed();
                }
                if (entity.getSolidArea().intersects(target[i].getSolidArea())) {
                    if (target[i] != entity) {
                        entity.setCollisionOn(true);
                        index = i;
                    }
                }

                // Reset solid area
                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();

                target[i].getSolidArea().x = target[i].getSolidAreaDefaultX();
                target[i].getSolidArea().y = target[i].getSolidAreaDefaultY();
            }
        }
        return index;
    }

    /**
     * Checks if an entity will collide with the player character.
     * This method is typically used by NPCs or monsters to detect contact with the player.
     *
     * @param entity the entity to check for player collision
     * @return true if the entity will contact the player, false otherwise
     */
    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        // Get entity's solid area position
        entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
        entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

        // Get object's solid area position
        gamePanel.getPlayer().getSolidArea().x = gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSolidArea().x;
        gamePanel.getPlayer().getSolidArea().y = gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSolidArea().y;

        switch (entity.getDirection()) {
            case "up" -> entity.getSolidArea().y -= entity.getSpeed();
            case "down" -> entity.getSolidArea().y += entity.getSpeed();
            case "left" -> entity.getSolidArea().x -= entity.getSpeed();
            case "right" -> entity.getSolidArea().x += entity.getSpeed();
        }
        if (entity.getSolidArea().intersects(gamePanel.getPlayer().getSolidArea())) {
            entity.setCollisionOn(true);
            contactPlayer = true;
        }

        // Reset solid area
        entity.getSolidArea().x = entity.getSolidAreaDefaultX();
        entity.getSolidArea().y = entity.getSolidAreaDefaultY();

        gamePanel.getPlayer().getSolidArea().x = gamePanel.getPlayer().getSolidAreaDefaultX();
        gamePanel.getPlayer().getSolidArea().y = gamePanel.getPlayer().getSolidAreaDefaultY();

        return contactPlayer;
    }
}
