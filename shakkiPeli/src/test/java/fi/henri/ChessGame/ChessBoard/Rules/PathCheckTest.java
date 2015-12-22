/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.ChessBoard.Rules;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import static fi.henri.ChessGame.ChessPieces.Color.*;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import fi.henri.ChessGame.Rules.Pieces.PathCheck;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author manhenri
 */
public class PathCheckTest {
    private ChessBoard board;
    private ChessPiece pawn;
    private ChessPiece rook;
    private PathCheck check;
    
    @Before
    public void setUp() {
        this.board = new ChessBoard();
        this.pawn = new ChessPiece(WHITE, PAWN);
        this.rook = new ChessPiece(BLACK, ROOK);
        this.check = new PathCheck(board);
    } 
    
    @Test
    public void pathClearStartingSquareIsNotCounted() {
        board.AttemptToPlacePieceOnBoard(pawn, 2, 4);
        assertEquals(true, check.isThePathClear(2, 4, 2, 6));
    }
    
    @Test
    public void pathObstructedStartingSquareIsNotCounted() {
        board.AttemptToPlacePieceOnBoard(pawn, 2, 4);
        board.AttemptToPlacePieceOnBoard(rook, 2, 5);
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
        board.AttemptToPlacePieceOnBoard(pawn, 4, 4);
        assertEquals(false, check.isThePathClear(0, 4, 7, 4));
        assertEquals(false, check.isThePathClear(7, 4, 0, 4));
    }
    
    @Test
    public void pathBlockedVertical() {
        board.AttemptToPlacePieceOnBoard(pawn, 4, 4);
        assertEquals(false, check.isThePathClear(4, 0, 4, 7));
        assertEquals(false, check.isThePathClear(4, 7, 4, 0));
    }
    
    @Test
    public void pathBlockedDiaconalRising() {
        board.AttemptToPlacePieceOnBoard(pawn, 4, 4);
        assertEquals(false, check.isThePathClear(0, 0, 7, 7));
        assertEquals(false, check.isThePathClear(7, 7, 0, 0));
    }
    
    @Test
    public void pathBlockedDiaconalDecreasing() {
        board.AttemptToPlacePieceOnBoard(pawn, 3, 4);
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
        ChessPiece soldier = new ChessPiece(BLACK, PAWN);
        assertEquals(true, check.isThePieceEnemy(soldier, pawn));
    }
    
    @Test
    public void testWithBadValues() {
        assertEquals(false, check.isThePathClear(-2, 14, -100, 2));
    }
}
