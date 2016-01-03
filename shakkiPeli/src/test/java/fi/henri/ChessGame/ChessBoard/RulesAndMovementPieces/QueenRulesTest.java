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
import fi.henri.ChessGame.Logic.Pieces.QueenRules;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author melchan
 */
public class QueenRulesTest {
    private ChessBoard board;
    private QueenRules queenR;
    private ChessPiece pawn;
    
    @Before
    public void setUp() {
        this.board = new ChessBoard();
        this.queenR = new QueenRules(board);
        this.pawn = new ChessPiece(BLACK, PAWN);
    }
    
    @Test
    public void knowsWhenToChooseBishopMovement() {
        assertEquals(true, queenR.isMoveLegal(pawn, 0, 0, 7, 7));
    }
    
    @Test
    public void knowsWhenToChooseRookMovement() {
        assertEquals(true, queenR.isMoveLegal(pawn, 0, 0, 0, 7));
    }
    
    @Test
    public void canHandleBadMovement() {
        assertEquals(false, queenR.isMoveLegal(pawn, -4, 20, -2, 9));
        assertEquals(false, queenR.isMoveLegal(pawn, 0, 0, 2, 1));
    }
    
    @Test
    public void moveHappensBishop() {
        board.attemptToPlacePieceOnBoard(pawn, 0, 0);
        queenR.commitMoveIfLegal(pawn, 0, 0, 7, 7);
        assertEquals(pawn, board.getChessBoard()[7][7]);
    }
    
    @Test
    public void moveHappensRook() {
        board.attemptToPlacePieceOnBoard(pawn, 0, 0);
        queenR.commitMoveIfLegal(pawn, 0, 0, 0, 7);
        assertEquals(pawn, board.getChessBoard()[0][7]);
    }
}
