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
    private int ownKingPosition;
    private final ChessBoard board;
    private ChessPiece target;
    private ChessPiece actor;
    private ArrayList<ChessPiece> threateners;

    public CheckObserver(ChessBoard board) {
        this.board = board;
        this.library = new MoveLibrary(board);
        this.threateners = new ArrayList<ChessPiece>();
    }

    /**
     * Method Checks if king is in checkPosition in current board position.
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
            target = board.getChessBoard()[toA][toB];
        if (checkIfMoveIsLegal(a, b, toA, toB)) {
            movePiece(a, b, toA, toB);
            initializeVariables(color);
            if (!tryToEatKing()) {
                return true;
            }
        }
        board.getChessBoard()[a][b] = actor;
        board.getChessBoard()[toA][toB] = target;
        return false;
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
                if (p.getColor() == BLACK) {
                    target = board.getChessBoard()[toA][toB + 1];
                } else {
                    target = board.getChessBoard()[toA][toB - 1];
                }
            }
        }
    }
    
    private boolean tryToEatKing() {
        boolean result = false;
        threateners.clear();
        int[] king = board.integerToCoordinate(ownKingPosition);
        for (int n : enemyPieces.values()) {
            int[] enemy = board.integerToCoordinate(n);
            ChessPiece p = board.getChessBoard()[enemy[0]][enemy[1]];
            if (library.getMovementLibrary().get(p.getPieceType()).isMoveLegal(p, enemy[0], enemy[1], king[0], king[1])) {
                result = true;
                threateners.add(p);
            }
        }
        return result;
    }
    
    public ArrayList<ChessPiece> getThreateners() {
        return this.threateners;
    }

    private void setKingPosition(ChessColor color) {
        HashMap<ChessPiece, Integer> ownPieces = board.getChessPiecesByColor(color);
        for (int n : ownPieces.values()) {
            int[] i = board.integerToCoordinate(n);
            ChessPiece p = board.getChessBoard()[i[0]][i[1]];
            if (p.getPieceType() == KING) {
                ownKingPosition = ownPieces.get(p);
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
