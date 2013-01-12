package net.anatolich.mahjong.game.layout;

import java.util.Collection;
import net.anatolich.mahjong.game.Coordinates;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public interface Layout {

    Collection<Slot> getSlots();
    
    Coordinates getLowerBound();

    Coordinates getUpperBound();

}
