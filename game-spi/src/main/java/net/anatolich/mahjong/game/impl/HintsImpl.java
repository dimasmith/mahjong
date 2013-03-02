package net.anatolich.mahjong.game.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.anatolich.mahjong.game.AvailableMove;
import net.anatolich.mahjong.game.capabilities.Hints;
import net.anatolich.mahjong.game.capabilities.NoHintsException;

/**
 * Default implementation of Hints.
 *
 * @author Dmytro Kovalchuk
 * @since 0.2
 */
public final class HintsImpl implements Hints {
   
    private Iterator<AvailableMove> hintIterator = Collections.EMPTY_LIST.iterator();

    @Override
    public boolean hasHints() {
        return hintIterator.hasNext();                
    }

    @Override
    public AvailableMove nextHint() {
        if (!hintIterator.hasNext()) {
            throw new NoHintsException(this);
        }

        return hintIterator.next();
    }

    public void setHints(List<AvailableMove> hints) {        
        this.hintIterator = CyclicalIterator.forList(Collections.unmodifiableList(hints));
    }
}
