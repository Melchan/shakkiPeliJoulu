/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Rules.Pieces;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import fi.henri.ChessGame.ChessPieces.Color;

/**
 *
 * @author Melchan
 */
public class PathCheck {

    private ChessBoard chessBoard;
    private ChessPiece[][] board;

    public PathCheck(ChessBoard board) {
        
        this.chessBoard = board;
        this.board = board.getChessBoard();
    }

    public boolean isThePathClear(int a, int b, int toA, int toB) {
        int xChange = change(a, toA);
        int yChange = change(b, toB);
        int x = a + xChange;
        int y = b + yChange;
        while (notEndPoint(toA, toB, x, y)) {
            if (!chessBoard.allowedCoordinates(x, y)) {
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
        if (actor.getColor() == target.getColor()) {
            return false;
        }
        return true;
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

    private int change(int a, int toA) {
        if (toA - a > 0) {
            return 1;
        } else if (toA - a < 0) {
            return -1;
        } else {
            return 0;
        }
        
    }
}
