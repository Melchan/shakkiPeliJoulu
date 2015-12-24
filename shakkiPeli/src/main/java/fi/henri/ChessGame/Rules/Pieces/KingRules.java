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
public class KingRules extends BasicPieceRules {

    ChessBoard board;

    public KingRules(ChessBoard board) {
        super(board);
        this.board = board;
    }

    @Override
    public boolean isMoveLegal(ChessPiece p, int a, int b, int toA, int toB) {
        if (board.allowedCoordinates(a, b)) {
            if (board.allowedCoordinates(toA, toB)) {
                if (isAllowedKingMovement(a, b, toA, toB)) {
                    ChessPiece target = board.getChessBoard()[toA][toB];
                    if (super.isThePieceEnemy(p, target)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isAllowedKingMovement(int a, int b, int toA, int toB) {
        int xChange = Math.abs(a - toA);
        int yChange = Math.abs(b - toB);
        if (xChange < 2) {
            if (yChange < 2) {
                return true;
            }
        }
        return false;
    }
}
