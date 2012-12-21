package net.anatolich.mahjong.game;

import java.util.List;

/**
 * Contains set of tiles to be used in game.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public interface TileSet {

    /**
     * Get full list of tiles.
     * @return
     */
    List<Tile> getTiles();
}
