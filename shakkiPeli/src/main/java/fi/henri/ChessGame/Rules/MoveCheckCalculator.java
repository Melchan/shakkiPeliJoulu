/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Rules;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.Color;
import static fi.henri.ChessGame.ChessPieces.Color.*;

/**
 *
 * @author Melchan
 */
public class MoveCheckCalculator {
    private ChessBoard board;
    private Color turn;
    
    
    public MoveCheckCalculator(ChessBoard board) {
        this.board = board;
        this.turn = WHITE;
        
    }
}
