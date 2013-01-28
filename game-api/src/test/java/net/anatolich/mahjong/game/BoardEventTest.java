package net.anatolich.mahjong.game;

import org.easymock.EasyMock;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class BoardEventTest {

    private final Board board = EasyMock.createMock("board", Board.class);
    private final Piece firstPiece = new Piece(new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE), new Coordinates(0, 0, 0));
    private final Piece secontPiece = new Piece(new Tile(Tile.Type.CIRCLES, Tile.Value.TWO), new Coordinates(2, 2, 2));

    @Test
    public void testConstructorAssignment_NoPieces() {
        BoardEvent evt = new BoardEvent(BoardEvent.Type.PIECES_ADDED, board);

        assertThat("Event type must be \"Added\"", evt.getType(), is(BoardEvent.Type.PIECES_ADDED));
        assertThat("Board must be set", evt.getBoard(), is(board));
        assertThat("Selected pieces collection must be empty", evt.getPieces().isEmpty(), is(true));
        assertThat("Selected piece must be null", evt.getPiece(), nullValue());
    }

    @Test
    public void testConstructorAssignment_SinglePiece() {
        BoardEvent evt = new BoardEvent(BoardEvent.Type.PIECES_ADDED, board, firstPiece);

        assertThat("Affected pieces list does not contain all items", evt.getPieces(), hasItems(firstPiece));
        assertThat("Selected piece must be null", evt.getPiece(), is(firstPiece));
    }

    @Test
    public void testConstructorAssignment_MultiplePieces() {
        BoardEvent evt = new BoardEvent(BoardEvent.Type.PIECES_ADDED, board, firstPiece, secontPiece);

        assertThat("Affected pieces list does not contain all items", evt.getPieces(), hasItems(firstPiece, secontPiece));
        assertThat("Selected piece must be null", evt.getPiece(), is(firstPiece));
    }

    @Test
    public void testConstructorAssignment_NullPieces() {
        BoardEvent evt = new BoardEvent(BoardEvent.Type.PIECES_ADDED, board, null);

        assertThat("Affected pieces list does not contain all items", evt.getPieces().isEmpty(), is(true));
        assertThat("Selected piece must be null", evt.getPiece(), nullValue());
    }
}