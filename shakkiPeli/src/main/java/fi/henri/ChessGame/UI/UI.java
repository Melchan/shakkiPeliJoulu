/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.UI;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.Logic.LogicHandler;
import java.awt.Container;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author manhenri
 */
public class UI implements Runnable {

    ChessBoard board;
    LogicHandler handler;

    public UI(ChessBoard board, LogicHandler handler) {
        this.board = board;
        this.handler = handler;
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("ChessGame");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Container contentPane = new ChessBoardContent(board, handler);
        frame.setContentPane(contentPane);
        frame.addMouseListener((MouseListener) contentPane);

        frame.pack();
        frame.setVisible(true);
    }
}
