package net.anatolich.mahjong.desktopclient.assets.util;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        final Font effectiveFont = font.deriveFont(font.getStyle(), tileSize);

        final Graphics2D referenceGraphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
        referenceGraphics.setFont(effectiveFont);

        int maximalCharWidth = 0;
        float maxAscent = 0.0f;
        float maxDescent = 0.0f;
        float maxHeight = 0.0f;
        float maxLeading = 0.0f;
        for ( int i = 0; i < amountOfTiles; i++ ) {
            final int codePoint = baseCodePoint + i;
            final FontMetrics fontMetrics = referenceGraphics.getFontMetrics();
            int charWidth = fontMetrics.charWidth(codePoint);
            maximalCharWidth = Math.max(maximalCharWidth, charWidth);

            final String tileChar = new String(Character.toChars(codePoint));
            LineMetrics lineMetrics = effectiveFont.getLineMetrics(tileChar, referenceGraphics.getFontRenderContext());
            float ascent = lineMetrics.getAscent();
            float descent = lineMetrics.getDescent();
            float height = lineMetrics.getHeight();
            float leading = lineMetrics.getLeading();


            maxAscent = Math.max(maxAscent, ascent);
            maxDescent = Math.max(maxDescent, descent);
            maxHeight = Math.max(maxHeight, height);
            maxLeading = Math.max(maxLeading, leading);
        }
        final float tileYPosition = maxAscent - maxDescent - maxLeading / 2;

        final BufferedImage spriteImage = new BufferedImage(maximalCharWidth * amountOfTiles, (int) (maxHeight - maxDescent - maxLeading), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2 = spriteImage.createGraphics();
        g2.setFont(effectiveFont);
        g2.setColor(color);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for ( int i = 0; i < amountOfTiles; i++ ) {
            final int codePoint = baseCodePoint + i;
            final String tileChar = new String(Character.toChars(codePoint));

            g2.setColor(Color.BLACK);
            g2.drawString(tileChar, i * maximalCharWidth, tileYPosition);

        }
        return spriteImage;
    }

    public void saveTileSets() throws IOException {
        final int[] sizes = new int[]{ 196, 96, 64 };
        for ( int size : sizes ) {
            BufferedImage sptire = createTileSprite(size);
            File assetFile = new File(String.format("%s/tilemap-%s.png", System.getProperty("user.home"), size));
            ImageIO.write(sptire, "png", assetFile);
        }
    }

    public void previewTileSet( int size ) {
        try {
            BufferedImage sptire = createTileSprite(size);
            File assetFile = File.createTempFile("tilemap", ".png");
            ImageIO.write(sptire, "png", assetFile);
            Desktop.getDesktop().open(assetFile);
        } catch ( IOException ex ) {
            Logger.getLogger(TileSpriteGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        generator.previewTileSet(192);

    }
}
