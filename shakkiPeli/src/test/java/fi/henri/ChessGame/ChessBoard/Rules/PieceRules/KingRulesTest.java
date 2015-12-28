/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.ChessBoard.Rules.PieceRules;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import static fi.henri.ChessGame.ChessPieces.Color.*;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import fi.henri.ChessGame.Rules.PieceMovement.KingRules;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author melchan
 */
public class KingRulesTest {

    ChessBoard board;
    KingRules kingR;
    ChessPiece pawn;
    ChessPiece rook;
    ChessPiece queen;

    @Before
    public void setUp() {
        this.board = new ChessBoard();
        this.kingR = new KingRules(board);
        this.pawn = new ChessPiece(WHITE, PAWN);
        this.rook = new ChessPiece(WHITE, ROOK);
        this.queen = new ChessPiece(BLACK, QUEEN);
    }

    @Test
    public void moveInAllVerticalAndHorizontalLegalFreeSpaces() {
        assertEquals(true, kingR.isMoveLegal(pawn, 4, 4, 3, 4));
        assertEquals(true, kingR.isMoveLegal(pawn, 4, 4, 5, 4));
        assertEquals(true, kingR.isMoveLegal(pawn, 4, 4, 4, 3));
        assertEquals(true, kingR.isMoveLegal(pawn, 4, 4, 4, 5));
    }

    @Test
    public void moveInAllDiaconalLegalFreeSpaces() {
        assertEquals(true, kingR.isMoveLegal(pawn, 4, 4, 3, 3));
        assertEquals(true, kingR.isMoveLegal(pawn, 4, 4, 5, 5));
        assertEquals(true, kingR.isMoveLegal(pawn, 4, 4, 5, 3));
        assertEquals(true, kingR.isMoveLegal(pawn, 4, 4, 3, 5));
    }
    
    @Test
    public void cantMoveTooMuch() {
        assertEquals(false, kingR.isMoveLegal(pawn, 4, 4, 2, 4));
        assertEquals(false, kingR.isMoveLegal(pawn, 4, 4, 6, 4));
        assertEquals(false, kingR.isMoveLegal(pawn, 4, 4, 4, 2));
        assertEquals(false, kingR.isMoveLegal(pawn, 4, 4, 4, 6));
        assertEquals(false, kingR.isMoveLegal(pawn, 4, 4, 2, 2));
        assertEquals(false, kingR.isMoveLegal(pawn, 4, 4, 6, 6));
        assertEquals(false, kingR.isMoveLegal(pawn, 4, 4, 6, 2));
        assertEquals(false, kingR.isMoveLegal(pawn, 4, 4, 2, 6));
    }
    
    @Test
    public void cantEatOwnPiece() {
        board.attemptToPlacePieceOnBoard(rook, 4, 4);
        assertEquals(false, kingR.isMoveLegal(pawn, 3, 3, 4, 4));
    }
    
    @Test
    public void canEatEnemyPiece() {
        board.attemptToPlacePieceOnBoard(queen, 4, 4);
        assertEquals(true, kingR.isMoveLegal(pawn, 3, 3, 4, 4));
    }
    
    
}
