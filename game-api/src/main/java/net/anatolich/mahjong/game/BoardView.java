package net.anatolich.mahjong.game;

import java.util.Map;

/**
 * View interface for board. One can only query board state through it but cannot modify it's state
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public interface BoardView {

    /**
     * Gets map that contains all tiles on board along with it's coordinates.
     * @return
     */
    Map<Tile, Coordinates> getAllTiles();

    /**
     * Gets topmost tile on column specified by x and y.
     * @param x
     * @param y
     * @return topmost tile on column or null if no tile found. 
     */
    Tile getTileAt(int x, int y);
}
