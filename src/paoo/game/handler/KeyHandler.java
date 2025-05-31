package paoo.game.handler;

import paoo.game.database.SaveLoad;
import paoo.game.panel.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles keyboard input for the game, managing different input behaviors based on the current game state.
 * This class implements KeyListener to capture and process keyboard events for movement, menu navigation,
 * and game state transitions.
 */
public class KeyHandler implements KeyListener {
    /**
     * Reference to the main game panel
     */
    private GamePanel gamePanel;

    /**
     * Flag indicating if the UP key (W) is currently pressed
     */
    private boolean isUpPressed;

    /**
     * Flag indicating if the DOWN key (S) is currently pressed
     */
    private boolean isDownPressed;

    /**
     * Flag indicating if the LEFT key (A) is currently pressed
     */
    private boolean isLeftPressed;

    /**
     * Flag indicating if the RIGHT key (D) is currently pressed
     */
    private boolean isRightPressed;

    /**
     * Flag indicating if the ENTER key is currently pressed
     */
    private boolean isEnterPressed = false;

    /**
     * Current level for debug purposes
     */
    private int currentLevel = 1;

    /**
     * Debug flag to show/hide debug text information
     */
    private boolean showDebugText = false;

    /**
     * Constructs a new KeyHandler with a reference to the game panel.
     */
    public KeyHandler() {
        this.gamePanel = GamePanel.getInstance();
    }

    /**
     * Returns whether the UP key is currently pressed.
     *
     * @return true if UP key is pressed, false otherwise
     */
    public boolean isUpPressed() {
        return isUpPressed;
    }

    /**
     * Returns whether the DOWN key is currently pressed.
     *
     * @return true if DOWN key is pressed, false otherwise
     */
    public boolean isDownPressed() {
        return isDownPressed;
    }

    /**
     * Returns whether the LEFT key is currently pressed.
     *
     * @return true if LEFT key is pressed, false otherwise
     */
    public boolean isLeftPressed() {
        return isLeftPressed;
    }

    /**
     * Returns whether the RIGHT key is currently pressed.
     *
     * @return true if RIGHT key is pressed, false otherwise
     */
    public boolean isRightPressed() {
        return isRightPressed;
    }

    /**
     * Returns whether the ENTER key is currently pressed.
     *
     * @return true if ENTER key is pressed, false otherwise
     */
    public boolean isEnterPressed() {
        return isEnterPressed;
    }

    /**
     * Returns whether debug text should be displayed.
     *
     * @return true if debug text should be shown, false otherwise
     */
    public boolean isShowDebugText() {
        return showDebugText;
    }

    /**
     * Sets the ENTER key pressed state.
     *
     * @param enterPressed true to set ENTER as pressed, false otherwise
     */
    public void setEnterPressed(boolean enterPressed) {
        isEnterPressed = enterPressed;
    }

    /**
     * Gets the current level (used for debug purposes).
     *
     * @return the current level number
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Sets the current level (used for debug purposes).
     *
     * @param level the level number to set
     */
    public void setCurrentLevel(int level) {
        this.currentLevel = level;
    }

    /**
     * Handles key typed events. Currently not implemented.
     *
     * @param e the key event
     */
    @Override
    public void keyTyped(KeyEvent e) { // Won't be used
    }

    /**
     * Handles key pressed events by delegating to appropriate state-specific methods
     * based on the current game state.
     *
     * @param e the key event containing information about the pressed key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Title state
        if (gamePanel.getGameState() == gamePanel.getTITLE_STATE()) {
            gamePanel.getUi().setPlayTime(0);
            titleState(code);
        }
        // Play state
        else if (gamePanel.getGameState() == gamePanel.getPLAY_STATE()) {
            playState(code);
        }
        // Pause state
        else if (gamePanel.getGameState() == gamePanel.getPAUSE_STATE()) {
            pauseState(code);
        }
        // Dialogue state
        else if (gamePanel.getGameState() == gamePanel.getDIALOG_STATE()) {
            dialogueState(code);
        }
        // Inventory state
        else if (gamePanel.getGameState() == gamePanel.getINVENTORY_STATE()) {
            inventoryState(code);
        }
        // Game over state
        else if (gamePanel.getGameState() == gamePanel.getGAME_OVER_STATE()) {
            gameOverState(code);
        }
        // Game end state
        else if (gamePanel.getGameState() == gamePanel.getGAME_END_STATE()) {
            gameEndState(code);
        }
        // Options state
        else if (gamePanel.getGameState() == gamePanel.getOptionsState()) {
            optionsState(code);
        }
    }

    /**
     * Handles key input when the game is in the title screen state.
     * Manages menu navigation (W/S keys) and menu selection (ENTER key).
     *
     * @param code the key code of the pressed key
     */
    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            gamePanel.getUi().setCommandNum(gamePanel.getUi().getCommandNum() - 1);

