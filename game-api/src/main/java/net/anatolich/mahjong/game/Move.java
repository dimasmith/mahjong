package net.anatolich.mahjong.game;

/**
 * Move that is made by player
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class Move {

    private final Tile startTile;
    private final Tile endTile;

    public Move( Tile startTile, Tile endTile ) {
        this.startTile = startTile;
        this.endTile = endTile;
    }

    public Tile getStartTile() {
        return startTile;
    }

    public Tile getEndTile() {
        return endTile;
    }
}
