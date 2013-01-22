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
public class LayoutImplTest {

    @Test( expected = IllegalArgumentException.class )
    public void testNullCannotBePassedAsConstructorArgument() {
        new LayoutImpl("Default", "", null);
    }

    @Test( expected = EmptyLayerException.class )
    public void testValidate_DirectLayerGap() {
        final int emptyLayer = 1;
        final Coordinates coordinates1 = new Coordinates(0, 0, 0);
        final Coordinates coordinates2 = new Coordinates(0, 0, 2);
        final Set<Coordinates> slots = new HashSet<>();
        slots.add(coordinates1);
        slots.add(coordinates2);

        try {
            new LayoutImpl("Default", "", slots);
        } catch ( EmptyLayerException ex ) {
            assertThat(ex.getEmptyLayer(), is(emptyLayer));
            throw ex;
        }

    }

    @Test( expected = HangingSlotException.class )
    public void testValidate_DirectColumnGap() {
        final Set<Coordinates> slots = new HashSet<>();

        slots.add(new Coordinates(0, 0, 0));
        slots.add(new Coordinates(2, 2, 1));
        final Coordinates hangingSlot = new Coordinates(0, 0, 2);
        slots.add(hangingSlot);

        try {
            new LayoutImpl("Default", "", slots);
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

        LayoutImpl layout = new LayoutImpl("Default", "", slots); // No exception must be thrown

    }

    @Test
    public void testTopLayer_EmptyLayout() {
        LayoutImpl layout = new LayoutImpl("Default", "", Collections.EMPTY_SET);
        assertThat(layout.getUpperBound().getLayer(), is(0));
    }

    @Test
    public void testTopLayer_SingleLayer() {
        final Set<Coordinates> slots = new HashSet<>();
        slots.add(new Coordinates(0, 0, 0));

        LayoutImpl layout = new LayoutImpl("Default", "", slots);
        assertThat(layout.getUpperBound().getLayer(), is(0));
    }

    @Test
    public void testTopLayer_MultipleLayers() {
        final Set<Coordinates> slots = new HashSet<>();
        slots.add(new Coordinates(0, 0, 0));
        slots.add(new Coordinates(0, 0, 1));
        slots.add(new Coordinates(0, 0, 2));

        LayoutImpl layout = new LayoutImpl("Default", "", slots);
        assertThat(layout.getUpperBound().getLayer(), is(2));
    }

    @Test
    public void testGetBounds() {
        List<Coordinates> slotCoords = new ArrayList<>();

        slotCoords.add(new Coordinates(2, 2, 0));
        slotCoords.add(new Coordinates(5, 6, 0));
        slotCoords.add(new Coordinates(12, 16, 0));
        slotCoords.add(new Coordinates(10, 10, 0));
        slotCoords.add(new Coordinates(10, 10, 1));
        slotCoords.add(new Coordinates(10, 10, 2));

        LayoutImpl layout = new LayoutImpl("Default", "", slotCoords);

        Coordinates lowerBound = layout.getLowerBound();
        Coordinates upperBound = layout.getUpperBound();

        assertThat(lowerBound, is(new Coordinates(2, 2, 0)));
        assertThat(upperBound, is(new Coordinates(12, 16, 2)));
    }



    @Test( expected = IntersectingSlotsException.class )
    public void testValidate_OverlappingSlots() {
        final Set<Coordinates> slots = new HashSet<>();
        final int checkedLayer = 0;

        final Coordinates coordinates1 = new Coordinates(0, 0, checkedLayer);
        final Coordinates coordinates2 = new Coordinates(1, 0, checkedLayer);

        slots.add(coordinates1);
        slots.add(coordinates2);

        new LayoutImpl("Default", "", slots); // Must throw IntersectingSlotsException
    }
}
