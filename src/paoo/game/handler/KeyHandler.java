package paoo.game.handler;

import paoo.game.database.SaveLoad;
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

    private int currentLevel = 1;

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

    public boolean isShowDebugText() {
        return showDebugText;
    }

    public void setEnterPressed(boolean enterPressed) {
        isEnterPressed = enterPressed;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int level) {
        this.currentLevel = level;
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
        // Game end state
        else if (gamePanel.getGameState() == gamePanel.getGAME_END_STATE()) {
            gameEndState(code);
        }

        // Options state
        else if (gamePanel.getGameState() == gamePanel.getOptionsState()) {
            optionsState(code);
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
                    gamePanel.setGameState(gamePanel.getPLAY_STATE());
                    gamePanel.playMusic(0);

                    SaveLoad saveLoad = new SaveLoad(gamePanel);
                    saveLoad.load();
                }
                case 2 -> System.exit(0);
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

        if (code == KeyEvent.VK_ESCAPE) {
            gamePanel.setGameState(gamePanel.getOptionsState());
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
                case 1 -> {
                    System.exit(0);
                }
            }
        }
    }

    public void optionsState(int code) {

        if(code == KeyEvent.VK_ESCAPE) {
            gamePanel.setGameState(gamePanel.getPLAY_STATE());
        }

        if(code == KeyEvent.VK_ENTER) {
            isEnterPressed = true;
        }

        int maxCommandNum = 0;
        switch(gamePanel.getUi().getSubState()) {
            case 0: maxCommandNum = 6;break;
            case 4: maxCommandNum = 1;break;
        }

        if(code == KeyEvent.VK_W) {
            gamePanel.getUi().setCommandNum(gamePanel.getUi().getCommandNum() - 1);
            gamePanel.playSE(5);
            if(gamePanel.getUi().getCommandNum() < 0) {
                gamePanel.getUi().setCommandNum(maxCommandNum);
            }
        }

        if(code == KeyEvent.VK_S) {
            gamePanel.getUi().setCommandNum(gamePanel.getUi().getCommandNum() + 1);
            gamePanel.playSE(5);
            if(gamePanel.getUi().getCommandNum() > maxCommandNum) {
                gamePanel.getUi().setCommandNum(0);
            }
        }

        if(code == KeyEvent.VK_A) {
            if(gamePanel.getUi().getSubState() == 0) {
                if (gamePanel.getUi().getCommandNum() == 1 && gamePanel.getMusic().getVolumeScale() > 0) {
                    gamePanel.getMusic().setVolumeScale(gamePanel.getMusic().getVolumeScale() - 1);
                    gamePanel.getMusic().checkVolume();
                    gamePanel.playSE(5);
                }
                if (gamePanel.getUi().getCommandNum() == 2 && gamePanel.getSoundEffect().getVolumeScale() > 0) {
                    gamePanel.getSoundEffect().setVolumeScale(gamePanel.getSoundEffect().getVolumeScale() - 1);
                    gamePanel.playSE(5);
                }
            }

        }

        if(code == KeyEvent.VK_D) {
            if (gamePanel.getUi().getSubState() == 0) {
                if (gamePanel.getUi().getCommandNum() == 1 && gamePanel.getMusic().getVolumeScale() < 5) {
                    gamePanel.getMusic().setVolumeScale(gamePanel.getMusic().getVolumeScale() + 1);
                    gamePanel.getMusic().checkVolume();
                    gamePanel.playSE(5);
                }
                if (gamePanel.getUi().getCommandNum() == 2 && gamePanel.getSoundEffect().getVolumeScale() < 5) {
                    gamePanel.getSoundEffect().setVolumeScale(gamePanel.getSoundEffect().getVolumeScale() + 1);
                    gamePanel.playSE(5);
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
    }
}
