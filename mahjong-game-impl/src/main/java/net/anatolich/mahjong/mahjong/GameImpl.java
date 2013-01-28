package net.anatolich.mahjong.mahjong;

import java.util.Collections;
import java.util.List;
import net.anatolich.mahjong.game.Game;
import net.anatolich.mahjong.game.GameSession;
import net.anatolich.mahjong.game.Layout;
import net.anatolich.mahjong.game.impl.BoardFiller;
import net.anatolich.mahjong.game.impl.DefaultBoard;
import net.anatolich.mahjong.game.impl.DefaultTileSet;
import net.anatolich.mahjong.game.layout.LayoutFactory;
import net.anatolich.mahjong.game.rules.Rules;
import net.anatolich.mahjong.game.spi.MutableBoard;
import net.anatolich.mahjong.game.spi.TileSet;

/**
 * Implementation of Mahjong game.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class GameImpl implements Game {
    private final Rules mahjongRules = new MahjongRules();
    private final TileSet tileSet = new DefaultTileSet();

    @Override
    public String getName() {
        return "Mahjong";
    }

    @Override
    public GameSession startGame() {
        final Layout defaultLayout = new LayoutFactory().getDefaultLayout();

        return new GameSessionImpl(fillBoard(defaultLayout), mahjongRules);
    }

    @Override
    public GameSession startGame( Layout layout ) {
        return new GameSessionImpl(fillBoard(layout), mahjongRules);
    }

    @Override
    public List<Layout> supportedLayouts() {
        final Layout defaultLayout = new LayoutFactory().getDefaultLayout();
        return Collections.singletonList(defaultLayout);
    }

    private MutableBoard fillBoard( final Layout defaultLayout ) {
        final MutableBoard board = new DefaultBoard();
        new BoardFiller(board).fill(defaultLayout, tileSet);
        return board;
    }

}
