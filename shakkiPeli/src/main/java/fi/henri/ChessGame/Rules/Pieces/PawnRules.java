/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Rules.Pieces;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;

/**
 *
 * @author manhenri
 */
public class PawnRules extends BasicPieceRules {

    public PawnRules(ChessBoard board) {
        super(board);
    }
    
    @Override
    public boolean isMoveLegal(ChessPiece pawn, int a, int b, int toA, int toB){
        
        
        return false;
    }
    
    
}
