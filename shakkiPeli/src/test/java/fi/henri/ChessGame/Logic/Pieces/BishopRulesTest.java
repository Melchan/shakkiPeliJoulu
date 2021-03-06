/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Logic.Pieces;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import static fi.henri.ChessGame.ChessPieces.ChessColor.*;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import fi.henri.ChessGame.Logic.Pieces.BishopRules;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author melchan
 */
public class BishopRulesTest {
    private ChessBoard board;
    private BishopRules bishopR;
    private ChessPiece pawn;
    private ChessPiece queen;
    private ChessPiece rook;
    
    @Before
    public void setUp() {
        this.board = new ChessBoard();
        this.bishopR = new BishopRules(board);
        this.pawn = new ChessPiece(BLACK, PAWN);
        this.queen = new ChessPiece(BLACK, QUEEN);
        this.rook = new ChessPiece(WHITE, ROOK);
    }
    
    @Test
    public void bishopCorrectRisingDiaconalMovementWithFreePath() {
        assertEquals(true, bishopR.isMoveLegal(pawn, 0, 0, 7, 7));
        assertEquals(true, bishopR.isMoveLegal(pawn, 7, 7, 0, 0));
    }
    
    @Test
    public void bishopCorrectDecreasingDiaconalMovementWithFreePath() {
        assertEquals(true, bishopR.isMoveLegal(pawn, 7, 0, 0, 7));
        assertEquals(true, bishopR.isMoveLegal(pawn, 0, 7, 7, 0));
    }
    
    @Test 
    public void bishopWrongMovement() {
        assertEquals(false, bishopR.isMoveLegal(pawn, 7, 0, 0, 0));
        assertEquals(false, bishopR.isMoveLegal(pawn, 0, 0, 2, 4));
        assertEquals(false, bishopR.isMoveLegal(pawn, 2, 4, 7, 3));
    }
    
    @Test
    public void badInputMovement() {
        assertEquals(false, bishopR.isMoveLegal(pawn, -12, 3, 2, 4));
        assertEquals(false, bishopR.isMoveLegal(pawn, 25, -12, -4, 4));
    }
    
    @Test 
    public void bishopCorrectRisingDiaconalMovementWithFriendOnPath() {
        board.attemptToPlacePieceOnBoard(queen, 3, 3);
        assertEquals(false, bishopR.isMoveLegal(pawn, 0, 0, 7, 7));
        assertEquals(false, bishopR.isMoveLegal(pawn, 7, 7, 0, 0));
    }
    
    @Test
    public void bishopCorrectDecreasingDiaconalMovementWithEnemyOnPath() {
        board.attemptToPlacePieceOnBoard(rook, 4, 3);
        assertEquals(false, bishopR.isMoveLegal(pawn, 7, 0, 0, 7));
        assertEquals(false, bishopR.isMoveLegal(pawn, 0, 7, 7, 0));
    }
    
    @Test
    public void bishopCantEatFriendlyPiece() {
        board.attemptToPlacePieceOnBoard(queen, 7, 7);
        assertEquals(false, bishopR.isMoveLegal(pawn, 0, 0, 7, 7));
    }
    
    @Test
    public void bishopCanEatEnemyPiece() {
        board.attemptToPlacePieceOnBoard(rook, 7, 0);
        assertEquals(true, bishopR.isMoveLegal(pawn, 0, 7, 7, 0));
    }
    
    @Test
    public void moveHappens() {
        board.attemptToPlacePieceOnBoard(pawn, 0, 0);
        assertEquals(true, bishopR.commitMoveIfLegal(pawn, 0, 0, 7, 7));
        assertEquals(pawn, board.getChessBoard()[7][7]);
    }
    
    @Test
    public void moveDoesntAlwaysHappen() {
        board.attemptToPlacePieceOnBoard(pawn, 7, 0);
        assertEquals(false, bishopR.commitMoveIfLegal(pawn, 7, 0, 7, 7));
        assertEquals(null, board.getChessBoard()[7][7]);
    }
}
