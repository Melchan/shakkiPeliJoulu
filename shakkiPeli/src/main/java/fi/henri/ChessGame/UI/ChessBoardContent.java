/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.UI;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author manhenri
 */
public class ChessBoardContent extends JPanel{
    private ChessBoard board;
    
    public ChessBoardContent(ChessBoard board) {
        this.board = board;
        this.setLayout(new GridLayout(8, 8));
        this.setPreferredSize(new Dimension(640, 640));
        
        for (int i = 63; i > -1; i--) {
            this.add(new SquarePanel(board, i));
        }    
    }
}
