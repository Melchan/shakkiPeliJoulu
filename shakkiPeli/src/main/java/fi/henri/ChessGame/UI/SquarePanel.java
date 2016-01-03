/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.UI;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import java.awt.Color;
import static java.awt.Color.BLACK;
import static java.awt.Color.GRAY;
import static java.awt.Color.WHITE;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;

/**
 *
 * @author manhenri
 */
public class SquarePanel extends JLayeredPane {

    private ChessBoard board;
    private Color color = WHITE;
    private static boolean isBlack = false;

    public SquarePanel(ChessBoard board, int panel) {
        this.board = board;
        this.setPreferredSize(new Dimension(80, 80));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        ChessPiece piece = paneNumberToChessBoardSquareContent(panel);
        
        this.add(new ChessPiecePicture(piece), new Integer(2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(color);
        g.fillRect(0, 0, 80, 80);
    }

    private ChessPiece paneNumberToChessBoardSquareContent(int n) {
        ChessPiece[][] chessBoard = board.getChessBoard();
        int y = n / 8;
        int x = 7 - n % 8;

        setRightColorForCoordinate(x);

        return chessBoard[x][y];
    }

    private void setRightColorForCoordinate(int x) {
        if (isBlack) {
            this.color = GRAY;
            this.isBlack = false;
        } else {
            this.color = WHITE;
            this.isBlack = true;
        }
        if (x == 7) {
            isBlack = !isBlack;
        }
    }
}
