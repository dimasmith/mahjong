package net.anatolich.mahjong.game.layout;

import net.anatolich.mahjong.game.Coordinates;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class LayoutImplTest {

    @Test
    public void testNullCannotBePassedAsConstructorArgument() {
        //noinspection ConstantConditions
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new LayoutImpl("Default", "", null));
    }

    @Test
    public void testValidate_DirectLayerGap() {
        final int emptyLayer = 1;
        final Coordinates coordinates1 = new Coordinates(0, 0, 0);
        final Coordinates coordinates2 = new Coordinates(0, 0, 2);
        final Set<Coordinates> slots = new HashSet<>();
        slots.add(coordinates1);
        slots.add(coordinates2);

        Assertions.assertThatExceptionOfType(EmptyLayerException.class)
                .isThrownBy(() -> new LayoutImpl("Default", "", slots))
                .extracting(EmptyLayerException::getEmptyLayer)
                .isEqualTo(emptyLayer);
    }

    @Test
    public void testValidate_DirectColumnGap() {
        final Set<Coordinates> slots = new HashSet<>();

        slots.add(new Coordinates(0, 0, 0));
        slots.add(new Coordinates(2, 2, 1));
        final Coordinates hangingSlot = new Coordinates(0, 0, 2);
        slots.add(hangingSlot);

        Assertions.assertThatExceptionOfType(HangingSlotException.class)
                .isThrownBy(() -> new LayoutImpl("Default", "", slots))
                .extracting(HangingSlotException::getHangingSlot)
                .isEqualTo(hangingSlot);
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

        Assertions.assertThatNoException()
                .isThrownBy(() -> new LayoutImpl("Default", "", slots));
    }

    @Test
    public void testTopLayer_EmptyLayout() {
        LayoutImpl layout = new LayoutImpl("Default", "", Set.of());
        Assertions.assertThat(layout.getUpperBound().getLayer()).isZero();
    }

    @Test
    public void testTopLayer_SingleLayer() {
        final Set<Coordinates> slots = new HashSet<>();
        slots.add(new Coordinates(0, 0, 0));

        LayoutImpl layout = new LayoutImpl("Default", "", slots);
        Assertions.assertThat(layout.getUpperBound().getLayer()).isZero();
    }

    @Test
    public void testTopLayer_MultipleLayers() {
        final Set<Coordinates> slots = new HashSet<>();
        slots.add(new Coordinates(0, 0, 0));
        slots.add(new Coordinates(0, 0, 1));
        slots.add(new Coordinates(0, 0, 2));

        LayoutImpl layout = new LayoutImpl("Default", "", slots);
        Assertions.assertThat(layout.getUpperBound().getLayer()).isEqualTo(2);
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

        Assertions.assertThat(lowerBound).isEqualTo(new Coordinates(2, 2, 0));
        Assertions.assertThat(upperBound).isEqualTo(new Coordinates(12, 16, 2));
    }



    @Test
    public void testValidate_OverlappingSlots() {
        final Set<Coordinates> slots = new HashSet<>();
        final int checkedLayer = 0;

        final Coordinates coordinates1 = new Coordinates(0, 0, checkedLayer);
        final Coordinates coordinates2 = new Coordinates(1, 0, checkedLayer);

        slots.add(coordinates1);
        slots.add(coordinates2);

        Assertions.assertThatExceptionOfType(IntersectingSlotsException.class)
                .isThrownBy(() -> new LayoutImpl("Default", "", slots));
    }
}
