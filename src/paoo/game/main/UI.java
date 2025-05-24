package paoo.game.main;

import paoo.game.database.SaveLoad;
import paoo.game.entity.Entity;
import paoo.game.object.ObjCarrot;
import paoo.game.object.ObjHeart;
import paoo.game.panel.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
    private int subState = 0;

    private double playTime;
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial20 = new Font("Arial", Font.BOLD, 20);
        arial25 = new Font("Arial", Font.BOLD, 25);

        // Create HUD object
        Entity heart = new ObjHeart(gamePanel);
        heart_full = heart.getImage1();
        heart_half = heart.getImage2();
        heart_blank = heart.getImage3();
    }

    public int getCommandNum() {
        return commandNum;
    }

    public double getPlayTime() {
        return playTime;
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

    public void setPlayTime(double playTime) {
        this.playTime = playTime;
    }

    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

    public void setSlotCol(int slotCol) {
        this.slotCol = slotCol;
    }

    public void setSlotRow(int slotRow) {
        this.slotRow = slotRow;
    }

    public int getSubState() {
        return subState;
    }

    public void setSubState(int subState) {
        this.subState = subState;
    }

    public void showMessage(String message) {
        this.message = message;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial20);
        g2.setColor(Color.WHITE);

        if (gamePanel.getGameState() == gamePanel.getTITLE_STATE()) {
            drawTitleScreen();
        }
        if (gamePanel.getGameState() == gamePanel.getPLAY_STATE()) {
            drawMonsterLife();
            drawPlayerLife();
            drawTimer();
        }
        if (gamePanel.getGameState() == gamePanel.getPAUSE_STATE()) {
            drawMonsterLife();
            drawPlayerLife();
            drawPauseScreen();
        }
        if (gamePanel.getGameState() == gamePanel.getDIALOG_STATE()) {
            drawPlayerLife();
            drawMonsterLife();
            drawDialogScreen();
        }
        if (gamePanel.getGameState() == gamePanel.getINVENTORY_STATE()) {
            drawPlayerLife();
            drawInventory();
        }
        if (gamePanel.getGameState() == gamePanel.getGAME_OVER_STATE()) {
            gameOverScreen();
        }
        if (gamePanel.getGameState() == gamePanel.getGAME_END_STATE()) {
            gameEndScreen();
        }

        if (gamePanel.getGameState() == gamePanel.getOptionsState()) {
            drawOptionScreen();
        }

        // Message
        if (messageOn) {
            g2.setFont(g2.getFont().deriveFont(20F));
            g2.drawString(message, gamePanel.getTILE_SIZE() * 5, gamePanel.getTILE_SIZE() * 5);

            messageCounter++;
            if (messageCounter > 30) {
                messageCounter = 0;
                messageOn = false;
            }
        }
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

    public void drawTimer() {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
        playTime += (double) 1 / 60;
        g2.drawString("Time: " + decimalFormat.format(playTime), gamePanel.getTILE_SIZE() * 13, gamePanel.getTILE_SIZE());
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        g2.setColor(Color.WHITE);

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

    public void drawMonsterLife() {
        for (int i = 0; i < gamePanel.getMonster().length; ++i) {
            Entity monster = gamePanel.getMonster()[i];

            if (monster != null && monster.inCamera()) {
                if (monster.isHpBarOn() && !monster.isBoss()) {
                    double onScale = (double) gamePanel.getTILE_SIZE() / monster.getMaxLife();
                    double hpBarValue = onScale * monster.getLife();

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(monster.getScreenX() - 1, monster.getScreenY() - 16, gamePanel.getTILE_SIZE() + 2, 12);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int) hpBarValue, 10);

                    monster.setHpBarCounter(monster.getHpBarCounter() + 1);
                    if (monster.getHpBarCounter() > 600) {
                        monster.setHpBarCounter(0);
                        monster.setHpBarOn(false);
                    }
                } else if (monster.isBoss()) {
                    double onScale = (double) gamePanel.getTILE_SIZE() * 8 / monster.getMaxLife();
                    double hpBarValue = onScale * monster.getLife();

                    int x = gamePanel.getSCREEN_WIDTH() / 2 - gamePanel.getTILE_SIZE() * 4;
                    int y = gamePanel.getTILE_SIZE() * 10;

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(x - 1, y - 1, gamePanel.getTILE_SIZE() * 8 + 2, 22);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(x, y, (int) hpBarValue, 20);

                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
                    g2.setColor(Color.WHITE);
                    g2.drawString(monster.getName(), x + 4, y - 10);
                }
            }
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
        ArrayList<Entity> playerInventory = gamePanel.getPlayer().getInventory();
        for (int i = 0; i < playerInventory.size(); ++i) {
            g2.drawImage(playerInventory.get(i).getDown2(), slotX, slotY, null);

            // Display amount
            if (playerInventory.get(i).getAmount() > 1) {
                g2.setFont(g2.getFont().deriveFont(15F));

                int amountX;
                int amountY;

                String s = "" + playerInventory.get(i).getAmount();
                amountX = getXAlignToRightText(s, slotX + 44);
                amountY = slotY + gamePanel.getTILE_SIZE();

                // Shadow
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);

                // Number
                g2.setColor(Color.WHITE);
                g2.drawString(s, amountX - 3, amountY - 3);
            }

            slotX += slotSize;
            if ((i == 4) || (i == 9) || (i == 14)) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // Cursor
        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYStart + (slotSize * slotRow);

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

    public void gameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gamePanel.getSCREEN_WIDTH(), gamePanel.getSCREEN_HEIGHT());

        int x;
        int y;
        String text;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));

        // Shadow
        text = "Game Over!";
        g2.setColor(Color.BLACK);
        x = getXCenteredText(text);
        y = gamePanel.getTILE_SIZE() * 4;
        g2.drawString(text, x, y);

        // Main text
        g2.setColor(Color.WHITE);
        g2.drawString(text, x - 4, y - 4);

        // Retry
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));

        // Menu options
        String[] options = {"Retry", "Quit"};
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

    public void gameEndScreen() {
        // Semi-transparent dark background overlay
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gamePanel.getSCREEN_WIDTH(), gamePanel.getSCREEN_HEIGHT());

        int x;
        int y;
        String text;

        // Large "Game Completed!" title with shadow
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        text = "Game Completed!";
        g2.setColor(Color.BLACK);
        x = getXCenteredText(text);
        y = gamePanel.getTILE_SIZE() * 4;
        g2.drawString(text, x, y);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x - 4, y - 4);

        // Congratulatory message smaller font
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        text = "The altar is back glowing!";
        x = getXCenteredText(text);
        y += gamePanel.getTILE_SIZE() * 2;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // Display play time
        text = String.format("Your playing time is %.2f seconds", playTime);
        x = getXCenteredText(text);
        y += gamePanel.getTILE_SIZE();
        g2.drawString(text, x, y);

        String[] options = {"Back to title screen", "Quit"};
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

    public void drawOptionScreen() {

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20F));

        //Sub window

        int frameX = gamePanel.getTILE_SIZE() * 6;
        int frameY = gamePanel.getTILE_SIZE();
        int frameWidth = gamePanel.getTILE_SIZE() * 8;
        int frameHeight = gamePanel.getTILE_SIZE() * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                options_top(frameX, frameY);
                break;
            case 1:
                option_control(frameX, frameY);
                break;
            case 2:
                options_endGameConfirmation(frameX, frameY);
                break;
            case 3:
                options_savedGame(frameX, frameY);
        }

        gamePanel.getKeyHandler().setEnterPressed(false);
    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "Options";
        textX = getXCenteredText(text);
        textY = frameY + gamePanel.getTILE_SIZE();
        g2.drawString(text, textX, textY);

        textX = frameX + gamePanel.getTILE_SIZE();
        textY += gamePanel.getTILE_SIZE();

        // Music
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("Music", textX, textY);
        if (getCommandNum() == 0) {
            g2.drawString(">", textX - 25, textY);
        }

        // SE
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("SE", textX, textY);
        if (getCommandNum() == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        // Control
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("Control", textX, textY);
        if (getCommandNum() == 2) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isEnterPressed()) {
                subState = 1;
                commandNum = 0;
            }
        }

        // End Game
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("End Game", textX, textY);
        if (getCommandNum() == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isEnterPressed()) {
                subState = 2;
                commandNum = 0;
            }
        }

        // Save game
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("Save Game", textX, textY);
        if (getCommandNum() == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isEnterPressed()) {
                SaveLoad saveLoad = new SaveLoad(gamePanel);
                saveLoad.save();

                subState = 3;
                commandNum = 0;
            }
        }

        // Back
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("Back", textX, textY);
        if (getCommandNum() == 5) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isEnterPressed()) {
                gamePanel.setGameState(gamePanel.getPLAY_STATE());
                commandNum = 0;
            }
        }

        textX = frameX + (int) (gamePanel.getTILE_SIZE() * 5);
        textY = frameY + gamePanel.getTILE_SIZE() * 2 + 24;
        // Music volume

        textY += gamePanel.getTILE_SIZE() / 5;
        g2.drawRect(textX, textY, 120, 24); // 120 /5 = 24
        int volumeWidth = 24 * gamePanel.getMusic().getVolumeScale();
        g2.fillRect(textX, textY, volumeWidth, 24);

        // Sound effect

        textY += gamePanel.getTILE_SIZE();
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gamePanel.getSoundEffect().getVolumeScale();
        g2.fillRect(textX, textY, volumeWidth, 24);

    }

    public void options_savedGame(int frameX, int frameY) {
        int textX = frameX + gamePanel.getTILE_SIZE();
        int textY = frameY + gamePanel.getTILE_SIZE() * 3;

        currentDialogue = "Data saved successfully!";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Back
        textY = frameY + gamePanel.getTILE_SIZE() * 9;
        g2.drawString("Back", textX, textY);
        if (getCommandNum() == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isEnterPressed()) {
                commandNum = 4;
                subState = 0;
            }
        }
    }

    public void option_control(int frameX, int frameY) {

        int textX;
        int textY;

        // Title

        String text = "Control";
        textX = getXCenteredText(text);
        textY = frameY + gamePanel.getTILE_SIZE();

        //g2.drawString(text, textX, textY);

        textX = frameX + gamePanel.getTILE_SIZE();
        textY += gamePanel.getTILE_SIZE();

        g2.drawString("Move", textX, textY);
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("Confirm/Attack", textX, textY);
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("Pause", textX, textY);
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("Options", textX, textY);
        textY += gamePanel.getTILE_SIZE();

        textX = frameX + gamePanel.getTILE_SIZE() * 4;
        textY = frameY + gamePanel.getTILE_SIZE() * 2;
        g2.drawString("WASD", textX, textY);
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("ENTER", textX, textY);
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("P", textX, textY);
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("ESC", textX, textY);
        textY += gamePanel.getTILE_SIZE();

        // Back

        textX = frameX + gamePanel.getTILE_SIZE();
        textY = frameY + gamePanel.getTILE_SIZE();
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isEnterPressed() == true) {
                subState = 0;
                commandNum = 2;
            }
        }

    }

    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gamePanel.getTILE_SIZE();
        int textY = frameY + gamePanel.getTILE_SIZE();

        currentDialogue = "Quit the game and \nreturn to the title screen?";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Yes

        String text = "Yes";
        textX = getXCenteredText(text);
        textY += gamePanel.getTILE_SIZE() * 3;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isEnterPressed()) {
                gamePanel.stopMusic();
                subState = 0;
                gamePanel.setGameState(gamePanel.getTITLE_STATE());
            }
        }

        // No

        text = "No";
        textX = getXCenteredText(text);
        textY += gamePanel.getTILE_SIZE();
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isEnterPressed()) {
                subState = 0;
                commandNum = 3;
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

    public int getXAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        return tailX - length;
    }
}
