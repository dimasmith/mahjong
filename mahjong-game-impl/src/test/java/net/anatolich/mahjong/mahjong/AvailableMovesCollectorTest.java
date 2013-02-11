package net.anatolich.mahjong.mahjong;

import java.util.List;
import net.anatolich.mahjong.game.AvailableMove;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.rules.Rules;
import net.anatolich.mahjong.test.MockBoardBuilder;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static net.anatolich.mahjong.test.PieceBuilder.makePiece;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Dmytro
 */
public class AvailableMovesCollectorTest {

    private Rules rules;
    private Board board;
    private Piece pa1;
    private Piece pb1;
    private Piece pa2;
    private Piece pb2;
    
    @Before
    public void setUp(){
        rules = EasyMock.createMock("rules", Rules.class);
        pa1 = makePiece().bamboo().one().at(0, 0, 0);
        pb1 = makePiece().bamboo().two().at(2, 0, 0);
        pa2 = makePiece().bamboo().one().at(4, 0, 0);
        pb2 = makePiece().bamboo().two().at(6, 0, 0);
    }
    
    @Test
    public void testNoValidMoves(){
        board = MockBoardBuilder.createBoard(pa1, pb1, pa2, pb2);
        
        EasyMock.expect(rules.isMoveLegal(EasyMock.anyObject(Coordinates.class), EasyMock.anyObject(Coordinates.class), 
                EasyMock.anyObject(Board.class))).andReturn(false).anyTimes(); // No legal moves
        EasyMock.replay(rules);
        
        AvailableMovesCollector collector = new AvailableMovesCollector(board, rules);
        List<AvailableMove> availableMoves = collector.collectMoves();
        
        assertTrue(availableMoves.isEmpty());
        
    }
    
    @Test
    public void testSingleValidMove(){
        board = MockBoardBuilder.createBoard(pa1, pb1, pb2, pa2);
        
        EasyMock.expect(rules.isMoveLegal(pa1.getCoordinates(), pa2.getCoordinates(), 
                board)).andReturn(true).anyTimes(); // One legal move
        EasyMock.expect(rules.isMoveLegal(pa2.getCoordinates(), pa1.getCoordinates(), 
                board)).andReturn(true).anyTimes(); // Duplicate to be non-specific to piece order
        EasyMock.expect(rules.isMoveLegal(EasyMock.anyObject(Coordinates.class), EasyMock.anyObject(Coordinates.class), 
                EasyMock.anyObject(Board.class))).andReturn(false).anyTimes(); // No other legal moves
        EasyMock.replay(rules);
        
        AvailableMovesCollector collector = new AvailableMovesCollector(board, rules);
        List<AvailableMove> availableMoves = collector.collectMoves();
        
        
        final AvailableMove possibleMove = new AvailableMove(pa1, pa2);
        assertThat(availableMoves, anyOf(hasItem(possibleMove)));
        assertThat(availableMoves.size(), is(1));
        
    }
    
    @Test
    public void testMultipleValidMoves(){
        board = MockBoardBuilder.createBoard(pa1, pb1, pb2, pa2);
        
        EasyMock.expect(rules.isMoveLegal(pa1.getCoordinates(), pa2.getCoordinates(), 
                board)).andReturn(true).anyTimes(); // One legal move
        EasyMock.expect(rules.isMoveLegal(pa2.getCoordinates(), pa1.getCoordinates(), 
                board)).andReturn(true).anyTimes(); // Duplicate to be non-specific to piece order
        EasyMock.expect(rules.isMoveLegal(pb1.getCoordinates(), pb2.getCoordinates(), 
                board)).andReturn(true).anyTimes(); // One legal move
        EasyMock.expect(rules.isMoveLegal(pb2.getCoordinates(), pb1.getCoordinates(), 
                board)).andReturn(true).anyTimes(); // Duplicate to be non-specific to piece order
        EasyMock.expect(rules.isMoveLegal(EasyMock.anyObject(Coordinates.class), EasyMock.anyObject(Coordinates.class), 
                EasyMock.anyObject(Board.class))).andReturn(false).anyTimes(); // No other legal moves
        EasyMock.replay(rules);
        
        AvailableMovesCollector collector = new AvailableMovesCollector(board, rules);
        List<AvailableMove> availableMoves = collector.collectMoves();
                
        final AvailableMove possibleMove1 = new AvailableMove(pa1, pa2);
        
        final AvailableMove possibleMove2 = new AvailableMove(pb1, pb2);
        
        assertThat(availableMoves, (hasItems(possibleMove1, possibleMove2)));
        assertThat(availableMoves.size(), is(2));
        
    }
}