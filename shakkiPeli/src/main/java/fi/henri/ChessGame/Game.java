/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame;

import fi.henriChessGame.ChessBoard.ChessBoard;
import fi.henriChessGame.ChessBoard.ChessBoardInitializer;

/**
 *
 * @author Melchan
 */
public class Game {
    ChessBoard board;
    
    public Game() {
        this.board = new ChessBoard();
        new ChessBoardInitializer(board);
    }
    
    public void run() {
        
    }
}