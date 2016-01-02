/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.ChessBoard.RulesAndMovementPieces;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import static fi.henri.ChessGame.ChessPieces.Color.*;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import fi.henri.ChessGame.Logic.Pieces.PieceMovement;
import fi.henri.ChessGame.Logic.Pieces.RookRules;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Melchan
 */
public class PieceMovementTest {
    private ChessBoard board;
    private ChessPiece pawn;
    private ChessPiece rook;
    private PieceMovement check;
    
    @Before
    public void setUp() {
        this.board = new ChessBoard();
        this.pawn = new ChessPiece(WHITE, PAWN);
        this.rook = new ChessPiece(BLACK, ROOK);
        this.check = new RookRules(board);
    } 
    
    @Test
    public void pathClearStartingSquareIsNotCounted() {
        board.attemptToPlacePieceOnBoard(pawn, 2, 4);
        assertEquals(true, check.isThePathClear(2, 4, 2, 6));
    }
    
    @Test
    public void pathObstructedStartingSquareIsNotCounted() {
        board.attemptToPlacePieceOnBoard(pawn, 2, 4);
        board.attemptToPlacePieceOnBoard(rook, 2, 5);
        assertEquals(false, check.isThePathClear(2, 4, 2, 6));
    }
    
    @Test
    public void pathClearHorizontal() {
        assertEquals(true, check.isThePathClear(2, 4, 7, 4));
        assertEquals(true, check.isThePathClear(7, 4, 0, 4));
    }
    
    @Test
    public void pathClearVertical() {
        assertEquals(true, check.isThePathClear(2, 4, 2, 6));
        assertEquals(true, check.isThePathClear(2, 4, 2, 2));
    }
    
    @Test
    public void pathClearDiaconalRising() {
        assertEquals(true, check.isThePathClear(0, 0, 7, 7));
        assertEquals(true, check.isThePathClear(7, 7, 0, 0));
    }
    
    @Test
    public void pathClearDiaconalDecreasing() {
        assertEquals(true, check.isThePathClear(0, 7, 7, 0));
        assertEquals(true, check.isThePathClear(7, 0, 0, 7));
    }
    
    @Test
    public void pathBlockedHorizontal() {
        board.attemptToPlacePieceOnBoard(pawn, 4, 4);
        assertEquals(false, check.isThePathClear(0, 4, 7, 4));
        assertEquals(false, check.isThePathClear(7, 4, 0, 4));
    }
    
    @Test
    public void pathBlockedVertical() {
        board.attemptToPlacePieceOnBoard(pawn, 4, 4);
        assertEquals(false, check.isThePathClear(4, 0, 4, 7));
        assertEquals(false, check.isThePathClear(4, 7, 4, 0));
    }
    
    @Test
    public void pathBlockedDiaconalRising() {
        board.attemptToPlacePieceOnBoard(pawn, 4, 4);
        assertEquals(false, check.isThePathClear(0, 0, 7, 7));
        assertEquals(false, check.isThePathClear(7, 7, 0, 0));
    }
    
    @Test
    public void pathBlockedDiaconalDecreasing() {
        board.attemptToPlacePieceOnBoard(pawn, 3, 4);
        assertEquals(false, check.isThePathClear(0, 7, 7, 0));
        assertEquals(false, check.isThePathClear(7, 0, 0, 7));
    }
    
    @Test
    public void itIsNotEnemy() {
        ChessPiece soldier = new ChessPiece(WHITE, PAWN);
        assertEquals(false, check.isThePieceEnemy(pawn, soldier));
    }
    
    @Test
    public void itIsEnemy() {
        assertEquals(true, check.isThePieceEnemy(pawn, rook));
    }
    
    @Test
    public void testWithBadValues() {
        assertEquals(false, check.isThePathClear(-2, 14, -100, 2));
    }
    
    @Test
    public void isItEnemyNull() {
        assertEquals(true, check.isThePieceEnemy(pawn, null));
    }
    
    @Test
    public void isAllowedSlopeWhenOneChangeIsZero() {
        assertEquals(true, check.isAllowedSlope(2, 0, 2, 7));
        assertEquals(true, check.isAllowedSlope(0, 4, 7, 4));
    }
    
    @Test
    public void isAllowedSlopeDiaconalMovement() {
        assertEquals(true, check.isAllowedSlope(0, 0, 7, 7));
        assertEquals(true, check.isAllowedSlope(7, 7, 0, 0));
        assertEquals(true, check.isAllowedSlope(2, 4, 7, 4));
        assertEquals(true, check.isAllowedSlope(7, 4, 0, 4));
    }
    
    @Test
    public void isAllowedSlopeWhenSlopeIsWrong() {
        assertEquals(false, check.isAllowedSlope(0, 4, 1, 6));
        assertEquals(false, check.isAllowedSlope(4, 0, 6, 1));
        assertEquals(false, check.isAllowedSlope(1, 4, 7, 7));
        assertEquals(false, check.isAllowedSlope(7, 7, 2, 0));
    }
}
