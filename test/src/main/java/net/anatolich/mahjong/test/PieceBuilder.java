package net.anatolich.mahjong.test;

import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.Tile;
import static net.anatolich.mahjong.game.Tile.Type.*;
import static net.anatolich.mahjong.game.Tile.Value.*;

/**
 * Provides syntactic sugar for creating pieces.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 *
 *
 */
public class PieceBuilder {

    private Tile.Type type;
    private Tile.Value value;
    private Coordinates coordinates;

    public PieceBuilder tile( Tile.Type type, Tile.Value value ) {
        return type(type).value(value);
    }

    public PieceBuilder type( Tile.Type type ) {
        this.type = type;
        return this;
    }

    public PieceBuilder value( Tile.Value value ) {
        this.value = value;
        return this;
    }

    public PieceBuilder bamboo() {
        return type(BAMBOOS);
    }

    public PieceBuilder character() {
        return type(CHARACTERS);
    }

    public PieceBuilder circle() {
        return type(CIRCLES);
    }

    public PieceBuilder dragon() {
        return type(DRAGONS);
    }

    public PieceBuilder flower() {
        return type(FLOWERS);
    }

    public PieceBuilder season() {
        return type(SEASONS);
    }

    public PieceBuilder wind() {
        return type(WINDS);
    }

    public PieceBuilder one() {
        return value(ONE);
    }

    public PieceBuilder two() {
        return value(TWO);
    }

    public PieceBuilder three() {
        return value(THREE);
    }

    public PieceBuilder four() {
        return value(FOUR);
    }

    public PieceBuilder five() {
        return value(FIVE);
    }

    public PieceBuilder six() {
        return value(SIX);
    }

    public PieceBuilder seven() {
        return value(SEVEN);
    }

    public PieceBuilder eight() {
        return value(EIGHT);
    }

    public PieceBuilder nine() {
        return value(NINE);
    }

    public PieceBuilder autumn() {
        return value(AUTUMN);
    }

    public PieceBuilder winter() {
        return value(WINTER);
    }

    public PieceBuilder spring() {
        return value(SPRING);
    }

    public PieceBuilder summer() {
        return value(SUMMER);
    }

    public PieceBuilder orchid() {
        return value(ORCHID);
    }

    public PieceBuilder plum() {
        return value(PLUM);
    }

    public PieceBuilder chrysantemum() {
        return value(CHRYSANTEMUM);
    }

    public PieceBuilder bamb() {
        return value(BAMBOO);
    }

    public Piece at( Coordinates coordinates ) {
        this.coordinates = coordinates;
        return create();
    }

    public Piece at( int x, int y, int z ) {
        return at(new Coordinates(x, y, z));
    }

    private Piece create() {
        return new Piece(new Tile(type, value), coordinates);
    }

    /**
     * Provides new instance of PieceBuilder each time it called.
     * @return
     */
    public static PieceBuilder makePiece(){
        return new PieceBuilder();
    }
}
