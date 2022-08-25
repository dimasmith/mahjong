package net.anatolich.mahjong.game.layout;

import net.anatolich.mahjong.game.Layout;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class LayoutFactoryTest {

    @Test
    public void testDefaultLayoutSize() {
        LayoutFactory factory = new LayoutFactory();
        Layout layout = factory.getDefaultLayout();

        Assertions.assertThat(layout.getUpperBound().getLayer()).isEqualTo(4);
        Assertions.assertThat(layout.getSlots()).hasSize(144);
    }
}
