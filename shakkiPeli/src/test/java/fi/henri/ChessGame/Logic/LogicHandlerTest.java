/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Logic;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.*;
import static fi.henri.ChessGame.ChessPieces.ChessColor.*;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import fi.henri.ChessGame.Logic.LogicHandler;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author melchan
 */
public class LogicHandlerTest {

    private ChessBoard board;
    private ChessPiece[][] cB;
    private LogicHandler handler;
    private ChessPiece wQueen;
    private ChessPiece bRook;
    private ChessPiece bKing;
    private ChessPiece wKing;

    @Before
    public void setUp() {
        this.board = new ChessBoard();
        this.cB = board.getChessBoard();
        this.handler = new LogicHandler(board);
        this.wQueen = new ChessPiece(WHITE, QUEEN);
        this.bRook = new ChessPiece(BLACK, ROOK);
        this.bKing = new ChessPiece(BLACK, KING);
        this.wKing = new ChessPiece(WHITE, KING);
        board.attemptToPlacePieceOnBoard(wQueen, 3, 0);
        board.attemptToPlacePieceOnBoard(bRook, 7, 7);
    }

    @Test
    public void whiteMovementHappensFirstAndMovementHappens() {
        handler.movePiece(3, 0, 0, 0);
        assertEquals(wQueen, cB[0][0]);
    }

    @Test
    public void blackMovementHappensAfterWhite() {
        handler.movePiece(3, 0, 0, 0);
        handler.movePiece(7, 7, 7, 0);
        assertEquals(bRook, cB[7][0]);
    }

    @Test
    public void whiteTurnComesAfterBlack() {
        handler.movePiece(3, 0, 0, 0);
        handler.movePiece(7, 7, 7, 0);
        handler.movePiece(0, 0, 0, 7);
        assertEquals(wQueen, cB[0][7]);
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
        assertEquals(wQueen, cB[3][0]);
        handler.movePiece(2, 0, -1, 10);
        assertEquals(wQueen, cB[3][0]);
    }

    @Test
    public void newGameSetsNewGame() {
        handler.newGame();
        assertEquals(ROOK, handler.getChessBoard().getChessBoard()[0][7].getPieceType());
    }

    @Test
    public void newGameClearsAndSetsNewBoard() {
        cB[4][4] = bRook;
        handler.newGame();
        assertEquals(null, handler.getChessBoard().getChessBoard()[4][4]);
    }

    @Test
    public void cantMoveIfOwnKingIsInCheck() {
        board.attemptToPlacePieceOnBoard(bKing, 4, 7);
        board.attemptToPlacePieceOnBoard(wKing, 4, 0);
        assertEquals(true, handler.movePiece(3, 0, 4, 1));
        assertEquals(false, handler.movePiece(7, 7, 7, 0));
    }
    
    @Test
    public void canGiveRightThreateners() {
        board.attemptToPlacePieceOnBoard(wKing, 7, 0);
        handler.movePiece(3, 0, 3, 1);
        assertEquals(1, handler.getKingThreateners().size());
    }
}
