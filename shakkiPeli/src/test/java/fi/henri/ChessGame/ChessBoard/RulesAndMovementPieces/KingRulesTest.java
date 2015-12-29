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
import fi.henri.ChessGame.RulesAndMovement.Pieces.KingRules;
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

    @Test
    public void castlingWhiteRight() {
        board.attemptToPlacePieceOnBoard(pawn, 4, 0);
        board.attemptToPlacePieceOnBoard(queen, 7, 0);
        assertEquals(true, kingR.commitMoveIfLegal(pawn, 4, 0, 6, 0));
        assertEquals(queen, board.getChessBoard()[5][0]);
    }

    @Test
    public void castlingWhiteLeft() {
        board.attemptToPlacePieceOnBoard(pawn, 4, 0);
        board.attemptToPlacePieceOnBoard(queen, 0, 0);
        assertEquals(true, kingR.commitMoveIfLegal(pawn, 4, 0, 2, 0));
        assertEquals(queen, board.getChessBoard()[3][0]);
    }

    @Test
    public void castlingBlackRight() {
        board.attemptToPlacePieceOnBoard(pawn, 4, 7);
        board.attemptToPlacePieceOnBoard(queen, 7, 7);
        assertEquals(true, kingR.commitMoveIfLegal(pawn, 4, 7, 6, 7));
        assertEquals(queen, board.getChessBoard()[5][7]);
    }

    @Test
    public void castlingBlackLeft() {
        board.attemptToPlacePieceOnBoard(pawn, 4, 7);
        board.attemptToPlacePieceOnBoard(queen, 0, 7);
        assertEquals(true, kingR.commitMoveIfLegal(pawn, 4, 7, 2, 7));
        assertEquals(queen, board.getChessBoard()[3][7]);
    }

    @Test
    public void castlingWhiteNotAllowedWhenBlocked() {
        board.attemptToPlacePieceOnBoard(pawn, 4, 0);
        board.attemptToPlacePieceOnBoard(rook, 3, 0);
        board.attemptToPlacePieceOnBoard(queen, 0, 0);
        assertEquals(false, kingR.commitMoveIfLegal(pawn, 4, 0, 2, 0));
        assertEquals(rook, board.getChessBoard()[3][0]);
    }

    @Test
    public void castlingBlackNotAllowedWhenBlocked() {
        board.attemptToPlacePieceOnBoard(pawn, 4, 7);
        board.attemptToPlacePieceOnBoard(rook, 5, 7);
        board.attemptToPlacePieceOnBoard(queen, 7, 7);
        assertEquals(false, kingR.commitMoveIfLegal(pawn, 4, 7, 6, 7));
        assertEquals(rook, board.getChessBoard()[5][7]);
    }

    @Test
    public void castlingNotAllowedWhenKingMoved() {
        board.attemptToPlacePieceOnBoard(pawn, 4, 7);
        pawn.move();
        board.attemptToPlacePieceOnBoard(queen, 7, 7);
        assertEquals(false, kingR.commitMoveIfLegal(pawn, 4, 7, 6, 7));
        assertEquals(null, board.getChessBoard()[5][7]);
    }
    
    @Test
    public void castlingNotAllowedWhenRookMoved() {
        board.attemptToPlacePieceOnBoard(pawn, 4, 0);
        board.attemptToPlacePieceOnBoard(queen, 0, 0);
        queen.move();
        assertEquals(false, kingR.commitMoveIfLegal(pawn, 4, 0, 2, 0));
        assertEquals(null, board.getChessBoard()[3][0]);
    }
    
    @Test
    public void moveHappens() {
        board.attemptToPlacePieceOnBoard(pawn, 0, 0);
        kingR.commitMoveIfLegal(pawn, 0, 0, 1, 1);
        assertEquals(pawn, board.getChessBoard()[1][1]);
    }
}
