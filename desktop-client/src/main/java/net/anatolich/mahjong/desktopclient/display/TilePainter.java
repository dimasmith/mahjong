package net.anatolich.mahjong.desktopclient.display;

import net.anatolich.mahjong.game.Coordinates;

/**
 * Paints tile on coordinates specified with coordinates of top-left-lower point.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class TilePainter {

    public static final int TILE_WIDTH = 2;
    public static final int TILE_LENGTH = 2;
    public static final int TILE_HEIGHT = 1;

    public enum BoundingPoints {

        LEFT_TOP_LOWER(new Coordinates(0, 0, 0)),
        LEFT_TOP_UPPER(new Coordinates(0, 0, TILE_HEIGHT)),
        RIGHT_TOP_LOWER(new Coordinates(TILE_WIDTH, 0, 0)),
        RIGHT_TOP_UPPER(new Coordinates(TILE_WIDTH, 0, TILE_HEIGHT)),
        LEFT_BOTTOM_LOWER(new Coordinates(0, TILE_LENGTH, 0)),
        LEFT_BOTTOM_UPPER(new Coordinates(0, TILE_LENGTH, TILE_HEIGHT)),
        RIGHT_BOTTOM_LOWER(new Coordinates(TILE_WIDTH, TILE_LENGTH, 0)),
        RIGHT_BOTTOM_UPPER(new Coordinates(TILE_WIDTH, TILE_LENGTH, TILE_HEIGHT));
        private final Coordinates translation;

        private BoundingPoints( Coordinates translation ) {
            this.translation = translation;
        }

        public Coordinates translate(Coordinates base){
            return base.translate(translation);
        }
    }
    private final Coordinates leftTopLower;

    TilePainter( Coordinates leftTopLower ) {
        this.leftTopLower = leftTopLower;
    }

    Coordinates getBoundingPoint( BoundingPoints point ) {
        return point.translate(leftTopLower);
    }
}
