package net.anatolich.mahjong.game;

import java.util.Collection;

/**
 *
 * @author Dmytro
 */
public class MoveCompletedEvent extends GameEvent {

    public MoveCompletedEvent(GameSession source, Collection<Piece> pieces) {
        super(Type.TURN_COMPLETED, source, pieces);
    }

    public MoveCompletedEvent(GameSession source, Piece... pieces) {
        super(Type.TURN_COMPLETED, source, pieces);
    }
    
}
