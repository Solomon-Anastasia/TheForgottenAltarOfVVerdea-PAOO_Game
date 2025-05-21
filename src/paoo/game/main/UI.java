package paoo.game.main;

import paoo.game.entity.Entity;
import paoo.game.object.ObjCarrot;
import paoo.game.object.ObjHeart;
import paoo.game.panel.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class UI {
    private GamePanel gamePanel;
    private Graphics2D g2;

    private BufferedImage heart_full;
    private BufferedImage heart_half;
    private BufferedImage heart_blank;

    private Font arial20;
    private Font arial25;

    private ObjCarrot carrot;

    private boolean messageOn = false;
    private String message = "";
    private int messageCounter = 0;
    private String currentDialogue;
    private int commandNum = 0;

    private int slotCol = 0;
    private int slotRow = 0;

    private boolean isGameFinished = false;

    // TODO: Don't forget to add time back
//    private double playTime;
//    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial20 = new Font("Arial", Font.BOLD, 20);
        arial25 = new Font("Arial", Font.BOLD, 25);

        // carrot = new ObjCarrot(gamePanel);

        // Create HUD object
        Entity heart = new ObjHeart(gamePanel);
        heart_full = heart.getImage1();
        heart_half = heart.getImage2();
        heart_blank = heart.getImage3();
    }

    public int getCommandNum() {
        return commandNum;
    }

    public int getSlotRow() {
        return slotRow;
    }

    public int getSlotCol() {
        return slotCol;
    }

    public void setCommandNum(int commandNum) {
        this.commandNum = commandNum;
    }

    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

    public void setGameFinished(boolean gameFinished) {
        isGameFinished = gameFinished;
    }

    public void setSlotCol(int slotCol) {
        this.slotCol = slotCol;
    }

    public void setSlotRow(int slotRow) {
        this.slotRow = slotRow;
    }

    public void showMessage(String message) {
        this.message = message;
        messageOn = true;
    }

    // TODO: Display nr. of carrots player currently have
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial20);
        g2.setColor(Color.WHITE);

        if (gamePanel.getGameState() == gamePanel.getTITLE_STATE()) {
            drawTitleScreen();
        }
        if (gamePanel.getGameState() == gamePanel.getPLAY_STATE()) {
            drawPlayerLife();
        }
        if (gamePanel.getGameState() == gamePanel.getPAUSE_STATE()) {
            drawPlayerLife();
            drawPauseScreen();
        }
        if (gamePanel.getGameState() == gamePanel.getDIALOG_STATE()) {
            drawPlayerLife();
            drawDialogScreen();
        }
        if (gamePanel.getGameState() == gamePanel.getINVENTORY_STATE()) {
            drawPlayerLife();
            drawInventory();
        }


        // TODO: ADD later only necessary
