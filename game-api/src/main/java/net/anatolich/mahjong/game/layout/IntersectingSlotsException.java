package net.anatolich.mahjong.game.layout;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class IntersectingSlotsException extends IllegalLayoutException {

    private final Slot baseSlot;
    private final Slot intersectingSlot;

    public IntersectingSlotsException( Slot baseSlot, Slot intersectingSlot ) {
        super(String.format("Slot intersection detected: baseSlot(%s); intersectingSlot(%s)", baseSlot.getCoordinates(), intersectingSlot.getCoordinates()));
        this.baseSlot = baseSlot;
        this.intersectingSlot = intersectingSlot;
    }

    public Slot getBaseSlot() {
        return baseSlot;
    }

    public Slot getIntersectingSlot() {
        return intersectingSlot;
    }



}
