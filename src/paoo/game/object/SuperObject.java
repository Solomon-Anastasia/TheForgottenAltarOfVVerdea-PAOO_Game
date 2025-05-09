package paoo.game.object;

import paoo.game.main.UtilityTool;
import paoo.game.panel.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    protected BufferedImage image;
    protected String name;
    protected boolean collision = false;
    protected int worldX;
    protected int worldY;

    // By default, all object is considered solid
    protected Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    protected int solidAreaDefaultX = 0;
    protected int solidAreaDefaultY = 0;

    // Default size
    protected int width = 16;
    protected int height = 16;

    protected UtilityTool utilityTool = new UtilityTool();

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setSolidArea(int xOffset, int yOffset, int width, int height) {
        this.solidArea = new Rectangle(xOffset, yOffset, width, height);
        this.solidAreaDefaultX = xOffset;
        this.solidAreaDefaultY = yOffset;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public boolean isCollision() {
        return collision;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y();

        // Boundary (so not all map is loaded because it's not needed => save memory)
        if (worldX + gamePanel.getTILE_SIZE() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getSCREEN_X()
                && worldX - gamePanel.getTILE_SIZE() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X()
                && worldY + gamePanel.getTILE_SIZE() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getSCREEN_Y()
                && worldY - gamePanel.getTILE_SIZE() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y()
        ) {
            graphics2D.drawImage(image, screenX, screenY, width, height, null);
        }
    }
}
