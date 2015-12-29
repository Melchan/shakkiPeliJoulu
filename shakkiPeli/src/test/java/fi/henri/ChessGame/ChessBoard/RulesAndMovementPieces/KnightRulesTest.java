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
import fi.henri.ChessGame.RulesAndMovement.Pieces.KnightRules;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author melchan
 */
public class KnightRulesTest {
    ChessBoard board;
    KnightRules knightR;
    ChessPiece pawn;
    ChessPiece rook;
    
    @Before
    public void setUp() {
        this.board = new ChessBoard();
        this.pawn = new ChessPiece(WHITE, PAWN);
        this.rook = new ChessPiece(WHITE, ROOK);
        this.knightR = new KnightRules(board);
    }
    
    @Test
    public void upWardsLegalMoveArePossibleAtMiddleOfBoard() {
        assertEquals(true, knightR.isMoveLegal(pawn, 4, 4, 6, 5));
        assertEquals(true, knightR.isMoveLegal(pawn, 4, 4, 6, 3));
    }
    
    @Test
    public void downWardsLegalMoveArePossibleAtMiddleOfBoard() {
        assertEquals(true, knightR.isMoveLegal(pawn, 4, 4, 2, 5));
        assertEquals(true, knightR.isMoveLegal(pawn, 4, 4, 2, 3));
    }
    
    @Test
    public void toRightSideLegalMoveArePossibleAtMiddleOfBoard() {
        assertEquals(true, knightR.isMoveLegal(pawn, 4, 4, 5, 6));
        assertEquals(true, knightR.isMoveLegal(pawn, 4, 4, 3, 6));
    }
    
    @Test
    public void toLeftSideLegalMoveArePossibleAtMiddleOfBoard() {
        assertEquals(true, knightR.isMoveLegal(pawn, 4, 4, 5, 2));
        assertEquals(true, knightR.isMoveLegal(pawn, 4, 4, 3, 2));
    }
    
    @Test
    public void tryingToMoveIllegalPlaces() {
        assertEquals(false, knightR.isMoveLegal(pawn, 2, 2, 0, 0));
        assertEquals(false, knightR.isMoveLegal(pawn, 0, 0, 4, 2));
        assertEquals(false, knightR.isMoveLegal(pawn, 0, 0, -2, -1));
        assertEquals(false, knightR.isMoveLegal(pawn, 8, 8, 6, 7));
        assertEquals(false, knightR.isMoveLegal(pawn, 0, 0, 4, 0));
        assertEquals(false, knightR.isMoveLegal(pawn, 0, 4, 2, 0));
        assertEquals(false, knightR.isMoveLegal(pawn, 2, 2, 0, 0));
    }
    
    @Test
    public void cantEatFriendlyPiece() {
        board.attemptToPlacePieceOnBoard(rook, 5, 2);
        assertEquals(false, knightR.isMoveLegal(pawn, 4, 4, 5, 2));
    }
    
    @Test
    public void canEatEnemyPiece() {
        ChessPiece queen = new ChessPiece(BLACK, QUEEN);
        board.attemptToPlacePieceOnBoard(queen, 5, 2);
        assertEquals(true, knightR.isMoveLegal(pawn, 4, 4, 5, 2));
    }
    
    @Test
    public void moveHappens() {
        board.attemptToPlacePieceOnBoard(pawn, 4, 4);
        knightR.commitMoveIfLegal(pawn, 4, 4, 6, 5);
        assertEquals(pawn, board.getChessBoard()[6][5]);
    }
}
