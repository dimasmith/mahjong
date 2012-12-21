package net.anatolich.mahjong.game.layout;

import java.text.MessageFormat;
import net.anatolich.mahjong.game.Coordinates;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class HangingSlotException extends IllegalLayoutException {

    private final Coordinates hangingSlot;

    public HangingSlotException( Coordinates hangingSlot ) {
        super(MessageFormat.format("Slot {0} has no underlaying slot", hangingSlot));
        this.hangingSlot = hangingSlot;
    }

    public Coordinates getHangingSlot() {
        return hangingSlot;
    }
}
