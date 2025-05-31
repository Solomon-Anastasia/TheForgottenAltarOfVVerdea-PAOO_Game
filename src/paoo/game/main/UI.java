package paoo.game.main;

import paoo.game.database.SaveLoad;
import paoo.game.entity.Entity;
import paoo.game.object.ObjHeart;
import paoo.game.panel.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Handles all user interface rendering and management for the game.
 * This class is responsible for drawing various game screens including title screen,
 * game HUD, menus, dialog boxes, and managing user interface state.
 */
public class UI {
    /**
     * Reference to the main game panel
     */
    private GamePanel gamePanel;

    /**
     * Graphics2D object for rendering
     */
    private Graphics2D g2;

    /**
     * Image for full heart (2 health points)
     */
    private BufferedImage heart_full;

    /**
     * Image for half heart (1 health point)
     */
    private BufferedImage heart_half;

    /**
     * Image for empty heart (0 health points)
     */
    private BufferedImage heart_blank;

    /**
     * Font used for UI text
     */
    private Font arial20;

    /**
     * Flag indicating if a message should be displayed
     */
    private boolean messageOn = false;

    /**
     * Current message text to display
     */
    private String message = "";

    /**
     * Counter for message display duration
     */
    private int messageCounter = 0;

    /**
     * Current dialogue text being displayed
     */
    private String currentDialogue;

    /**
     * Current selected command/menu option
     */
    private int commandNum = 0;

    /**
     * Current column position in inventory grid
     */
    private int slotCol = 0;

    /**
     * Current row position in inventory grid
     */
    private int slotRow = 0;

    /**
     * Current sub-state for menus (used in options menu)
     */
    private int subState = 0;

    /**
     * Total play time in seconds
     */
    private double playTime;

    /**
     * Formatter for displaying play time with 2 decimal places
     */
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    /**
     * Constructs a new UI instance.
     * Initializes fonts, loads heart images, and sets up the UI system.
     */
    public UI() {
        this.gamePanel = GamePanel.getInstance();

        arial20 = new Font("Arial", Font.BOLD, 20);

        // Create HUD object
        Entity heart = new ObjHeart();
        heart_full = heart.getImage1();
        heart_half = heart.getImage2();
        heart_blank = heart.getImage3();
    }

    /**
     * Gets the current command number (selected menu option).
     *
     * @return The current command number
     */
    public int getCommandNum() {
        return commandNum;
    }

    /**
     * Gets the current play time in seconds.
     *
     * @return The current play time
     */
    public double getPlayTime() {
        return playTime;
    }

    /**
     * Gets the current inventory slot row.
     *
     * @return The current slot row
     */
    public int getSlotRow() {
        return slotRow;
    }

    /**
     * Gets the current inventory slot column.
     *
     * @return The current slot column
     */
    public int getSlotCol() {
        return slotCol;
    }

    /**
     * Sets the current command number (selected menu option).
     *
     * @param commandNum The command number to set
     */
    public void setCommandNum(int commandNum) {
        this.commandNum = commandNum;
    }

    /**
     * Sets the current play time.
     *
     * @param playTime The play time to set in seconds
     */
    public void setPlayTime(double playTime) {
        this.playTime = playTime;
    }

    /**
     * Sets the current dialogue text to be displayed.
     *
     * @param currentDialogue The dialogue text to display
     */
    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

    /**
     * Sets the current inventory slot column.
     *
     * @param slotCol The slot column to set
     */
    public void setSlotCol(int slotCol) {
        this.slotCol = slotCol;
    }

    /**
     * Sets the current inventory slot row.
     *
     * @param slotRow The slot row to set
     */
    public void setSlotRow(int slotRow) {
        this.slotRow = slotRow;
    }

    /**
     * Gets the current sub-state for menu navigation.
     *
     * @return The current sub-state
     */
    public int getSubState() {
        return subState;
    }

    /**
     * Displays a temporary message on screen.
     * The message will automatically disappear after a short duration.
     *
     * @param message The message text to display
     */
    public void showMessage(String message) {
        this.message = message;
        messageOn = true;
    }

    /**
     * Main drawing method that renders the appropriate UI based on current game state.
     * This method delegates to specific drawing methods based on the current game state.
     *
     * @param g2 The Graphics2D object to draw with
     */
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

    /**
     * Loads an image from the specified resource path.
     *
     * @param path The resource path to the image file
     * @return The loaded BufferedImage, or null if loading failed
     */
    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Draws the title screen with game logo and main menu options.
     * Includes "New Game", "Load Game", and "Exit" options with navigation cursor.
     */
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

    /**
     * Draws the game timer in the top-right corner of the screen.
     * Updates and displays the current play time in seconds.
     */
    public void drawTimer() {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
        playTime += (double) 1 / 60;
        g2.drawString("Time: " + decimalFormat.format(playTime), gamePanel.getTILE_SIZE() * 13, gamePanel.getTILE_SIZE());
    }

