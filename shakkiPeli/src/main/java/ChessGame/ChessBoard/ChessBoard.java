/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessGame.ChessBoard;

import ChessGame.ChessPieces.ChessPiece;
import java.util.HashMap;

/**
 *
 * @author Melchan
 */
public class ChessBoard {

    private ChessPiece[][] board;
    private HashMap<ChessPiece, ChessPiece> chessPieces;

    public ChessBoard() {
        this.board = new ChessPiece[8][8];
        this.chessPieces = new HashMap<ChessPiece, ChessPiece>();
    }
    
    public void setChessPieces(HashMap<ChessPiece, ChessPiece> pieces) {
        this.chessPieces = pieces;
    }
    
    public HashMap<ChessPiece, ChessPiece> getChessPieces() {
        return chessPieces;
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
            boolean result = placePieceOnBoard(piece, toX, toY);
            if (result) {
                piece.move(toX, toY);
                board[x][y] = null;
            }
            return result;
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
