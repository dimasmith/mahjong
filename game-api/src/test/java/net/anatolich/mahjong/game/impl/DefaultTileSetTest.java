package net.anatolich.mahjong.game.impl;

import java.util.List;
import net.anatolich.mahjong.game.Tile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class DefaultTileSetTest {

    private List<Tile> tiles;

    @Before
    public void setUp() {
        tiles = new DefaultTileSet().getTiles();
    }

    @Test
    public void testWinds() {
        int eastWinds = 0;
        int westWinds = 0;
        int northWinds = 0;
        int southWinds = 0;

        for ( Tile tile : tiles ) {
            if ( tile.getType() == Tile.Type.WINDS ) {
                switch ( tile.getValue() ) {
                    case EAST:
                        eastWinds++;
                        break;
                    case WEST:
                        westWinds++;
                        break;
                    case NORTH:
                        northWinds++;
                        break;
                    case SOUTH:
                        southWinds++;
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        }

        assertThat(eastWinds, is(4));
        assertThat(westWinds, is(4));
        assertThat(northWinds, is(4));
        assertThat(southWinds, is(4));
    }

    @Test
    public void testDragons() {
        int redDragons = 0;
        int greenDragons = 0;
        int whiteDragons = 0;

        for ( Tile tile : tiles ) {
            if ( tile.getType() == Tile.Type.DRAGONS ) {
                switch ( tile.getValue() ) {
                    case RED:
                        redDragons++;
                        break;
                    case GREEN:
                        greenDragons++;
                        break;
                    case WHITE:
                        whiteDragons++;
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        }

        assertThat(redDragons, is(4));
        assertThat(greenDragons, is(4));
        assertThat(whiteDragons, is(4));
    }

    @Test
    public void testSeasons() {
        int winters = 0;
        int springs = 0;
        int summers = 0;
        int autumns = 0;

        for ( Tile tile : tiles ) {
            if ( tile.getType() == Tile.Type.SEASONS ) {
                switch ( tile.getValue() ) {
                    case WINTER:
                        winters++;
                        break;
                    case SPRING:
                        springs++;
                        break;
                    case SUMMER:
                        summers++;
                        break;
                    case AUTUMN:
                        autumns++;
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        }

        assertThat(winters, is(4));
        assertThat(springs, is(4));
        assertThat(summers, is(4));
        assertThat(autumns, is(4));
    }

    @Test
    public void testFlowers() {
        int plums = 0;
        int orchids = 0;
        int chrysantemums = 0;
        int bamboos = 0;

        for ( Tile tile : tiles ) {
            if ( tile.getType() == Tile.Type.FLOWERS ) {
                switch ( tile.getValue() ) {
                    case PLUM:
                        plums++;
                        break;
                    case ORCHID:
                        orchids++;
                        break;
                    case CHRYSANTEMUM:
                        chrysantemums++;
                        break;
                    case BAMBOO:
                        bamboos++;
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        }

        assertThat(plums, is(4));
        assertThat(orchids, is(4));
        assertThat(chrysantemums, is(4));
        assertThat(bamboos, is(4));
    }

    @Test
    public void testBamboos() {
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int six = 0;
        int seven = 0;
        int eight = 0;
        int nine = 0;

        for ( Tile tile : tiles ) {
            if ( tile.getType() == Tile.Type.BAMBOOS ) {
                switch ( tile.getValue() ) {
                    case ONE:
                        one++;
                        break;
                    case TWO:
                        two++;
                        break;
                    case THREE:
                        three++;
                        break;
                    case FOUR:
                        four++;
                        break;
                    case FIVE:
                        five ++;
                        break;
                    case SIX:
                        six ++;
                        break;
                    case SEVEN:
                        seven ++;
                        break;
                    case EIGHT:
                        eight ++;
                        break;
                    case NINE:
                        nine ++;
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        }

        assertThat(one, is(4));
        assertThat(two, is(4));
        assertThat(three, is(4));
        assertThat(four, is(4));
        assertThat(five, is(4));
        assertThat(six, is(4));
        assertThat(seven, is(4));
        assertThat(eight, is(4));
        assertThat(nine, is(4));
    }

    @Test
    public void testCircles() {
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int six = 0;
        int seven = 0;
        int eight = 0;
        int nine = 0;

        for ( Tile tile : tiles ) {
            if ( tile.getType() == Tile.Type.CIRCLES ) {
                switch ( tile.getValue() ) {
                    case ONE:
                        one++;
                        break;
                    case TWO:
                        two++;
                        break;
                    case THREE:
                        three++;
                        break;
                    case FOUR:
                        four++;
                        break;
                    case FIVE:
                        five ++;
                        break;
                    case SIX:
                        six ++;
                        break;
                    case SEVEN:
                        seven ++;
                        break;
                    case EIGHT:
                        eight ++;
                        break;
                    case NINE:
                        nine ++;
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        }

        assertThat(one, is(4));
        assertThat(two, is(4));
        assertThat(three, is(4));
        assertThat(four, is(4));
        assertThat(five, is(4));
        assertThat(six, is(4));
        assertThat(seven, is(4));
        assertThat(eight, is(4));
        assertThat(nine, is(4));
    }

    @Test
    public void testCharacters() {
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int six = 0;
        int seven = 0;
        int eight = 0;
        int nine = 0;

        for ( Tile tile : tiles ) {
            if ( tile.getType() == Tile.Type.CHARACTERS ) {
                switch ( tile.getValue() ) {
                    case ONE:
                        one++;
                        break;
                    case TWO:
                        two++;
                        break;
                    case THREE:
                        three++;
                        break;
                    case FOUR:
                        four++;
                        break;
                    case FIVE:
                        five ++;
                        break;
                    case SIX:
                        six ++;
                        break;
                    case SEVEN:
                        seven ++;
                        break;
                    case EIGHT:
                        eight ++;
                        break;
                    case NINE:
                        nine ++;
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        }

        assertThat(one, is(4));
        assertThat(two, is(4));
        assertThat(three, is(4));
        assertThat(four, is(4));
        assertThat(five, is(4));
        assertThat(six, is(4));
        assertThat(seven, is(4));
        assertThat(eight, is(4));
        assertThat(nine, is(4));
    }
}
