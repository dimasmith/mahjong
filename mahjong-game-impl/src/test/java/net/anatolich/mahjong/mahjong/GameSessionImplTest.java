package net.anatolich.mahjong.mahjong;

import java.util.Arrays;
import java.util.Collections;
import net.anatolich.mahjong.game.AvailableMove;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.GameEvent;
import net.anatolich.mahjong.game.GameSessionListener;
import net.anatolich.mahjong.game.MoveCompletedEvent;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.Tile;
import net.anatolich.mahjong.game.rules.Rules;
import net.anatolich.mahjong.game.spi.MutableBoard;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static net.anatolich.mahjong.test.GameSessionMatcher.*;
import static net.anatolich.mahjong.test.PieceBuilder.makePiece;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import org.junit.After;


/**
 * Tests session implementation for correct behavior.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 * @version 1.0
 * @since 1.0
 */
public class GameSessionImplTest {

    private Coordinates coords1 = new Coordinates(0, 0, 0);
    private Coordinates coords2 = new Coordinates(2, 0, 0);
    private Tile bambooOne = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);
    private Tile seasonWinter = new Tile(Tile.Type.SEASONS, Tile.Value.WINTER);
    private Tile seasonSummer = new Tile(Tile.Type.SEASONS, Tile.Value.SUMMER);
    private GameSessionImpl session;
    private MutableBoard board;
    private Rules rules;
    private GameSessionListener listener;

    @Before
    public void setUp() {
        board = EasyMock.createMock("board", MutableBoard.class);
        rules = EasyMock.createMock("rules", Rules.class);

        EasyMock.expect(board.getAllPieces()).andReturn(Collections.EMPTY_LIST).anyTimes(); // TODO Change this test
        EasyMock.replay(board);
        session = new GameSessionImpl(board, rules);
        EasyMock.reset(board);
        
        listener = EasyMock.createMock("listener", GameSessionListener.class);
        session.addListener(listener);
        
    }
    
    @After
    public void tearDown(){
        session.removeListener(listener);
    }

    /**
     * Marked tiles list is empty. Picking up blocked tile. No changes should be in marked tiles list.
     * No move should be occurred.
     */
    @Test
    public void testPickPieceAt_EmptyBlocked() {
        final Piece bambooOnePiece = new Piece(bambooOne, coords1);

        setUpBoard(bambooOnePiece);

        EasyMock.expect(rules.isPieceOpen(bambooOnePiece.getCoordinates(), board)).andReturn(false);

        EasyMock.replay(rules);
        EasyMock.replay(listener); // Nothing should happen to listener

        session.pickPieceAt(bambooOnePiece.getCoordinates());

        assertThat(session, allOf(not(hasPickedPieces()), not(moveWasCompleted())));
        EasyMock.verify(listener);

    }

    /**
     * Marked tiles list is empty. Picking up selectable tile. Picked tile must be placed to
     * picked tiles list. Move should not complete on this stage.
     */
    @Test
    public void testPickPieceAt_EmptySelectable() {
        final Piece newPiece = makePiece().bamboo().one().at(0, 0, 0);

        setUpBoard(newPiece);

        pieceIsOpen(newPiece);

        EasyMock.replay(rules);
        
        final GameEvent event = new GameEvent(GameEvent.Type.SELECTION_CHANGED, session, newPiece);        
        listener.pickedPiecesChanged(event);
        EasyMock.replay(listener);

        session.pickPieceAt(newPiece.getCoordinates());

        assertThat(session, allOf(hasPickedPieces(), moveWasNotCompleted()));
        assertThat(session.getPickedPieces(), hasItem(newPiece));

        EasyMock.verify(listener);
    }

    /**
     * Tests scenario that leads to valid mahjong move.
     */
    @Test
    public void testPickPieceAt_PickedAllowed() {
        final Piece pickedPiece = new Piece(seasonWinter, coords2);

        session.pickPiece(pickedPiece);

        final Piece newPiece = new Piece(seasonSummer, coords1);

        board.removePieceAt(newPiece.getCoordinates());
        EasyMock.expectLastCall().once();
        board.removePieceAt(pickedPiece.getCoordinates());
        EasyMock.expectLastCall().once();
        setUpBoard(pickedPiece, newPiece);

        EasyMock.expect(rules.isMoveLegal(pickedPiece.getCoordinates(), newPiece.getCoordinates(), board)).andReturn(true).anyTimes();

        EasyMock.replay(rules);
        
        EasyMock.reset(listener);
        final GameEvent event = new MoveCompletedEvent(session, pickedPiece, newPiece);
        listener.turnCompleted(event);
        EasyMock.replay(listener);
        
        session.pickPieceAt(newPiece.getCoordinates());

        assertThat(session, allOf(noPiecesArePicked(), moveWasCompleted()));
        EasyMock.verify(board, listener);   
    }

    @Test
    public void testPickPieceAt_PickedDeniedBlocked() {
        final Piece pickedPiece = new Piece(seasonWinter, coords2);

        session.pickPiece(pickedPiece);

        final Piece newPiece = new Piece(seasonSummer, coords1);

        setUpBoard(pickedPiece, newPiece);

        moveIsIllegal(pickedPiece, newPiece);
        EasyMock.expect(rules.isPieceOpen(newPiece.getCoordinates(), board)).andReturn(false);

        EasyMock.replay(rules);

        session.pickPieceAt(newPiece.getCoordinates());

        assertThat(session, allOf(hasPickedPieces(), moveWasNotCompleted()));
        assertThat(session.getPickedPieces(), hasItem(pickedPiece));

    }

    @Test
    public void testPickPieceAt_PickedDeniedOpen() {
        final Piece selectedPiece = new Piece(seasonWinter, coords2);

        session.pickPiece(selectedPiece);

        final Piece newPiece = new Piece(seasonSummer, coords1);

        setUpBoard(selectedPiece, newPiece);

        moveIsIllegal(selectedPiece, newPiece);
        pieceIsOpen(newPiece);

        EasyMock.replay(rules);

        session.pickPieceAt(newPiece.getCoordinates());

        assertThat(session, allOf(hasPickedPieces(), moveWasNotCompleted()));
        assertThat(session.getPickedPieces(), hasItem(newPiece));

    }

    @Test
    public void testPickPieceAt_PickAfterMove() {
        final Piece bambooOnePiece = new Piece(bambooOne, new Coordinates(0, 2, 0));

        makeMove();

        setUpBoard(bambooOnePiece);

        EasyMock.expect(rules.isPieceOpen(bambooOnePiece.getCoordinates(), board)).andReturn(true).anyTimes();
        EasyMock.replay(rules);

        session.pickPieceAt(bambooOnePiece.getCoordinates());

        assertThat(session, moveWasNotCompleted());
        EasyMock.verify(board);
    }
    
    @Test
    public void testGameWon(){
        final Piece piece1 = makePiece().bamboo().one().at(0, 0, 0);
        final Piece piece2 = makePiece().bamboo().one().at(2, 0, 0);
        
        EasyMock.reset(board);
        board.removePieceAt(EasyMock.anyObject(Coordinates.class));
        EasyMock.expectLastCall().anyTimes();
        EasyMock.expect(board.getAllPieces()).andReturn(Collections.EMPTY_LIST); // No more pieces left
        EasyMock.replay(board);
        
        listener.turnCompleted(EasyMock.anyObject(GameEvent.class));
        listener.gameWon();
        EasyMock.replay(listener);
        
        session.completeMove(piece1, piece2);
        
        EasyMock.verify(listener);        
    }
    
    @Test
    public void testGameLost(){
        
        final Piece piece1 = makePiece().bamboo().one().at(0, 0, 0);
        final Piece piece2 = makePiece().bamboo().one().at(4, 0, 0);
        
        final Piece piece3 = makePiece().bamboo().two().at(2, 0, 0);
        final Piece piece4 = makePiece().bamboo().three().at(6, 0, 0);
        
        AvailableMove move = new AvailableMove(piece1, piece2);
        AvailableMovesCollector availableMovesCollector = EasyMock.createMock("availableMovesCollector", AvailableMovesCollector.class);
        EasyMock.expect(availableMovesCollector.collectMoves()).andReturn(Arrays.asList(new AvailableMove[]{move}));
        EasyMock.replay(availableMovesCollector);
        
        session = new GameSessionImpl(board, rules, availableMovesCollector);        
        
        EasyMock.reset(board);
        board.removePieceAt(EasyMock.anyObject(Coordinates.class));
        EasyMock.expectLastCall().anyTimes();
        EasyMock.expect(board.getAllPieces()).andReturn(Arrays.asList(new Piece[] {piece3, piece4 })); // Some pieces left
        EasyMock.replay(board);
        
        EasyMock.reset(availableMovesCollector);
        EasyMock.expect(availableMovesCollector.collectMoves()).andReturn(Collections.EMPTY_LIST);
        EasyMock.replay(availableMovesCollector);
        
        listener.turnCompleted(EasyMock.anyObject(GameEvent.class));
        listener.noMovesLeft();
        EasyMock.replay(listener);
        
        session.addListener(listener);
        session.completeMove(piece1, piece2);
        
        EasyMock.verify(listener);
        
    }

    private void setUpBoard( final Piece... pieces ) {
        for ( Piece piece : pieces ) {
            EasyMock.expect(board.getPieceAt(piece.getCoordinates())).andReturn(piece).anyTimes();
        }
        EasyMock.expect(board.getAllPieces()).andReturn(Arrays.asList(pieces)).anyTimes();
        EasyMock.replay(board);
    }

    /**
     * Sets board in right-after-move state
     *
     */
    private void makeMove() {
        final Piece seasonWinterPiece = new Piece(seasonWinter, coords2);
        final Piece seasonSummerPiece = new Piece(seasonSummer, coords1);

        session.pickPiece(seasonWinterPiece);
        board.removePieceAt(coords1);
        EasyMock.expectLastCall().once();
        board.removePieceAt(coords2);
        EasyMock.expectLastCall().once();
        setUpBoard(seasonWinterPiece, seasonSummerPiece);

        EasyMock.expect(rules.isMoveLegal(coords2, coords1, board)).andReturn(true).anyTimes();

        EasyMock.replay(rules);

        session.pickPieceAt(coords1);
        assertThat(session, moveWasCompleted());

        EasyMock.reset(board);
        EasyMock.reset(rules);
    }

    private void moveIsIllegal(final Piece selectedPiece, final Piece newPiece) {
        EasyMock.expect(rules.isMoveLegal(selectedPiece.getCoordinates(), newPiece.getCoordinates(), board)).andReturn(false);
    }

    private void pieceIsOpen(final Piece newPiece) {
        EasyMock.expect(rules.isPieceOpen(newPiece.getCoordinates(), board)).andReturn(true);
    }
}
class SessionBuilder {
    private GameSessionImpl session;
}