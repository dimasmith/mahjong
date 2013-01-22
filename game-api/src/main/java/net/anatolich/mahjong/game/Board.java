package net.anatolich.mahjong.game;

import java.util.List;

/**
 * Board where pieces are placed.
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

    /**
     * Gets piece on given coordinates or null if no piece present for those coordinates.
     * @param coordinates coordinates of piece
     * @return piece placed on coordinates or null.
     */
    Piece getPieceAt(Coordinates coordinates);
}
