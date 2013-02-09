package net.anatolich.mahjong.mahjong;

import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public interface BlockChecker {

    boolean isBlocked( Coordinates coordinates, Board board );

}
