/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.ChessBoard;

import fi.henri.ChessGame.ChessPieces.ChessPiece;

/**
 * Houses chessboard and couple of helping methods.
 *
 * @author Melchan
 */
public class ChessBoard {

    private ChessPiece[][] board;
    private ChessPiece[][] boardBefore;

    public ChessBoard() {
        initialize();
    }

    public ChessPiece[][] getChessBoard() {
        return board;
    }

    public ChessPiece[][] getPreviousChessBoard() {
        return boardBefore;
    }
    
    /**
     * Makes clean version of the board.
     */
    public void initialize() {
        this.board = new ChessPiece[8][8];
        this.boardBefore = new ChessPiece[8][8];
    }

    /**
     * method places piece on board if coordinates are on board.
     *
     * @param piece chess piece to be placed on board.
     * @param x x-axis for piece.
     * @param y y-axis for piece.
     * @return boolean.
     */
    public boolean attemptToPlacePieceOnBoard(ChessPiece piece, int x, int y) {
        if (allowedCoordinates(x, y)) {
            if (piece == null) {
                return false;
            }
            board[x][y] = piece;
            return true;
        }
        return false;
    }

    /**
     * method moves piece if start and end coordinates are on board. Doesn't
     * mind if piece that is currently being moved is null.
     *
     * @param x x-axis
     * @param y y-axis
     * @param toX x-axis.
     * @param toY y-axis.
     * @return boolean
     */
    public boolean attemptToMovePieceOnBoard(int x, int y, int toX, int toY) {
        if (allowedCoordinates(x, y)) {
            ChessPiece[][] boardNow = cloneBoard();
            ChessPiece piece = board[x][y];
            boolean result = attemptToPlacePieceOnBoard(piece, toX, toY);
            if (result) {
                piece.move();
                board[x][y] = null;
                boardBefore = boardNow;
            }
            return result;
        }
        return false;
    }

    /**
     * method will tell if coordinates are allowed.
     *
     * @param x x-axis.
     * @param y y-axis.
     * @return boolean.
     */
    public boolean allowedCoordinates(int x, int y) {
        if (x > -1 && y > -1 && x < 8 && y < 8) {
            return true;
        }
        return false;
    }

    private ChessPiece[][] cloneBoard() {
        ChessPiece[][] boardNow = new ChessPiece[8][8];
        for (int i = 0; i < board.length; i++) {
            boardNow[i] = board[i].clone();
        }
        return boardNow;
    }
}
