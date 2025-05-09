package paoo.game.main;

import paoo.game.object.ObjCarrot;
import paoo.game.panel.GamePanel;

import java.awt.*;
import java.text.DecimalFormat;

public class UI {
    private GamePanel gamePanel;
    private Font arial20;
    private Font arial25;
    private ObjCarrot carrot;
    private boolean messageOn = false;
    private String message = "";
    private int messageCounter = 0;

    private boolean isGameFinished = false;

    private double playTime;
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial20 = new Font("Arial", Font.BOLD, 20);
        arial25 = new Font("Arial", Font.BOLD, 25);

        carrot = new ObjCarrot(gamePanel);
    }

    public void setGameFinished(boolean gameFinished) {
        isGameFinished = gameFinished;
    }

    // TODO: Display nr. of carrots player currently have
    public void draw(Graphics g2) {
        g2.setFont(arial20);
        g2.setColor(Color.WHITE);

        if (isGameFinished) {
            String text = "Level 1 finished!";
            int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x;
            int y;

            x = gamePanel.getSCREEN_WIDTH() / 2 - textLength / 2;
            y = gamePanel.getSCREEN_HEIGHT() / 2  - (gamePanel.getTILE_SIZE() * 3);
            g2.drawString(text, x, y);

            text = "Your time is " + decimalFormat.format(playTime) + "!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gamePanel.getSCREEN_WIDTH() / 2 - textLength / 2;
            y = gamePanel.getSCREEN_HEIGHT() / 2  + (gamePanel.getTILE_SIZE() * 4);
            g2.drawString(text, x, y);

            g2.setFont(arial25);
            g2.setColor(Color.BLACK);
            text = "Congratulation!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gamePanel.getSCREEN_WIDTH() / 2 - textLength / 2;
            y = gamePanel.getSCREEN_HEIGHT() / 2  + (gamePanel.getTILE_SIZE() * 2);
            g2.drawString(text, x, y);

            // Stop game
            gamePanel.setGameThread(null);
        } else {
            g2.drawImage(carrot.getImage(), gamePanel.getTILE_SIZE() / 2, gamePanel.getTILE_SIZE() / 2, carrot.getHeight() * 2, carrot.getWidth() * 2, null);
            g2.drawString("x" + gamePanel.getPlayer().getNrCarrots(), 60, 35);

            // Time (seconds)
            playTime += (double) 1 / 60;
            g2.drawString("Time: " + decimalFormat.format(playTime), gamePanel.getTILE_SIZE() * 13, gamePanel.getTILE_SIZE());

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
        }
    }

    public void showMessage(String message) {
        this.message = message;
        messageOn = true;
    }
}
