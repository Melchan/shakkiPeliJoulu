/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessGame.ChessBoard;

import ChessGame.ChessPieces.ChessPiece;

/**
 *
 * @author Melchan
 */
public class ChessBoard {

    private ChessPiece[][] board;

    public ChessBoard() {
        this.board = new ChessPiece[8][8];
    }

    public ChessPiece[][] getChessBoard() {
        return board;
    }

    public boolean placePieceOnBoard(ChessPiece piece, int x, int y) {
        if (allowedCoordinates(x, y)) {
            if (piece == null) {
                return false;
            }
            board[x][y] = piece;
            return true;
        }
        return false;
    }

    public boolean movePieceOnBoard(int x, int y, int toX, int toY) {
        if (allowedCoordinates(x, y)) {
            ChessPiece piece = board[x][y];
            board[x][y] = null;
            return placePieceOnBoard(piece, toX, toY);
        }
        return false;
    }

    private boolean allowedCoordinates(int x, int y) {
        if (x > -1 && y > -1 && x < 8 && y < 8) {
            return true;
        }
        return false;
    }
}
