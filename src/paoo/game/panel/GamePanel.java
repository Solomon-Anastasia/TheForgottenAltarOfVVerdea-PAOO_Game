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

/**
 * Main game panel that handles the core game loop, rendering, and state management.
 * This class extends JPanel and implements Runnable to provide a dedicated game thread.
 * <p>
 * GamePanel manages:
 * - Game rendering and graphics
 * - Entity updates and interactions
 * - Game state transitions
 * - Audio playback
 * - Input handling coordination
 * - Multiple game maps and levels
 */
public class GamePanel extends JPanel implements Runnable {
    // Screen Settings
    /**
     * Original tile size in pixels before scaling
     */
    private final int ORIGINAL_TILE_SIZE = 16; // 16 x 16 tile pixel
    /**
     * Scale factor applied to original tile size
     */
    private final int SCALE = 3;

    /**
     * Final tile size after scaling (48x48 pixels)
     */
    protected final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;  // 48 x 48 pixel
    /**
     * Number of tile columns visible on screen
     */
    private final int MAX_SCREEN_COLUMN = 16;
    /**
     * Number of tile rows visible on screen
     */
    private final int MAX_SCREEN_ROW = 12;
    /**
     * Total screen width in pixels (960px)
     */
    private final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMN; // 960 pixels
    /**
     * Total screen height in pixels (576px)
     */
    private final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels

    /**
     * Temporary screen buffer for rendering optimization
     */
    BufferedImage tempScreen;
    /**
     * Graphics2D context for the temporary screen
     */
    Graphics2D g2;

    // World settings
    /**
     * Maximum number of columns in the game world
     */
    private final int MAX_WORLD_COLUMN = 96;
    /**
     * Maximum number of rows in the game world
     */
    private final int MAX_WORLD_ROW = 90;
    /**
     * Maximum number of available maps
     */
    private final int MAX_MAP = 3;
    /**
     * Index of the currently active map
     */
    private int currentMap = 0;

    // FPS
    /**
     * Target frames per second for the game loop
     */
    private final int FPS = 60;

    // System components
    /**
     * Manages tile rendering and collision
     */
    private TileManager tileManager;
    /**
     * Handles keyboard input
     */
    private KeyHandler keyHandler;
    /**
     * Manages background music
     */
    private Sound music;
    /**
     * Manages sound effects
     */
    private Sound soundEffect;
    /**
     * Handles collision detection between entities
     */
    private CollisionChecker collisionChecker;
    /**
     * Places entities and objects in the game world
     */
    private AssetSetter assetSetter;
    /**
     * Main game thread for the game loop
     */
    private Thread gameThread;

    // UI
    /**
     * User interface manager
     */
    private UI ui;

    // Entity and objects
    /**
     * The player character
     */
    private Player player;
    /**
     * Array of interactive objects in the game world
     */
    private Entity[] objects = new Entity[20];
    /**
     * Array of non-player characters
     */
    private Entity[] npc = new Entity[10];
    /**
     * Array of monster entities
     */
    private Entity[] monster = new Entity[20];
    /**
     * Dynamic list for entity rendering order
     */
    private ArrayList<Entity> entityList = new ArrayList<>();

    // Game state constants and current state
    /**
     * Current game state
     */
    private int gameState;
    /**
     * Game state: Title screen
     */
    private final int TITLE_STATE = 0;
    /**
     * Game state: Active gameplay
     */
    private final int PLAY_STATE = 1;
    /**
     * Game state: Game paused
     */
    private final int PAUSE_STATE = 2;
    /**
     * Game state: Dialogue interaction
     */
    private final int DIALOG_STATE = 3;
    /**
     * Game state: Inventory screen
     */
    private final int INVENTORY_STATE = 4;
    /**
     * Game state: Game over screen
     */
    private final int GAME_OVER_STATE = 6;
    /**
     * Game state: Game completion screen
     */
    private final int GAME_END_STATE = 7;
    /**
     * Game state: Options/settings menu
     */
    private final int optionsState = 5;

    /**
     * Check if the current state of the game is loaded from database
     */
    private boolean loadedFromSave = false;

    /**
     * Constructs a new GamePanel with default settings.
     * Initializes the panel dimensions, background color, and input handling.
     */
    private GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    private static GamePanel instance = null;

