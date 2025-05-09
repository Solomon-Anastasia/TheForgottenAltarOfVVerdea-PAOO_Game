package paoo.game.main;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

public class CollisionChecker {
    private GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

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
                    tileNum1 = gamePanel.getTileManager().getMapTileNumber()[i][entityLeftCol][entityTopRow];
                    tileNum2 = gamePanel.getTileManager().getMapTileNumber()[i][entityRightCol][entityTopRow];

                    if (gamePanel.getTileManager().getTile(tileNum1).isCollision() ||
                            gamePanel.getTileManager().getTile(tileNum2).isCollision()) {
                        entity.setCollisionOn(true);
                    }
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gamePanel.getTILE_SIZE();

                for (int i = 0; i < layers; ++i) {
                    tileNum1 = gamePanel.getTileManager().getMapTileNumber()[i][entityLeftCol][entityBottomRow];
                    tileNum2 = gamePanel.getTileManager().getMapTileNumber()[i][entityRightCol][entityBottomRow];

                    if (gamePanel.getTileManager().getTile(tileNum1).isCollision() ||
                            gamePanel.getTileManager().getTile(tileNum2).isCollision()) {
                        entity.setCollisionOn(true);
                    }
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gamePanel.getTILE_SIZE();

                for (int i = 0; i < layers; ++i) {
                    tileNum1 = gamePanel.getTileManager().getMapTileNumber()[i][entityLeftCol][entityTopRow];
                    tileNum2 = gamePanel.getTileManager().getMapTileNumber()[i][entityLeftCol][entityBottomRow];

                    if (gamePanel.getTileManager().getTile(tileNum1).isCollision() ||
                            gamePanel.getTileManager().getTile(tileNum2).isCollision()) {
                        entity.setCollisionOn(true);
                    }
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gamePanel.getTILE_SIZE();

                for (int i = 0; i < layers; ++i) {
                    tileNum1 = gamePanel.getTileManager().getMapTileNumber()[i][entityRightCol][entityTopRow];
                    tileNum2 = gamePanel.getTileManager().getMapTileNumber()[i][entityRightCol][entityBottomRow];

                    if (gamePanel.getTileManager().getTile(tileNum1).isCollision() ||
                            gamePanel.getTileManager().getTile(tileNum2).isCollision()) {
                        entity.setCollisionOn(true);
                    }
                }
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {
        // Suppose the player isn't touching any object
        int index = -1;

        for (int i = 0; i < gamePanel.getObject().length; ++i) {
            if (gamePanel.getObject()[i] != null) {
                // Get entity's solid area position
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                // Get object's solid area position
                gamePanel.getObject()[i].getSolidArea().x = gamePanel.getObject()[i].getWorldX() + gamePanel.getObject()[i].getSolidArea().x;
                gamePanel.getObject()[i].getSolidArea().y = gamePanel.getObject()[i].getWorldY() + gamePanel.getObject()[i].getSolidArea().y;

                switch (entity.getDirection()) {
                    case "up" -> {
                        entity.getSolidArea().y -= entity.getSpeed();

                        // Check if 2 rectangle intersects (for collision)
                        if (entity.getSolidArea().intersects(gamePanel.getObject()[i].getSolidArea())) {
                            if (gamePanel.getObject()[i].isCollision()) {
                                entity.setCollisionOn(true);
                            }

                            // Only player can get object
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    case "down" -> {
                        entity.getSolidArea().y += entity.getSpeed();

                        // Check if 2 rectangle intersects (for collision)
                        if (entity.getSolidArea().intersects(gamePanel.getObject()[i].getSolidArea())) {
                            if (gamePanel.getObject()[i].isCollision()) {
                                entity.setCollisionOn(true);
                            }

                            // Only player can get object
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    case "left" -> {
                        entity.getSolidArea().x -= entity.getSpeed();

                        // Check if 2 rectangle intersects (for collision)
                        if (entity.getSolidArea().intersects(gamePanel.getObject()[i].getSolidArea())) {
                            if (gamePanel.getObject()[i].isCollision()) {
                                entity.setCollisionOn(true);
                            }

                            // Only player can get object
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    case "right" -> {
                        entity.getSolidArea().x += entity.getSpeed();

                        // Check if 2 rectangle intersects (for collision)
                        if (entity.getSolidArea().intersects(gamePanel.getObject()[i].getSolidArea())) {
                            if (gamePanel.getObject()[i].isCollision()) {
                                entity.setCollisionOn(true);
                            }

                            // Only player can get object
                            if (player) {
                                index = i;
                            }
                        }
                    }
                }

                // Reset solid area
                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();

                gamePanel.getObject()[i].getSolidArea().x = gamePanel.getObject()[i].getSolidAreaDefaultX();
                gamePanel.getObject()[i].getSolidArea().y = gamePanel.getObject()[i].getSolidAreaDefaultY();
            }
        }

        return index;
    }
}
