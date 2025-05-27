package paoo.game.main;

import paoo.game.database.DbManager;
import paoo.game.panel.GamePanel;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static JFrame window;

    public static void main(String[] args) {
        window = new JFrame();
        GamePanel gamePanel = new GamePanel();

        window.add(gamePanel);
        window.pack();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("The forgotten Altar of Verdea");
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Create db table
        DbManager dbManager = new DbManager();
        try {
            dbManager.createPlayerTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
