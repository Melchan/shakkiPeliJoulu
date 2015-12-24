/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.ChessBoard.Rules.BasicPieceRules;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import static fi.henri.ChessGame.ChessPieces.Color.*;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import fi.henri.ChessGame.Rules.Pieces.RookRules;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author melchan
 */
public class RookRulesTest {
    private ChessBoard board;
    private RookRules rookR;
    private ChessPiece pawn;
    private ChessPiece queen;
    private ChessPiece bishop;
    
    @Before
    public void setUp() {
        this.board = new ChessBoard();
        this.rookR = new RookRules(board);
        this.pawn = new ChessPiece(WHITE, PAWN);
        this.queen = new ChessPiece(WHITE, QUEEN);
        this.bishop = new ChessPiece(BLACK, BISHOP);
    }
    
    @Test
    public void rookCorrectMovementWithFreePath() {
        assertEquals(true, rookR.isMoveLegal(pawn, 0, 0, 0, 7));
        assertEquals(true, rookR.isMoveLegal(pawn, 0, 0, 7, 0));
        assertEquals(true, rookR.isMoveLegal(pawn, 7, 7, 0, 7));
        assertEquals(true, rookR.isMoveLegal(pawn, 7, 7, 7, 0));
    }
    
    @Test 
    public void rookWrongMovement() {
        assertEquals(false, rookR.isMoveLegal(pawn, 0, 0, 3, 4));
        assertEquals(false, rookR.isMoveLegal(pawn, 0, 0, 7, 7));
        assertEquals(false, rookR.isMoveLegal(pawn, 1, 5, 3, 6));
    }
    
    @Test 
    public void rookBadMovementImput() {
        assertEquals(false, rookR.isMoveLegal(pawn, -12, 4, 7, 19));
    }
    
    @Test
    public void rookCantEatOwnPiece() {
        board.attemptToPlacePieceOnBoard(queen, 2, 4);
        assertEquals(false, rookR.isMoveLegal(pawn, 4, 4, 2, 4));
    }
    
    @Test
    public void rookCanEatEnemyPiece() {
        board.attemptToPlacePieceOnBoard(bishop, 2, 4);
        assertEquals(true, rookR.isMoveLegal(pawn, 4, 4, 2, 4));
    }
    
    @Test
    public void rookCantMoveIfOwnPieceOnTheWay() {
        board.attemptToPlacePieceOnBoard(queen, 4, 4);
        assertEquals(false, rookR.isMoveLegal(pawn, 4, 0, 4, 7));
    }
    
    @Test
    public void rookCantMoveifEnemyPieceOnTheWay() {
        board.attemptToPlacePieceOnBoard(bishop, 4, 4);
        assertEquals(false, rookR.isMoveLegal(pawn, 0, 4, 7, 4));
    } 
}
