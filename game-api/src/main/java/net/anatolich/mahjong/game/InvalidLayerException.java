package net.anatolich.mahjong.game;

import java.text.MessageFormat;

/**
 * Exception is thrown when client requests layer that is not present in layout.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class InvalidLayerException extends RuntimeException {

    private final int layer;

    public InvalidLayerException( int layer ) {
        super(MessageFormat.format("There are no layer #{0} in layout", layer));
        this.layer = layer;
    }

    public int getLayer() {
        return layer;
    }


}
