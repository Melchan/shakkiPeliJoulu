/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Logic.Pieces;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.PieceType;
import static fi.henri.ChessGame.ChessPieces.PieceType.BISHOP;
import static fi.henri.ChessGame.ChessPieces.PieceType.KING;
import static fi.henri.ChessGame.ChessPieces.PieceType.KNIGHT;
import static fi.henri.ChessGame.ChessPieces.PieceType.PAWN;
import static fi.henri.ChessGame.ChessPieces.PieceType.QUEEN;
import static fi.henri.ChessGame.ChessPieces.PieceType.ROOK;
import java.util.HashMap;

/**
 * Houses HashMap with all the normal movement rules.
 * @author manhenri
 */
public class MoveLibrary {
    private final HashMap<PieceType, PieceMovement> movementLibrary;
    private ChessBoard board;
    
    public MoveLibrary(ChessBoard board) {
        this.board = board;
        this.movementLibrary = new HashMap<PieceType, PieceMovement>();
        initializeMovementLibrary();
    }
    
    public HashMap<PieceType, PieceMovement> getMovementLibrary() {
        return this.movementLibrary;
    }
    
    private void initializeMovementLibrary() {
        this.movementLibrary.put(KING, new KingRules(board));
        this.movementLibrary.put(QUEEN, new QueenRules(board));
        this.movementLibrary.put(BISHOP, new BishopRules(board));
        this.movementLibrary.put(KNIGHT, new KnightRules(board));
        this.movementLibrary.put(ROOK, new RookRules(board));
        this.movementLibrary.put(PAWN, new PawnRules(board));
    }
}
