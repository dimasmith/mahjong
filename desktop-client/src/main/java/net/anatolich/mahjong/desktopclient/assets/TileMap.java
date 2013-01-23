package net.anatolich.mahjong.desktopclient.assets;

import java.awt.image.BufferedImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class TileMap {

    private static final Logger log = LoggerFactory.getLogger(TileMap.class);
    private final int size;
    private final BufferedImage image;
    private final int width;
    private final int height;
    private final int tileCount;

    public TileMap( int size, BufferedImage image ) {
        this.size = size;
        this.image = image;
        this.width = image.getWidth() / size;
        this.height = image.getHeight() / size;
        this.tileCount = width * height;
    }

    public BufferedImage getTile( int number ) {
        final int x = number % width;
        final int y = number / width;

        BufferedImage tile = image.getSubimage(x * size, y * size, size, size);
        return tile;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTileCount() {
        return tileCount;
    }
}
