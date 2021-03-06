/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Logic.Pieces;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;

/**
 * rules for rook movement
 * @author manhenri
 */
public class RookRules extends PieceMovement {
    private ChessBoard board;
    public RookRules(ChessBoard board) {
        super(board);
        this.board = board;
    }

    @Override
    public boolean isMoveLegal(ChessPiece p, int a, int b, int toA, int toB) {
        if (super.isAllowedSlope(a, b, toA, toB)) {
            if (isAllowedRookMovement(a, b, toA, toB)) {
                ChessPiece target = board.getChessBoard()[toA][toB];
                if (super.isThePieceEnemy(p, target)) {
                    return super.isThePathClear(a, b, toA, toB);
                }
            }
        }
        return false;
    }

    private boolean isAllowedRookMovement(int a, int b, int toA, int toB) {
        if (super.change(a, toA) != 0 || super.change(b, toB) != 0) {
            if (super.change(a, toA) == 0 || super.change(b, toB) == 0) {
                return true;
            }
        }
        return false;
    }
}
