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

    private boolean isLevel2;

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

    public boolean isLevel2(){ return isLevel2;}

    public void setEnterPressed(boolean enterPressed) {
        isEnterPressed = enterPressed;
    }

    @Override
    public void keyTyped(KeyEvent e) { // Won't be used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Play state
        if (gamePanel.getGameState() == gamePanel.getPLAY_STATE()) {
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
            if (code == KeyEvent.VK_2) {
                isLevel2 = true;
            }
        }
        // Pause state
        else if (gamePanel.getGameState() == gamePanel.getPAUSE_STATE()) {
            if (code == KeyEvent.VK_P) {
                gamePanel.setGameState(gamePanel.getPLAY_STATE());
            }
        }
        // Dialogue state
        else if (gamePanel.getGameState() == gamePanel.getDIALOG_STATE()) {
            if (code == KeyEvent.VK_ENTER) {
                 gamePanel.setGameState(gamePanel.getPLAY_STATE());
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
        if(code == KeyEvent.VK_2) {
            isLevel2 = false;
        }
    }
}