            if (gamePanel.getUi().getCommandNum() < 0) {
                gamePanel.getUi().setCommandNum(2);
            }
            gamePanel.playSE(2);
        }
        if (code == KeyEvent.VK_S) {
            gamePanel.getUi().setCommandNum(gamePanel.getUi().getCommandNum() + 1);

            if (gamePanel.getUi().getCommandNum() > 2) {
                gamePanel.getUi().setCommandNum(0);
            }
            gamePanel.playSE(2);
        }

        if (code == KeyEvent.VK_ENTER) {
            switch (gamePanel.getUi().getCommandNum()) {
                case 0 -> {
                    gamePanel.setGameState(gamePanel.getPLAY_STATE());
                    gamePanel.playMusic(0);
                }
                case 1 -> {
                    gamePanel.setGameState(gamePanel.getPLAY_STATE());
                    gamePanel.playMusic(0);

                    SaveLoad saveLoad = new SaveLoad();
                    saveLoad.load();
                }
                case 2 -> System.exit(0);
            }
        }
    }

    /**
     * Handles key input during active gameplay.
     * Manages player movement (WASD), game pause (P), inventory access (I),
     * interaction (ENTER), options menu (ESCAPE), and debug features (T, 1-3, R).
     *
     * @param code the key code of the pressed key
     */
    public void playState(int code) {
        if (code == KeyEvent.VK_W) {
            isUpPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            isDownPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            isLeftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            isRightPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gamePanel.setGameState(gamePanel.getPAUSE_STATE());
        }
        if (code == KeyEvent.VK_ENTER) {
            isEnterPressed = true;
        }
        if (code == KeyEvent.VK_I) {
            gamePanel.setGameState(gamePanel.getINVENTORY_STATE());
        }

        if (code == KeyEvent.VK_ESCAPE) {
            gamePanel.setGameState(gamePanel.getOptionsState());
        }

        // Debug
        if (code == KeyEvent.VK_T) {
            showDebugText = !showDebugText;
        }
        if (showDebugText) {
            if (code == KeyEvent.VK_1) {
                currentLevel = 1;
            }
            if (code == KeyEvent.VK_2) {
                currentLevel = 2;
            }
            if (code == KeyEvent.VK_3) {
                currentLevel = 3;
            }
        }
        if (code == KeyEvent.VK_R) {
            gamePanel.getTileManager().loadMap(new String[]{"/maps/Map1.txt", "/maps/Map1_Objects.txt"}, 0);
        }
    }

    /**
     * Handles key input when the game is paused.
     * Only responds to the P key to unpause the game.
     *
     * @param code the key code of the pressed key
     */
    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gamePanel.setGameState(gamePanel.getPLAY_STATE());
        }
    }

    /**
     * Handles key input during dialogue sequences.
     * ENTER key advances dialogue and handles teleportation if ready.
     * ESCAPE key opens the options menu.
     *
     * @param code the key code of the pressed key
     */
    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            if (gamePanel.getPlayer().isTeleportReady()) {
                gamePanel.getPlayer().teleportToNextLevel();
                gamePanel.getPlayer().setTeleportReady(false);
            }

            gamePanel.setGameState(gamePanel.getPLAY_STATE());
        }

        if (code == KeyEvent.VK_ESCAPE) {
            gamePanel.setGameState(gamePanel.getOptionsState());
        }
    }

    /**
     * Handles key input when the inventory is open.
     * WASD keys navigate through inventory slots, I key closes inventory.
     *
     * @param code the key code of the pressed key
     */
    public void inventoryState(int code) {
        if (code == KeyEvent.VK_I) {
            gamePanel.setGameState(gamePanel.getPLAY_STATE());
        }
        if (code == KeyEvent.VK_W) {
            if (gamePanel.getUi().getSlotRow() != 0) {
                gamePanel.getUi().setSlotRow(gamePanel.getUi().getSlotRow() - 1);
                gamePanel.playSE(2);
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gamePanel.getUi().getSlotCol() != 0) {
                gamePanel.getUi().setSlotCol(gamePanel.getUi().getSlotCol() - 1);
                gamePanel.playSE(2);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gamePanel.getUi().getSlotRow() != 3) {
                gamePanel.getUi().setSlotRow(gamePanel.getUi().getSlotRow() + 1);
                gamePanel.playSE(2);
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gamePanel.getUi().getSlotCol() != 4) {
                gamePanel.getUi().setSlotCol(gamePanel.getUi().getSlotCol() + 1);
                gamePanel.playSE(2);
            }
        }
    }

    /**
     * Handles key input when the game over screen is displayed.
     * W/S keys navigate menu options, ENTER key selects option (retry or return to title).
     *
     * @param code the key code of the pressed key
     */
    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gamePanel.getUi().setCommandNum(gamePanel.getUi().getCommandNum() - 1);

            if (gamePanel.getUi().getCommandNum() < 0) {
                gamePanel.getUi().setCommandNum(1);
            }
            gamePanel.playSE(2);
        }
        if (code == KeyEvent.VK_S) {
            gamePanel.getUi().setCommandNum(gamePanel.getUi().getCommandNum() + 1);

            if (gamePanel.getUi().getCommandNum() > 1) {
                gamePanel.getUi().setCommandNum(0);
            }
            gamePanel.playSE(2);
        }

        if (code == KeyEvent.VK_ENTER) {
            switch (gamePanel.getUi().getCommandNum()) {
                case 0 -> {
                    gamePanel.setGameState(gamePanel.getPLAY_STATE());
                    gamePanel.retry();
                }
                case 1 -> {
                    gamePanel.setGameState(gamePanel.getTITLE_STATE());
                    gamePanel.restart();
                }
            }
        }
    }

    /**
     * Handles key input when the game end screen is displayed.
     * W/S keys navigate menu options, ENTER key selects option (return to title or exit).
     *
     * @param code the key code of the pressed key
     */
    public void gameEndState(int code) {
        if (code == KeyEvent.VK_W) {
            gamePanel.getUi().setCommandNum(gamePanel.getUi().getCommandNum() - 1);

            if (gamePanel.getUi().getCommandNum() < 0) {
                gamePanel.getUi().setCommandNum(1);
            }
            gamePanel.playSE(2);
        }
        if (code == KeyEvent.VK_S) {
            gamePanel.getUi().setCommandNum(gamePanel.getUi().getCommandNum() + 1);

            if (gamePanel.getUi().getCommandNum() > 1) {
                gamePanel.getUi().setCommandNum(0);
            }
            gamePanel.playSE(2);
        }

        if (code == KeyEvent.VK_ENTER) {
            switch (gamePanel.getUi().getCommandNum()) {
                case 0 -> {
                    gamePanel.setGameState(gamePanel.getTITLE_STATE());
                    gamePanel.restart();
                }
                case 1 -> System.exit(0);
            }
        }
    }

    /**
     * Handles key input when the options menu is open.
     * Manages navigation through options submenus, volume adjustments (A/D keys),
     * and menu selection. ESCAPE key returns to gameplay.
     *
     * @param code the key code of the pressed key
     */
    public void optionsState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gamePanel.setGameState(gamePanel.getPLAY_STATE());
        }

        if (code == KeyEvent.VK_ENTER) {
            isEnterPressed = true;
        }

        int maxCommandNum = switch (gamePanel.getUi().getSubState()) {
            case 0 -> 5;
            case 2, 4 -> 1;
            default -> 0;
        };

        if (code == KeyEvent.VK_W) {
            gamePanel.getUi().setCommandNum(gamePanel.getUi().getCommandNum() - 1);

            gamePanel.playSE(5);
            if (gamePanel.getUi().getCommandNum() < 0) {
                gamePanel.getUi().setCommandNum(maxCommandNum);
            }
        }

        if (code == KeyEvent.VK_S) {
            gamePanel.getUi().setCommandNum(gamePanel.getUi().getCommandNum() + 1);
            gamePanel.playSE(5);
            if (gamePanel.getUi().getCommandNum() > maxCommandNum) {
                gamePanel.getUi().setCommandNum(0);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gamePanel.getUi().getSubState() == 0) {
                if (gamePanel.getUi().getCommandNum() == 0 && gamePanel.getMusic().getVolumeScale() > 0) {
                    gamePanel.getMusic().setVolumeScale(gamePanel.getMusic().getVolumeScale() - 1);
                    gamePanel.getMusic().checkVolume();
                    gamePanel.playSE(5);
                }
                if (gamePanel.getUi().getCommandNum() == 1 && gamePanel.getSoundEffect().getVolumeScale() > 0) {
                    gamePanel.getSoundEffect().setVolumeScale(gamePanel.getSoundEffect().getVolumeScale() - 1);
                    gamePanel.playSE(5);
                }
            }

        }

        if (code == KeyEvent.VK_D) {
            if (gamePanel.getUi().getSubState() == 0) {
                if (gamePanel.getUi().getCommandNum() == 0 && gamePanel.getMusic().getVolumeScale() < 5) {
                    gamePanel.getMusic().setVolumeScale(gamePanel.getMusic().getVolumeScale() + 1);
                    gamePanel.getMusic().checkVolume();
                    gamePanel.playSE(5);
                }
                if (gamePanel.getUi().getCommandNum() == 1 && gamePanel.getSoundEffect().getVolumeScale() < 5) {
                    gamePanel.getSoundEffect().setVolumeScale(gamePanel.getSoundEffect().getVolumeScale() + 1);
                    gamePanel.playSE(5);
                }
            }
        }
    }

    /**
     * Handles key released events to reset movement flags when keys are released.
     * Only handles movement keys (WASD).
     *
     * @param e the key event containing information about the released key
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            isUpPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            isDownPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            isLeftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            isRightPressed = false;
        }
    }
}