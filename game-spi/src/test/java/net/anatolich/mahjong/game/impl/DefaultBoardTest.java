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

import static net.anatolich.mahjong.game.impl.PieceBuilder.makePiece;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class DefaultBoardTest {

    private Piece eastWindPiece;
    private Coordinates zeroCoordinates;
    private Tile eastWind;
    private DefaultBoard boardImpl;

    @Before
    public void setUp() {
        boardImpl = new DefaultBoard();

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
    public void testRemovePiece() {
        boardImpl.putPiece(eastWindPiece);

        boardImpl.removePieceAt(eastWindPiece.getCoordinates());

        assertThat(boardImpl.getPieceAt(eastWindPiece.getCoordinates()), nullValue());
    }

    @Test
    public void testGetAllPieces_Empty() {
        List<Piece> allPieces = boardImpl.getAllPieces();
        assertThat(allPieces, isEmpty());
    }

    @Test
    public void testGetAllPieces() {

        Piece[] pieces = new Piece[]{
            makePiece().bamboo().one().at(2, 0, 0),
            makePiece().bamboo().two().at(4, 0, 0),
            makePiece().bamboo().three().at(6, 0, 0),
            makePiece().bamboo().four().at(8, 0, 0) };

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
            makePiece().bamboo().one().at(2, 0, 0),
            makePiece().bamboo().two().at(4, 0, 0),
            makePiece().bamboo().three().at(6, 0, 0),
            makePiece().bamboo().four().at(8, 0, 0) };

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
            makePiece().bamboo().one().at(2, 0, 0),
            makePiece().bamboo().two().at(4, 0, 0),
            makePiece().bamboo().three().at(6, 0, 0),
            makePiece().bamboo().four().at(8, 0, 0),
            makePiece().bamboo().five().at(8, 0, 1),
            makePiece().bamboo().five().at(8, 0, 2)
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
            makePiece().bamboo().one().at(2, 0, 0),
            makePiece().bamboo().two().at(4, 0, 0),
            makePiece().bamboo().three().at(6, 0, 0),
            makePiece().bamboo().four().at(8, 0, 0),
            makePiece().bamboo().five().at(8, 0, 1),
            topPiece };

        for ( Piece piece : pieces ) {
            boardImpl.putPiece(piece);
        }

        Piece topmostPiece = boardImpl.getTopmostPieceAt(8, 0);

        assertThat(topmostPiece, is(topPiece));
    }
}
