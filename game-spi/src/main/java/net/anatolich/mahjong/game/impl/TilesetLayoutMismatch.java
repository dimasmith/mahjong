package net.anatolich.mahjong.game.impl;

import net.anatolich.mahjong.game.Layout;
import net.anatolich.mahjong.game.spi.TileSet;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class TilesetLayoutMismatch extends RuntimeException {

    private final TileSet tileSet;
    private final Layout layout;

    public TilesetLayoutMismatch( TileSet tileSet, Layout layout ) {
        super(String.format("Tileset %s cannot be used with layout %s: count of tiles must be equal to count of slots", tileSet, layout));
        this.tileSet = tileSet;
        this.layout = layout;
    }

    public TileSet getTileSet() {
        return tileSet;
    }

    public Layout getLayout() {
        return layout;
    }
}
