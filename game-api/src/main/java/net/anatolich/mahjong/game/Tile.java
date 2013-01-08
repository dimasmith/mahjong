package net.anatolich.mahjong.game;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Game tile with type and value.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class Tile {

    public enum Value {

        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, EAST, SOUTH, WEST, NORTH,
        RED, GREEN, WHITE, PLUM, ORCHID, CHRYSANTEMUM, BAMBOO, SPRING, SUMMER, AUTUMN, WINTER;
    }
    private static final Set<Value> NUMBERS =
            EnumSet.of(Tile.Value.ONE, Tile.Value.TWO, Tile.Value.THREE,
                       Tile.Value.FOUR, Tile.Value.FIVE, Tile.Value.SIX,
                       Tile.Value.SEVEN, Tile.Value.EIGHT, Tile.Value.NINE);

    public enum Type {

        BAMBOOS(NUMBERS),
        CHARACTERS(NUMBERS),
        CIRCLES(NUMBERS),
        WINDS(EnumSet.of(Tile.Value.EAST, Tile.Value.WEST, Tile.Value.NORTH, Tile.Value.SOUTH)),
        DRAGONS(EnumSet.of(Tile.Value.RED, Tile.Value.GREEN, Tile.Value.WHITE)),
        FLOWERS(EnumSet.of(Tile.Value.PLUM, Tile.Value.ORCHID, Tile.Value.CHRYSANTEMUM, Tile.Value.BAMBOO)),
        SEASONS(EnumSet.of(Tile.Value.WINTER, Tile.Value.SPRING, Tile.Value.SUMMER, Tile.Value.AUTUMN));
        private final Set<Value> supportedValues;

        private Type( Set<Value> supportedValues ) {
            this.supportedValues = supportedValues;
        }

        public boolean isValidForValue( Value value ) {
            return supportedValues.contains(value);
        }

        public Set<Value> getSupportedValues() {
            return Collections.unmodifiableSet(supportedValues);
        }
    }
    private final Type type;
    private final Value value;

    /**
     * Creates new tile.
     *
     * @param type
     * @param value
     *
     * @throws InvalidTileException when tile type does not fit to tile value.
     */
    public Tile( Type type, Value value ) {
        if ( !type.isValidForValue(value) ) {
            throw new InvalidTileException(type, value);
        }
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Tile{" + "type=" + type + ", value=" + value + '}';
    }
}
