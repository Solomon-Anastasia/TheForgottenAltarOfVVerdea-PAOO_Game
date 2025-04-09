package paoo.game.panel;

import paoo.game.entity.Player;
import paoo.game.handler.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Screen Settings
    private final int ORIGINAL_TILE_SIZE = 16; // 16 x 16 tile pixel
    private final int SCALE = 3;
    private final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;  // 48 x 48 pixel

    private final int MAX_SCREEN_COLUMN = 16;
    private final int MAX_SCREEN_ROW = 12;

    // TODO: change screen size based of map size
    private final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMN; // 768 pixels
    private final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576

    // FPS
    private final int FPS = 60;

    private KeyHandler keyHandler = new KeyHandler();
    private Thread gameThread;
    private Player player = new Player(this, keyHandler);

//    TODO: REMOVE
//    // Set player's default position
//    private int playerX = 100;
//    private int playerY = 100;
//    private int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public int getTILE_SIZE() {
        return TILE_SIZE;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        player.draw(graphics2D);

        graphics2D.dispose(); // Save some memory
    }

    @Override
    public void run() {
        double drawInterval = (double) 1_000_000_000 / FPS;  // Nanoseconds as time
        double delta = 0;

        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint(); // calls paintComponent method

                delta--;
                drawCount++;
            }

            if (timer >= 1_000_000_000) { // 1 second
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
}
