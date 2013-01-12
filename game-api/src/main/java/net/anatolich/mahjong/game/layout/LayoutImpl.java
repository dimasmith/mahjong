package net.anatolich.mahjong.game.layout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
public final class LayoutImpl implements Layout {

    private final Set<Coordinates> coordinates;
    private final Set<Slot> slots;
    private final Map<Integer, List<Column>> layerColumns;
    private final int topLayer;
    private final Coordinates lowerBound;
    private final Coordinates upperBound;

    public LayoutImpl( Collection<Coordinates> coordinates ) {
        if ( coordinates == null ) {
            throw new IllegalArgumentException("slots parameter cannot be null");
        }

        this.coordinates = new HashSet<>(coordinates);
        this.slots = new HashSet<>();
        for ( Coordinates coordinate : coordinates ) {
            slots.add(new Slot(coordinate));
        }
        this.topLayer = calculateTopLayer();


        this.layerColumns = mapSlotsToColumnLayers();

        validateLayerGaps();
        validateTilesGap();
        validateSlotsIntersection();

        upperBound = calculateUpperBound();
        lowerBound = calculateLowerBound();

    }

    @Override
    public Coordinates getLowerBound() {
        return lowerBound;
    }

    @Override
    public Coordinates getUpperBound() {
        return upperBound;
    }

    @Override
    public Collection<Slot> getSlots() {
        return slots;
    }

    private Map<Integer, List<Column>> mapSlotsToColumnLayers() {
        Map<Integer, List<Column>> slotsByLayer = new HashMap<>();
        for ( Coordinates coordinate : coordinates ) {
            int layer = coordinate.getLayer();
            if ( !slotsByLayer.containsKey(layer) ) {
                slotsByLayer.put(layer, new ArrayList<Column>());
            }
            slotsByLayer.get(layer).add(coordinate.getColumn());
        }
        return slotsByLayer;
    }

    private void validateTilesGap() throws IllegalLayoutException {
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

    private int calculateTopLayer() {
        int topmostLayer = 0;
        for ( Coordinates coordinate : coordinates ) {
            topmostLayer = Math.max(topmostLayer, coordinate.getLayer());
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

    private void validateSlotsIntersection() {
        List<Slot> checkedSlots = new ArrayList<>();

        for ( Slot slot : slots ) {
            checkedSlots.add(slot);
        }
        Iterator<Slot> baseSlotIterator = checkedSlots.iterator();
        while ( baseSlotIterator.hasNext() ) {
            Slot baseSlot = baseSlotIterator.next();
            baseSlotIterator.remove();

            for ( Slot checkedSlot : checkedSlots ) {
                if ( baseSlot.intersectsWith(checkedSlot) ) {
                    throw new IntersectingSlotsException(baseSlot, checkedSlot);
                }
            }
        }
    }

    private Coordinates calculateLowerBound() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minLayer = Integer.MAX_VALUE;
        for ( Coordinates coordinate : coordinates ) {
            minX = Math.min(minX, coordinate.getX());
            minY = Math.min(minY, coordinate.getY());
            minLayer = Math.min(minLayer, coordinate.getLayer());
        }
        return new Coordinates(minX, minY, minLayer);
    }

    private Coordinates calculateUpperBound() {
        int maxX = 0;
        int maxY = 0;
        int maxLayer = 0;
        for ( Coordinates coordinate : coordinates ) {
            maxX = Math.max(maxX, coordinate.getX());
            maxY = Math.max(maxY, coordinate.getY());
            maxLayer = Math.max(maxLayer, coordinate.getLayer());
        }
        return new Coordinates(maxX, maxY, maxLayer);
    }
}
