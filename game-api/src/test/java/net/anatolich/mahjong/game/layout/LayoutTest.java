package net.anatolich.mahjong.game.layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.anatolich.mahjong.game.Column;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.InvalidLayerException;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class LayoutTest {

    @Test( expected = IllegalArgumentException.class )
    public void testNullCannotBePassedAsConstructorArgument() {
        new Layout(null);
    }

    @Test( expected = EmptyLayerException.class )
    public void testValidate_DirectLayerGap() {
        final int emptyLayer = 1;
        final Coordinates coordinates1 = new Coordinates(0, 0, 0);
        final Coordinates coordinates2 = new Coordinates(0, 0, 2);
        final Set<Coordinates> slots = new HashSet<>();
        slots.add(coordinates1);
        slots.add(coordinates2);

        try{
            new Layout(slots);
        } catch ( EmptyLayerException ex ) {
            assertThat(ex.getEmptyLayer(), is(emptyLayer));
            throw ex;
        }

    }

    @Test(expected = HangingSlotException.class)
    public void testValidate_DirectColumnGap() {
        final Set<Coordinates> slots = new HashSet<>();

        slots.add(new Coordinates(0, 0, 0));
        slots.add(new Coordinates(2, 2, 1));
        final Coordinates hangingSlot = new Coordinates(0, 0, 2);
        slots.add(hangingSlot);

        try {
            new Layout(slots);
        } catch ( HangingSlotException ex ) {
            assertThat(ex.getHangingSlot(), is(hangingSlot));
            throw ex;
        }
    }

    @Test
    public void testValidate_Success() {
        Set<Coordinates> slots = new HashSet<>();
        final Coordinates west = new Coordinates(2, 2, 0);
        final Coordinates east = new Coordinates(2, 3, 1);
        final Coordinates north = new Coordinates(4, 4, 0);
        final Coordinates south = new Coordinates(4, 4, 1);

        slots.add(west);
        slots.add(east);
        slots.add(north);
        slots.add(south);

        Layout layout = new Layout(slots); // No exception must be thrown

    }

    @Test
    public void testTopLayer_EmptyLayout() {
        Layout layout = new Layout(Collections.EMPTY_SET);
        assertThat(layout.getTopLayer(), is(0));
    }

    @Test
    public void testTopLayer_SingleLayer() {
        final Set<Coordinates> slots = new HashSet<>();
        slots.add(new Coordinates(0, 0, 0));

        Layout layout = new Layout(slots);
        assertThat(layout.getTopLayer(), is(0));
    }

    @Test
    public void testTopLayer_MultipleLayers() {
        final Set<Coordinates> slots = new HashSet<>();
        slots.add(new Coordinates(0, 0, 0));
        slots.add(new Coordinates(0, 0, 1));
        slots.add(new Coordinates(0, 0, 2));

        Layout layout = new Layout(slots);
        assertThat(layout.getTopLayer(), is(2));
    }

    @Test( expected = IllegalLayoutException.class )
    public void testGetLayerSlots_Empty() {
        Layout layout = new Layout(Collections.EMPTY_SET);

        layout.getLayerSlots(0);
    }

    @Test( expected = InvalidLayerException.class )
    public void testGetLayerSlots_MissingLayer() {
        final Set<Coordinates> slots = new HashSet<>();
        slots.add(new Coordinates(0, 0, 0));
        slots.add(new Coordinates(0, 0, 1));
        slots.add(new Coordinates(0, 0, 2));

        Layout layout = new Layout(slots);
        layout.getLayerSlots(3);
    }

    @Test
    public void testGetLayerSlots_MultipleColumns() {
        final Set<Coordinates> slots = new HashSet<>();
        final int checkedLayer = 0;

        final Coordinates coordinates1 = new Coordinates(0, 0, checkedLayer);
        final Coordinates coordinates2 = new Coordinates(2, 2, checkedLayer);
        final Coordinates coordinates3 = new Coordinates(4, 4, checkedLayer);

        slots.add(coordinates1);
        slots.add(coordinates2);
        slots.add(coordinates3);

        Layout layout = new Layout(slots);
        List<Column> columnsByLayer = layout.getLayerSlots(checkedLayer);

        assertThat(columnsByLayer, hasItems(coordinates1.getColumn(), coordinates2.getColumn(), coordinates3.getColumn()));
    }

    @Test
    public void testGetBounds(){
        List<Coordinates> slotCoords = new ArrayList<>();

        slotCoords.add(new Coordinates(2, 2, 0));
        slotCoords.add(new Coordinates(5, 6, 0));
        slotCoords.add(new Coordinates(12, 16, 0));
        slotCoords.add(new Coordinates(10, 10, 0));
        slotCoords.add(new Coordinates(10, 10, 1));
        slotCoords.add(new Coordinates(10, 10, 2));

        Layout layout = new Layout(slotCoords);

        Coordinates lowerBound = layout.getLowerBound();
        Coordinates upperBound = layout.getUpperBound();

        assertThat(lowerBound, is(new Coordinates(2,2,0)));
        assertThat(upperBound, is(new Coordinates(12,16, 2)));
    }

    @Test
    public void testValidate_OverlappingSlots(){
        final Set<Coordinates> slots = new HashSet<>();
        final int checkedLayer = 0;

        final Coordinates coordinates1 = new Coordinates(0, 0, checkedLayer);
        final Coordinates coordinates2 = new Coordinates(1, 0, checkedLayer);

        slots.add(coordinates1);
        slots.add(coordinates2);

        Layout layout = new Layout(slots);
        List<Column> columnsByLayer = layout.getLayerSlots(checkedLayer);

    }
}
