package net.anatolich.mahjong.game;

import java.util.List;

/**
 * View interface for board. One can only query board state through it but cannot modify it's state
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public interface Board {

    /**
     * Gets all pieces placed on board.
     * @return
     */
    List<Piece> getAllPieces();

    /**
     * Gets topmost piece on column specified by x and y.
     * @param x
     * @param y
     * @return topmost piece on column or null if no tile found.
     */
    Piece getTopmostPieceAt(int x, int y);
}
