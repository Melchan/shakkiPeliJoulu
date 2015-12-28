/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Rules.PieceMovement;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;

/**
 *
 * @author Melchan
 */
public class QueenRules extends BasicPieceRules{
    BishopRules bRules;
    RookRules rRules;

    public QueenRules(ChessBoard board) {
        super(board);
        this.bRules = new BishopRules(board);
        this.rRules = new RookRules(board);
    } 

    @Override
    public boolean isMoveLegal(ChessPiece p, int a, int b, int toA, int toB) {
        if (bRules.isMoveLegal(p, a, b, toA, toB)) {
            return true;
        } else if (rRules.isMoveLegal(p, a, b, toA, toB)) {
            return true;
        } else {
            return false;
        }
    }
    
}
