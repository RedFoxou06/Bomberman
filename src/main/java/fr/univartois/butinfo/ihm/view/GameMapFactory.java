package fr.univartois.butinfo.ihm.view;

import java.util.Random;
import fr.univartois.butinfo.ihm.model.GameMap;
import fr.univartois.butinfo.ihm.model.Tile;
import fr.univartois.butinfo.ihm.model.TileContent;


/**
 * Génération de cartes de jeu.
 */
public class GameMapFactory {
    public static final int MAP_WIDTH = 15;
    public static final int MAP_HEIGHT = 9;

    /** Crée une carte aléatoire de dimensions données. */
    public static GameMap createRandomMap(int width, int height) {
        GameMap map = new GameMap(width, height);
        Random rnd = new Random();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Tile tile = new Tile(row, col);

                // bordure et murs fixes
                if (row == 0 || col == 0 || row == height - 1 || col == width - 1
                        || (row % 2 == 0 && col % 2 == 0)) {
                    tile.setContent(TileContent.SOLID_WALL);
                }
                // zones de départ en pelouse
                else if ((row <= 1 && col <= 1) || (row >= height - 2 && col >= width - 2)) {
                    tile.setContent(TileContent.LAWN);
                }
                // aléatoire ensuite
                else {
                    tile.setContent(rnd.nextBoolean()
                            ? TileContent.BRICK_WALL
                            : TileContent.LAWN);
                }

                map.setTile(tile);
            }
        }
        return map;
    }

    /** Crée une carte aux dimensions par défaut. */
    public static GameMap createDefaultMap() {
        return createRandomMap(MAP_WIDTH, MAP_HEIGHT);
    }
}
