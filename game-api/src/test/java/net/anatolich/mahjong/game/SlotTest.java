package net.anatolich.mahjong.game;

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

    @Test
    public void testConstructorAssignment_WithTile() {
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Tile tile = new Tile(Tile.Type.WINDS, Tile.Value.EAST);

        final Slot slot = new Slot(coordinates, tile);

        assertThat(slot.getCoordinates(), is(coordinates));
        assertThat(slot.getTile(), is(tile));
    }

    @Test
    public void testCreateEmptySlot() {
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Slot slot = new Slot(coordinates);

        assertThat(slot.isEmpty(), is(true));
    }

    @Test
    public void testCreateFilledSlot() {
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Tile tile = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);

        final Slot slot = new Slot(coordinates, tile);

        assertThat(slot.isEmpty(), is(false));
    }

    @Test( expected = IllegalArgumentException.class )
    public void testNullCoordinates() {
        new Slot(null);
    }

    @Test( expected = IllegalArgumentException.class )
    public void testNullCoordinates_ConstructorWithTile() {
        new Slot(null, new Tile(Tile.Type.WINDS, Tile.Value.EAST));
    }

    @Test
    public void testSetTile() {
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Tile tile = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);

        final Slot slot = new Slot(coordinates);
        slot.setTile(tile);

        assertThat(slot.isEmpty(), is(false));
    }

    @Test
    public void testSetTile_Null() {
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Tile tile = null;

        final Slot slot = new Slot(coordinates);
        slot.setTile(tile);

        assertThat(slot.isEmpty(), is(true));
    }

    @Test
    public void testRemoveTile() {
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Tile tile = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);;

        final Slot slot = new Slot(coordinates, tile);
        slot.removeTile();

        assertThat(slot.isEmpty(), is(true));
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
