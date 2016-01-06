 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Logic;

import fi.henri.ChessGame.Logic.Pieces.RookRules;
import fi.henri.ChessGame.Logic.Pieces.KnightRules;
import fi.henri.ChessGame.Logic.Pieces.QueenRules;
import fi.henri.ChessGame.Logic.Pieces.KingRules;
import fi.henri.ChessGame.Logic.Pieces.PawnRules;
import fi.henri.ChessGame.Logic.Pieces.BishopRules;
import fi.henri.ChessGame.Logic.Pieces.PieceMovement;
import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessBoard.ChessBoardInitializer;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import fi.henri.ChessGame.ChessPieces.ChessColor;
import static fi.henri.ChessGame.ChessPieces.ChessColor.*;
import fi.henri.ChessGame.ChessPieces.PieceType;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import java.util.HashMap;

/**
 * Class that will handle game logic and tie in different classes.
 * @author Melchan
 */
public class LogicHandler {

    private ChessBoard board;
    private ChessColor turn;
    private HashMap<PieceType, PieceMovement> movementLibrary;
    private boolean checkMate;

    public LogicHandler(ChessBoard board) {
        this.board = board;
        this.turn = WHITE;
        this.checkMate = false;
        this.movementLibrary = new HashMap<PieceType, PieceMovement>();
        initializeMovementLibrary();
    }

    private void initializeMovementLibrary() {
        movementLibrary.put(KING, new KingRules(board));
        movementLibrary.put(QUEEN, new QueenRules(board));
        movementLibrary.put(BISHOP, new BishopRules(board));
        movementLibrary.put(KNIGHT, new KnightRules(board));
        movementLibrary.put(ROOK, new RookRules(board));
        movementLibrary.put(PAWN, new PawnRules(board));
    }
    
    public void newGame() {
        new ChessBoardInitializer(board);
    }

    /**
     * method will move piece if it is legal for chesspiece in question and
     * change turn.
     *
     * @param a starting x axis.
     * @param b starting y axis.
     * @param toA end point x axis.
     * @param toB end point y axis.
     */
    public void movePiece(int a, int b, int toA, int toB) {
        if (isPieceMove(a, b, toA, toB)) {
            ChessPiece p = board.getChessBoard()[a][b];
            if (p.getColor() == turn) {
                PieceMovement movement = movementLibrary.get(p.getPieceType());
                if (movement.commitMoveIfLegal(p, a, b, toA, toB)) {
                    changeTurn();
                }
            }
        }
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
