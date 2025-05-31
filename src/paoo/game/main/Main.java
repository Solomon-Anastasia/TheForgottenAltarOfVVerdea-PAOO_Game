package paoo.game.main;

import paoo.game.database.DbManager;
import paoo.game.panel.GamePanel;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Main entry point for "The Forgotten Altar of Verdea" game application.
 * This class is responsible for initializing the game window, setting up the database,
 * and starting the main game loop. It creates and configures the JFrame window
 * that will contain the game panel and handles initial application setup.
 * <p>
 * The Main class performs the following initialization sequence:
 * 1. Creates and configures the main game window
 * 2. Initializes the game panel and adds it to the window
 * 3. Sets up the database tables for player data persistence
 * 4. Starts the game setup and main game thread
 *
 * @author Solomon Anastasia
 * @author Diaconu Adina-Iuliana
 */
public class Main {
    /**
     * The main application window that contains the game panel.
     * This JFrame serves as the primary container for the entire game interface.
     */
    public static JFrame window;

    /**
     * Main entry point for the application.
     * Initializes the game window, sets up the database, and starts the game.
     * <p>
     * This method performs the following operations in sequence:
     * 1. Creates a new JFrame window with appropriate settings
     * 2. Initializes the GamePanel that handles all game logic and rendering
     * 3. Configures window properties (title, size, behavior)
     * 4. Sets up the database tables for game data persistence
     * 5. Initializes game assets and starts the main game thread
     * <p>
     * Window Configuration:
     * - Title: "The forgotten Altar of Verdea"
     * - Non-resizable to maintain consistent game layout
     * - Centered on screen
     * - Closes application when window is closed
     * - Size determined by GamePanel's preferred size via pack()
     * <p>
     * Database Setup:
     * - Creates player table if it doesn't exist
     * - Handles SQL exceptions gracefully with error logging
     *
     * @param args command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        // Initialize the main game window
        window = new JFrame();

        // Add the game panel to the window and size appropriately
        window.add(GamePanel.getInstance());
        GamePanel.getInstance().setupGame();
        window.pack(); // Size window to fit the preferred size of GamePanel

        // Configure window behavior and appearance
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close app when window closes
        window.setResizable(false); // Prevent resizing to maintain game layout integrity
        window.setTitle("The forgotten Altar of Verdea"); // Set application title
        window.setLocationRelativeTo(null); // Center window on screen
        window.setVisible(true); // Make the window visible to the user

        // Initialize database for game data persistence
        DbManager dbManager = new DbManager();
        try {
            dbManager.createPlayerTable(); // Create player data table if it doesn't exist
        } catch (SQLException e) {
            // Log database initialization errors but continue with game startup
            System.out.println(e.getMessage());
        }

        // Initialize game assets and start the main game loop
//        GamePanel.getInstance().setupGame(); // Load game assets, initialize entities and game state
        GamePanel.getInstance().startGameThread(); // Begin the main game update/render loop
    }
}