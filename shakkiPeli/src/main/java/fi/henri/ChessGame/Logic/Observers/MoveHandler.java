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
public class MoveHandler {

    private final MoveLibrary library;
    private HashMap<ChessPiece, Integer> enemyPieces;
    private Integer ownKingPosition;
    private final ChessBoard board;
    private ChessPiece target;
    private ChessPiece actor;
    private boolean actorHasMovedBefore;
    private boolean enPassantDone;
    private final ArrayList<Integer> threateners;
    private int x;
    private int y;
    private int toX;
    private int toY;

    public MoveHandler(ChessBoard board) {
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
        if (isPieceMove(a, b, toA, toB)) {
            ChessPiece p = board.getChessBoard()[a][b];
            if (p.getColor() == color) {
                initializeMovementParameters(a, b, toA, toB);
                if (checkIfMoveIsLegal(a, b, toA, toB)) {
                    if (isCastlingMove(a, toA)) {
                        if (!checkKingThreatWhenCastling(a, b, toA)) {
                            movePiece(a, b, toA, toB);
                            return true;
                        }
                        return false;
                    }
                    movePiece(a, b, toA, toB);
                    if (!isKingThreatened(color)) {
                        return true;
                    }
                    resetMovement();
                }
            }
        }
        return false;
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

    private void initializeMovementParameters(int a, int b, int toA, int toB) {
        this.x = a;
        this.y = b;
        this.toX = toA;
        this.toY = toB;
        this.actor = board.getChessBoard()[a][b];
        this.actorHasMovedBefore = actor.hasMoved();
        this.target = board.getChessBoard()[toA][toB];
        this.enPassantDone = false;
    }

    /**
     * Method allows for previous move done with this class to be cancelled.
     */
    public void resetMovement() {
        resetMovedIfNecessary();
        board.attemptToPlacePieceOnBoard(actor, x, y);
        if (enPassantDone) {
            resetEnPassantMovement(toX, toY);
        } else {
            board.attemptToPlacePieceOnBoard(target, toX, toY);
        }
    }

    private void resetMovedIfNecessary() {
        if (actorHasMovedBefore == false) {
            actor.setMovedToFalse();
        }
    }

    private void resetEnPassantMovement(int toA, int toB) {
        board.attemptToPlacePieceOnBoard(null, toA, toB);
        if (target.getColor() == BLACK) {
            board.attemptToPlacePieceOnBoard(target, toA, toB - 1);
        } else {
            board.attemptToPlacePieceOnBoard(target, toA, toB + 1);
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

    private void initializeIsKingThreatened(ChessColor color) {
        setKingPosition(color);
        initializeVariables(color);
        threateners.clear();
    }

    public int getOwnKingLocation(ChessColor color) {
        initializeIsKingThreatened(color);
        return this.ownKingPosition;
    }

    /**
     * method checks if king is in check.
     *
     * @param color
     * @return if King Is In Check returns true.
     */
    public boolean isKingThreatened(ChessColor color) {
        initializeIsKingThreatened(color);
        boolean result = false;
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
                    ownKingPosition = n;
                }
            }
        }
    }

    private void initializeVariables(ChessColor color) {
        this.enemyPieces = null;
        if (color == BLACK) {
            this.enemyPieces = board.getChessPiecesByColor(WHITE);
        } else {
            this.enemyPieces = board.getChessPiecesByColor(BLACK);
        }
    }

    private boolean isCastlingMove(int a, int toA) {
        if (actor.getPieceType() == KING && Math.abs(a - toA) > 1) {
            return true;
        }
        return false;
    }

    private boolean checkKingThreatWhenCastling(int a, int b, int toA) {
        int c = a - toA;
        if (c > 0) {
            for (int i = a; i > toA - 1; i--) {
                board.attemptToPlacePieceOnBoard(actor, i, b);
                if (this.isKingThreatened(actor.getColor())) {
                    return true;
                }
                board.attemptToPlacePieceOnBoard(null, i, b);
            }
        } else {
            for (int i = a; i < toA + 1; i++) {
                board.attemptToPlacePieceOnBoard(actor, i, b);
                if (this.isKingThreatened(actor.getColor())) {
                    return true;
                }
                board.attemptToPlacePieceOnBoard(null, i, b);              
            }
        }
        this.resetMovement();
        return false;
    }
}
