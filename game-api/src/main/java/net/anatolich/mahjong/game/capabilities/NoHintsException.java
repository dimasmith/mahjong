package net.anatolich.mahjong.game.capabilities;

/**
 * Thrown when {@link Hints#nextHint()} is called when no hints are available.
 * Be sure to check hints presence by calling {@link Hints#hasHints()} before
 * getting hints.
 *
 * @author Dmytro Kovalchuk
 * @version 1.0
 * @since 0.2
 */
public class NoHintsException extends RuntimeException {

    private final Hints hints;

    public NoHintsException(Hints hints) {
        this.hints = hints;
    }

    public Hints getHints() {
        return hints;
    }
}
