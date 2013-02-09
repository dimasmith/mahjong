package net.anatolich.mahjong.mahjong;

import java.util.EnumSet;
import net.anatolich.mahjong.game.Tile;

/**
 * Matches tiles.
 * Most of tiles are matched by both type and values. {@link Tile.Type.SEASONS} and {@link Tile.Type.FLOWERS} are matched
 * by type only.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 * @version 1.0
 * @since 1.0
 */
public final class TileMatcherImpl implements TileMatcher {

    private static final EnumSet<Tile.Type> typeMatched = EnumSet.of(Tile.Type.SEASONS, Tile.Type.FLOWERS);

    @Override
    public boolean match( Tile t1, Tile t2 ) {
        if ( typeMatched.contains(t1.getType()) ) {
            return t1.getType().equals(t2.getType());
        }
        return t1.getType().equals(t2.getType()) && t1.getValue().equals(t2.getValue());
    }
}
