/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.ChessBoard;

import fi.henri.ChessGame.ChessPieces.ChessColor;
import static fi.henri.ChessGame.ChessPieces.ChessColor.BLACK;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import java.util.HashMap;

/**
 * Houses chessboard and two previous board positions. Has Every piece on board
 * in two hashMaps with respective color.
 *
 * @author Melchan
 */
public class ChessBoard {

    private ChessPiece[][] board;
    private ChessPiece[][] boardOneMoveBack;
    private HashMap<ChessPiece, Integer> blackChessPiecesOnBoard;
    private HashMap<ChessPiece, Integer> whiteChessPiecesOnBoard;

    public ChessBoard() {
        initialize();
    }

    /**
     * Cloning constructor.
     *
     * @param board
     * @param boardOneMoveBack
     * @param blackChessPiecesOnBoard
     * @param whiteChessPiecesOnBoard
     */
    private ChessBoard(ChessPiece[][] board, ChessPiece[][] boardOneMoveBack,
            HashMap<ChessPiece, Integer> blackChessPiecesOnBoard, HashMap<ChessPiece, Integer> whiteChessPiecesOnBoard) {
        this.board = board;
        this.boardOneMoveBack = boardOneMoveBack;
        this.blackChessPiecesOnBoard = blackChessPiecesOnBoard;
        this.whiteChessPiecesOnBoard = whiteChessPiecesOnBoard;
    }

    public ChessPiece[][] getChessBoard() {
        return board;
    }

    public ChessPiece[][] getPreviousChessBoard() {
        return boardOneMoveBack;
    }

    /**
     * Makes clean version of the board.
     */
    public void initialize() {
        this.board = new ChessPiece[8][8];
        this.boardOneMoveBack = new ChessPiece[8][8];
        this.blackChessPiecesOnBoard = new HashMap<ChessPiece, Integer>();
        this.whiteChessPiecesOnBoard = new HashMap<ChessPiece, Integer>();
    }

    /**
     * Cloning method for this class.
     *
     * @return clone of this class.
     */
    public ChessBoard getClone() {
        HashMap<ChessPiece, Integer> cloneBlackPieces = (HashMap<ChessPiece, Integer>) blackChessPiecesOnBoard.clone();
        HashMap<ChessPiece, Integer> cloneWhitePieces = (HashMap<ChessPiece, Integer>) whiteChessPiecesOnBoard.clone();
        ChessPiece[][] cloneBoard = this.cloneBoard(board);
        ChessPiece[][] cloneBoardBefore = this.cloneBoard(this.boardOneMoveBack);
        return new ChessBoard(cloneBoard, cloneBoardBefore, cloneBlackPieces, cloneWhitePieces);
    }

    public HashMap<ChessPiece, Integer> getChessPiecesByColor(ChessColor color) {
        if (color == BLACK) {
            return this.blackChessPiecesOnBoard;
        } else {
            return this.whiteChessPiecesOnBoard;
        }
    }

    /**
     * method places piece on board if coordinates are on board. And records
     * it's position if it has id.
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
            emptyRecordAboutThatCoordinate(x, y);
            board[x][y] = piece;
            recordNewPiecePosition(x, y, piece);
            return true;
        }
        return false;
    }

    private void emptyRecordAboutThatCoordinate(int x, int y) {
        whiteChessPiecesOnBoard.remove(board[x][y]);
        blackChessPiecesOnBoard.remove(board[x][y]);
    }

    private void recordNewPiecePosition(int x, int y, ChessPiece p) {
        if (p != null) {
            int coordinate = coordinateToInteger(x, y);
            if (p.getColor() == BLACK) {
                blackChessPiecesOnBoard.put(p, coordinate);

            } else {
                whiteChessPiecesOnBoard.put(p, coordinate);

            }
        }
    }

    /**
     * turns 2 coordinates to handy integer
     *
     * @param x
     * @param y
     * @return handy integer
     */
    public int coordinateToInteger(int x, int y) {
        return -((y - 7) * 8) + x;
    }

    /**
     * turns handy integer back to coordinate.
     *
     * @param n
     * @return int[] with x and y coordinates.
     */
    public int[] integerToCoordinate(int n) {
        int[] coordinates = new int[2];
        coordinates[0] = n % 8;
        coordinates[1] = 7 - n / 8;
        return coordinates;
    }

    /**
     * method moves piece if start and end coordinates are on board. Doesn't
     * mind if piece that is currently being moved is null. Also set's
     * chesspieces moved status to true. And clears starting coordinate from
     * hasmaps.
     *
     * @param x x-axis
     * @param y y-axis
     * @param toX x-axis.
     * @param toY y-axis.
     * @return boolean
     */
    public boolean attemptToMovePieceOnBoard(int x, int y, int toX, int toY) {
        if (allowedCoordinates(x, y)) {
            ChessPiece[][] boardNow = cloneBoard(board);
            ChessPiece piece = board[x][y];
            boolean result = attemptToPlacePieceOnBoard(piece, toX, toY);
            if (result) {
                piece.move();
                clearCoordinateMovedFrom(x, y);
                boardOneMoveBack = boardNow;
            }
            return result;
        }
        return false;
    }

    private void clearCoordinateMovedFrom(int x, int y) {
        board[x][y] = null;
        int coordinate = coordinateToInteger(x, y);
    }

    /**
     * method will tell if coordinates are inside matrix.
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

    private ChessPiece[][] cloneBoard(ChessPiece[][] cloneBoard) {
        ChessPiece[][] boardNow = new ChessPiece[8][8];
        for (int i = 0; i < cloneBoard.length; i++) {
            boardNow[i] = cloneBoard[i].clone();
        }
        return boardNow;
    }
}
