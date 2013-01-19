package net.anatolich.mahjong.desktopclient.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.GameSession;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class BoardComponent extends JComponent {

    private Board board;

    public BoardComponent() {
        GameSession session = GameSession.startGame();
        this.board = session.getBoard();
    }

    @Override
    protected void paintComponent( Graphics g ) {
        IsometricBoardRenderer isometricBoard = new IsometricBoardRenderer(board);
        isometricBoard.setWidth(getWidth());
        isometricBoard.setHeight(getHeight());

        isometricBoard.draw(( Graphics2D ) g);

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard( Board board ) {
        this.board = board;
    }
}
