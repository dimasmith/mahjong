package net.anatolich.mahjong.game;

import java.util.Collection;

/**
 *
 * @author Dmytro
 */
public class SelectionChangedEvent extends GameEvent {
    private final Collection<Piece> deselectedPieces;

    public SelectionChangedEvent(GameSession source, Collection<Piece> selectedPieces, Collection<Piece> deselectedPieces) {
        super(Type.SELECTION_CHANGED, source, selectedPieces);
        this.deselectedPieces = deselectedPieces;
    }

    public Collection<Piece> getDeselectedPieces() {
        return deselectedPieces;
    }
    
}