//        if (isGameFinished) {
//            String text = "Level 1 finished!";
//            int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//            int x;
//            int y;
//
//            x = gamePanel.getSCREEN_WIDTH() / 2 - textLength / 2;
//            y = gamePanel.getSCREEN_HEIGHT() / 2  - (gamePanel.getTILE_SIZE() * 3);
//            g2.drawString(text, x, y);
//
//            text = "Your time is " + decimalFormat.format(playTime) + "!";
//            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//            x = gamePanel.getSCREEN_WIDTH() / 2 - textLength / 2;
//            y = gamePanel.getSCREEN_HEIGHT() / 2  + (gamePanel.getTILE_SIZE() * 4);
//            g2.drawString(text, x, y);
//
//            g2.setFont(arial25);
//            g2.setColor(Color.BLACK);
//            text = "Congratulation!";
//            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//            x = gamePanel.getSCREEN_WIDTH() / 2 - textLength / 2;
//            y = gamePanel.getSCREEN_HEIGHT() / 2  + (gamePanel.getTILE_SIZE() * 2);
//            g2.drawString(text, x, y);
//
//            // Stop game
//            gamePanel.setGameThread(null);
//        } else {
//            g2.drawImage(carrot.getImage(), gamePanel.getTILE_SIZE() / 2, gamePanel.getTILE_SIZE() / 2, carrot.getHeight() * 2, carrot.getWidth() * 2, null);
//            g2.drawString("x" + gamePanel.getPlayer().getNrCarrots(), 60, 35);
//
//            // Timer (seconds)
//            playTime += (double) 1 / 60;
//            g2.drawString("Time: " + decimalFormat.format(playTime), gamePanel.getTILE_SIZE() * 13, gamePanel.getTILE_SIZE());
//
        // Message
        if (messageOn) {
            // TODO: Pick place for message
            g2.setFont(g2.getFont().deriveFont(20F));
            g2.drawString(message, gamePanel.getTILE_SIZE() * 5, gamePanel.getTILE_SIZE() * 5);

            messageCounter++;
            if (messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
//        }
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void drawTitleScreen() {
        // Title image + name
        BufferedImage menuImage = loadImage("/Titles/TitleScreen.png");
        g2.drawImage(menuImage, 0, 0, gamePanel.getSCREEN_WIDTH(), gamePanel.getSCREEN_HEIGHT(), null);

        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, 0, gamePanel.getSCREEN_WIDTH(), gamePanel.getSCREEN_HEIGHT());

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        String text = "The Forgotten Altar of Verdea";
        int x = getXCenteredText(text);
        int y = gamePanel.getTILE_SIZE() * 2;

        // Shadow
        g2.setColor(Color.GRAY);
        g2.drawString(text, x + 2, y + 2);

        // Main color
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));

        // Menu options
        String[] options = {"New Game", "Load Game", "Exit"};
        y += gamePanel.getTILE_SIZE() * 2;
        for (int i = 0; i < options.length; ++i) {
            x = getXCenteredText(options[i]);
            y += gamePanel.getTILE_SIZE();

            g2.drawString(options[i], x, y);
            if (commandNum == i) {
                g2.drawString(">", x - gamePanel.getTILE_SIZE() / 2, y);
            }
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        String text = "PAUSED";
        int x = getXCenteredText(text);
        int y = gamePanel.getSCREEN_HEIGHT() / 2;
        g2.drawString(text, x, y);
    }

    public void drawDialogScreen() {
        // Window
        int x = gamePanel.getTILE_SIZE() * 2;
        int y = gamePanel.getTILE_SIZE() / 2;
        int width = gamePanel.getSCREEN_WIDTH() - (gamePanel.getTILE_SIZE() * 4);
        int height = gamePanel.getTILE_SIZE() * 4;

        drawSubWindow(x, y, width, height);

        x += gamePanel.getTILE_SIZE();
        y += gamePanel.getTILE_SIZE();

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 35;
        }
    }

    public void drawPlayerLife() {
        int x = gamePanel.getTILE_SIZE() / 2;
        int y = gamePanel.getTILE_SIZE() / 2;
        int i = 0;

        // Draw max file
        while (i < (gamePanel.getPlayer().getMaxLife() / 2)) {
            g2.drawImage(heart_blank, x, y, null);

            ++i;
            x += gamePanel.getTILE_SIZE() + 5;
        }

        // Reset
        x = gamePanel.getTILE_SIZE() / 2;
        y = gamePanel.getTILE_SIZE() / 2;
        i = 0;

        // Draw current life
        while (i < gamePanel.getPlayer().getLife()) {
            g2.drawImage(heart_half, x, y, null);
            ++i;

            if (i < gamePanel.getPlayer().getLife()) {
                g2.drawImage(heart_full, x, y, null);
            }

            ++i;
            x += gamePanel.getTILE_SIZE() + 5;
        }
    }

    public void drawInventory() {
        // Frame
        int frameX = gamePanel.getTILE_SIZE() * 9;
        int frameY = gamePanel.getTILE_SIZE();
        int frameWidth = gamePanel.getTILE_SIZE() * 6;
        int frameHeight = gamePanel.getTILE_SIZE() * 5;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slot
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gamePanel.getTILE_SIZE() + 3;

        // Draw player's items
        for (int i = 0; i < gamePanel.getPlayer().getInventory().size(); ++i) {
            g2.drawImage(gamePanel.getPlayer().getInventory().get(i).getDown2(), slotX, slotY, null);
            slotX += slotSize;

            if ((i == 4) || (i == 9) || (i == 14)) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // Cursor
        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYStart + (slotSize * slotRow);
        ;
        int cursorWidth = gamePanel.getTILE_SIZE();
        int cursorHeight = gamePanel.getTILE_SIZE();

        // Draw cursor
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // Description frame
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight + 10;
        int dframeWidth = frameWidth;
        int dframeHeight = gamePanel.getTILE_SIZE() * 3;

        // Draw description text
        int textX = dFrameX + 20;
        int textY = dFrameY + gamePanel.getTILE_SIZE();
        g2.setFont(g2.getFont().deriveFont(15F));

        int itemIndex = getItemIndexLSot();
        if (itemIndex < gamePanel.getPlayer().getInventory().size()) {
            drawSubWindow(dFrameX, dFrameY, dframeWidth, dframeHeight);

            for (String line : gamePanel.getPlayer().getInventory().get(itemIndex).getDescription().split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }

    public int getItemIndexLSot() {
        return slotCol + (slotRow * 5);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        // New color
        Color color = new Color(0, 0, 0, 210);

        g2.setColor(color);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gamePanel.getSCREEN_WIDTH() / 2 - length / 2;
    }

    public int getXAlignToRight(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        return tailX - length;
    }
}
