package paoo.game.tile;

import paoo.game.panel.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

// Flyweight pattern
public class TileManager {
    private GamePanel gamePanel;
    private Tile[] tiles;

    private int[][] mapTileNumber;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[10]; // Array of different tiles type
        mapTileNumber = new int[gamePanel.getMAX_SCREEN_COLUMN()][gamePanel.getMAX_SCREEN_ROW()];

        getTileImage();
        loadMap("/maps/map1.txt");
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/grass.png")));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/sand.png")));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/water.png")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void draw(Graphics2D graphics2D) {
        int colum = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (colum < gamePanel.getMAX_SCREEN_COLUMN() && row < gamePanel.getMAX_SCREEN_ROW()) {
            int tileNumber = mapTileNumber[colum][row];

            graphics2D.drawImage(tiles[tileNumber].image, x, y, gamePanel.getTILE_SIZE(), gamePanel.getTILE_SIZE(), null);
            colum++;
            x += gamePanel.getTILE_SIZE();

            if (colum == gamePanel.getMAX_SCREEN_COLUMN()) {
                colum = 0;
                x = 0;

                row++;
                y += gamePanel.getTILE_SIZE();
            }
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int column = 0;
            int row = 0;

            while (column < gamePanel.getMAX_SCREEN_COLUMN() && row < gamePanel.getMAX_SCREEN_ROW()) {
                String line = bufferedReader.readLine();

                while (column < gamePanel.getMAX_SCREEN_COLUMN()) {
                    String[] numbers = line.split(" ");
                    int number = Integer.parseInt(numbers[column]);

                    mapTileNumber[column][row] = number;
                    column++;
                }
                if (column == gamePanel.getMAX_SCREEN_COLUMN()) {
                    column = 0;
                    row++;
                }
            }

            bufferedReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
