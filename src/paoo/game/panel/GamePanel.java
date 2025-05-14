package paoo.game.panel;

import paoo.game.entity.Player;
import paoo.game.handler.KeyHandler;
import paoo.game.main.AssetSetter;
import paoo.game.main.CollisionChecker;
import paoo.game.main.Sound;
import paoo.game.main.UI;
import paoo.game.object.ObjCarrot;
import paoo.game.object.SuperObject;
import paoo.game.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Screen Settings
    private final int ORIGINAL_TILE_SIZE = 16; // 16 x 16 tile pixel
    private final int SCALE = 3;

    protected final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;  // 48 x 48 pixel
    private final int MAX_SCREEN_COLUMN = 16;
    private final int MAX_SCREEN_ROW = 12;
    private final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMN; // 768 pixels
    private final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels

    // World settings
    // TODO: Change based of final map1
    private final int MAX_WORLD_COLUMN = 96;
    private final int MAX_WORLD_ROW = 90;
    private final int maxMap = 3;
    private int currentMap = 0;

    // FPS
    private final int FPS = 60;

    // System
    private TileManager tileManager = new TileManager(this);
    private KeyHandler keyHandler = new KeyHandler(this);
    private Sound music = new Sound();
    private Sound soundEffect = new Sound();
    private CollisionChecker collisionChecker = new CollisionChecker(this);
    private AssetSetter assetSetter = new AssetSetter(this);
    private Thread gameThread;

    // UI
    private UI ui = new UI(this);

    // Entity and objects
    private Player player = new Player(this, keyHandler);
    // TODO: Change number of objects
    // Nr. of max objects displayed
    private SuperObject[] objects = new SuperObject[10];

    // Game state
    private int gameState;
    private final int PLAY_STATE = 1;
    private final int PAUSE_STATE = 2;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObject();

        // TODO: Uncomment this after level is done
        // playMusic(0);

        gameState = PLAY_STATE;
    }

    public void setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public SuperObject[] getObjects() {
        return objects;
    }

    public int getTILE_SIZE() {
        return TILE_SIZE;
    }

    public int getSCREEN_WIDTH() {
        return SCREEN_WIDTH;
    }

    public int getSCREEN_HEIGHT() {
        return SCREEN_HEIGHT;
    }

    public int getMAX_WORLD_COLUMN() {
        return MAX_WORLD_COLUMN;
    }

    public int getMAX_WORLD_ROW() {
        return MAX_WORLD_ROW;
    }

    public Player getPlayer() {
        return player;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    public UI getUi() {
        return ui;
    }

    public int getGameState() {
        return gameState;
    }

    public int getPLAY_STATE() {
        return PLAY_STATE;
    }

    public int getPAUSE_STATE() {
        return PAUSE_STATE;
    }

    public int getCurrentMap(){return currentMap;}

    public int getMaxMap(){return maxMap;}

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        if (gameState == PLAY_STATE) {
            player.update();

            for (SuperObject object : objects) {
                if (object != null) {
                    if (object instanceof ObjCarrot) {
                        ((ObjCarrot) object).update();  // Call update on Carrot to possibly advance its stage
                    }
                }
            }

            if (keyHandler.isLevel2()) {
                if (currentMap != 1) {
                    currentMap = 1;
                    assetSetter.setObject();
                    player.setDefaultPosition(30, 30);
                }
            }
        }

        // TODO: Do the pause
        if (gameState == PAUSE_STATE) {

        }
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    // Sound effect
    public void playSE(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        tileManager.draw(graphics2D);

        for (SuperObject superObject : objects) {
            if (superObject != null) {
                superObject.draw(graphics2D, this);
            }
        }

        player.draw(graphics2D);
        ui.draw(graphics2D);

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
