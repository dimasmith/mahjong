package net.anatolich.mahjong.game.layout;

import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Tile;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class SlotTest {

    @Test
    public void testConstructorAssignment() {
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Slot slot = new Slot(coordinates);

        assertThat(slot.getCoordinates(), is(coordinates));
    }

    @Test( expected = IllegalArgumentException.class )
    public void testNullCoordinates() {
        new Slot(null);
    }

    @Test
    public void testSlotIntersection_Intersect() {
        Slot baseSlot = new Slot(new Coordinates(2, 2, 0));
        Slot[] intersectingSlots = new Slot[]{
            new Slot(new Coordinates(1, 1, 0)),
            new Slot(new Coordinates(1, 2, 0)),
            new Slot(new Coordinates(1, 3, 0)),
            new Slot(new Coordinates(2, 1, 0)),
            new Slot(new Coordinates(2, 2, 0)),
            new Slot(new Coordinates(2, 3, 0)),
            new Slot(new Coordinates(3, 1, 0)),
            new Slot(new Coordinates(3, 2, 0)),
            new Slot(new Coordinates(3, 3, 0))
        };

        for ( Slot intersectingSlot : intersectingSlots ) {
            assertThat(String.format("Slot on %s must intersect base slot",
                                     intersectingSlot.getCoordinates()),
                       baseSlot.intersectsWith(intersectingSlot), is(true));
        }
    }

    @Test
    public void testSlotIntersection_Touching() {
        Slot baseSlot = new Slot(new Coordinates(2, 2, 0));
        Slot[] touchingSlots = new Slot[]{
            new Slot(new Coordinates(0, 0, 0)),
            new Slot(new Coordinates(1, 0, 0)),
            new Slot(new Coordinates(2, 0, 0)),
            new Slot(new Coordinates(3, 0, 0)),
            new Slot(new Coordinates(4, 0, 0)),
            new Slot(new Coordinates(0, 1, 0)),
            new Slot(new Coordinates(0, 2, 0)),
            new Slot(new Coordinates(0, 3, 0)),
            new Slot(new Coordinates(0, 4, 0)),
            new Slot(new Coordinates(4, 1, 0)),
            new Slot(new Coordinates(4, 2, 0)),
            new Slot(new Coordinates(4, 3, 0)),
            new Slot(new Coordinates(4, 4, 0)),
            new Slot(new Coordinates(1, 4, 0)),
            new Slot(new Coordinates(2, 4, 0)),
            new Slot(new Coordinates(3, 4, 0))
        };

        for ( Slot touchingSlot : touchingSlots ) {
            assertThat(String.format("Slot on %s must not intersect base slot",
                                     touchingSlot.getCoordinates()),
                       baseSlot.intersectsWith(touchingSlot), is(false));
        }
    }

    @Test
    public void testSlotIntersection_OtherLayer() {
        Slot baseSlot = new Slot(new Coordinates(2, 2, 0));
        Slot otherSlot = new Slot(new Coordinates(2, 2, 1));
        assertThat(String.format("Slot on %s must not intersect base slot. It's on other layer",
                                 otherSlot.getCoordinates()),
                   baseSlot.intersectsWith(otherSlot), is(false));
    }
}
