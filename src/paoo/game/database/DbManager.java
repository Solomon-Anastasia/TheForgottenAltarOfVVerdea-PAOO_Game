package paoo.game.database;

import java.sql.*;
import java.util.ArrayList;

public class DbManager {
    private final Connection connection;
    private final Statement stmt;

    public DbManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:game.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    public void saveData(int level, int maxLife, int life, double time, String itemName) {
        String sqlInsert = """
                INSERT INTO Player (Level, Max_life, Life, Time, ItemName) VALUES\s
                    (?, ?, ?, ?, ?);
                \s""";


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

    public DataStorage loadData() {
        ResultSet rs;

        // Get latest saved data
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
                String itemName = rs.getString("ItemName"); // Changed from "itemName" to "ItemName"

                DataStorage data = new DataStorage();
                data.setLevel(level);
                data.setMaxLife(maxLife);
                data.setLife(life);
                data.setTime(time);

                // Handle empty inventory case
                if (itemName != null && !itemName.isEmpty()) {
                    String[] entries = itemName.split(",");

                    ArrayList<String> itemNames = new ArrayList<>();
                    ArrayList<Integer> itemAmounts = new ArrayList<>();

                    for (String entry : entries) {
                        String[] parts = entry.split(":");
                        if (parts.length == 2) { // Ensure we have both name and amount
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

    void closeConnection() {
        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
