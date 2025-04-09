package paoo.game.entity;

import paoo.game.handler.KeyHandler;
import paoo.game.panel.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void update() {
        if (keyHandler.isUpPressed() || keyHandler.isDownPressed()
                || keyHandler.isRightPressed() || keyHandler.isLeftPressed()) {
            if (keyHandler.isUpPressed()) {
                direction = "up";
                y -= speed;
            } else if (keyHandler.isDownPressed()) {
                direction = "down";
                y += speed;
            } else if (keyHandler.isLeftPressed()) {
                direction = "left";
                x -= speed;
            } else if (keyHandler.isRightPressed()) {
                direction = "right";
                x += speed;
            }

            spriteCounter++;
            if (spriteCounter > 5) {
                switch (spriteNumber) {
                    case 1 -> spriteNumber = 2;
                    case 2 -> spriteNumber = 3;
                    case 3 -> spriteNumber = 4;
                    case 4 -> spriteNumber = 5;
                    case 5 -> spriteNumber = 6;
                    case 6 -> spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        switch (direction) {
            case "up" -> image = getBufferedImage(image, up1, up2, up3, up4, up5, up6);
            case "down" -> image = getBufferedImage(image, down1, down2, down3, down4, down5, down6);
            case "left" -> image = getBufferedImage(image, left1, left2, left3, left4, left5, left6);
            case "right" -> image = getBufferedImage(image, right1, right2, right3, right4, right5, right6);
        }

        graphics2D.drawImage(image, x, y, gamePanel.getTILE_SIZE(), gamePanel.getTILE_SIZE(), null);
    }

    private BufferedImage getBufferedImage(BufferedImage image, BufferedImage down1, BufferedImage down2, BufferedImage down3, BufferedImage down4, BufferedImage down5, BufferedImage down6) {
        switch (spriteNumber) {
            case 1 -> image = down1;
            case 2 -> image = down2;
            case 3 -> image = down3;
            case 4 -> image = down4;
            case 5 -> image = down5;
            case 6 -> image = down6;
        }
        return image;
    }

    public void getPlayerImage() {
        try {
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right_2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right_3.png")));
            right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right_4.png")));
            right5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right_5.png")));
            right6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right_6.png")));

            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left_2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left_3.png")));
            left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left_4.png")));
            left5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left_5.png")));
            left6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left_6.png")));

            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up_2.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up_3.png")));
            up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up_4.png")));
            up5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up_5.png")));
            up6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up_6.png")));

            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down_2.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down_3.png")));
            down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down_4.png")));
            down5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down_5.png")));
            down6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down_6.png")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
