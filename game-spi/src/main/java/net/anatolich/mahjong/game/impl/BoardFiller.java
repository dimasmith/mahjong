package net.anatolich.mahjong.game.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.anatolich.mahjong.game.Layout;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.Tile;
import net.anatolich.mahjong.game.layout.Slot;
import net.anatolich.mahjong.game.spi.FillableBoard;
import net.anatolich.mahjong.game.spi.TileSet;

/**
 * Fills board with tiles according to provided layout.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 * @version 1.0
 * @since 1.0
 */
public class BoardFiller {

    private final FillableBoard board;

    public BoardFiller( FillableBoard board ) {
        this.board = board;
    }

    /**
     * Fills board passed in constructor with tiles from tileset according to layout
     * @param layout
     * @param tileSet
     * @throws TilesetLayoutMismatch when amount of tiles does not match free spaces in layout.
     */
    public void fill(Layout layout, TileSet tileSet) {

        if (layout.getSlots().size() != tileSet.getTiles().size()){
            throw new TilesetLayoutMismatch(tileSet, layout);
        }

        List<Tile> shuffledTiles = new ArrayList<>(tileSet.getTiles());
        Collections.shuffle(shuffledTiles);
        Collection<Slot> slots = layout.getSlots();
        Iterator<Slot> slotIterator = slots.iterator();
        Iterator<Tile> tilesIterator = shuffledTiles.iterator();

        final List<Piece> pieces = new LinkedList<>();
        while ( tilesIterator.hasNext() ) {
            Tile tile = tilesIterator.next();
            Slot slot = slotIterator.next();

            final Piece piece = new Piece(tile, slot.getCoordinates());
            pieces.add(piece);
        }

        board.putPieces(pieces);
    }
}
