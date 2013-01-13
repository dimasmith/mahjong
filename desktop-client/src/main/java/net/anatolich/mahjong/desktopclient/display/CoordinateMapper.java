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
    private static final double PERSPECTIVE_COEFFICIENT = Math.sqrt(0.5);
    private final int xCoef;
    private final int yCoef;
    private final double zCoef;

    public CoordinateMapper( int pixelRatio ) {
        zCoef = pixelRatio * SCALE_Z * PERSPECTIVE_COEFFICIENT;
        yCoef = pixelRatio * SCALE_Y;
        xCoef = pixelRatio * SCALE_X;
    }

    public Point2D mapTo2D( Coordinates gameCoordinates ) {
        double z = zCoef * gameCoordinates.getLayer();
        double x = xCoef * gameCoordinates.getX() + z;
        double y = yCoef * gameCoordinates.getY() - z;
        return new Point2D.Double(x, y);
    }

    public double getLengthAlongX( int length ) {
        return xCoef * length;
    }

    public double getLengthAlongY( int length ) {
        return yCoef * length;
    }
}
