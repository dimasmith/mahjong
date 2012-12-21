package net.anatolich.mahjong.game.layout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.anatolich.mahjong.game.Column;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.InvalidLayerException;

/**
 * Holds bunch of slots that can be filled with tiles.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public final class Layout {

    private final Set<Coordinates> slots;
    private final Map<Integer, List<Column>> layerColumns;
    private final int topLayer;

    public Layout( Collection<Coordinates> slots ) {
        if ( slots == null ) {
            throw new IllegalArgumentException("slots parameter cannot be null");
        }

        this.slots = new HashSet<>(slots);
        this.topLayer = calculateTopLayer();


        this.layerColumns = mapSlotsToColumnLayers(this.slots);

        validateLayerGaps();
        validateTilesGap(topLayer, layerColumns);

    }

    public int getTopLayer() {
        return topLayer;
    }

    private Map<Integer, List<Column>> mapSlotsToColumnLayers( final Set<Coordinates> slots ) {
        Map<Integer, List<Column>> slotsByLayer = new HashMap<>();
        for ( Coordinates coordinates : slots ) {
            int layer = coordinates.getLayer();
            if ( !slotsByLayer.containsKey(layer) ) {
                slotsByLayer.put(layer, new ArrayList<Column>());
            }
            slotsByLayer.get(layer).add(coordinates.getColumn());
        }
        return slotsByLayer;
    }

    private void validateTilesGap( int topLayer,
                                   Map<Integer, List<Column>> layerColumns ) throws IllegalLayoutException {
        for ( int layer = topLayer; layer > 0; layer-- ) {
            final List<Column> thisLayerColumns = layerColumns.get(layer);
            final List<Column> deeperLayerColumns = layerColumns.get(layer - 1);
            for ( Column column : thisLayerColumns ) {
                boolean hasUnderlayingColumn = false;
                for ( Column deeperColumn : deeperLayerColumns ) {
                    if ( column.isOnTopOf(deeperColumn) ) {
                        hasUnderlayingColumn = true;
                        break;
                    }
                }
                if ( !hasUnderlayingColumn ) {
                    throw new HangingSlotException(new Coordinates(column, layer));
                }
            }
        }
    }

    public List<Column> getLayerSlots( int layer ) {
        if ( layerColumns.isEmpty() ) {
            throw new IllegalLayoutException();
        }
        if ( layer < 0 || layer > getTopLayer() ) {
            throw new InvalidLayerException(layer);
        }
        return layerColumns.get(layer);
    }

    private int calculateTopLayer() {
        int topmostLayer = 0;
        for ( Coordinates coordinates : slots ) {
            topmostLayer = Math.max(topmostLayer, coordinates.getLayer());
        }
        return topmostLayer;
    }

    private void validateLayerGaps() throws IllegalLayoutException {
        for ( int i = 0; i < topLayer; i++ ) {
            if ( !layerColumns.containsKey(i) ) {
                throw new EmptyLayerException(i);
            }

        }
    }
}
