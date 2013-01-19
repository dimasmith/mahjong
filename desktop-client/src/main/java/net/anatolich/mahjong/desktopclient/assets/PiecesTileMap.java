package net.anatolich.mahjong.desktopclient.assets;

import java.awt.image.BufferedImage;
import net.anatolich.mahjong.game.Tile;
import net.anatolich.mahjong.game.Tile.Type;
import net.anatolich.mahjong.game.Tile.Value;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class PiecesTileMap {

    private static final int SIZE = 64;
    private static final String PATH = String.format("tile/tilemap-%s.png", SIZE);
    private final TileMap sourceTileMap;

    public PiecesTileMap() {
        this.sourceTileMap = new AssetManager().loadTileMap(PATH, SIZE);
    }

    public BufferedImage getPieceImage( Tile tile ) {
        int typeOffset = getTypeOffset(tile.getType());
        int valueOffset = getValueOffset(tile.getValue());
        return sourceTileMap.getTile(typeOffset + valueOffset);

    }

    private int getTypeOffset( Type type ) {
        switch ( type ) {
            case WINDS:
                return 0;
            case DRAGONS:
                return 4;
            case CHARACTERS:
                return 7;
            case BAMBOOS:
                return 16;
            case CIRCLES:
                return 25;
            case FLOWERS:
                return 34;
            case SEASONS:
                return 38;
            default:
                throw new IllegalArgumentException();
        }
    }

    private int getValueOffset( Value value ) {
        switch ( value ) {
            case ONE:
                return 0;
            case TWO:
                return 1;
            case THREE:
                return 2;
            case FOUR:
                return 3;
            case FIVE:
                return 4;
            case SIX:
                return 5;
            case SEVEN:
                return 6;
            case EIGHT:
                return 7;
            case NINE:
                return 8;
            case EAST:
                return 0;
            case SOUTH:
                return 1;
            case WEST:
                return 2;
            case NORTH:
                return 3;
            case RED:
                return 0;
            case GREEN:
                return 1;
            case WHITE:
                return 2;
            case PLUM:
                return 0;
            case ORCHID:
                return 1;
            case BAMBOO:
                return 2;
            case CHRYSANTEMUM:
                return 3;
            case SPRING:
                return 0;
            case SUMMER:
                return 1;
            case AUTUMN:
                return 2;
            case WINTER:
                return 3;
            default:
                throw new IllegalArgumentException();
        }
    }
}
