package net.anatolich.mahjong.game.layout;

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

        assertThat(layout.getTopLayer(), is(4));
        assertThat(layout.getSlotsCount(), is(144));
    }
}
