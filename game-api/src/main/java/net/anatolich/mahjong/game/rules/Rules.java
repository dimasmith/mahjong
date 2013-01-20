package net.anatolich.mahjong.game.rules;

import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;

/**
 * Interface for game rules. Two basic questions which must be answered by rules are
 * whether particular piece on board is "open" so it can be selected by player as a part of move and
 * is move with two particular pieces is legal.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public interface Rules {

    /**
     * Determines whether piece on board can be selected or it is blocked by neighbor tile.
     * @param piece piece to be checked.
     * @param board game board.
     * @return true if piece is selectable.
     */
    boolean isPieceOpen( Coordinates piece, Board board);

    /**
     * Determines whether move involving two pieces is legal or not.
     * @param startPiece piece selected first.
     * @param endPiece piece selected second.
     * @param board game board.
     * @return true if move is allowed.
     */
    boolean isMoveLegal( Coordinates startPiece, Coordinates endPiece, Board board);
}
