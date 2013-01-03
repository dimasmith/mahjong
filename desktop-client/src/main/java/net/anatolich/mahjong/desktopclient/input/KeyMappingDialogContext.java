package net.anatolich.mahjong.desktopclient.input;

import java.awt.event.KeyEvent;
import javax.swing.Action;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class KeyMappingDialogContext {

    private final KeyMapper keyMapper;
    private final KeyMappingDialog dialog;

    public KeyMappingDialogContext( KeyMapper keyMapper, KeyMappingDialog dialog ) {
        this.keyMapper = keyMapper;
        this.dialog = dialog;
    }

    public void remapAction( Action action, KeyEvent event ) {
        final Action mappedAction = keyMapper.getActionFor(event);
        if ( mappedAction == null ) {
            keyMapper.changeMapping(action, event);
            return;
        }

        String mappedActionName = ( String ) mappedAction.getValue(Action.NAME);
        
        if ( dialog.askIsRemappingAllowed(mappedActionName) ) {
            keyMapper.removeMapping(mappedAction);
            keyMapper.changeMapping(action, event);
        }
    }
}
