package net.anatolich.mahjong.game.layout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.anatolich.mahjong.game.Coordinates;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class LayoutFactory {

    public LayoutImpl getDefaultLayout() {
        List<Coordinates> slotCoords = new ArrayList<>();

        addRow(2, 12, 0, 0, slotCoords);

        addRow(6, 8, 2, 0, slotCoords);
        addRow(8, 6, 2, 1, slotCoords);

        addRow(4, 10, 4, 0, slotCoords);
        addRow(8, 6, 4, 1, slotCoords);
        addRow(10, 4, 4, 2, slotCoords);

        addRow(2, 12, 6, 0, slotCoords);
        addRow(8, 6, 6, 1, slotCoords);
        addRow(10, 4, 6, 2, slotCoords);
        addRow(12, 2, 6, 3, slotCoords);

        addRow(2, 12, 8, 0, slotCoords);
        addRow(8, 6, 8, 1, slotCoords);
        addRow(10, 4, 8, 2, slotCoords);
        addRow(12, 2, 8, 3, slotCoords);

        addRow(4, 10, 10, 0, slotCoords);
        addRow(8, 6, 10, 1, slotCoords);
        addRow(10, 4, 10, 2, slotCoords);

        addRow(6, 8, 12, 0, slotCoords);
        addRow(8, 6, 12, 1, slotCoords);

        addRow(2, 12, 14, 0, slotCoords);

        slotCoords.add(new Coordinates(0, 9, 0));
        slotCoords.add(new Coordinates(26, 9, 0));
        slotCoords.add(new Coordinates(28, 9, 0));

        slotCoords.add(new Coordinates(13, 9, 4));

        LayoutImpl layout = new LayoutImpl(slotCoords);
        return layout;
    }

    private void addRow( int startX, int rowLength, int y, int layer, Collection<Coordinates> coordinates ) {
        for ( int i = startX; i < startX + rowLength * 2; i += 2 ) {
            coordinates.add(new Coordinates(i, y, layer));

        }
    }
}
