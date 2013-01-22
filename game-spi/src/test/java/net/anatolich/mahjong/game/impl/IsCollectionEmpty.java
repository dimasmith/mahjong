package net.anatolich.mahjong.game.impl;

import java.util.Collection;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class IsCollectionEmpty extends TypeSafeMatcher<Collection> {

    @Override
    protected boolean matchesSafely( Collection item ) {
        return item.isEmpty();
    }

    @Override
    public void describeTo( Description description ) {
        description.appendText("Collection is not empty");
    }

    @Factory
    public static <T> Matcher<Collection> isEmpty() {
        return new IsCollectionEmpty();
    }

}
