 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Logic;

import fi.henri.ChessGame.Logic.Pieces.*;
import fi.henri.ChessGame.ChessBoard.*;
import fi.henri.ChessGame.ChessPieces.*;
import static fi.henri.ChessGame.ChessPieces.ChessColor.*;
import fi.henri.ChessGame.ChessPieces.PieceType;
import fi.henri.ChessGame.Logic.Observers.CheckObserver;
import java.util.HashMap;

/**
 * Class that will handle logic of the game.
 *
 * @author Melchan
 */
public class LogicHandler {

    private ChessBoard board;
    private ChessColor turn;
    private MoveLibrary library;
    private boolean checkMate;
    private CheckObserver check;

    public LogicHandler(ChessBoard board) {
        this.board = board;
        this.turn = WHITE;
        this.checkMate = false;
        this.library = new MoveLibrary(board);
        this.check = new CheckObserver(board);
    }

    public ChessBoard getChessBoard() {
        return board;
    }

    public void newGame() {
        new ChessBoardInitializer(board);
        this.turn = WHITE;
    }

    public void setTurn(ChessColor color) {
        this.turn = color;
    }

    /**
     * method will move piece if it is legal for chesspiece in question and
     * change turn.
     *
     * @param a starting x axis.
     * @param b starting y axis.
     * @param toA end point x axis.
     * @param toB end point y axis.
     * @return true if action happened.
     */
    public boolean movePiece(int a, int b, int toA, int toB) {
        if (isPieceMove(a, b, toA, toB)) {
            ChessPiece p = board.getChessBoard()[a][b];
            if (p.getColor() == turn) {
                if (check.movePieceIfLegal(turn, a, b, toA, toB)) {
                    System.out.println(turn);
                    changeTurn();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCheckMate() {
        return checkMate;
    }

    private boolean isPieceMove(int a, int b, int toA, int toB) {
        if (board.allowedCoordinates(a, b)) {
            if (board.allowedCoordinates(toA, toB)) {
                if (board.getChessBoard()[a][b] != null) {
                    if (a != toA || b != toB) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void changeTurn() {
        if (turn == WHITE) {
            turn = BLACK;
        } else if (turn == BLACK) {
            turn = WHITE;
        }
    }
}
