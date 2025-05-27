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

    // NPC or monster collision
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
