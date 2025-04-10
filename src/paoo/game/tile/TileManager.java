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
        mapTileNumber = new int[gamePanel.getMAX_WORLD_COLUMN()][gamePanel.getMAX_WORLD_ROW()];

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

    // CAMERA
    public void draw(Graphics2D graphics2D) {
        int worldColum = 0;
        int worldRow = 0;

        while (worldColum < gamePanel.getMAX_WORLD_COLUMN() && worldRow < gamePanel.getMAX_WORLD_ROW()) {
            int tileNumber = mapTileNumber[worldColum][worldRow];

            int worldX = worldColum * gamePanel.getTILE_SIZE();
            int worldY = worldRow * gamePanel.getTILE_SIZE();
            int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X();
            int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y();

            // Boundary (so not all map is loaded because it's not needed => save memory
            if (worldX + gamePanel.getTILE_SIZE() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getSCREEN_X()
                    && worldX - gamePanel.getTILE_SIZE() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X()
                    && worldY + gamePanel.getTILE_SIZE() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getSCREEN_Y()
                    && worldY - gamePanel.getTILE_SIZE() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y()
            ) {
                graphics2D.drawImage(tiles[tileNumber].image, screenX, screenY, gamePanel.getTILE_SIZE(), gamePanel.getTILE_SIZE(), null);
            }
            worldColum++;

            if (worldColum == gamePanel.getMAX_WORLD_COLUMN()) {
                worldColum = 0;
                worldRow++;
            }
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int column = 0;
            int row = 0;

            while (column < gamePanel.getMAX_WORLD_COLUMN() && row < gamePanel.getMAX_WORLD_ROW()) {
                String line = bufferedReader.readLine();

                while (column < gamePanel.getMAX_WORLD_COLUMN()) {
                    String[] numbers = line.split(" ");
                    int number = Integer.parseInt(numbers[column]);

                    mapTileNumber[column][row] = number;
                    column++;
                }
                if (column == gamePanel.getMAX_WORLD_COLUMN()) {
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
