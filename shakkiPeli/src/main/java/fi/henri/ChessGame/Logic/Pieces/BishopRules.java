/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Logic.Pieces;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;

/**
 *
 * @author melchan
 */
public class BishopRules extends PieceMovement {

    public BishopRules(ChessBoard board) {
        super(board);
    }

    @Override
    public boolean isMoveLegal(ChessPiece p, int a, int b, int toA, int toB) {
        if (super.isAllowedSlope(a, b, toA, toB)) {
            if (isAllowedBishopMovement(a, b, toA, toB)) {
                ChessPiece target = super.getBoard()[toA][toB];
                if (super.isThePieceEnemy(p, target)) {
                    return super.isThePathClear(a, b, toA, toB);
                }
            }
        }
        return false;
    }

    private boolean isAllowedBishopMovement(int a, int b, int toA, int toB) {
        if (super.change(a, toA) != 0 && super.change(b, toB) != 0) {
            return true;
        } else {
            return false;
        }
    }
}
