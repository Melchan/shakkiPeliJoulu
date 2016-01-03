/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.ChessBoard.RulesAndMovementPieces;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import static fi.henri.ChessGame.ChessPieces.ChessColor.*;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import fi.henri.ChessGame.Logic.Pieces.PawnRules;
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
    ChessPiece wPawn;
    ChessPiece bPawn;

    @Before
    public void setUp() {
        this.board = new ChessBoard();
        this.pawnR = new PawnRules(board);
        this.rook = new ChessPiece(BLACK, ROOK);
        this.king = new ChessPiece(BLACK, KING);
        this.queen = new ChessPiece(WHITE, QUEEN);
        this.knight = new ChessPiece(WHITE, KNIGHT);
        this.wPawn = new ChessPiece(WHITE, PAWN);
        this.bPawn = new ChessPiece(BLACK, PAWN);
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
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 4, 4, 1));
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 4, 0, 0));
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 2, 4, 5));
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 2, 0, 0));
    }

    @Test
    public void canMakeTwoSpaceMovementWhenNotMoved() {
        assertEquals(true, pawnR.isMoveLegal(rook, 4, 4, 4, 2));
        assertEquals(true, pawnR.isMoveLegal(queen, 4, 2, 4, 4));
    }

    @Test
    public void cantMakeTwoSpaceMovementWhenMoved() {
        board.attemptToPlacePieceOnBoard(rook, 2, 2);
        board.attemptToPlacePieceOnBoard(queen, 3, 3);
        board.attemptToMovePieceOnBoard(2, 2, 4, 4);
        board.attemptToMovePieceOnBoard(3, 3, 3, 2);
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 4, 4, 2));
        assertEquals(false, pawnR.isMoveLegal(queen, 3, 2, 3, 4));
    }

    @Test
    public void cantMakeTwoSpaceMovementWhenSomethingIsBlockingTheWay() {
        board.attemptToPlacePieceOnBoard(king, 4, 3);
        assertEquals(false, pawnR.isMoveLegal(rook, 4, 4, 4, 2));
        assertEquals(false, pawnR.isMoveLegal(queen, 4, 2, 4, 4));
    }

    @Test
    public void canEnPassantWithWhiteToRight() {
        board.attemptToPlacePieceOnBoard(bPawn, 6, 6);
        board.attemptToMovePieceOnBoard(6, 6, 6, 4);
        assertEquals(true, pawnR.isMoveLegal(queen, 5, 4, 6, 5));
    }

    @Test
    public void canEnPassantWithWhiteToLeft() {
        board.attemptToPlacePieceOnBoard(bPawn, 4, 6);
        board.attemptToMovePieceOnBoard(4, 6, 4, 4);
        assertEquals(true, pawnR.isMoveLegal(queen, 5, 4, 4, 5));
    }

    @Test
    public void canEnPassantWithBlackToRight() {
        board.attemptToPlacePieceOnBoard(wPawn, 6, 1);
        board.attemptToMovePieceOnBoard(6, 1, 6, 3);
        assertEquals(true, pawnR.isMoveLegal(rook, 5, 3, 6, 2));
    }

    @Test
    public void canEnPassantWithBlackToLeft() {
        board.attemptToPlacePieceOnBoard(wPawn, 4, 1);
        board.attemptToMovePieceOnBoard(4, 1, 4, 3);
        assertEquals(true, pawnR.isMoveLegal(rook, 5, 3, 4, 2));
    }

    @Test
    public void cantEnPassantOtherThanPawnWhite() {
        board.attemptToPlacePieceOnBoard(rook, 6, 6);
        board.attemptToMovePieceOnBoard(6, 6, 6, 4);
        assertEquals(false, pawnR.isMoveLegal(queen, 5, 4, 6, 5));

    }

    @Test
    public void cantEnPassantOtherThanPawnBlack() {
        board.attemptToPlacePieceOnBoard(queen, 6, 1);
        board.attemptToMovePieceOnBoard(6, 1, 6, 3);
        assertEquals(false, pawnR.isMoveLegal(rook, 5, 3, 4, 2));
    }

    @Test
    public void cantEnPassantWithBlackWhenEnemyDidntMoveTwoSpaces() {
        board.attemptToPlacePieceOnBoard(wPawn, 6, 2);
        board.attemptToMovePieceOnBoard(6, 2, 6, 3);
        assertEquals(false, pawnR.isMoveLegal(rook, 5, 3, 6, 2));
    }

    @Test
    public void cantEnPassantWithWhiteWhenEnemyDidntMoveTwoSpaces() {
        board.attemptToPlacePieceOnBoard(bPawn, 6, 5);
        board.attemptToMovePieceOnBoard(6, 5, 6, 4);
        assertEquals(false, pawnR.isMoveLegal(queen, 5, 4, 6, 5));
    }

    @Test
    public void afterEnPassantPieceIsEatenBlack() {
        board.attemptToPlacePieceOnBoard(wPawn, 6, 1);
        board.attemptToMovePieceOnBoard(6, 1, 6, 3);
        pawnR.commitMoveIfLegal(rook, 5, 3, 6, 2);
        assertEquals(null, board.getChessBoard()[6][3]);
    }

    @Test
    public void afterEnPassantPieceIsEatenWhite() {
        board.attemptToPlacePieceOnBoard(bPawn, 6, 6);
        board.attemptToMovePieceOnBoard(6, 6, 6, 4);
        pawnR.commitMoveIfLegal(queen, 5, 4, 6, 5);
        assertEquals(null, board.getChessBoard()[6][4]);
    }

    @Test
    public void cantTryEnPassantNearLowerEdgeBlack() {
        board.attemptToPlacePieceOnBoard(wPawn, 4, 0);
        board.attemptToMovePieceOnBoard(4, 0, 4, 2);
        assertEquals(false, pawnR.isMoveLegal(rook, 5, 2, 4, 1));
    }

    @Test
    public void cantTryEnPassantNearUpperEdgeWhite() {
        board.attemptToPlacePieceOnBoard(bPawn, 6, 7);
        board.attemptToMovePieceOnBoard(6, 7, 6, 5);
        assertEquals(false, pawnR.isMoveLegal(queen, 5, 5, 6, 6));
    }

    @Test
    public void movementHappens() {
        board.attemptToPlacePieceOnBoard(queen, 3, 3);
        assertEquals(true, pawnR.commitMoveIfLegal(queen, 3, 3, 3, 4));
        board.attemptToPlacePieceOnBoard(queen, 4, 3);
        assertEquals(false, pawnR.commitMoveIfLegal(queen, 4, 3, 4, 2));        
    }
}
