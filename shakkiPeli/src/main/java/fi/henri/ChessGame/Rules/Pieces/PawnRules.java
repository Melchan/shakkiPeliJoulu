/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Rules.Pieces;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import fi.henri.ChessGame.ChessPieces.Color;
import static fi.henri.ChessGame.ChessPieces.Color.*;

/**
 *
 * @author melchan
 */
public class PawnRules extends BasicPieceRules {
    private ChessBoard board;

    public PawnRules(ChessBoard board) {
        super(board);
        this.board = board;
    }

    @Override
    public boolean isMoveLegal(ChessPiece p, int a, int b, int toA, int toB) {
        if (board.allowedCoordinates(a, b)) {
            if (board.allowedCoordinates(toA, toB)) {
                if (allowedMovement(p, a, b, toA, toB)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean allowedMovement(ChessPiece p,int a,int b,int toA,int toB) {
        int xChange = Math.abs(a - toA);
        int yChange = b - toB;
        ChessPiece target = board.getChessBoard()[toA][toB];
        
        if (blackPieceM(p, xChange, yChange, target)) {
            return true;
        } else if (whitePieceM(p, xChange, yChange, target)) {
            return true;
        }
        return false;
    }
    
    private boolean blackPieceM(ChessPiece p, int xC, int yC, ChessPiece t){
        if (p.getColor() == BLACK && yC == 1) {
            if (xC == 1 && super.isThePieceEnemy(p, t) && t != null) {
                return true;
            } else if (xC == 0 && t == null) {
                return true;
            }
        }
        return false;
    }
    
    private boolean whitePieceM(ChessPiece p, int xC, int yC, ChessPiece t) {
        if (p.getColor() == WHITE && yC == -1) {
            if (xC == 1 && super.isThePieceEnemy(p, t) && t != null) {
                return true;
            } else if (xC == 0 && t == null) {
                return true;
            }
        }
        return false;
    }
}
