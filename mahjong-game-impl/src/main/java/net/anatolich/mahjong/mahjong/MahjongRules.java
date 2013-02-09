package net.anatolich.mahjong.mahjong;

import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Tile;
import net.anatolich.mahjong.game.rules.Rules;

/**
 * Default mahjong rules implementation.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class MahjongRules implements Rules {

    private final TileMatcher tileMatcher;
    private final BlockChecker blockChecker;

    public MahjongRules() {
        this.blockChecker = new BlockedPieceChecker();
        this.tileMatcher = new TileMatcherImpl();
    }

    public MahjongRules( BlockChecker blockChecker, TileMatcher tileMatcher ) {
        this.blockChecker = blockChecker;
        this.tileMatcher = tileMatcher;
    }

    @Override
    public boolean isPieceOpen( Coordinates coordinates, Board board ) {
        return !blockChecker.isBlocked(coordinates, board);
    }

    @Override
    public boolean isMoveLegal( Coordinates start, Coordinates end, Board board ) {
        if ( start.equals(end) ) {
            return false;
        }

        if ( !isPieceOpen(start, board) || !isPieceOpen(end, board) ) {
            return false;
        }

        final Tile startTile = board.getPieceAt(start).getTile();
        final Tile endTile = board.getPieceAt(end).getTile();

        return tileMatcher.match(startTile, endTile);

    }
}
