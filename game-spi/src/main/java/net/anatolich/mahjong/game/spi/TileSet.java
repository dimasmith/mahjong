package net.anatolich.mahjong.game.spi;

import java.util.List;
import net.anatolich.mahjong.game.Tile;

/**
 * Complete set of tiles used in game. Tilesets may vary from game to game or even in single game.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 * @version 1.0
 * @since 1.0
 */
public interface TileSet {

    /**
     * Lists tiles used in tileset.
     * @return unmodifiable list of tiles. Cannot return null.
     */
    List<Tile> getTiles();
}
