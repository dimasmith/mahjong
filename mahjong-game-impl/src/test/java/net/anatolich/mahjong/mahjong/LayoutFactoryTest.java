package net.anatolich.mahjong.mahjong;

import net.anatolich.mahjong.mahjong.LayoutFactory;
import net.anatolich.mahjong.game.Layout;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class LayoutFactoryTest {

    @Test
    public void testDefaultLayoutSize() {
        LayoutFactory factory = new LayoutFactory();
        Layout layout = factory.getDefaultLayout();

        assertThat(layout.getUpperBound().getLayer(), is(4));
        assertThat(layout.getSlots().size(), is(144));
    }
}
