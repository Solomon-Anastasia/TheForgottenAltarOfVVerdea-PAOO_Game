package paoo.game.tile;

import paoo.game.panel.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

// Flyweight pattern
public class TileManager {
    private GamePanel gamePanel;
    private Tile[] tiles;

    private int[][][][] mapTileNumber;
    private HashMap<String, BufferedImage> imageCache = new HashMap<>();

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[3336]; // Array of different tiles type
        mapTileNumber = new int[gamePanel.getMAX_MAP()][2][gamePanel.getMAX_WORLD_COLUMN()][gamePanel.getMAX_WORLD_ROW()];

        getTileImage();
        loadMap(new String[]{"/maps/Map1.txt", "/maps/Map1_Objects.txt"}, 0);
        loadMap(new String[]{"/maps/Map2.txt", "/maps/Map2_Objects.txt"}, 1);
        loadMap(new String[]{"/maps/Map3.txt", "/maps/Map3_Objects.txt"}, 2);
    }

    public int[][][][] getMapTileNumber() {
        return mapTileNumber;
    }

    public Tile getTile(int i) {
        return tiles[i];
    }

    public void getTileImage() {
        setup(12, "map3/tile_12", false);
        setup(65, "map1/tile_65", false);
        setup(66, "map1/tile_66", false);
        setup(67, "map1/tile_67", false);
        setup(70, "map3/tile_70", false);
        setup(261, "map1/tile_261", true);
        setup(262, "map1/tile_262", true);
        setup(263, "map1/tile_263", true);
        setup(266, "map1/tile_266", true);
        setup(394, "map2/tile_394", true);
        setup(396, "map2/tile_396", true);
        setup(449, "map1/tile_449", false);
        setup(468, "map1/tile_468", false);
        setup(469, "map1/tile_469", false);
        setup(532, "map1/tile_532", false);
        setup(533, "map1/tile_533", false);
        setup(1163, "map1/tile_1163", true);
        setup(1164, "map1/tile_1164", true);
        setup(1165, "map1/tile_1165", true);
        setup(1166, "map1/tile_1166", true);
        setup(1227, "map1/tile_1227", true);
        setup(1228, "map1/tile_1228", true);
        setup(1229, "map1/tile_1229", true);
        setup(1230, "map1/tile_1230", true);
        setup(1291, "map1/tile_1291", true);
        setup(1292, "map1/tile_1292", true);
        setup(1293, "map1/tile_1293", true);
        setup(1294, "map1/tile_1294", true);
        setup(1355, "map1/tile_1355", true);
        setup(1356, "map1/tile_1356", true);
        setup(1357, "map1/tile_1357", true);
        setup(1358, "map1/tile_1358", true);
        setup(1921, "map2/tile_1921", true);
        setup(1922, "map2/tile_1922", true);
        setup(1923, "map2/tile_1923", true);
        setup(1985, "map1/tile_1985", true);
        setup(1986, "map1/tile_1986", true);
        setup(1987, "map1/tile_1987", true);

        // Second layer tiles
        setup(0, "secondlayer/tile_0", false);
        setup(102, "secondlayer/tile_102", true);
        setup(103, "secondlayer/tile_103", true);
        setup(105, "secondlayer/tile_105", true);
        setup(106, "secondlayer/tile_106", true);
        setup(115, "secondlayer2/tile_115", true);
        setup(116, "secondlayer2/tile_116", true);
        setup(121, "secondlayer/tile_121", false);
        setup(122, "secondlayer/tile_122", false);
        setup(138, "secondlayer2/tile_138", true);
        setup(139, "secondlayer2/tile_139", true);
        setup(140, "secondlayer2/tile_140", true);
        setup(167, "secondlayer/tile_167", true);
        setup(169, "secondlayer2/tile_169", true);
        setup(179, "secondlayer2/tile_179", true);
        setup(180, "secondlayer2/tile_180", true);
        setup(183, "secondlayer/tile_183", false);
        setup(184, "secondlayer/tile_184", false);
        setup(185, "secondlayer/tile_185", false);
        setup(186, "secondlayer/tile_186", false);
        setup(232, "secondlayer/tile_232", true);
        setup(241, "secondlayer2/tile_241", true);
        setup(242, "secondlayer2/tile_242", true);
        setup(244, "secondlayer2/tile_244", true);
        setup(245, "secondlayer/tile_245", false);
        setup(246, "secondlayer2/tile_246", true);
        setup(247, "secondlayer/tile_247", false);
        setup(248, "secondlayer/tile_248", false);
        setup(249, "secondlayer/tile_249", false);
        setup(250, "secondlayer/tile_250", false);
        setup(305, "secondlayer2/tile_305", true);
        setup(306, "secondlayer2/tile_306", true);
        setup(307, "secondlayer2/tile_307", false);
        setup(308, "secondlayer2/tile_308", true);
        setup(309, "secondlayer/tile_309", false);
        setup(310, "secondlayer/tile_310", false);
        setup(311, "secondlayer/tile_311", false);
        setup(312, "secondlayer/tile_312", false);
        setup(313, "secondlayer/tile_313", false);
        setup(314, "secondlayer/tile_314", false);
        setup(315, "secondlayer/tile_315", false);
        setup(316, "secondlayer/tile_316", false);
        setup(352, "secondlayer2/tile_352", false);
        setup(372, "secondlayer2/tile_372", true);
        setup(374, "secondlayer/tile_374", false);
        setup(375, "secondlayer/tile_375", false);
        setup(376, "secondlayer/tile_376", false);
        setup(377, "secondlayer/tile_377", false);
        setup(378, "secondlayer/tile_378", false);
        setup(379, "secondlayer/tile_379", false);
        setup(437, "secondlayer/tile_437", false);
        setup(438, "secondlayer/tile_438", false);
        setup(439, "secondlayer/tile_439", false);
        setup(440, "secondlayer/tile_440", false);
        setup(441, "secondlayer/tile_441", false);
        setup(442, "secondlayer/tile_442", false);
        setup(443, "secondlayer/tile_443", false);
        setup(444, "secondlayer/tile_444", false);
        setup(501, "secondlayer/tile_501", false);
        setup(502, "secondlayer/tile_502", false);
        setup(503, "secondlayer/tile_503", false);
        setup(504, "secondlayer/tile_504", false);
        setup(505, "secondlayer/tile_505", false);
        setup(507, "secondlayer/tile_507", false);
        setup(508, "secondlayer/tile_508", false);
        setup(567, "secondlayer/tile_567", false);
        setup(568, "secondlayer/tile_568", false);
        setup(569, "secondlayer/tile_569", false);
        setup(570, "secondlayer/tile_570", false);
        setup(598, "secondlayer/tile_598", false);
        setup(599, "secondlayer/tile_599", false);
        setup(631, "secondlayer/tile_631", false);
        setup(632, "secondlayer/tile_632", false);
        setup(633, "secondlayer/tile_633", false);
        setup(634, "secondlayer/tile_634", false);
        setup(724, "secondlayer/tile_724", false);
        setup(725, "secondlayer/tile_725", false);
        setup(726, "secondlayer/tile_726", false);
        setup(730, "secondlayer/tile_730", false);
        setup(732, "secondlayer/tile_732", false);
        setup(788, "secondlayer/tile_788", false);
        setup(789, "secondlayer/tile_789", false);
        setup(790, "secondlayer/tile_790", false);
        setup(791, "secondlayer/tile_791", false);
        setup(792, "secondlayer/tile_792", false);
        setup(794, "secondlayer/tile_794", false);
        setup(795, "secondlayer/tile_795", false);
        setup(796, "secondlayer/tile_796", false);
        setup(852, "secondlayer/tile_852", false);
        setup(860, "secondlayer/tile_860", false);
        setup(862, "secondlayer/tile_862", false);
        setup(918, "secondlayer/tile_918", false);
        setup(920, "secondlayer/tile_920", false);
        setup(922, "secondlayer/tile_922", false);
        setup(923, "secondlayer/tile_923", true);
        setup(924, "secondlayer/tile_924", true);
        setup(980, "secondlayer/tile_980", false);
        setup(981, "secondlayer/tile_981", false);
        setup(982, "secondlayer/tile_982", false);
        setup(985, "secondlayer/tile_985", true);
        setup(986, "secondlayer/tile_986", false);
        setup(987, "secondlayer/tile_987", false);
        setup(988, "secondlayer/tile_988", false);
        setup(1196, "secondlayer/tile_1196", false);
        setup(1197, "secondlayer/tile_1197", false);
        setup(1260, "secondlayer/tile_1260", false);
        setup(1261, "secondlayer/tile_1261", false);
        setup(1324, "secondlayer/tile_1324", false);
        setup(1325, "secondlayer/tile_1325", false);
        setup(1388, "secondlayer/tile_1388", false);
        setup(1389, "secondlayer/tile_1389", false);
        setup(1511, "secondlayer/tile_1511", false);
        setup(2022, "secondlayer/tile_2022", false);
        setup(2049, "secondlayer/tile_2049", true);
        setup(2050, "secondlayer/tile_2050", true);
        setup(2051, "secondlayer/tile_2051", true);

        setup(0, "secondlayer3/tile_0", false);
        setup(287, "secondlayer3/tile_287", false);
        setup(288, "secondlayer3/tile_288", false);
        setup(290, "secondlayer3/tile_290", false);
        setup(493, "secondlayer3/tile_493", false);
        setup(494, "secondlayer3/tile_494", false);
        setup(495, "secondlayer3/tile_495", false);
        setup(496, "secondlayer3/tile_496", false);
        setup(557, "secondlayer3/tile_557", false);
        setup(558, "secondlayer3/tile_558", false);
        setup(559, "secondlayer3/tile_559", false);
        setup(560, "secondlayer3/tile_560", false);
        setup(615, "secondlayer3/tile_615", true);
        setup(616, "secondlayer3/tile_616", true);
        setup(679, "secondlayer3/tile_679", true);
        setup(680, "secondlayer3/tile_680", true);
        setup(702, "secondlayer3/tile_702", false);
        setup(703, "secondlayer3/tile_703", false);
        setup(704, "secondlayer3/tile_704", false);
        setup(1393, "secondlayer3/tile_1393", true);
        setup(1394, "secondlayer3/tile_1394", true);
        setup(1457, "secondlayer3/tile_1457", true);
        setup(1458, "secondlayer3/tile_1458", true);
        setup(1463, "secondlayer3/tile_1463", false);
        setup(301, "secondlayer3/tile_301", true);
        setup(302, "secondlayer3/tile_302", true);
        setup(303, "secondlayer3/tile_303", true);
        setup(172, "secondlayer3/tile_172", true);
        setup(108, "secondlayer3/tile_108", true);
        setup(109, "secondlayer3/tile_109", true);
        setup(110, "secondlayer3/tile_110", true);
        setup(111, "secondlayer3/tile_111", true);

    }

    // CAMERA
    public void draw(Graphics2D graphics2D) {
        int maxRow = gamePanel.getMAX_WORLD_ROW();
        int maxCol = gamePanel.getMAX_WORLD_COLUMN();
        int tileSize = gamePanel.getTILE_SIZE();
        int currentMap = gamePanel.getCurrentMap();
        int playerWorldX = gamePanel.getPlayer().getWorldX();
        int playerWorldY = gamePanel.getPlayer().getWorldY();
        int playerScreenX = gamePanel.getPlayer().getSCREEN_X();
        int playerScreenY = gamePanel.getPlayer().getSCREEN_Y();

        for (int layer = 0; layer < 2; layer++) {
            for (int row = 0; row < maxRow; row++) {
                for (int col = 0; col < maxCol; col++) {
                    int tileNum = mapTileNumber[currentMap][layer][col][row];

                    if (tileNum >= 0 && tileNum < tiles.length) {
                        Tile tile = tiles[tileNum];
                        if (tile != null && tile.image != null) {
                            int worldX = col * tileSize;
                            int worldY = row * tileSize;

                            int screenX = worldX - playerWorldX + playerScreenX;
                            int screenY = worldY - playerWorldY + playerScreenY;

                            graphics2D.drawImage(tile.image, screenX, screenY, tileSize, tileSize, null);
                        }
                    }
                }
            }
        }
    }

    public void setup(int index, String imagePath, boolean collision) {
        try {
            if (!imageCache.containsKey(imagePath)) {
                BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imagePath + ".png")));
                imageCache.put(imagePath, image);
            }
            tiles[index] = new Tile();
            tiles[index].image = imageCache.get(imagePath);
            tiles[index].collision = collision;
        } catch (IOException e) {
            System.err.println("Error loading image for tile: " + imagePath);
            System.out.println(e.getMessage());
        }
    }

    public void loadMap(String[] filePaths, int map) {
        for (int layer = 0; layer < 2; layer++) {
            try {
                InputStream inputStream = getClass().getResourceAsStream(filePaths[layer]);
                if (inputStream != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String firstLine = bufferedReader.readLine();
                    if (firstLine == null) {
                        throw new IOException("Empty file: " + filePaths[layer]);
                    }

                    String[] numbers = firstLine.split(" ");
                    int columnsInFile = numbers.length;

                    // Reinitialize stream to start from the beginning again
                    bufferedReader.close();
                    inputStream = getClass().getResourceAsStream(filePaths[layer]);
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    int row = 0;
                    while (row < gamePanel.getMAX_WORLD_ROW()) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            throw new IOException("Unexpected end of file at row " + row + " in " + filePaths[layer]);
                        }

                        String[] lineNumbers = line.trim().split(" ");
                        if (lineNumbers.length != columnsInFile) {
                            throw new IOException("Inconsistent column count at row " + row + " in " + filePaths[layer]);
                        }

                        for (int col = 0; col < columnsInFile; col++) {
                            int number = Integer.parseInt(lineNumbers[col]);
                            if (map < mapTileNumber.length &&
                                    layer < mapTileNumber[map].length &&
                                    col < mapTileNumber[map][layer].length &&
                                    row < mapTileNumber[map][layer][col].length) {

                                mapTileNumber[map][layer][col][row] = number;
                            }
                        }
                        row++;
                    }

                    bufferedReader.close();
                } else {
                    System.out.println("Could not find file: " + filePaths[layer]);
                }
            } catch (Exception e) {
                System.out.println("Error loading layer " + layer + ": " + e.getMessage());
            }
        }
    }
}

