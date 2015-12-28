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
public abstract class BasicPieceRules {

    private ChessBoard cB;
    private ChessPiece[][] board;

    public BasicPieceRules(ChessBoard board) {

        this.cB = board;
        this.board = board.getChessBoard();
    }

    abstract public boolean isMoveLegal(ChessPiece p, int a, int b, int toA, int toB);

    public boolean isThePathClear(int a, int b, int toA, int toB) {
        int xChange = change(a, toA);
        int yChange = change(b, toB);
        int x = a + xChange;
        int y = b + yChange;
        while (notEndPoint(toA, toB, x, y)) {
            if (!cB.allowedCoordinates(x, y)) {
                return false;
            }
            if (squareOccupied(x, y)) {
                return false;
            }
            x += xChange;
            y += yChange;
        }
        return true;
    }

    public boolean isThePieceEnemy(ChessPiece actor, ChessPiece target) {
        if (target == null) {
            return true;
        } else if (actor.getColor() == target.getColor()) {
            return false;
        }
        return true;
    }

    public int change(int a, int toA) {
        if (toA - a > 0) {
            return 1;
        } else if (toA - a < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    public boolean isAllowedSlope(int a, int b, int toA, int toB) {
        if (!cB.allowedCoordinates(a, b) || !cB.allowedCoordinates(toA, toB)) {
            return false;
        } else if (change(a, toA) == 0 || change(b, toB) == 0) {
            return true;
        } else if ((a - toA) / (b - toB) == 1 || (a - toA) / (b - toB) == -1) {
            return true;
        } else {
            return false;
        }
    }

    public ChessPiece[][] getBoard() {
        return board;
    }

    private boolean squareOccupied(int x, int y) {
        if (board[x][y] != null) {
            return true;
        }
        return false;
    }

    private boolean notEndPoint(int toA, int toB, int x, int y) {
        if (toA == x && toB == y) {
            return false;
        }
        return true;
    }
}
