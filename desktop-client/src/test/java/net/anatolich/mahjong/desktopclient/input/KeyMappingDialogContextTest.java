package net.anatolich.mahjong.desktopclient.input;

import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class KeyMappingDialogContextTest {

    public KeyMappingDialogContextTest() {
    }

    @Test
    public void testKeyRemap_NoActionMapped() {
        JComponent fakeComponent = new JLabel();
        KeyMapper keyMapper = EasyMock.createMock(KeyMapper.class);
        KeyMappingDialog dialog = EasyMock.createMock(KeyMappingDialog.class);
        Action action = EasyMock.createMock("action", Action.class);
        KeyEvent event = new KeyEvent(fakeComponent, 1, System.nanoTime(), KeyEvent.ALT_DOWN_MASK, KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);

        EasyMock.expect(keyMapper.getActionFor(event)).andReturn(null);
        keyMapper.changeMapping(action, event);

        EasyMock.replay(keyMapper, dialog);

        KeyMappingDialogContext context = new KeyMappingDialogContext(keyMapper, dialog);
        context.remapAction(action, event);

        EasyMock.verify(keyMapper, dialog);
    }

    @Test
    public void testKeyRemap_MappingAlreadyUsed_RemappingAllowed() {

        JComponent fakeComponent = new JLabel();
        KeyMapper keyMapper = EasyMock.createMock(KeyMapper.class);
        KeyMappingDialog dialog = EasyMock.createMock(KeyMappingDialog.class);
        Action action = EasyMock.createMock("action", Action.class);
        final String otherActionName = "otherAction";
        Action otherAction = EasyMock.createMock(otherActionName, Action.class);
        KeyEvent event = new KeyEvent(fakeComponent, 1, System.nanoTime(), KeyEvent.ALT_DOWN_MASK, KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);

        EasyMock.expect(keyMapper.getActionFor(event)).andReturn(otherAction);

        EasyMock.expect(otherAction.getValue(Action.NAME)).andReturn(otherActionName);

        EasyMock.expect(dialog.askIsRemappingAllowed(otherActionName)).andReturn(true);

        keyMapper.removeMapping(otherAction);
        keyMapper.changeMapping(action, event);

        EasyMock.replay(keyMapper, dialog, otherAction);

        KeyMappingDialogContext context = new KeyMappingDialogContext(keyMapper, dialog);
        context.remapAction(action, event);

        EasyMock.verify(keyMapper, dialog, otherAction);
    }

    @Test
    public void testKeyRemap_MappingAlreadyUsed_RemappingDenied() {

        JComponent fakeComponent = new JLabel();
        KeyMapper keyMapper = EasyMock.createMock(KeyMapper.class);
        KeyMappingDialog dialog = EasyMock.createMock(KeyMappingDialog.class);
        Action action = EasyMock.createMock("action", Action.class);
        final String otherActionName = "otherAction";
        Action otherAction = EasyMock.createMock(otherActionName, Action.class);
        KeyEvent event = new KeyEvent(fakeComponent, 1, System.nanoTime(), KeyEvent.ALT_DOWN_MASK, KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);

        EasyMock.expect(keyMapper.getActionFor(event)).andReturn(otherAction);

        EasyMock.expect(otherAction.getValue(Action.NAME)).andReturn(otherActionName);

        EasyMock.expect(dialog.askIsRemappingAllowed(otherActionName)).andReturn(false);

        EasyMock.replay(keyMapper, dialog, otherAction);

        KeyMappingDialogContext context = new KeyMappingDialogContext(keyMapper, dialog);
        context.remapAction(action, event);

        EasyMock.verify(keyMapper, dialog, otherAction);
    }
}
