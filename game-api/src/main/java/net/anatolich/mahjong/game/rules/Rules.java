package net.anatolich.mahjong.game.rules;

import net.anatolich.mahjong.game.Slot;

/**
 * Interface for game rules. Two basic questions which must be answered by rules are
 * whether particular slot on board is "open" so it can be selected by player as a part of move and
 * is move with two particular slots is legal.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public interface Rules {

    boolean isSlotOpen( Slot slot );

    boolean isMoveLegal( Slot startSlot, Slot endSlot );
}
