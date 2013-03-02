package net.anatolich.mahjong.game.capabilities;

import net.anatolich.mahjong.game.AvailableMove;

/**
 * Provides list of hints for player. Hint is an object that holds pieces that are valid for move.
 * <p>Despite interface is similar to Iterator all implementations of Hints should behave 
 * in slightly different manner. Each hints implementation should be cyclical iterator. 
 * That means that after returning of last element of hints list it should 
 * start over returning hints from the first one.<p>
 * 
 * @author Dmytro Kovalchuk
 * @version 1.0
 * @since 0.2
 */
public interface Hints {
   
    /**
     * Checks whether any hints are present.
     * @return true if there is at least one hint or false otherwise.
     */
    boolean hasHints();

    /**
     * Returns next hint if available. Hints are cyclic so after returning last 
     * hint of hints list method will start over from first hint.
     * @return next hint in list.
     * @throws NoHintsException when asked for next hint and no any hints available.
     */
    AvailableMove nextHint();
    
}
