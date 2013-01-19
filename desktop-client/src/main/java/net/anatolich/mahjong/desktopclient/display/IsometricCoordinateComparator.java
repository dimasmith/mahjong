package net.anatolich.mahjong.desktopclient.display;

import java.util.Comparator;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;

/**
 * Comparator to force rendering of tiles in correct order to avoid tile overlapping.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class IsometricCoordinateComparator implements Comparator<Piece> {

    @Override
    public int compare( Piece p1, Piece p2 ) {
        final Coordinates c1 = p1.getCoordinates();
        final Coordinates c2 = p2.getCoordinates();

        if ( c1.getLayer() != c2.getLayer() ) {
            return c1.getLayer() - c2.getLayer();
        }

        if ( c1.getX() != c2.getX() ) {
            return c2.getX() - c1.getX();
        }

        if ( c1.getY() != c2.getY() ) {
            return c1.getY() - c2.getY();
        }

        return 0;
    }
}
