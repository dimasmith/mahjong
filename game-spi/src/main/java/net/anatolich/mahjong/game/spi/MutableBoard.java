package net.anatolich.mahjong.game.spi;

import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.spi.FillableBoard;

/**
 * Board that can be both populated with tiles and cleared up.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 * @version 1.0
 * @since 1.0
 */
public interface MutableBoard extends FillableBoard {

    /**
     * Removes piece at coordinates provided.
     * @param coordinates
     */
    void removePieceAt( Coordinates coordinates );

}
