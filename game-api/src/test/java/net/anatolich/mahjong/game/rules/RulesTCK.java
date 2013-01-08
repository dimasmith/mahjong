package net.anatolich.mahjong.game.rules;

import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Slot;
import net.anatolich.mahjong.game.Tile;
import org.junit.Test;

/**
 * Test compatibility kit for rules implementations. In order to use it please
 * override this TCK and implement {@link #getRules() } method in your class.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public abstract class RulesTCK {

    private final Slot zeroSlot = new Slot(new Coordinates(0, 0, 0));
    private final Slot emptySlot = zeroSlot;
    private final Slot fullSlot = new Slot(new Coordinates(0, 0, 0), new Tile(Tile.Type.BAMBOOS, Tile.Value.FIVE));

    /**
     * Implement this method so it returns new instance of rules under test.
     *
     * @return
     */
    protected abstract Rules getRules();

    @Test( expected = IllegalArgumentException.class )
    public void testIsSlotOpen_NullParameter() {
        Rules rules = getRules();

        rules.isSlotOpen(null);
    }

    @Test( expected = IllegalArgumentException.class )
    public void testIsSlotOpen_EmptySlot() {
        Rules rules = getRules();

        rules.isSlotOpen(emptySlot);
    }

    @Test( expected = IllegalArgumentException.class )
    public void testIsMoveLegal_NullParameter1() {
        Rules rules = getRules();

        rules.isMoveLegal(null, zeroSlot);
    }

    @Test( expected = IllegalArgumentException.class )
    public void testIsMoveLegal_NullParameter2() {
        Rules rules = getRules();

        rules.isMoveLegal(zeroSlot, null);
    }

    @Test( expected = IllegalArgumentException.class )
    public void testIsMoveLegal_EmptySlot1() {
        Rules rules = getRules();

        rules.isMoveLegal(fullSlot, emptySlot);
    }

    @Test( expected = IllegalArgumentException.class )
    public void testIsMoveLegal_EmptySlot2() {
        Rules rules = getRules();

        rules.isMoveLegal(emptySlot, fullSlot);
    }
}
