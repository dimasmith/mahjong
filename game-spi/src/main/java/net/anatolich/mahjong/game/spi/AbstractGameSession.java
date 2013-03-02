package net.anatolich.mahjong.game.spi;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import net.anatolich.mahjong.game.GameEvent;
import net.anatolich.mahjong.game.GameSession;
import net.anatolich.mahjong.game.GameSessionListener;
import net.anatolich.mahjong.game.MoveCompletedEvent;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.capabilities.Capabilities;
import net.anatolich.mahjong.game.impl.CapabilitiesImpl;

/**
 * Abstract superclass of Game Session implementations
 *
 * @author Dmytro
 */
public abstract class AbstractGameSession implements GameSession {

    protected final List<GameSessionListener> listeners = new LinkedList<>();
    private final CapabilitiesImpl capabilities = new CapabilitiesImpl();

    @Override
    public final void addListener(GameSessionListener listener) {
        listeners.add(listener);
    }

    @Override
    public final void removeListener(GameSessionListener listener) {
        listeners.remove(listener);
    }

    protected final void fireGameWon() {
        for (GameSessionListener listener : listeners) {
            listener.gameWon();
        }
    }

    protected final void fireMoveCompleted(Collection<Piece> pieces) {
        final GameEvent event = new MoveCompletedEvent(this, pieces);
        fireTurnCompleted(event);
    }

    protected final void fireNoMovesLeft() {
        for (GameSessionListener listener : listeners) {
            listener.noMovesLeft();
        }
    }

    protected final void firePickedPieceChanged(final GameEvent event) {
        for (GameSessionListener gameSessionListener : listeners) {
            gameSessionListener.pickedPiecesChanged(event);
        }
    }

    protected final void fireTurnCompleted(final GameEvent event) {
        for (GameSessionListener gameSessionListener : listeners) {
            gameSessionListener.turnCompleted(event);
        }
    }

    /**
     * Checks whether game is won by preceding move.
     *
     * @return true when game is won.
     */
    protected abstract boolean isGameWon();

    /**
     * Checks whether any legal moves are available. This method normally won't
     * be called if {@link #isGameWon()} returned true.
     *
     * @return true when there is at least one valid move.
     */
    protected abstract boolean isMovesAvailable();

    /**
     * Called to make all necessary manipulations with board, selection, score
     * e.t.c when legal move is performed.
     *
     * @param affectedPieces
     */
    protected abstract void doMove(List<Piece> affectedPieces);

    protected final void completeMove(List<Piece> affectedPieces) {
        doMove(affectedPieces);
        fireMoveCompleted(affectedPieces);
        if (isGameWon()) {
            fireGameWon();
        } else if (isMovesAvailable()) {
            fireNoMovesLeft();
        }
    }

    @Override
    public final Capabilities capabilities() {
        return capabilities;
    }

    protected final <T> void registerCapability(T capability) {
        capabilities.register(capability);
    }

    public void registerCapability(Object capability, Class<?> capabilityType) {
        capabilities.register(capability, capabilityType);
    }
    
    
}
