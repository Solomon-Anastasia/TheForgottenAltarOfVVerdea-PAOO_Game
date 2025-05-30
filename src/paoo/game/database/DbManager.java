package paoo.game.database;

import java.sql.*;
import java.util.ArrayList;

/**
 * The {@code DbManager} class provides an interface to manage the database operations
 * related to saving and loading player data for the game.
 * <p>
 * It connects to a SQLite database, creates the required table if it doesn't exist,
 * and provides methods to persist and retrieve player state.
 */
public class DbManager {
    /**
     * The active connection to the SQLite database.
     */
    private final Connection connection;

    /**
     * A {@code Statement} object for executing SQL statements.
     */
    private final Statement stmt;

    /**
     * Constructs a {@code DbManager} instance and establishes a connection
     * to the SQLite database file named {@code game.db}.
     *
     * @throws RuntimeException if the JDBC driver is not found or the connection fails
     */
    public DbManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:game.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the {@code Player} table in the database if it does not already exist.
     * The table includes columns for player level, life, time, and inventory items.
     *
     * @throws SQLException if an SQL error occurs during table creation
     */
    public void createPlayerTable() throws SQLException {
        String sqlCreateTable = """
                CREATE TABLE IF NOT EXISTS Player(
                    ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    Level INTEGER,
                    Max_life INTEGER,
                    Life INTEGER,
                    Time DOUBLE,
                    ItemName TEXT
                );
                """;
        stmt.execute(sqlCreateTable);
    }

    /**
     * Saves player data into the {@code Player} table.
     *
     * @param level    the player's level
     * @param maxLife  the player's maximum life
     * @param life     the player's current life
     * @param time     the game time
     * @param itemName the serialized inventory string (e.g., "Potion:2,Sword:1")
     */
    public void saveData(int level, int maxLife, int life, double time, String itemName) {
        String sqlInsert = """
                INSERT INTO Player (Level, Max_life, Life, Time, ItemName) VALUES
                    (?, ?, ?, ?, ?);
                """;

        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlInsert);

            pstmt.setInt(1, level);
            pstmt.setInt(2, maxLife);
            pstmt.setInt(3, life);
            pstmt.setDouble(4, time);
            pstmt.setString(5, itemName);

            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Loads the most recently saved player data from the {@code Player} table.
     *
     * @return a {@link DataStorage} object containing the loaded data,
     * or {@code null} if no data is found or an error occurs
     */
    public DataStorage loadData() {
        ResultSet rs;

        String selectPlayer = """
                SELECT * FROM Player ORDER BY ID DESC LIMIT 1;
                """;

        try {
            rs = stmt.executeQuery(selectPlayer);

            if (rs.next()) {
                int level = rs.getInt("Level");
                int maxLife = rs.getInt("Max_life");
                int life = rs.getInt("Life");
                double time = rs.getDouble("Time");
                String itemName = rs.getString("ItemName");

                DataStorage data = new DataStorage();
                data.setLevel(level);
                data.setMaxLife(maxLife);
                data.setLife(life);
                data.setTime(time);

                // Parse inventory string into item names and amounts
                if (itemName != null && !itemName.isEmpty()) {
                    String[] entries = itemName.split(",");

                    ArrayList<String> itemNames = new ArrayList<>();
                    ArrayList<Integer> itemAmounts = new ArrayList<>();

                    for (String entry : entries) {
                        String[] parts = entry.split(":");
                        if (parts.length == 2) {
                            itemNames.add(parts[0]);
                            itemAmounts.add(Integer.parseInt(parts[1]));
                        }
                    }

                    data.setItemNames(itemNames);
                    data.setItemAmounts(itemAmounts);
                }

                rs.close();
                return data;
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Closes the database connection and statement to free resources.
     */
    void closeConnection() {
        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
