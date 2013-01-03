package net.anatolich.mahjong.desktopclient.input;

import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.Action;

/**
 * Maps keyboard keystrokes to certain actions.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public interface KeyMapper {

    List<Action> getAllActions();

    /**
     * Returns either action mapped to provided keystroke or null if no action is mapped.
     * @param keyEvent
     * @return
     */
    Action getActionFor( KeyEvent keyEvent );

    KeyEvent getMappingFor(Action action);

    void removeMapping( Action action );

    void changeMapping( Action action, KeyEvent newMapping );

}
