package net.anatolich.mahjong.game.layout;

import java.text.MessageFormat;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class EmptyLayerException extends IllegalLayoutException {

    private final int emptyLayer;

    public EmptyLayerException( int layer ) {
        super(MessageFormat.format("Layer #{0} is empty", layer));
        this.emptyLayer = layer;
    }

    public int getEmptyLayer() {
        return emptyLayer;
    }



}
