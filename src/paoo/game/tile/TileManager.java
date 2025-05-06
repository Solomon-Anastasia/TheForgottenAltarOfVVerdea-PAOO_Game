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

    private int[][][] mapTileNumber;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[3336]; // Array of different tiles type
        mapTileNumber = new int[2][gamePanel.getMAX_WORLD_COLUMN()][gamePanel.getMAX_WORLD_ROW()];

        getTileImage();
        loadMap(new String[]{"/maps/Map1.txt", "/maps/Map1_Objects.txt"});
    }

    public int getMapTileNumber(int layer, int row, int col) {
        return mapTileNumber[layer][row][col];
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

            tiles[261] = new Tile();
            tiles[261].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_261.png")));
            tiles[261].collision = true;

            tiles[262] = new Tile();
            tiles[262].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_262.png")));
            tiles[262].collision = true;

            tiles[263] = new Tile();
            tiles[263].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/map1/tile_263.png")));
            tiles[263].collision = true;

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

            // Tiles for second layer

            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_0.png")));

            tiles[102] = new Tile();
            tiles[102].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_102.png")));
            tiles[102].collision = true;

            tiles[103] = new Tile();
            tiles[103].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_103.png")));
            tiles[103].collision = true;

            tiles[105] = new Tile();
            tiles[105].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_105.png")));
            tiles[105].collision = true;

            tiles[106] = new Tile();
            tiles[106].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_106.png")));
            tiles[106].collision = true;

            tiles[121] = new Tile();
            tiles[121].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_121.png")));

            tiles[122] = new Tile();
            tiles[122].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_122.png")));

            tiles[167] = new Tile();
            tiles[167].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_167.png")));
            tiles[167].collision = true;

            tiles[183] = new Tile();
            tiles[183].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_183.png")));

            tiles[184] = new Tile();
            tiles[184].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_184.png")));

            tiles[185] = new Tile();
            tiles[185].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_185.png")));

            tiles[186] = new Tile();
            tiles[186].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_186.png")));

            tiles[232] = new Tile();
            tiles[232].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_232.png")));
            tiles[232].collision = true;

            tiles[245] = new Tile();
            tiles[245].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_245.png")));

            tiles[247] = new Tile();
            tiles[247].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_247.png")));

            tiles[248] = new Tile();
            tiles[248].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_248.png")));

            tiles[249] = new Tile();
            tiles[249].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_249.png")));

            tiles[250] = new Tile();
            tiles[250].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_250.png")));

            tiles[309] = new Tile();
            tiles[309].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_309.png")));

            tiles[310] = new Tile();
            tiles[310].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_310.png")));

            tiles[311] = new Tile();
            tiles[311].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_311.png")));

            tiles[312] = new Tile();
            tiles[312].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_312.png")));

            tiles[313] = new Tile();
            tiles[313].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_313.png")));

            tiles[314] = new Tile();
            tiles[314].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_314.png")));

            tiles[315] = new Tile();
            tiles[315].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_315.png")));

            tiles[316] = new Tile();
            tiles[316].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_316.png")));

            tiles[374] = new Tile();
            tiles[374].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_374.png")));

            tiles[375] = new Tile();
            tiles[375].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_375.png")));

            tiles[376] = new Tile();
            tiles[376].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_376.png")));

            tiles[377] = new Tile();
            tiles[377].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_377.png")));

            tiles[378] = new Tile();
            tiles[378].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_378.png")));

            tiles[379] = new Tile();
            tiles[379].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_379.png")));

            tiles[437] = new Tile();
            tiles[437].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_437.png")));

            tiles[438] = new Tile();
            tiles[438].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_438.png")));

            tiles[439] = new Tile();
            tiles[439].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_439.png")));

            tiles[440] = new Tile();
            tiles[440].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_440.png")));

            tiles[441] = new Tile();
            tiles[441].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_441.png")));

            tiles[442] = new Tile();
            tiles[442].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_442.png")));

            tiles[443] = new Tile();
            tiles[443].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_443.png")));

            tiles[444] = new Tile();
            tiles[444].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_444.png")));

            tiles[501] = new Tile();
            tiles[501].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_501.png")));

            tiles[502] = new Tile();
            tiles[502].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_502.png")));

            tiles[503] = new Tile();
            tiles[503].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_503.png")));

            tiles[504] = new Tile();
            tiles[504].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_504.png")));

            tiles[505] = new Tile();
            tiles[505].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_505.png")));

            tiles[507] = new Tile();
            tiles[507].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_507.png")));

            tiles[508] = new Tile();
            tiles[508].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_508.png")));

            tiles[567] = new Tile();
            tiles[567].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_567.png")));

            tiles[568] = new Tile();
            tiles[568].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_568.png")));

            tiles[569] = new Tile();
            tiles[569].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_569.png")));

            tiles[570] = new Tile();
            tiles[570].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_570.png")));

            tiles[598] = new Tile();
            tiles[598].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_598.png")));

            tiles[599] = new Tile();
            tiles[599].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_599.png")));

            tiles[631] = new Tile();
            tiles[631].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_631.png")));

            tiles[632] = new Tile();
            tiles[632].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_632.png")));

            tiles[633] = new Tile();
            tiles[633].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_633.png")));

            tiles[634] = new Tile();
            tiles[634].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_634.png")));

            tiles[724] = new Tile();
            tiles[724].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_724.png")));

            tiles[725] = new Tile();
            tiles[725].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_725.png")));

            tiles[726] = new Tile();
            tiles[726].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_726.png")));

            tiles[730] = new Tile();
            tiles[730].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_730.png")));

            tiles[732] = new Tile();
            tiles[732].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_732.png")));

            tiles[788] = new Tile();
            tiles[788].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_788.png")));

            tiles[789] = new Tile();
            tiles[789].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_789.png")));

            tiles[790] = new Tile();
            tiles[790].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_790.png")));

            tiles[791] = new Tile();
            tiles[791].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_791.png")));

            tiles[792] = new Tile();
            tiles[792].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_792.png")));

            tiles[794] = new Tile();
            tiles[794].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_794.png")));

            tiles[795] = new Tile();
            tiles[795].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_795.png")));

            tiles[796] = new Tile();
            tiles[796].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_796.png")));

            tiles[852] = new Tile();
            tiles[852].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_852.png")));

            tiles[860] = new Tile();
            tiles[860].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_860.png")));

            tiles[862] = new Tile();
            tiles[862].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_862.png")));

            tiles[918] = new Tile();
            tiles[918].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_918.png")));

            tiles[920] = new Tile();
            tiles[920].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_920.png")));

            tiles[922] = new Tile();
            tiles[922].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_922.png")));

            tiles[923] = new Tile();
            tiles[923].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_923.png")));
            tiles[923].collision = true;

            tiles[924] = new Tile();
            tiles[924].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_924.png")));
            tiles[924].collision = true;

            tiles[980] = new Tile();
            tiles[980].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_980.png")));

            tiles[981] = new Tile();
            tiles[981].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_981.png")));

            tiles[982] = new Tile();
            tiles[982].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_982.png")));

            tiles[985] = new Tile();
            tiles[985].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_985.png")));
            tiles[985].collision = true;

            tiles[986] = new Tile();
            tiles[986].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_986.png")));

            tiles[987] = new Tile();
            tiles[987].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_987.png")));

            tiles[988] = new Tile();
            tiles[988].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_988.png")));

            tiles[1196] = new Tile();
            tiles[1196].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_1196.png")));

            tiles[1197] = new Tile();
            tiles[1197].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_1197.png")));

            tiles[1260] = new Tile();
            tiles[1260].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_1260.png")));

            tiles[1261] = new Tile();
            tiles[1261].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_1261.png")));

            tiles[1324] = new Tile();
            tiles[1324].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_1324.png")));

            tiles[1325] = new Tile();
            tiles[1325].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_1325.png")));

            tiles[1388] = new Tile();
            tiles[1388].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_1388.png")));

            tiles[1389] = new Tile();
            tiles[1389].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_1389.png")));

            tiles[1511] = new Tile();
            tiles[1511].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_1511.png")));

            tiles[2022] = new Tile();
            tiles[2022].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_2022.png")));

            tiles[2049] = new Tile();
            tiles[2049].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_2049.png")));
            tiles[2049].collision = true;

            tiles[2050] = new Tile();
            tiles[2050].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_2050.png")));
            tiles[2050].collision = true;

            tiles[2051] = new Tile();
            tiles[2051].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/secondlayer/tile_2051.png")));
            tiles[2051].collision = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // CAMERA
    /*public void draw(Graphics2D graphics2D) {
        int worldColum = 0;
        int worldRow = 0;

        while (worldColum < gamePanel.getMAX_WORLD_COLUMN() && worldRow < gamePanel.getMAX_WORLD_ROW()) {
            int tileNumber = mapTileNumber[2][worldColum][worldRow];

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
    }*/

    public void draw(Graphics2D graphics2D) {
        for (int row = 0; row < gamePanel.getMAX_WORLD_ROW(); row++) {
            for (int col = 0; col < gamePanel.getMAX_WORLD_COLUMN(); col++) {
                int worldX = col * gamePanel.getTILE_SIZE();
                int worldY = row * gamePanel.getTILE_SIZE();
                int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X();
                int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y();

                if (worldX + gamePanel.getTILE_SIZE() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getSCREEN_X()
                        && worldX - gamePanel.getTILE_SIZE() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X()
                        && worldY + gamePanel.getTILE_SIZE() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getSCREEN_Y()
                        && worldY - gamePanel.getTILE_SIZE() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y()) {

                    for (int layer = 0; layer < mapTileNumber.length; layer++) {
                        int tileNum = mapTileNumber[layer][col][row];

                        if (tileNum >= 0 && tileNum < tiles.length) {
                            Tile tile = tiles[tileNum];
                            if (tile != null && tile.image != null) {
                                graphics2D.drawImage(tile.image, screenX, screenY,
                                        gamePanel.getTILE_SIZE(), gamePanel.getTILE_SIZE(), null);
                            }
                        }
                    }
                }
            }
        }
    }

    public void loadMap(String[] filePaths) {
        for (int layer = 0; layer < filePaths.length; layer++) {
            try {
                InputStream inputStream = getClass().getResourceAsStream(filePaths[layer]);
                if (inputStream != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String firstLine = bufferedReader.readLine();
                    String[] numbers = firstLine.split(" ");
                    int columnsInFile = numbers.length;

                    bufferedReader.close();
                    inputStream = getClass().getResourceAsStream(filePaths[layer]);
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    int row = 0;
                    while (row < gamePanel.getMAX_WORLD_ROW()) {
                        String line = bufferedReader.readLine();
                        String[] lineNumbers = line.split(" ");
                        for (int col = 0; col < columnsInFile; col++) {
                            int number = Integer.parseInt(lineNumbers[col]);
                            if (layer < mapTileNumber.length && col < mapTileNumber[layer].length && row < mapTileNumber[layer][col].length) {
                                mapTileNumber[layer][col][row] = number;
                            }
                        }
                        row++;
                    }

                    bufferedReader.close();
                }
            } catch (Exception e) {
                System.out.println("Error loading layer " + layer + ": " + e.getMessage());
            }
        }
        }
    }

