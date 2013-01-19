package net.anatolich.mahjong.desktopclient.assets.util;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Generates linear sprite of mahjong tiles using subset of mahjong tiles in unicode.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class TileSpriteGenerator {

    private Font font;
    private Color color = Color.BLACK;

    public TileSpriteGenerator() {
        font = new Font("FreeSerif", Font.PLAIN, 32);
    }

    public BufferedImage createTileSprite( int tileSize ) {
        final int amountOfTiles = 0x2c;
        final int baseCodePoint = 0x1f000;
        final BufferedImage spriteImage = new BufferedImage(tileSize * amountOfTiles, tileSize, BufferedImage.TYPE_INT_ARGB);

        final Graphics2D g2 = spriteImage.createGraphics();
        final Font effectiveFont = font.deriveFont(font.getStyle(), tileSize);
        g2.setFont(effectiveFont);
        g2.setColor(color);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for ( int i = 0; i < amountOfTiles; i++ ) {
            final int codePoint = baseCodePoint + i;
            final String tileChar = new String(Character.toChars(codePoint));
            LineMetrics lineMetrics = effectiveFont.getLineMetrics(tileChar, g2.getFontRenderContext());
            float ascent = lineMetrics.getAscent();
            float descent = lineMetrics.getDescent();
            int charWidth = g2.getFontMetrics().charWidth(codePoint);

            g2.drawString(tileChar, ( i * tileSize ) + ( tileSize - charWidth ) / 2, ascent - descent);
        }
        return spriteImage;
    }

    public Font getFont() {
        return font;
    }

    public void setFont( Font font ) {
        this.font = font;
    }

    public Color getColor() {
        return color;
    }

    public void setColor( Color color ) {
        this.color = color;
    }

    public static void main( String[] args ) throws IOException {
        TileSpriteGenerator generator = new TileSpriteGenerator();
        final int[] sizes = new int[]{ 196, 96, 64 };
        for ( int size : sizes ) {
            BufferedImage sptire = generator.createTileSprite(size);
            File assetFile = new File(String.format("%s/tilemap-%s.png",System.getProperty("user.home"), size));
            ImageIO.write(sptire, "png", assetFile);
        }

    }
}
