package net.anatolich.mahjong.game.spi;

import java.util.List;
import net.anatolich.mahjong.game.Tile;

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
