/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessGame.ChessBoard;

import ChessGame.ChessPieces.ChessPiece;
import java.util.HashMap;

/**
 *
 * @author Melchan
 */
public class ChessBoardInitializer {
    private ChessBoard board;
    private HashMap<ChessPiece, ChessPiece> ChessPieces;
    
    public ChessBoardInitializer(ChessBoard board) {
        this.board = board;
        this.ChessPieces = new HashMap<ChessPiece, ChessPiece>();
    }
    
    public void InitializeBoard() {
        
    }
}
