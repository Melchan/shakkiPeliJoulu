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
public class KingRules extends PieceMovement {

    private ChessBoard board;
    private boolean castlingToLeft;
    private boolean castlingToRight;

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
                } else if (b - toB == 0 && castling(p, a, b, toA)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean commitMoveIfLegal(ChessPiece p, int a, int b, int toA, int toB) {
        castlingToLeft = false;
        castlingToRight = false;
        if (isMoveLegal(p, a, b, toA, toB)) {
            board.attemptToMovePieceOnBoard(a, b, toA, toB);
            if (castlingToLeft) {
                board.attemptToMovePieceOnBoard(0, b, 3, toB);
                castlingToLeft = false;
            } else if (castlingToRight) {
                board.attemptToMovePieceOnBoard(7, b, 5, toB);
                castlingToRight = false;
            }
            return true;
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

    private boolean castling(ChessPiece p, int a, int b, int toA) {
        ChessPiece[][] cB = board.getChessBoard();
        if (p.hasMoved() == false && super.isThePathClear(a, b, toA, b)) {
            if (toA == 6) {
                if (cB[7][b] != null && cB[7][b].hasMoved() == false) {
                    castlingToRight = true;
                    return true;
                }
            } else if (toA == 2) {
                if (cB[0][b] != null && cB[0][b].hasMoved() == false) {
                    castlingToLeft = true;
                    return true;
                }
            }
        }
        return false;
    }
}
