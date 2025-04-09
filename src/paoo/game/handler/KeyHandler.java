package paoo.game.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean isUpPressed;
    private boolean isDownPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;

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

    @Override
    public void keyTyped(KeyEvent e) { // Won't be used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

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
