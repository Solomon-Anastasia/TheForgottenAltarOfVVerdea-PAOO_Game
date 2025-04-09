package paoo.game.entity;

import java.awt.image.BufferedImage;

public class Entity {
    protected int x;
    protected int y;
    protected int speed;

    protected BufferedImage up1, up2, up3, up4, up5, up6;
    protected BufferedImage down1, down2, down3, down4, down5, down6;
    protected BufferedImage left1, left2, left3, left4, left5, left6;
    protected BufferedImage right1, right2, right3, right4, right5, right6;

    protected String direction;

    protected int spriteCounter = 0;
    protected int spriteNumber = 1;
}
