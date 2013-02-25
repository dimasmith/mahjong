package net.anatolich.mahjong.desktopclient.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import net.anatolich.mahjong.game.AvailableMove;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.BoardEvent;
import net.anatolich.mahjong.game.BoardListener;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.GameEvent;
import net.anatolich.mahjong.game.GameSession;
import net.anatolich.mahjong.game.GameSessionListener;
import net.anatolich.mahjong.game.Piece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class BoardComponent extends JComponent implements GameSessionListener {

    private static final Logger log = LoggerFactory.getLogger(BoardComponent.class);
    
    private GameSession session;
    private Board board;
    private BoardView renderer;

    public BoardComponent() {
        this.session = new DummyGameSession();
        this.renderer = new BoardView(session, this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed( MouseEvent e ) {
                if ( e.getButton() == MouseEvent.BUTTON1 ) {
                    renderer.clickOn(e.getX(), e.getY());
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent( Graphics g ) {
        renderer.draw(( Graphics2D ) g);

    }

    public Board getBoard() {
        return session.getBoard();
    }

    public GameSession getGameSession() {
        return session;
    }

    public void setGameSession( GameSession game ) {
        this.session = game;
        this.board = game.getBoard();
        this.renderer = new BoardView(game, this);
        board.addChangeListener(renderer);
        session.addListener(this);
    }

    @Override
    public void pickedPiecesChanged(GameEvent event) {
        repaint();
    }

    @Override
    public void turnCompleted(GameEvent event) {
        int movesLeft = event.getSource().getAvailableMoves().size();
        log.debug(String.format("%s moves left.", movesLeft));
        repaint();
    }

    @Override
    public void noMovesLeft() {
        JOptionPane.showMessageDialog(this, "No moves left in this game");
    }

    @Override
    public void gameWon() {
        JOptionPane.showMessageDialog(this, "Congratulations! You have won!");
    }

    private static class DummyGameSession implements GameSession {

        private static final EmptyBoard EMPTY_BOARD = new EmptyBoard();

        @Override
        public Board getBoard() {
            return EMPTY_BOARD;
        }

        @Override
        public List<Piece> getPickedPieces() {
            return Collections.EMPTY_LIST;
        }

        @Override
        public boolean wasMoveCompleted() {
            return false;
        }

        @Override
        public void pickPieceAt( Coordinates coordinates ) {
        }

        @Override
        public boolean hasMoreMoves() {
            return false;
        }

        @Override
        public boolean isGameEnded() {
            return false;
        }

        @Override
        public List<AvailableMove> getAvailableMoves() {
            return Collections.EMPTY_LIST;
        }

        @Override
        public void addListener(GameSessionListener listener) {
        }

        @Override
        public void removeListener(GameSessionListener listener) {
        }

    }

    private static class EmptyBoard implements Board {

        @Override
        public List<Piece> getAllPieces() {
            return Collections.EMPTY_LIST;
        }

        @Override
        public Piece getTopmostPieceAt( int x, int y ) {
            return null;
        }

        @Override
        public Piece getPieceAt( Coordinates coordinates ) {
            return null;
        }

        @Override
        public void addChangeListener( BoardListener listener ) {
        }

        @Override
        public void removeChangeListener( BoardListener listener ) {
        }
    }

    public static class BoardView implements BoardListener {

        private static final Logger logger = LoggerFactory.getLogger(BoardView.class);
        private static final int TILE_WIDTH = 60;
        private static final int TILE_HEIGHT = 80;
        private int translateX = 400;
        private int translateY = 50;
        private GameSession session;
        private Board board;
        private int height;
        private int width;
        
        private PieceRenderer pieceRenderer = new PieceRenderer(TILE_WIDTH, TILE_HEIGHT);
        private List<Piece> renderingQueue; // Contains pieces in order it should be rendered
        private final int boardWidth;
        private final int boardHeight;

        public BoardView(GameSession session, JComponent canvas) {
            this.session = session;
            this.board = session.getBoard();
            
            this.width = canvas.getWidth();
            this.height = canvas.getHeight();
            
            Dimension boardSize = calculateBoardSize(board);
            boardWidth = (int) (boardSize.getWidth() / 2 * TILE_WIDTH) + 1;
            boardHeight = (int) (boardSize.getHeight() / 2 * TILE_HEIGHT) + 1;
            
            queuePiecesForRendering();
        }

        public void draw(Graphics2D g) {
            translateX = (width - boardWidth) / 2;
            translateY = (height - boardHeight) / 2;
            clearBoard(g);
            g.translate(translateX, translateY);
            for (Piece piece : renderingQueue) {
                paintPiece(piece, g);
            }
            g.dispose();
        }

        private void clearBoard(Graphics2D g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setBackground(Color.WHITE);
            logger.debug("Cleaning up board {}x{}", width, height);
            g2.clearRect(0, 0, width, height);
            g2.dispose();
        }

        public void clickOn(double x, double y) {
            logger.debug("Cliked at ({}, {})", x, y);
            Point2D clickPoint = new Point2D.Double(x, y);
            List<Piece> pieces = new ArrayList<>(board.getAllPieces());
            Collections.sort(pieces, Collections.reverseOrder(new LayerPieceComparator()));
            for (Piece piece : pieces) {
                Coordinates c = piece.getCoordinates();
                if (pieceRenderer.getFace(calculateX(c) + translateX, calculateY(c) + translateY).contains(clickPoint)) {
                    session.pickPieceAt(c);
                }
            }
        }

        private Shape createPieceClip(Coordinates coordinates) {
            final Coordinates[] touchingPiecesCoordinates = new Coordinates[]{coordinates.translate(-2, -1, 0), coordinates.translate(-2, 0, 0), coordinates.translate(-2, 1, 0), coordinates.translate(-2, 2, 0), coordinates.translate(-1, 2, 0), coordinates.translate(0, 2, 0)};
            final Area baseClippingPath = new Area(pieceRenderer.getClip(calculateX(coordinates), calculateY(coordinates)));
            for (Coordinates coords : touchingPiecesCoordinates) {
                if (board.getPieceAt(coords) == null) {
                    continue;
                }
                Shape touchingOutline = pieceRenderer.getClip(calculateX(coords), calculateY(coords));
                baseClippingPath.subtract(new Area(touchingOutline));
            }
            return baseClippingPath;
        }

        private int calculateX(final Coordinates coords) {
            final int layerShift = TILE_WIDTH / 4 * (coords.getLayer() - 1);
            return coords.getX() * TILE_WIDTH / 2 + layerShift;
        }

        private int calculateY(final Coordinates coords) {
            final int layerShift = TILE_WIDTH / 4 * (coords.getLayer() - 1);
            return coords.getY() * TILE_HEIGHT / 2 - layerShift;
        }

        private void paintPiece(Piece piece, Graphics2D g) {
            final Coordinates coords = piece.getCoordinates();
            final int x = calculateX(coords);
            final int y = calculateY(coords);
            Graphics2D pieceGraphics = (Graphics2D) g.create();
            Shape clip = createPieceClip(piece.getCoordinates());
            pieceGraphics.setClip(clip);
            pieceRenderer.paint(x, y, isSelected(piece), piece.getTile(), pieceGraphics);
            pieceGraphics.dispose();
        }

        private void queuePiecesForRendering() {
            renderingQueue = new ArrayList<>(board.getAllPieces());
            Collections.sort(renderingQueue, new LayerPieceComparator());
        }

        private boolean isSelected(Piece piece) {
            return session.getPickedPieces().contains(piece);
        }

        @Override
        public void boardChanged(BoardEvent evt) {
            queuePiecesForRendering();
        }

        private Dimension calculateBoardSize(Board board) {
            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int maxY = Integer.MIN_VALUE;
            
            for (Piece piece : board.getAllPieces()) {
                Coordinates c = piece.getCoordinates();
                final int x = c.getX();
                final int y = c.getY();
                
                if (x < minX) {
                    minX = x;
                }
                
                if (x > maxX){
                    maxX = x;
                }
                
                if (y < minY) {
                    minY = y;
                }
                
                if (y > maxY){
                    maxY = y;
                }
            }
            final int boardWidth = maxX - minX;
            final int boardHeight = maxY - minY;
            return new Dimension(boardWidth, boardHeight);
            
        }

        private static class LayerPieceComparator implements Comparator<Piece> {

            @Override
            public int compare(Piece o1, Piece o2) {
                return o1.getCoordinates().getLayer() - o2.getCoordinates().getLayer();
            }
        }
    }
}
