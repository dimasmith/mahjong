package net.anatolich.mahjong.game.rules.spi;

import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.BoardView;
import net.anatolich.mahjong.game.Slot;
import net.anatolich.mahjong.game.rules.Rules;

/**
 * Helper class to implement rules. One using it should implement all abstract methods it provides.
 * Public methods are implemented with all necessary validations so implementor should not worry about it.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public abstract class AbstractRules implements Rules {

    private final BoardView board;

    public AbstractRules( BoardView board ) {
        this.board = board;
    }

    @Override
    public final boolean isSlotOpen( Slot slot ) {
        if ( slot == null || slot.isEmpty() ) {
            throw new IllegalArgumentException();
        }
        return isOpen(slot);
    }

    @Override
    public final boolean isMoveLegal( Slot startSlot, Slot endSlot ) {
        if ( startSlot == null || startSlot.isEmpty() ) {
            throw new IllegalArgumentException();
        }

        if ( endSlot == null || endSlot.isEmpty() ) {
            throw new IllegalArgumentException();
        }

        return isLegal(startSlot, endSlot);

    }

    protected abstract boolean isLegal( Slot startSlot, Slot endSlot );

    protected abstract boolean isOpen( Slot slot );
}
