/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.RulesAndMovement.Pieces;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;

/**
 *
 * @author melchan
 */
public class KnightRules extends PieceMovement {

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
if (Math.abs(a - toA) == 2 && Math.abs(b - toB) == 1) {
            return true;
        } else if (Math.abs(b - toB) == 2 && Math.abs(a - toA) == 1) {
            return true;
        } else {
            return false;
        }
    }
}
