/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Logic.Observers;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessColor;
import static fi.henri.ChessGame.ChessPieces.ChessColor.*;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import fi.henri.ChessGame.ChessPieces.PieceType;
import static fi.henri.ChessGame.ChessPieces.PieceType.KING;
import static fi.henri.ChessGame.ChessPieces.PieceType.PAWN;
import fi.henri.ChessGame.Logic.Pieces.MoveLibrary;
import fi.henri.ChessGame.Logic.Pieces.PawnRules;
import fi.henri.ChessGame.Logic.Pieces.PieceMovement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class functions as referee to check if moves are legal.
 *
 * @author manhenri
 */
public class CheckObserver {

    private final MoveLibrary library;
    private HashMap<ChessPiece, Integer> enemyPieces;
    private Integer ownKingPosition;
    private final ChessBoard board;
    private ChessPiece target;
    private ChessPiece actor;
    private boolean actorMoved;
    private boolean enPassantDone;
    private ArrayList<Integer> threateners;

    public CheckObserver(ChessBoard board) {
        this.board = board;
        this.library = new MoveLibrary(board);
        this.threateners = new ArrayList<Integer>();
    }

    /**
     * Method Checks if king is in checkPosition in current board position. And
     * allows or disallowes moves. Special cases for Check EnPassant.
     *
     * @param color player whose turn it is.
     * @param a starting x-axis.
     * @param b starting y-axis.
     * @param toA end point x-axis.
     * @param toB end point y-axis.
     * @return true if king is in check.
     */
    public boolean movePieceIfLegal(ChessColor color, int a, int b, int toA, int toB) {
        actor = board.getChessBoard()[a][b];
        actorMoved = actor.hasMoved();
        target = board.getChessBoard()[toA][toB];
        enPassantDone = false;
        if (checkIfMoveIsLegal(a, b, toA, toB)) {
            movePiece(a, b, toA, toB);
            if (!isKingThreatened(color)) {
                return true;
            }
        }
        resetMovement(a, b, toA, toB);
        return false;
    }

    private void resetMovement(int a, int b, int toA, int toB) {
        if (actorMoved == false) {
            actor.setMovedToFalse();
        }
        board.getChessBoard()[a][b] = actor;
        if (enPassantDone) {
            resetEnPassantMovement(toA, toB);
        } else {
            board.getChessBoard()[toA][toB] = target;
        }
    }

    private void resetEnPassantMovement(int toA, int toB) {
        board.attemptToPlacePieceOnBoard(null, toA, toB);
        if (target.getColor() == BLACK) {
            board.getChessBoard()[toA][toB - 1] = target;
        } else {
            board.getChessBoard()[toA][toB + 1] = target;
        }
        
    }

    private boolean checkIfMoveIsLegal(int a, int b, int toA, int toB) {
        ChessPiece p = board.getChessBoard()[a][b];
        PieceType type = p.getPieceType();
        PieceMovement m = library.getMovementLibrary().get(type);
        boolean result = m.isMoveLegal(p, a, b, toA, toB);
        enPassantCorrection(p, m, toA, toB);
        return result;
    }

    private void movePiece(int a, int b, int toA, int toB) {
        ChessPiece p = board.getChessBoard()[a][b];
        PieceMovement m = library.getMovementLibrary().get(p.getPieceType());
        m.commitMoveIfLegal(p, a, b, toA, toB);
    }

    private void enPassantCorrection(ChessPiece p, PieceMovement m, int toA, int toB) {
        if (p.getPieceType() == PAWN) {
            PawnRules pRules = (PawnRules) m;
            if (pRules.isEnPassantUsedInLastMove()) {
                enPassantDone = true;
                if (p.getColor() == BLACK) {
                    target = board.getChessBoard()[toA][toB + 1];
                } else {
                    target = board.getChessBoard()[toA][toB - 1];
                }
            }
        }
    }

    private boolean isKingThreatened(ChessColor color) {
        initializeVariables(color);
        boolean result = false;
        threateners.clear();
        if (ownKingPosition != null) {
            int[] king = board.integerToCoordinate(ownKingPosition);
            for (int n : enemyPieces.values()) {
                int[] enemy = board.integerToCoordinate(n);
                ChessPiece p = board.getChessBoard()[enemy[0]][enemy[1]];
                if (p != null) {
                    if (library.getMovementLibrary().get(p.getPieceType()).isMoveLegal(p, enemy[0], enemy[1], king[0], king[1])) {
                        result = true;
                        threateners.add(n);
                    }
                }
            }
            return result;
        }
        return false;
    }

    public ArrayList<Integer> getThreateners() {
        return this.threateners;
    }

    private void setKingPosition(ChessColor color) {
        HashMap<ChessPiece, Integer> ownPieces = board.getChessPiecesByColor(color);
        for (int n : ownPieces.values()) {
            int[] i = board.integerToCoordinate(n);
            ChessPiece p = board.getChessBoard()[i[0]][i[1]];
            if (p != null) {
                if (p.getPieceType() == KING) {
                    ownKingPosition = ownPieces.get(p);
                }
            }
        }
    }

    private void initializeVariables(ChessColor color) {
        if (color == BLACK) {
            this.enemyPieces = board.getChessPiecesByColor(WHITE);
            setKingPosition(color);
        } else {
            this.enemyPieces = board.getChessPiecesByColor(BLACK);
            setKingPosition(color);
        }
    }
}
