/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessBoard.ChessBoardInitializer;
import fi.henri.ChessGame.Logic.LogicHandler;
import fi.henri.ChessGame.UI.UI;

/**
 *
 * @author Melchan
 */
public class Game {
    private ChessBoard board;
    private LogicHandler handler;
    private UI ui;
    
    public Game() {
        this.board = new ChessBoard();
        this.handler = new LogicHandler(board);
        handler.newGame();
        this.ui = new UI(board, handler);
    }
    
    public void run() {
        ui.run();
    }
}
