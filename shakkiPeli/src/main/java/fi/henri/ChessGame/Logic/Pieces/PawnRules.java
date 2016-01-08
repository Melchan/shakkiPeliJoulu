/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Logic.Pieces;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessColor;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import static fi.henri.ChessGame.ChessPieces.ChessColor.*;
import static fi.henri.ChessGame.ChessPieces.PieceType.PAWN;

/**
 *
 * @author melchan
 */
public class PawnRules extends PieceMovement {

    private ChessBoard board;
    private boolean whiteEnPassant;
    private boolean blackEnPassant;

    public PawnRules(ChessBoard board) {
        super(board);
        this.board = board;
    }

    @Override
    public boolean isMoveLegal(ChessPiece p, int a, int b, int toA, int toB) {
        whiteEnPassant = false;
        blackEnPassant = false;
        if (board.allowedCoordinates(a, b)) {
            if (board.allowedCoordinates(toA, toB)) {
                if (allowedMovement(p, a, b, toA, toB)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isEnPassantUsedInLastMove() {
        if (whiteEnPassant || blackEnPassant) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean commitMoveIfLegal(ChessPiece p, int a, int b, int toA, int toB) {

        if (isMoveLegal(p, a, b, toA, toB)) {
            board.attemptToMovePieceOnBoard(a, b, toA, toB);
            if (whiteEnPassant) {
                board.getChessBoard()[toA][toB - 1] = null;
            } else if (blackEnPassant) {
                board.getChessBoard()[toA][toB + 1] = null;
            }
            return true;
        }
        return false;
    }

    private boolean allowedMovement(ChessPiece p, int a, int b, int toA, int toB) {
        int xChange = Math.abs(a - toA);
        int yChange = b - toB;

        if (blackMovement(p, xChange, yChange, toA, toB)) {
            return true;
        } else if (whiteMovement(p, xChange, yChange, toA, toB)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean blackMovement(ChessPiece p, int xC, int yC, int toA, int toB) {
        ChessPiece t = board.getChessBoard()[toA][toB];
        if (p.getColor() == BLACK) {
            if (yC == 1) {
                return isMoveAllowedForPawn(p, xC, yC, toA, toB);
            } else if (yC == 2 && p.hasMoved() == false) {
                if (board.getChessBoard()[toA][toB + 1] == null) {
                    return isForwardMovementAllowed(xC, t);
                }
            }
        }
        return false;
    }

    private boolean whiteMovement(ChessPiece p, int xC, int yC, int toA, int toB) {
        ChessPiece t = board.getChessBoard()[toA][toB];
        if (p.getColor() == WHITE) {
            if (yC == -1) {
                return isMoveAllowedForPawn(p, xC, yC, toA, toB);
            } else if (yC == -2 && p.hasMoved() == false) {
                if (board.getChessBoard()[toA][toB - 1] == null) {
                    return isForwardMovementAllowed(xC, t);
                }
            }
        }
        return false;
    }

    private boolean isForwardMovementAllowed(int xC, ChessPiece t) {
        if (xC == 0 && t == null) {
            return true;
        }
        return false;
    }

    private boolean isMoveAllowedForPawn(ChessPiece p, int xC, int yC, int toA, int toB) {
        ChessPiece t = board.getChessBoard()[toA][toB];
        if (xC == 1 && super.isThePieceEnemy(p, t) && t != null) {
            return true;
        } else if (xC == 1 && enPassant(p, yC, toA, toB)) {
            return true;
        } else {
            return isForwardMovementAllowed(xC, t);
        }
    }

    private boolean enPassant(ChessPiece p, int yC, int toA, int toB) {
        ChessPiece[][] boardNow = board.getChessBoard();
        ChessPiece[][] boardBefore = board.getPreviousChessBoard();
        if (toB > 1 && toB < 6) {
            ChessPiece upperEnemy = boardNow[toA][toB + 1];
            ChessPiece lowerEnemy = boardNow[toA][toB - 1];
            if (lowerEnemy != null && lowerEnemy == boardBefore[toA][toB + 1]) {
                if (yC == -1 && lowerEnemy.getPieceType() == PAWN) {
                    if (super.isThePieceEnemy(p, lowerEnemy)) {
                        whiteEnPassant = true;
                        return true;
                    }
                }
            } else if (upperEnemy != null && upperEnemy == boardBefore[toA][toB - 1]) {
                if (yC == 1 && upperEnemy.getPieceType() == PAWN) {
                    if (super.isThePieceEnemy(p, upperEnemy)) {
                        blackEnPassant = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
