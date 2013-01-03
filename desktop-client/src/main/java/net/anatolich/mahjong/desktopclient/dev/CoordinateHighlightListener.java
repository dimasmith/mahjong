package net.anatolich.mahjong.desktopclient.dev;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Development mode tool that shows current cursor coordinates in terms of game board.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class CoordinateHighlightListener extends MouseMotionAdapter {

    private final Graphics2D g2;
    private int pX = 0;
    private int pY = 0;

    public CoordinateHighlightListener( Graphics2D g2 ) {
        if ( g2 == null ) {
            throw new IllegalArgumentException("Graphics context must not be null");
        }
        this.g2 = g2;
    }

    @Override
    public void mouseMoved( MouseEvent e ) {
        String oldCoords = String.format("%d - %d", pX, pY);
        String coords = String.format("%d - %d", e.getX(), e.getY());
        g2.setXORMode(Color.WHITE);
        g2.drawString(oldCoords, pX, pY);
        pX = e.getX();
        pY = e.getY();
        g2.drawString(coords, e.getX(), e.getY());
    }
}
