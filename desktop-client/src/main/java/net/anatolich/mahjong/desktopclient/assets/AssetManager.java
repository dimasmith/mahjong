package net.anatolich.mahjong.desktopclient.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Provides access to different game resources such as images, sounds and so on.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class AssetManager {

    private static final String ASSETS_ROOT = "/assets/";

    public BufferedImage loadImage( String path ) {
        final String assetPath = ASSETS_ROOT + path;
        try {
            BufferedImage image = ImageIO.read(AssetManager.class.getResourceAsStream(assetPath));
            return image;
        } catch ( IOException ex ) {
            throw new AssetException(ex);
        }
    }

    public TileMap loadTileMap( String path, int size ) {
        return new TileMap(size, loadImage(path));
    }

    public static void main( String[] args ) throws IOException {
        final AssetManager am = new AssetManager();
        TileMap tileMap = am.loadTileMap("tile/tilemap-196.png", 196);
        for ( int i = 0; i < tileMap.getTileCount(); i++ ) {
            BufferedImage tileImage = tileMap.getTile(i);
            File imageFile = new File(String.format("%s/tmp/tile-%s.png", System.getProperty("user.home"), i));
            ImageIO.write(tileImage, "png", imageFile);
        }

    }
}
