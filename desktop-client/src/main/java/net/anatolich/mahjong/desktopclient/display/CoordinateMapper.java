package net.anatolich.mahjong.desktopclient.display;

import java.awt.geom.Point2D;
import net.anatolich.mahjong.game.Coordinates;

/**
 * Maps 3D game coordinates to 2D graphical coordinates.
 * Projection is scaled differently along each axis to
 * keep tile image scaled as 3x4x2. Game coordinates are
 * 2x2x1 correspondingly.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class CoordinateMapper {

    private static final int SCALE_X = 3;
    private static final int SCALE_Y = 4;
    private static final int SCALE_Z = 2;

    private final int pixelRatio;

    CoordinateMapper( int pixelRatio ) {
        this.pixelRatio = pixelRatio;
    }

    Point2D mapTo2D( Coordinates gameCoordinates ) {
        double x = pixelRatio * SCALE_X * gameCoordinates.getX();
        double y = pixelRatio * SCALE_Y * gameCoordinates.getY();
        double z = pixelRatio * SCALE_Z * Math.sqrt(0.5) * gameCoordinates.getLayer();
        x = x + z;
        y = y - z;
        Point2D point = new Point2D.Double(x, y);
        return point;
    }
}
