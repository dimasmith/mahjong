package net.anatolich.mahjong.mahjong;

import java.util.Collections;
import java.util.List;
import net.anatolich.mahjong.game.Game;
import net.anatolich.mahjong.game.GameSession;
import net.anatolich.mahjong.game.Layout;
import net.anatolich.mahjong.game.layout.LayoutFactory;

/**
 * Implementation of Mahjong game.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class GameImpl implements Game {

    @Override
    public String getName() {
        return "Mahjong";
    }

    @Override
    public GameSession startGame() {
        return new GameSessionImpl();
    }

    @Override
    public GameSession startGame( Layout layout ) {
        return new GameSessionImpl(layout);
    }

    @Override
    public List<Layout> supportedLayouts() {
        final Layout defaultLayout = new LayoutFactory().getDefaultLayout();
        return Collections.singletonList(defaultLayout);
    }

}
