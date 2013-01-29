package net.anatolich.mahjong.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class Canvas extends JComponent {

    private final ComplexPath pathFactory ;

    public Canvas() {
        pathFactory = new ComplexPath();
    }

    @Override
    protected void paintComponent( Graphics g ) {
        Graphics2D g2 = ( Graphics2D ) g.create();
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, getWidth(), getHeight());

        g2.setColor(Color.BLACK);

        final Area clippingPath = new Area();

        final Shape renderedTile = pathFactory.createComplexShape(275, 50);
        clippingPath.add(new Area(renderedTile));
        final Shape neighborTile = pathFactory.createComplexShape(150, 50);
        clippingPath.subtract(new Area(neighborTile));
        final Shape bottomTile = pathFactory.createComplexShape(275, 200);
        clippingPath.subtract(new Area(bottomTile));

        g2.setClip(clippingPath);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.dispose();
    }

    public static void main( String[] args ) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final JFrame window = new JFrame();
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setLayout(new BorderLayout());
                window.setSize(800, 600);
                window.add(new Canvas(), BorderLayout.CENTER);
                window.setVisible(true);
            }
        });
    }
}
