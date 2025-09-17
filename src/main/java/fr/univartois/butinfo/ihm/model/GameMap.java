// GameMap.java
package fr.univartois.butinfo.ihm.model;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final int width;
    private final int height;
    private final Tile[][] tiles;

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                tiles[r][c] = new Tile(r, c);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    public void setTile(Tile tile) {
        tiles[tile.getRow()][tile.getColumn()] = tile;
    }

    public List<Tile> getEmptyTiles() {
        List<Tile> emptyTiles = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (tiles[row][col].isEmpty()) {
                    emptyTiles.add(tiles[row][col]);
                }
            }
        }
        return emptyTiles;
    }
}