package net.anatolich.mahjong.mahjong;

import net.anatolich.mahjong.game.Tile;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public interface TileMatcher {

    boolean match( Tile t1, Tile t2 );

}
