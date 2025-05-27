package paoo.game.panel;

import paoo.game.entity.Entity;
import paoo.game.entity.Player;
import paoo.game.handler.KeyHandler;
import paoo.game.main.*;
import paoo.game.object.ObjCarrot;
import paoo.game.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    // Screen Settings
    private final int ORIGINAL_TILE_SIZE = 16; // 16 x 16 tile pixel
    private final int SCALE = 3;

    protected final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;  // 48 x 48 pixel
    private final int MAX_SCREEN_COLUMN = 16;
    private final int MAX_SCREEN_ROW = 12;
    private final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMN; // 960 pixels
    private final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels

    BufferedImage tempScreen;
    Graphics2D g2;

    // World settings
    private final int MAX_WORLD_COLUMN = 96;
    private final int MAX_WORLD_ROW = 90;
    private final int MAX_MAP = 3;
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
    private Entity[] objects = new Entity[20];
    private Entity[] npc = new Entity[10];
    private Entity[] monster = new Entity[20];
    private ArrayList<Entity> entityList = new ArrayList<>();

    // Game state
    private int gameState;
    private final int TITLE_STATE = 0;
    private final int PLAY_STATE = 1;
    private final int PAUSE_STATE = 2;
    private final int DIALOG_STATE = 3;
    private final int INVENTORY_STATE = 4;
    private final int GAME_OVER_STATE = 6;
    private final int GAME_END_STATE = 7;
    private final int optionsState = 5;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObject();
        assetSetter.setNpc();
        assetSetter.setMonster();

        gameState = TITLE_STATE;

        tempScreen = new BufferedImage(getSCREEN_WIDTH(), getSCREEN_HEIGHT(), BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
    }

    public void retry() {
        stopMusic();
        playMusic(0);

        player.setDefaultPositions();
        player.restoreLife();

        assetSetter.setNpc();
        assetSetter.setObject();
        assetSetter.setMonster();
    }

    public void restart() {
        stopMusic();
        player.setDefaultValues();
        keyHandler.setCurrentLevel(1);
        player.getInventory().clear();

        assetSetter.setNpc();
        assetSetter.setObject();
        assetSetter.setMonster();
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public Entity[] getObjects() {
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

    public int getCurrentMap() {
        return currentMap;
    }

    public int getMAX_MAP() {
        return MAX_MAP;
    }

    public int getGAME_END_STATE() {
        return GAME_END_STATE;
    }

    public int getDIALOG_STATE() {
        return DIALOG_STATE;
    }

    public int getTITLE_STATE() {
        return TITLE_STATE;
    }

    public Entity[] getNpc() {
        return npc;
    }

    public Entity[] getMonster() {
        return monster;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public int getINVENTORY_STATE() {
        return INVENTORY_STATE;
    }

    public int getGAME_OVER_STATE() {
        return GAME_OVER_STATE;
    }

    public int getOptionsState() {
        return optionsState;
    }

    public Sound getMusic() {
        return music;
    }

    public Sound getSoundEffect() {
        return soundEffect;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void clearEntity(Entity[] obj) {
        for (int i = 0; i < obj.length; ++i) {
            if (obj[i] != null) {
                obj[i] = null;
            }
        }
    }

    public void update() {
        if (gameState == PLAY_STATE) {
            // Player
            player.update();

            // NPCs
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }

            // Monsters
            for (Entity entity : monster) {
                if (entity != null) {
                    entity.update();
                }
            }

            // Objects
            for (Entity object : objects) {
                if (object != null) {
                    if (object instanceof ObjCarrot) {
                        object.update();  // Call update on Carrot to possibly advance its stage
                    }
                }
            }

            if (keyHandler.getCurrentLevel() == 1) {
                if (currentMap != 0) {
                    clearEntity(npc);
                    clearEntity(monster);
                    clearEntity(objects);

                    currentMap = 0;
                    assetSetter.setObject();
                    assetSetter.setNpc();
                    assetSetter.setMonster();
                    player.setDefaultPosition(45, 26);
                }
            }

            if (keyHandler.getCurrentLevel() == 2) {
                if (currentMap != 1) {
                    clearEntity(npc);
                    clearEntity(monster);
                    clearEntity(objects);

                    currentMap = 1;
                    assetSetter.setObject();
                    assetSetter.setNpc();
                    assetSetter.setMonster();
                    player.setDefaultPosition(30, 30);
                }
            }

            if (keyHandler.getCurrentLevel() == 3) {
                if (currentMap != 2) {
                    clearEntity(npc);
                    clearEntity(monster);
                    clearEntity(objects);

                    currentMap = 2;
                    assetSetter.setObject();
                    assetSetter.setNpc();
                    assetSetter.setMonster();
                    player.setDefaultPosition(44, 65);
                }
            }
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

        // Title screen
        if (gameState == TITLE_STATE) {
            ui.draw(graphics2D);
        }
        // Others
        else {
            // Tile
            tileManager.draw(graphics2D);

            entityList.clear();
            entityList.add(player);

            // Npc
            for (Entity npc : npc) {
                if (npc != null) {
                    entityList.add(npc);
                }
            }

            // Monster
            for (Entity monster : monster) {
                if (monster != null) {
                    entityList.add(monster);
                }
            }

            // Object
            for (Entity object : objects) {
                if (object != null) {
                    entityList.add(object);
                }
            }

            // Sort by priority first, then by Y position for entities with same priority
            entityList.sort((e1, e2) -> {
                if (e1.getRenderPriority() != e2.getRenderPriority()) {
                    return Integer.compare(e1.getRenderPriority(), e2.getRenderPriority());
                }
                return Integer.compare(e1.getWorldY(), e2.getWorldY());
            });

            // Draw entities
            for (Entity entity : entityList) {
                entity.draw(graphics2D);
            }

            ui.draw(graphics2D);
        }

        // Debug
        if (keyHandler.isShowDebugText()) {
            graphics2D.setFont(new Font("Arial", Font.PLAIN, 20));
            graphics2D.setColor(Color.WHITE);

            int x = 10;
            int y = 500;
            int lineHeight = 20;

            graphics2D.drawString("WorldX " + player.getWorldX(), x, y);
            y += lineHeight;

            graphics2D.drawString("WorldY " + player.getWorldY(), x, y);
            y += lineHeight;

            graphics2D.drawString("Col " + (player.getWorldX() + player.getSolidArea().x) / TILE_SIZE, x, y);
            y += lineHeight;

            graphics2D.drawString("Row " + (player.getWorldY() + player.getSolidArea().y) / TILE_SIZE, x, y);
        }

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
                repaint(); // Calls paintComponent method

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
