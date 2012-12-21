package net.anatolich.mahjong.game.layout;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class IllegalLayoutException extends RuntimeException {

    public IllegalLayoutException() {
    }

    public IllegalLayoutException( String message ) {
        super(message);
    }
}
