package net.anatolich.mahjong.game.spi;

import java.util.Collection;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Piece;

/**
 * Marks board that can accept tiles put on it.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 * @version 1.0
 * @since 1.0
 */
public interface FillableBoard extends Board {

    /**
     * Puts piece at position contained in piece coordinates.
     * @param piece
     */
    void putPiece( Piece piece );

    void putPieces( Collection<Piece> pieces );

}
