package net.anatolich.mahjong.mahjong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.anatolich.mahjong.game.AvailableMove;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.rules.Rules;

/**
 * Collects available moves.
 * @author Dmytro
 * @since 1.0
 * @version 1.0
 */
public class AvailableMovesCollector {

    private final Board board;
    private final Rules rules;
    
    public AvailableMovesCollector(Board board, Rules rules) {
        this.board = board;
        this.rules = rules;
    }

    public List<AvailableMove> collectMoves() {
        
        List<AvailableMove> availableMoves = new ArrayList<>();
        
        List<Piece> allPieces = new ArrayList(board.getAllPieces());
        for (Piece piece : allPieces) {
            int pieceIndex = allPieces.indexOf(piece);
            List<Piece> comparedPieces = allPieces.subList(pieceIndex + 1, allPieces.size());
            for (Piece comparedPiece : comparedPieces) {
                if (rules.isMoveLegal(piece.getCoordinates(), comparedPiece.getCoordinates(), board)){
                    availableMoves.add(new AvailableMove(piece, comparedPiece));
                }
            }
        }
        
        return availableMoves;
    }
    
}
