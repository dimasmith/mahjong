package net.anatolich.mahjong.game.layout;

import net.anatolich.mahjong.game.Coordinates;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class SlotTest {

    @Test
    public void testConstructorAssignment() {
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Slot slot = new Slot(coordinates);

        Assertions.assertThat(slot.getCoordinates()).isEqualTo(coordinates);
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

        Assertions.assertThat(intersectingSlots)
                .allSatisfy(slot -> Assertions.assertThat(slot.intersectsWith(baseSlot)).isTrue());
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

        Assertions.assertThat(touchingSlots)
                .allSatisfy(slot -> Assertions.assertThat(slot.intersectsWith(baseSlot)).isFalse());
    }

    @Test
    public void testSlotIntersection_OtherLayer() {
        Slot baseSlot = new Slot(new Coordinates(2, 2, 0));
        Slot otherSlot = new Slot(new Coordinates(2, 2, 1));
        Assertions.assertThat(otherSlot.intersectsWith(baseSlot))
                .as("slots must not intersect when on different layers")
                .isFalse();
    }
}
