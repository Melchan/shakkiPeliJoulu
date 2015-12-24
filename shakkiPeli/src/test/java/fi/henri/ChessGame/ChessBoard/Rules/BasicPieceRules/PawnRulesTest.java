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
import fi.henri.ChessGame.Rules.Pieces.PawnRules;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Melchan
 */
public class PawnRulesTest {

    ChessBoard board;
    PawnRules pawnR;
    ChessPiece rook;
    ChessPiece king;
    ChessPiece queen;
    ChessPiece knight;

    @Before
    public void setUp() {
        this.board = new ChessBoard();
        this.pawnR = new PawnRules(board);
        this.rook = new ChessPiece(BLACK, ROOK);
        this.king = new ChessPiece(BLACK, KING);
        this.queen = new ChessPiece(WHITE, QUEEN);
        this.knight = new ChessPiece(WHITE, KNIGHT);
    }

    @Test
    public void allowedBlackNormalMovement() {
        assertEquals(true, pawnR.isMoveLegal(rook, 4, 4, 4, 3));
    }

    @Test
    public void allwedWhiteMovement() {
        assertEquals(true, pawnR.isMoveLegal(queen, 4, 4, 4, 5));
    }

    @Test
    public void blackCantMoveWhenSomeOneIsBlocking() {
        board.attemptToPlacePieceOnBoard(king, 4, 3);
        board.attemptToPlacePieceOnBoard(knight, 3, 2);
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 4, 4, 3));
        assertEquals(false, pawnR.isMoveLegal(queen, 3, 3, 3, 2));
    }

    @Test
    public void whiteCantMoveWhenSomeOneIsBlocking() {
        board.attemptToPlacePieceOnBoard(king, 4, 3);
        board.attemptToPlacePieceOnBoard(knight, 3, 2);
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 2, 4, 3));
        assertEquals(false, pawnR.isMoveLegal(rook, 3, 1, 3, 2));
    }

    @Test
    public void blackCantMoveBackWards() {
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 4, 3, 5));
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 4, 5, 5));
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 4, 4, 5));
    }

    @Test
    public void blackCantMoveToSides() {
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 4, 5, 3));
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 4, 3, 3));
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 4, 3, 4));
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 4, 5, 4));
    }

    @Test
    public void whiteCantMoveBackWards() {
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 4, 5, 3));
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 4, 3, 3));
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 4, 4, 3));
    }

    @Test
    public void whiteCantMoveToSides() {
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 4, 3, 4));
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 4, 5, 4));
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 4, 3, 5));
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 4, 5, 5));
    }
    
    @Test
    public void whiteCanEatNormallyEnemy() {
        board.attemptToPlacePieceOnBoard(knight, 3, 3);
        board.attemptToPlacePieceOnBoard(knight, 5, 3);
        assertEquals(true, pawnR.isMoveLegal(rook, 4, 4, 3, 3));
        assertEquals(true, pawnR.isMoveLegal(rook, 4, 4, 5, 3));
    }
    
    @Test
    public void whiteCantEatNormallyAlly() {
        board.attemptToPlacePieceOnBoard(king, 3, 3);
        board.attemptToPlacePieceOnBoard(king, 5, 3);
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 4, 3, 3));
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 4, 5, 3));
    }
    
    @Test
    public void blackCanEatNormallyEnemy() {
        board.attemptToPlacePieceOnBoard(king, 3, 3);
        board.attemptToPlacePieceOnBoard(king, 5, 3);
        assertEquals(true, pawnR.isMoveLegal(queen, 4, 2, 3, 3));
        assertEquals(true, pawnR.isMoveLegal(queen, 4, 2, 5, 3));
    }
    
    @Test
    public void blackCantEatNormallyAlly() {
        board.attemptToPlacePieceOnBoard(knight, 3, 3);
        board.attemptToPlacePieceOnBoard(knight, 5, 3);
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 2, 3, 3));
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 2, 5, 3));
    }
    
    @Test
    public void cantMoveWihtBadValues() {
        assertEquals(false, pawnR.isMoveLegal(rook, -2, 4, 4, 2));
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 20, 0, 0));
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 2, -1, 4));
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 2, 0, 40));
    }
    
    @Test
    public void canTMoveInBadSquares() {
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 4, 4, 2));
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 4, 0, 0));
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 2, 4, 4));
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 2, 0, 0));
    }
}
