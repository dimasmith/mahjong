package net.anatolich.mahjong.game;

import net.anatolich.mahjong.game.layout.Layout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class TileColumnResolver {

    public void layTilesOnBoard( Map<Coordinates, Tile> tileMap, Board board ) {
        if ( tileMap.isEmpty() ) {
            return;
        }
        Layout layout = new Layout(tileMap.keySet());

        resolveTilesToColumns(board, tileMap, layout);


    }

    protected void resolveTilesToColumns( Board board, Map<Coordinates, Tile> tileMap, Layout layout) {
        final int topLayer = layout.getTopLayer();
        for ( int layer = 0; layer <= topLayer; layer++ ) {
            for ( Column column : layout.getLayerSlots(layer) ) {
                Coordinates coordinates = new Coordinates(column, layer);
                board.putTile(tileMap.get(coordinates), column);
            }
        }
    }
}
