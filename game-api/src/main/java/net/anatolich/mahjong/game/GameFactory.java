package net.anatolich.mahjong.game;

import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Main entry point for mahjong-like games. Provides list of available games
 * and allows to instantiate game.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public final class GameFactory {

    /**
     * Lists all games available for application.
     * @return
     */
    public List<Game> availableGames(){
        final ServiceLoader<Game> gameTypesLoader = ServiceLoader.load(Game.class);

        final List<Game> games = new LinkedList<>();
        while ( gameTypesLoader.iterator().hasNext() ) {
            Game game = gameTypesLoader.iterator().next();
            games.add(game);
        }

        return games;
    }
}
