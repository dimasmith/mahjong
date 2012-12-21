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
    public void testConstructorAssignment(){
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Slot slot = new Slot(coordinates);

        assertThat(slot.getCoordinates(), is(coordinates));
    }

    @Test
    public void testConstructorAssignment_WithTile(){
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
    public void testCreateFilledSlot(){
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Tile tile = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);

        final Slot slot = new Slot(coordinates, tile);

        assertThat(slot.isEmpty(), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullCoordinates(){
        new Slot(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullCoordinates_ConstructorWithTile(){
        new Slot(null, new Tile(Tile.Type.WINDS, Tile.Value.EAST));
    }

    @Test
    public void testSetTile(){
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Tile tile = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);

        final Slot slot = new Slot(coordinates);
        slot.setTile(tile);

        assertThat(slot.isEmpty(), is(false));
    }

    @Test
    public void testSetTile_Null(){
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Tile tile = null;

        final Slot slot = new Slot(coordinates);
        slot.setTile(tile);

        assertThat(slot.isEmpty(), is(true));
    }

    @Test
    public void testRemoveTile(){
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Tile tile = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);;

        final Slot slot = new Slot(coordinates, tile);
        slot.removeTile();

        assertThat(slot.isEmpty(), is(true));
    }


}
