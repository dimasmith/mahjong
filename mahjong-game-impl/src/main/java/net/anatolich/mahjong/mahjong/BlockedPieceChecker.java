package net.anatolich.mahjong.mahjong;

import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;

/**
 * Detects whether piece is blocked or not.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 * @since 1.0
 * @version 1.0
 */
public class BlockedPieceChecker implements BlockChecker {

    @Override
    public boolean isBlocked( Coordinates coordinates, Board board ) {
        Piece piece = board.getPieceAt(coordinates);
        if ( piece == null ) {
            return true;
        }

        for ( int xOffset = -1; xOffset <= 1; xOffset++ ) {
            for ( int yOffset = -1; yOffset <= 1; yOffset++ ) {
                if ( board.getPieceAt(coordinates.translate(xOffset, yOffset, 1)) != null ) {
                    return true;
                }
            }
        }

        boolean blockedFromLeft = false;
        for ( int i = -1; i <= 1; i++ ) {
            if ( board.getPieceAt(coordinates.translate(-2, i, 0)) != null ) {
                blockedFromLeft = true;
            }
        }

        boolean blockedFromRight = false;
        for ( int i = -1; i <= 1; i++ ) {
            if ( board.getPieceAt(coordinates.translate(2, i, 0)) != null ) {
                blockedFromRight = true;
            }
        }

        if (blockedFromLeft && blockedFromRight){
            return true;
        }

        return false;
    }
}
