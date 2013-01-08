package net.anatolich.mahjong.game.rules.spi;

import net.anatolich.mahjong.game.BoardView;
import net.anatolich.mahjong.game.Slot;
import net.anatolich.mahjong.game.rules.Rules;
import net.anatolich.mahjong.game.rules.RulesTCK;
import org.easymock.EasyMock;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class AbstractRulesTest extends RulesTCK {

    @Override
    protected Rules getRules() {
        return new AbstractRulesImpl();
    }

    private static class AbstractRulesImpl extends AbstractRules {

        public AbstractRulesImpl( ) {
            super(EasyMock.createMock("board", BoardView.class));
        }

        @Override
        protected boolean isLegal( Slot startSlot, Slot endSlot ) {
            return false;
        }

        @Override
        protected boolean isOpen( Slot slot ) {
            return false;
        }
    }
}
