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
 * @author melchan
 */
public class KnightRules extends BasicPieceRules {

    ChessBoard board;

    public KnightRules(ChessBoard board) {
        super(board);
        this.board = board;
    }

    @Override
    public boolean isMoveLegal(ChessPiece p, int a, int b, int toA, int toB) {
        if (board.allowedCoordinates(a, b)) {
            if (board.allowedCoordinates(toA, toB)) {
                if (allowedKnightMovement(a, b, toA, toB)) {
                    ChessPiece target = board.getChessBoard()[toA][toB];
                    if (super.isThePieceEnemy(p, target)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean allowedKnightMovement(int a, int b, int toA, int toB) {
        if (super.change(a, toA) != 0 && super.change(b, toB) != 0) {
            if (allowedDistance(a, b, toA, toB)) {
                return true;
            }
        }
        return false;
    }

    private boolean allowedDistance(int a, int b, int toA, int toB) {
        if (Math.abs(a - toA) > 2) {
            return false;
        } else if (Math.abs(b - toB) > 2) {
            return false;
        } else {
            return true;
        }
    }
}