    public static GamePanel getInstance() {
        if (instance == null) {
            instance = new GamePanel();
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }

    /**
     * Initializes the game world and sets up initial game state.
     * Places all entities, objects, and NPCs in their starting positions.
     * Sets up the temporary screen buffer for optimized rendering.
     */
    public void setupGame() {
        if (tileManager == null) tileManager = new TileManager();
        if (keyHandler == null) keyHandler = new KeyHandler();
        if (music == null) music = new Sound();
        if (soundEffect == null) soundEffect = new Sound();
        if (collisionChecker == null) collisionChecker = new CollisionChecker();
        if (assetSetter == null) assetSetter = new AssetSetter();
        if (ui == null) ui = new UI();
        if (player == null) player = new Player(keyHandler);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.requestFocusInWindow();

        assetSetter.setObject();
        assetSetter.setNpc();
        assetSetter.setMonster();

        gameState = TITLE_STATE;

        tempScreen = new BufferedImage(getSCREEN_WIDTH(), getSCREEN_HEIGHT(), BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
    }

    /**
     * Resets the current level while preserving player progress and inventory.
     * Used when the player dies and wants to retry the current level.
     */
    public void retry() {
        stopMusic();
        playMusic(0);

        player.setDefaultPositions();
        player.restoreLife();

        assetSetter.setNpc();
        assetSetter.setObject();
        assetSetter.setMonster();
    }

    /**
     * Completely restarts the game from the beginning.
     * Resets player stats, inventory, and returns to level 1.
     */
    public void restart() {
        stopMusic();
        player.setDefaultValues();
        keyHandler.setCurrentLevel(1);
        player.getInventory().clear();

        assetSetter.setNpc();
        assetSetter.setObject();
        assetSetter.setMonster();
    }

    /**
     * Sets the current game state.
     *
     * @param gameState the new game state to set
     */
    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    /**
     * Gets the array of interactive objects.
     *
     * @return array of Entity objects representing interactive items
     */
    public Entity[] getObjects() {
        return objects;
    }

    /**
     * Gets the tile size in pixels.
     *
     * @return the scaled tile size (48 pixels)
     */
    public int getTILE_SIZE() {
        return TILE_SIZE;
    }

    /**
     * Gets the screen width in pixels.
     *
     * @return the screen width (960 pixels)
     */
    public int getSCREEN_WIDTH() {
        return SCREEN_WIDTH;
    }

    /**
     * Gets the screen height in pixels.
     *
     * @return the screen height (576 pixels)
     */
    public int getSCREEN_HEIGHT() {
        return SCREEN_HEIGHT;
    }

    /**
     * Gets the maximum number of world columns.
     *
     * @return the maximum world column count
     */
    public int getMAX_WORLD_COLUMN() {
        return MAX_WORLD_COLUMN;
    }

    /**
     * Gets the maximum number of world rows.
     *
     * @return the maximum world row count
     */
    public int getMAX_WORLD_ROW() {
        return MAX_WORLD_ROW;
    }

    /**
     * Gets the player entity.
     *
     * @return the Player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the tile manager.
     *
     * @return the TileManager instance
     */
    public TileManager getTileManager() {
        return tileManager;
    }

    /**
     * Gets the collision checker.
     *
     * @return the CollisionChecker instance
     */
    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    /**
     * Gets the UI manager.
     *
     * @return the UI instance
     */
    public UI getUi() {
        return ui;
    }

    /**
     * Gets the current game state.
     *
     * @return the current game state value
     */
    public int getGameState() {
        return gameState;
    }

    /**
     * Gets the play state constant.
     *
     * @return the PLAY_STATE value
     */
    public int getPLAY_STATE() {
        return PLAY_STATE;
    }

    /**
     * Gets the pause state constant.
     *
     * @return the PAUSE_STATE value
     */
    public int getPAUSE_STATE() {
        return PAUSE_STATE;
    }

    /**
     * Gets the current map index.
     *
     * @return the current map number
     */
    public int getCurrentMap() {
        return currentMap;
    }

    /**
     * Gets the maximum number of maps.
     *
     * @return the maximum map count
     */
    public int getMAX_MAP() {
        return MAX_MAP;
    }

    /**
     * Gets the game end state constant.
     *
     * @return the GAME_END_STATE value
     */
    public int getGAME_END_STATE() {
        return GAME_END_STATE;
    }

    /**
     * Gets the dialog state constant.
     *
     * @return the DIALOG_STATE value
     */
    public int getDIALOG_STATE() {
        return DIALOG_STATE;
    }

    /**
     * Gets the title state constant.
     *
     * @return the TITLE_STATE value
     */
    public int getTITLE_STATE() {
        return TITLE_STATE;
    }

    /**
     * Gets the array of NPCs.
     *
     * @return array of NPC entities
     */
    public Entity[] getNpc() {
        return npc;
    }

    /**
     * Gets the array of monsters.
     *
     * @return array of monster entities
     */
    public Entity[] getMonster() {
        return monster;
    }

    /**
     * Gets the key handler.
     *
     * @return the KeyHandler instance
     */
    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    /**
     * Gets the inventory state constant.
     *
     * @return the INVENTORY_STATE value
     */
    public int getINVENTORY_STATE() {
        return INVENTORY_STATE;
    }

    /**
     * Gets the game over state constant.
     *
     * @return the GAME_OVER_STATE value
     */
    public int getGAME_OVER_STATE() {
        return GAME_OVER_STATE;
    }

    /**
     * Gets the options state constant.
     *
     * @return the optionsState value
     */
    public int getOptionsState() {
        return optionsState;
    }

    /**
     * Gets the music sound manager.
     *
     * @return the background music Sound instance
     */
    public Sound getMusic() {
        return music;
    }

    /**
     * Gets the sound effect manager.
     *
     * @return the sound effects Sound instance
     */
    public Sound getSoundEffect() {
        return soundEffect;
    }

    /**
     * Sets the current form of the game (from bd or not)
     */
    public void setLoadedFromSave(boolean loadedFromSave) {
        this.loadedFromSave = loadedFromSave;
    }

    /**
     * Starts the main game thread.
     * This begins the game loop for updates and rendering.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Clears all entities from the specified array by setting them to null.
     * Used for cleanup when switching maps or restarting.
     *
     * @param obj the entity array to clear
     */
    public void clearEntity(Entity[] obj) {
        for (int i = 0; i < obj.length; ++i) {
            if (obj[i] != null) {
                obj[i] = null;
            }
        }
    }

    /**
     * Updates all game entities and handles level transitions.
     * Called once per frame during the PLAY_STATE.
     * <p>
     * Updates include:
     * - Player movement and actions
     * - NPC behavior and movement
     * - Monster AI and attacks
     * - Object state changes (like carrot growth)
     * - Map transitions between levels
     */
    public void update() {
        if (gameState == PLAY_STATE) {
            // Update player
            player.update();

            // Update NPCs
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }

            // Update monsters
            for (Entity entity : monster) {
                if (entity != null) {
                    entity.update();
                }
            }

            // Update objects (special handling for carrots)
            for (Entity object : objects) {
                if (object != null) {
                    if (object instanceof ObjCarrot) {
                        object.update();  // Call update on Carrot to possibly advance its stage
                    }
                }
            }

            // Handle level transitions
            if (keyHandler.getCurrentLevel() == 1) {
                if (currentMap != 0) {
                    clearEntity(npc);
                    clearEntity(monster);
                    clearEntity(objects);

                    currentMap = 0;
                    assetSetter.setObject();
                    assetSetter.setNpc();
                    assetSetter.setMonster();

                    if (!loadedFromSave) {
                        player.setDefaultPosition(45, 26);
                    } else {
                        loadedFromSave = false;
                    }
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

                    if (!loadedFromSave) {
                        player.setDefaultPosition(30, 30);
                    } else {
                        loadedFromSave = false;
                    }
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

                    if (!loadedFromSave) {
                        player.setDefaultPosition(44, 65);
                    } else {
                        loadedFromSave = false;
                    }
                }
            }
        }
    }

    /**
     * Plays background music on loop.
     *
     * @param i the index of the music file to play
     */
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    /**
     * Stops the currently playing background music.
     */
    public void stopMusic() {
        music.stop();
    }

    /**
     * Plays a sound effect once.
     *
     * @param i the index of the sound effect file to play
     */
    public void playSE(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }

    /**
     * Renders all game elements to the screen.
     * Handles different rendering based on current game state.
     * <p>
     * Rendering order:
     * 1. Tiles (background)
     * 2. Entities (sorted by render priority and Y position)
     * 3. UI elements (overlay)
     * 4. Debug information (if enabled)
     *
     * @param graphics the Graphics context provided by Swing
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        // Title screen rendering
        if (gameState == TITLE_STATE) {
            ui.draw(graphics2D);
        }
        // Game world rendering
        else {
            // Draw tiles (background layer)
            tileManager.draw(graphics2D);

            // Prepare entity list for sorted rendering
            entityList.clear();
            entityList.add(player);

            // Add NPCs to render list
            for (Entity npc : npc) {
                if (npc != null) {
                    entityList.add(npc);
                }
            }

            // Add monsters to render list
            for (Entity monster : monster) {
                if (monster != null) {
                    entityList.add(monster);
                }
            }

            // Add objects to render list
            for (Entity object : objects) {
                if (object != null) {
                    entityList.add(object);
                }
            }

            // Sort entities by render priority, then by Y position for depth
            entityList.sort((e1, e2) -> {
                if (e1.getRenderPriority() != e2.getRenderPriority()) {
                    return Integer.compare(e1.getRenderPriority(), e2.getRenderPriority());
                }
                return Integer.compare(e1.getWorldY(), e2.getWorldY());
            });

            // Draw all entities in sorted order
            for (Entity entity : entityList) {
                entity.draw(graphics2D);
            }

            // Draw UI overlay
            ui.draw(graphics2D);
        }

        // Debug information rendering
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

        graphics2D.dispose(); // Free graphics resources
    }

    /**
     * Main game loop implementation.
     * Maintains consistent 60 FPS by using delta timing.
     * <p>
     * The loop:
     * 1. Calculates time delta since last frame
     * 2. Updates game state when enough time has passed
     * 3. Triggers repaint for rendering
     * 4. Monitors and displays FPS for performance debugging
     */
    @Override
    public void run() {
        double drawInterval = (double) 1_000_000_000 / FPS;  // Nanoseconds per frame
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

            if (timer >= 1_000_000_000) { // Every second
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
}