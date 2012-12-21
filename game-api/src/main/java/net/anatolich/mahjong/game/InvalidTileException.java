package net.anatolich.mahjong.game;

import java.text.MessageFormat;

/**
 * Exception that is thrown when tile is created with invalid type/value combination
 * <p/>
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class InvalidTileException extends RuntimeException {

    private final Tile.Type type;
    private final Tile.Value value;

    public InvalidTileException( Tile.Type type, Tile.Value value ) {
        super(MessageFormat.format("Tile cannot be constructed. Tile of type {0} cannot have value {1}", type, value));
        this.type = type;
        this.value = value;
    }

    public InvalidTileException( Tile.Type type, Tile.Value value, String message ) {
        super(message);
        this.type = type;
        this.value = value;
    }

    public InvalidTileException( Tile.Type type, Tile.Value value, String message, Throwable cause ) {
        super(message, cause);
        this.type = type;
        this.value = value;
    }

    public InvalidTileException( Tile.Type type, Tile.Value value, Throwable cause ) {
        super(cause);
        this.type = type;
        this.value = value;
    }

    public Tile.Type getType() {
        return type;
    }

    public Tile.Value getValue() {
        return value;
    }
}
