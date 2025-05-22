package paoo.game.handler;

import paoo.game.panel.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private GamePanel gamePanel;

    private boolean isUpPressed;
    private boolean isDownPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;
    private boolean isEnterPressed = false;

    private boolean isLevel1 = true;
    private boolean isLevel2 = false;
    private boolean isLevel3 = false;

    // Debug
    private boolean showDebugText = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean isUpPressed() {
        return isUpPressed;
    }

    public boolean isDownPressed() {
        return isDownPressed;
    }

    public boolean isLeftPressed() {
        return isLeftPressed;
    }

    public boolean isRightPressed() {
        return isRightPressed;
    }

    public boolean isEnterPressed() {
        return isEnterPressed;
    }

    public boolean isLevel1() {
        return isLevel1;
    }

    public boolean isLevel2() {
        return isLevel2;
    }

    public boolean isLevel3() {
        return isLevel3;
    }

    public boolean isShowDebugText() {
        return showDebugText;
    }

    public void setEnterPressed(boolean enterPressed) {
        isEnterPressed = enterPressed;
    }

    public void setLevel1(boolean level1) {
        isLevel1 = level1;
    }

    public void setLevel2(boolean level2) {
        isLevel2 = level2;
    }

    public void setLevel3(boolean level3) {
        isLevel3 = level3;
    }

    @Override
    public void keyTyped(KeyEvent e) { // Won't be used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Title state
        if (gamePanel.getGameState() == gamePanel.getTITLE_STATE()) {
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
    }

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
                    // TODO: Add load from database
                }
                case 2 -> {
                    System.exit(0);
                }
            }
        }
    }

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
        // TODO: delete
//        if (code == KeyEvent.VK_1) {
//            isLevel1 = true;
//
//            isLevel2 = false;
//            isLevel3 = false;
//        }
//        if (code == KeyEvent.VK_2) {
//            isLevel2 = true;
//
//            isLevel1 = false;
//            isLevel3 = false;
//        }
//        if (code == KeyEvent.VK_3) {
//            isLevel3 = true;
//
//            isLevel1 = false;
//            isLevel2 = false;
//        }
        if (code == KeyEvent.VK_I) {
            gamePanel.setGameState(gamePanel.getINVENTORY_STATE());
        }

        // Debug
        if (code == KeyEvent.VK_T) {
            showDebugText = !showDebugText;
        }
        if (code == KeyEvent.VK_R) {
            gamePanel.getTileManager().loadMap(new String[]{"/maps/Map1.txt", "/maps/Map1_Objects.txt"}, 0);
        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gamePanel.setGameState(gamePanel.getPLAY_STATE());
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            if (gamePanel.getPlayer().isTeleportReady()) {
                gamePanel.getPlayer().teleportToNextLevel();
                gamePanel.getPlayer().setTeleportReady(false);
            }

            gamePanel.setGameState(gamePanel.getPLAY_STATE());
        }
    }

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
        if (code == KeyEvent.VK_2) {
            isLevel2 = false;
        }

        if (code == KeyEvent.VK_3) {
            isLevel3 = false;
        }
    }
}
