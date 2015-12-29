/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.RulesAndMovement;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import fi.henri.ChessGame.ChessPieces.Color;
import static fi.henri.ChessGame.ChessPieces.Color.*;
import fi.henri.ChessGame.ChessPieces.PieceType;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import fi.henri.ChessGame.RulesAndMovement.Pieces.*;
import java.util.HashMap;

/**
 *
 * @author Melchan
 */
public class MoveHandler {

    private ChessBoard board;
    private Color turn;
    private HashMap<PieceType, PieceMovement> movementLibrary;

    public MoveHandler(ChessBoard board) {
        this.board = board;
        this.turn = WHITE;
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