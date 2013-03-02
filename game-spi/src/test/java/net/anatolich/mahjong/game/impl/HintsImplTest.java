package net.anatolich.mahjong.game.impl;

import java.util.List;
import net.anatolich.mahjong.game.AvailableMove;
import net.anatolich.mahjong.game.capabilities.Hints;
import net.anatolich.mahjong.test.HintsCompatibilityTest;
import org.junit.Before;

/**
 *
 * @author Dmytro Kovalchuk
 */
public class HintsImplTest extends HintsCompatibilityTest {

    private HintsImpl hintsImpl;

    @Before
    public void createHints() {
        this.hintsImpl = new HintsImpl();
    }

    @Override
    protected Hints createHints(List<AvailableMove> testHints) {
        hintsImpl.setHints(testHints);
        return hintsImpl;
    }
}