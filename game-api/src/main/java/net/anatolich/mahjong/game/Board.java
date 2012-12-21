package net.anatolich.mahjong.game;

import java.util.HashMap;
import java.util.Map;

/**
 * Game board containing tiles placed on it's positions. Board can be viewed by player. GameMaster can view board and
 * remove tiles from it as well as shuffling it.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class Board {

    private final Map<Coordinates, Tile> tileMap;

    public Board() {
        tileMap = new HashMap<>();
    }

    /**
     * Sets tile onto specific column
     */
    public void putTile( Tile tile, Column column ) {
        final Coordinates coordinates = new Coordinates(column, getNextLayerFor(column));
        tileMap.put(coordinates, tile);
    }

    protected Tile getTileAt( Coordinates coordinates ) {
        return tileMap.get(coordinates);
    }

    protected Tile getTopmostTile( Column column ) {
        int topmostLayer = getColumnHeight(column);
        if (topmostLayer == -1){
            return null;
        }

        Coordinates topmostTileCoordinates = new Coordinates(column, topmostLayer);
        return getTileAt(topmostTileCoordinates);
    }

    /**
     * Get next available layer in current column.
     * @param column
     * @return
     */
    private int getNextLayerFor( Column column ) {
        return getColumnHeight(column) + 1;
    }

    /**
     * Get number of last layer filled with tile. Returns -1 if no tiles are placed in column yet.
     * @param column
     * @return
     */
    private int getColumnHeight( Column column ) {
        int topLayer = -1;
        for ( Coordinates coordinates : tileMap.keySet() ) {
            if ( coordinates.getColumn().isOnTopOf(column) ) {
                topLayer = Math.max(topLayer, coordinates.getLayer());
            }
        }
        return topLayer;
    }
}
