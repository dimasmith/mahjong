package net.anatolich.mahjong.game.rules;

import net.anatolich.mahjong.game.Piece;

/**
 * Interface for game rules. Two basic questions which must be answered by rules are
 * whether particular piece on board is "open" so it can be selected by player as a part of move and
 * is move with two particular pieces is legal.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public interface Rules {

    boolean isPieceOpen( Piece piece );

    boolean isMoveLegal( Piece startPiece, Piece endPiece);
}
