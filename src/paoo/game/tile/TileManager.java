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
        tiles = new Tile[3336]; // Array of different tiles type
        mapTileNumber = new int[gamePanel.getMAX_WORLD_COLUMN()][gamePanel.getMAX_WORLD_ROW()];

        getTileImage();
        loadMap("/maps/Map1.txt");
    }

    public int getMapTileNumber(int row, int col) {
        return mapTileNumber[row][col];
    }

    public Tile getTile(int i) {
        return tiles[i];
    }

    public void getTileImage() {
        try {
            // TODO: ADD tiles[i].collision = true where is needed
            tiles[65] = new Tile();
            tiles[65].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_65.png")));

            tiles[66] = new Tile();
            tiles[66].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_66.png")));

            tiles[67] = new Tile();
            tiles[67].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_67.png")));

            tiles[266] = new Tile();
            tiles[266].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_266.png")));
            tiles[266].collision = true;

            tiles[449] = new Tile();
            tiles[449].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_449.png")));

            tiles[468] = new Tile();
            tiles[468].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_468.png")));

            tiles[469] = new Tile();
            tiles[469].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_469.png")));

            tiles[532] = new Tile();
            tiles[532].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_532.png")));

            tiles[533] = new Tile();
            tiles[533].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_533.png")));

            tiles[1163] = new Tile();
            tiles[1163].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1163.png")));
            tiles[1163].collision = true;

            tiles[1164] = new Tile();
            tiles[1164].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1164.png")));
            tiles[1164].collision = true;

            tiles[1165] = new Tile();
            tiles[1165].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1165.png")));
            tiles[1165].collision = true;

            tiles[1166] = new Tile();
            tiles[1166].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1166.png")));
            tiles[1166].collision = true;

            tiles[1227] = new Tile();
            tiles[1227].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1227.png")));
            tiles[1227].collision = true;

            tiles[1228] = new Tile();
            tiles[1228].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1228.png")));
            tiles[1228].collision = true;

            tiles[1229] = new Tile();
            tiles[1229].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1229.png")));
            tiles[1229].collision = true;

            tiles[1230] = new Tile();
            tiles[1230].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1230.png")));
            tiles[1230].collision = true;

            tiles[1291] = new Tile();
            tiles[1291].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1291.png")));
            tiles[1291].collision = true;

            tiles[1292] = new Tile();
            tiles[1292].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1292.png")));
            tiles[1292].collision = true;

            tiles[1293] = new Tile();
            tiles[1293].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1293.png")));
            tiles[1293].collision = true;

            tiles[1294] = new Tile();
            tiles[1294].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1294.png")));
            tiles[1294].collision = true;

            tiles[1355] = new Tile();
            tiles[1355].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1355.png")));
            tiles[1355].collision = true;

            tiles[1356] = new Tile();
            tiles[1356].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1356.png")));
            tiles[1356].collision = true;

            tiles[1357] = new Tile();
            tiles[1357].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1357.png")));
            tiles[1357].collision = true;

            tiles[1358] = new Tile();
            tiles[1358].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1358.png")));
            tiles[1358].collision = true;

            tiles[1985] = new Tile();
            tiles[1985].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1985.png")));
            tiles[1985].collision = true;

            tiles[1986] = new Tile();
            tiles[1986].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1986.png")));
            tiles[1986].collision = true;

            tiles[1987] = new Tile();
            tiles[1987].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_1987.png")));
            tiles[1987].collision = true;
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

            // Boundary (so not all map is loaded because it's not needed => save memory)
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
