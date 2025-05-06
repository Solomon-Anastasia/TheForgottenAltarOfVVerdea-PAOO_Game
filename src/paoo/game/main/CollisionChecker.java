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

        switch (entity.getDirection()) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gamePanel.getTILE_SIZE();

                for (int i = 0; i < layers; ++i) {
                    tileNum1 = gamePanel.getTileManager().getMapTileNumber(i, entityLeftCol, entityTopRow);
                    tileNum2 = gamePanel.getTileManager().getMapTileNumber(i, entityRightCol, entityTopRow);

                    if (gamePanel.getTileManager().getTile(tileNum1).isCollision() ||
                            gamePanel.getTileManager().getTile(tileNum2).isCollision()) {
                        entity.setCollisionOn(true);
                    }
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gamePanel.getTILE_SIZE();

                for (int i = 0; i < layers; ++i){
                tileNum1 = gamePanel.getTileManager().getMapTileNumber(i, entityLeftCol, entityBottomRow);
                tileNum2 = gamePanel.getTileManager().getMapTileNumber(i, entityRightCol, entityBottomRow);

                if (gamePanel.getTileManager().getTile(tileNum1).isCollision() ||
                        gamePanel.getTileManager().getTile(tileNum2).isCollision()) {
                    entity.setCollisionOn(true);
                    }
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gamePanel.getTILE_SIZE();

                for (int i = 0; i < layers; ++i) {
                    tileNum1 = gamePanel.getTileManager().getMapTileNumber(i, entityLeftCol, entityTopRow);
                    tileNum2 = gamePanel.getTileManager().getMapTileNumber(i, entityLeftCol, entityBottomRow);

                    if (gamePanel.getTileManager().getTile(tileNum1).isCollision() ||
                            gamePanel.getTileManager().getTile(tileNum2).isCollision()) {
                        entity.setCollisionOn(true);
                    }
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gamePanel.getTILE_SIZE();

                for (int i = 0; i < layers; ++i) {

                    tileNum1 = gamePanel.getTileManager().getMapTileNumber(i, entityRightCol, entityTopRow);
                    tileNum2 = gamePanel.getTileManager().getMapTileNumber(i, entityRightCol, entityBottomRow);

                    if (gamePanel.getTileManager().getTile(tileNum1).isCollision() ||
                            gamePanel.getTileManager().getTile(tileNum2).isCollision()) {
                        entity.setCollisionOn(true);
                    }
                }
            }
        }

    }
}
