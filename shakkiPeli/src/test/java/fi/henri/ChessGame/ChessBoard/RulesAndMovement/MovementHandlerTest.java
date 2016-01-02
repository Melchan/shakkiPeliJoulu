/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.ChessBoard.RulesAndMovement;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.*;
import static fi.henri.ChessGame.ChessPieces.Color.*;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import fi.henri.ChessGame.Logic.LogicHandler;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author melchan
 */
public class MovementHandlerTest {
    
    private ChessBoard board;
    private ChessPiece[][] cB;
    private LogicHandler handler;
    private ChessPiece queen;
    private ChessPiece rook;
    
    @Before
    public void setUp() {
        this.board = new ChessBoard();
        this.cB = board.getChessBoard();
        this.handler = new LogicHandler(board);
        this.queen = new ChessPiece(WHITE, QUEEN);
        this.rook = new ChessPiece(BLACK, ROOK);
        board.attemptToPlacePieceOnBoard(queen, 3, 0);
        board.attemptToPlacePieceOnBoard(rook, 7, 7);
    }
    
    @Test
    public void whiteMovementHappensFirstAndMovementHappens() {
        handler.movePiece(3, 0, 0, 0);
        assertEquals(queen, cB[0][0]);
    }
    
    @Test
    public void blackMovementHappensAfterWhite() {
        handler.movePiece(3, 0, 0, 0);
        handler.movePiece(7, 7, 7, 0);
        assertEquals(rook, cB[7][0]);
    }
    
    @Test
    public void whiteTurnComesAfterBlack() {
        handler.movePiece(3, 0, 0, 0);
        handler.movePiece(7, 7, 7, 0);
        handler.movePiece(0, 0, 0, 7);
        assertEquals(queen, cB[0][7]);
    }
    
    @Test
    public void canOnlyMoveOnTheirOwnTurn() {
        handler.movePiece(7, 7, 0, 7);
        assertEquals(null, cB[7][0]);
        handler.movePiece(3, 0, 0, 0);
        handler.movePiece(0, 0, 0, 7);
        assertEquals(null, cB[0][7]);
    }
    
    @Test
    public void notMovingIsNotConsideredMove() {
        handler.movePiece(0, 3, 0, 3);
        handler.movePiece(7, 7, 0, 7);
        assertEquals(null, cB[7][0]);
    }
    
    @Test
    public void doesNothingWhenTryingToMoveNull() {
        handler.movePiece(0, 0, 0, 4);
        handler.movePiece(7, 7, 0, 7);
        assertEquals(null, cB[7][0]);
    }
    
    @Test
    public void doesntWorkWithBadInput() {
        handler.movePiece(-4, 20, 3, 0);
        assertEquals(queen, cB[3][0]);
        handler.movePiece(2, 0, -1, 10);
        assertEquals(queen, cB[3][0]);
    }
}