    /**
     * Draws the pause screen with "PAUSED" text centered on screen.
     */
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        g2.setColor(Color.WHITE);

        String text = "PAUSED";
        int x = getXCenteredText(text);
        int y = gamePanel.getSCREEN_HEIGHT() / 2;
        g2.drawString(text, x, y);
    }

    /**
     * Draws the dialog screen with a text box containing the current dialogue.
     * Supports multi-line text separated by newline characters.
     */
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

    /**
     * Draws the player's health bar using heart images.
     * Shows current health with full hearts, half hearts, and empty hearts.
     */
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

    /**
     * Draws health bars for monsters currently visible on screen.
     * Regular monsters show small health bars above them, while boss monsters
     * show larger health bars at the bottom of the screen with their name.
     */
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

    /**
     * Draws the inventory screen showing player's items in a grid layout.
     * Includes item icons, quantities, selection cursor, and item descriptions.
     */
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

    /**
     * Draws the game over screen with retry and quit options.
     * Displays "Game Over!" text with menu navigation.
     */
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

    /**
     * Draws the game completion screen showing victory message and final play time.
     * Includes options to return to title screen or quit the game.
     */
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

    /**
     * Draws the options/settings screen with various sub-menus.
     * Handles navigation between different option categories.
     */
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
            case 0 -> options_top(frameX, frameY);
            case 1 -> option_control(frameX, frameY);
            case 2 -> options_endGameConfirmation(frameX, frameY);
            case 3 -> options_savedGame(frameX, frameY);
        }

        gamePanel.getKeyHandler().setEnterPressed(false);
    }

    /**
     * Draws the main options menu with settings for music, sound effects, controls, etc.
     *
     * @param frameX The x-coordinate of the options frame
     * @param frameY The y-coordinate of the options frame
     */
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
                SaveLoad saveLoad = new SaveLoad();
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
        g2.drawRect(textX, textY, 120, 24); // 120 / 5 = 24
        int volumeWidth = 24 * gamePanel.getMusic().getVolumeScale();
        g2.fillRect(textX, textY, volumeWidth, 24);

        // Sound effect
        textY += gamePanel.getTILE_SIZE();
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gamePanel.getSoundEffect().getVolumeScale();
        g2.fillRect(textX, textY, volumeWidth, 24);

    }

    /**
     * Draws the save game confirmation screen.
     *
     * @param frameX The x-coordinate of the options frame
     * @param frameY The y-coordinate of the options frame
     */
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

    /**
     * Draws the control scheme information screen.
     *
     * @param frameX The x-coordinate of the options frame
     * @param frameY The y-coordinate of the options frame
     */
    public void option_control(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        textY = frameY + gamePanel.getTILE_SIZE();
        textX = frameX + gamePanel.getTILE_SIZE();
        textY += gamePanel.getTILE_SIZE();

        g2.drawString("Move", textX, textY);
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("Confirm/Attack", textX, textY);
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("Pause", textX, textY);
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("Options", textX, textY);

        textX = frameX + gamePanel.getTILE_SIZE() * 4;
        textY = frameY + gamePanel.getTILE_SIZE() * 2;
        g2.drawString("WASD", textX, textY);
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("ENTER", textX, textY);
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("P", textX, textY);
        textY += gamePanel.getTILE_SIZE();
        g2.drawString("ESC", textX, textY);

        // Back
        textX = frameX + gamePanel.getTILE_SIZE();
        textY = frameY + gamePanel.getTILE_SIZE();
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isEnterPressed()) {
                subState = 0;
                commandNum = 2;
            }
        }
    }

    /**
     * Draws the end game confirmation dialog.
     *
     * @param frameX The x-coordinate of the options frame
     * @param frameY The y-coordinate of the options frame
     */
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

    /**
     * Calculates the inventory item index based on current slot position.
     * Uses a 5-column grid layout to convert row/column coordinates to array index.
     *
     * @return The calculated item index in the inventory array
     */
    public int getItemIndexLSot() {
        return slotCol + (slotRow * 5);
    }

    /**
     * Draws a styled sub-window with rounded corners and border.
     * Used for dialog boxes, menus, and other UI panels.
     *
     * @param x      The x-coordinate of the window
     * @param y      The y-coordinate of the window
     * @param width  The width of the window
     * @param height The height of the window
     */
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

    /**
     * Calculates the x-coordinate to center text horizontally on the screen.
     *
     * @param text The text string to center
     * @return The x-coordinate for centered text positioning
     */
    public int getXCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gamePanel.getSCREEN_WIDTH() / 2 - length / 2;
    }

    /**
     * Calculates the x-coordinate to align text to the right from a given position.
     * Used for right-aligning numbers and other UI elements.
     *
     * @param text  The text string to align
     * @param tailX The right edge x-coordinate to align to
     * @return The x-coordinate for right-aligned text positioning
     */
    public int getXAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        return tailX - length;
    }
}
