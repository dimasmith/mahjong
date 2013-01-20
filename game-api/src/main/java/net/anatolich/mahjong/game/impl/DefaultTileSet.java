package net.anatolich.mahjong.game.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import net.anatolich.mahjong.game.Tile;
import net.anatolich.mahjong.game.Tile.Type;
import net.anatolich.mahjong.game.TileSet;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public final class DefaultTileSet implements TileSet {

    private final List<Tile> tiles;

    public DefaultTileSet() {
        this.tiles = new ArrayList<>();

        final EnumSet<Type> oneTimeTypes = EnumSet.<Type>of(Type.FLOWERS, Type.SEASONS);
        for ( Type type : oneTimeTypes ) {
            for ( Tile.Value value : type.getSupportedValues()) {
                this.tiles.add(new Tile(type, value));
            }
        }

        final EnumSet<Type> fourTimesTypes = EnumSet.complementOf(oneTimeTypes);
        for ( Type type : fourTimesTypes ) {
            for ( Tile.Value value : type.getSupportedValues() ) {
                addFourTiles(new Tile(type, value));
            }
        }

    }

    @Override
    public List<Tile> getTiles() {
        return Collections.unmodifiableList(tiles);
    }

    private void addFourTiles( Tile tile ) {
        for ( int i = 0; i < 4; i++ ) {
            this.tiles.add(tile);
        }
    }
}
