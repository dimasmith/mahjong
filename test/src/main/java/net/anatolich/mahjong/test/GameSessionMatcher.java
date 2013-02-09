package net.anatolich.mahjong.test;

import java.util.List;
import net.anatolich.mahjong.game.GameSession;
import net.anatolich.mahjong.game.Piece;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.CoreMatchers.*;

/**
 * Collection of hamcrest matchers for {@link GameSession}.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class GameSessionMatcher {

    private static class HasPickedPieces extends TypeSafeDiagnosingMatcher<GameSession> {

        @Override
        protected boolean matchesSafely( GameSession session, Description mismatchDescription ) {
            mismatchDescription.appendText("no pieces were picked");
            return !session.getPickedPieces().isEmpty();
        }

        @Override
        public void describeTo( Description description ) {
            description.appendText("session has picked items");
        }
    }

    private static class MoveWasCompleted extends TypeSafeMatcher<GameSession> {

        @Override
        protected boolean matchesSafely( GameSession session ) {
            return session.wasMoveCompleted();
        }

        @Override
        public void describeTo( Description description ) {
            description.appendText("move is completed");

        }
    }

    public static <T> Matcher<GameSession> hasPickedPieces() {
        return new HasPickedPieces();
    }

    public static <T> Matcher<GameSession> noPiecesArePicked() {
        return not(new HasPickedPieces());
    }

    public static <T> Matcher<GameSession> moveWasCompleted() {
        return new MoveWasCompleted();
    }

    public static <T> Matcher<GameSession> moveWasNotCompleted() {
        return not(new MoveWasCompleted());
    }
}
