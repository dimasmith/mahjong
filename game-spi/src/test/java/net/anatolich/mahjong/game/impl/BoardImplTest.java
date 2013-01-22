package net.anatolich.mahjong.game.impl;

import java.util.List;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.Tile;
import org.junit.Before;
import org.junit.Test;

import static net.anatolich.mahjong.game.impl.IsCollectionEmpty.*;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;


/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class BoardImplTest {

    private Piece eastWindPiece;
    private Coordinates zeroCoordinates;
    private Tile eastWind;
    private BoardImpl boardImpl;

    public BoardImplTest() {
    }

    @Before
    public void setUp() {
        boardImpl = new BoardImpl();

        zeroCoordinates = new Coordinates(0, 0, 0);
        eastWind = new Tile(Tile.Type.WINDS, Tile.Value.EAST);

        eastWindPiece = new Piece(eastWind, zeroCoordinates);
    }

    @Test
    public void testPutPiece() {
        boardImpl.putPiece(eastWindPiece);

        Piece pieceFromBoard = boardImpl.getPieceAt(zeroCoordinates);
        assertThat(pieceFromBoard, is(eastWindPiece));
    }

    @Test
    public void testGetAllPieces_Empty() {
        List<Piece> allPieces = boardImpl.getAllPieces();
        assertThat(allPieces, isEmpty());
    }

    @Test
    public void testGetAllPieces() {

        Piece[] pieces = new Piece[]{
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE), new Coordinates(2, 0, 0)),
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.TWO), new Coordinates(4, 0, 0)),
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.THREE), new Coordinates(6, 0, 0)),
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.FOUR), new Coordinates(8, 0, 0)) };

        for ( Piece piece : pieces ) {
            boardImpl.putPiece(piece);
        }

        List<Piece> allPieces = boardImpl.getAllPieces();
        for ( Piece piece : pieces ) {
            assertThat(allPieces, hasItem(piece));
        }
    }

    /**
     * Ensures that returned list of tiles does not affect underlying table upon modification.
     */
    @Test
    public void testGetAllPieces_EnsureUnmodifiable() {

        Piece[] pieces = new Piece[]{
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE), new Coordinates(2, 0, 0)),
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.TWO), new Coordinates(4, 0, 0)),
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.THREE), new Coordinates(6, 0, 0)),
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.FOUR), new Coordinates(8, 0, 0)) };

        for ( Piece piece : pieces ) {
            boardImpl.putPiece(piece);
        }

        List<Piece> modifiedPiecesList = boardImpl.getAllPieces();
        modifiedPiecesList.removeAll(modifiedPiecesList);

        for ( Piece piece : pieces ) {
            assertThat(boardImpl.getAllPieces(), hasItem(piece));
        }
    }

    @Test
    public void testGetTopmostPiece_EmptyColumn() {
        Piece[] pieces = new Piece[]{
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE), new Coordinates(2, 0, 0)),
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.TWO), new Coordinates(4, 0, 0)),
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.THREE), new Coordinates(6, 0, 0)),
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.FOUR), new Coordinates(8, 0, 0)),
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.FIVE), new Coordinates(8, 0, 1)),
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.FIVE), new Coordinates(8, 0, 2))
        };

        for ( Piece piece : pieces ) {
            boardImpl.putPiece(piece);
        }

        Piece topmostPiece = boardImpl.getTopmostPieceAt(0, 0);

        assertThat(topmostPiece, nullValue());
    }

    @Test
    public void testGetTopmostPiece_NonEmptyColumn() {
        final Piece topPiece = new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.FIVE), new Coordinates(8, 0, 2));

        Piece[] pieces = new Piece[]{
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE), new Coordinates(2, 0, 0)),
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.TWO), new Coordinates(4, 0, 0)),
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.THREE), new Coordinates(6, 0, 0)),
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.FOUR), new Coordinates(8, 0, 0)),
            new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.FIVE), new Coordinates(8, 0, 1)), topPiece };

        for ( Piece piece : pieces ) {
            boardImpl.putPiece(piece);
        }

        Piece topmostPiece = boardImpl.getTopmostPieceAt(8, 0);

        assertThat(topmostPiece, is(topPiece));
    }
}
