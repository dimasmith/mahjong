package net.anatolich.mahjong.graphics;

import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * Shape that is similar to outer mahjong tile outline.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class ComplexPath {

    public Shape createComplexShape( int startX, int startY ) {

        final Area area = new Area();
        final int x = startX;
        final int y = startY;
        final int width = 150;
        final int height = 200;
        final int bevel = 25;

        final Rectangle2D rectangle = new Rectangle2D.Double(x, y, width, height);
        area.add(new Area(rectangle));

        final Path2D topLeftTriangle = new Path2D.Double();
        topLeftTriangle.moveTo(x, y);
        topLeftTriangle.lineTo(x + bevel, y);
        topLeftTriangle.lineTo(x, y + bevel);
        topLeftTriangle.closePath();
        area.subtract(new Area(topLeftTriangle));

        final Path2D bottomRightTriangle = new Path2D.Double();
        bottomRightTriangle.moveTo(x + width, y + height);
        bottomRightTriangle.lineTo(x + width - bevel, y + height);
        bottomRightTriangle.lineTo(x + width, y + height - bevel);
        bottomRightTriangle.closePath();
        area.subtract(new Area(bottomRightTriangle));

        return area;
    }
}
