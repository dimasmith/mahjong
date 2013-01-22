package net.anatolich.mahjong.game;

import java.util.Collection;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.layout.Slot;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public interface Layout {

    String getName();

    String getDescription();

    Collection<Slot> getSlots();

    Coordinates getLowerBound();

    Coordinates getUpperBound();

}
