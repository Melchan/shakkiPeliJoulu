/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Logic.Observer;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessBoard.ChessBoardInitializer;
import static fi.henri.ChessGame.ChessPieces.ChessColor.*;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import fi.henri.ChessGame.Logic.RuleJudges.CheckMateObserver;
import fi.henri.ChessGame.Logic.RuleJudges.MoveHandler;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * included checkmate explanations
 *
 * @author manhenri
 */
public class CheckMateObserverTest {

    private ChessBoard board;
    private CheckMateObserver CMObserver;
    private MoveHandler observer;

    @Before
    public void setUp() {
        this.board = new ChessBoard();
        new ChessBoardInitializer(board);
        this.observer = new MoveHandler(board);
        this.CMObserver = new CheckMateObserver(board, observer);
    }

    //https://www.youtube.com/watch?v=3uDc5bfuOPI

    @Test
    public void checkMateInTwoTurnsNoBodyCanBlock() {
        observer.movePieceIfLegal(WHITE, 5, 1, 5, 3);
        observer.movePieceIfLegal(BLACK, 4, 6, 4, 4);
        observer.movePieceIfLegal(WHITE, 6, 1, 6, 3);
        observer.movePieceIfLegal(BLACK, 3, 7, 7, 3);

        assertEquals(true, CMObserver.isKingInCheckMate(WHITE));
    }

    // http://www.chess.com/blog/Brennigen/fastest-checkmate-5-moves

    @Test
    public void foolsCheckMate() {
        observer.movePieceIfLegal(WHITE, 4, 1, 4, 2);
        observer.movePieceIfLegal(BLACK, 5, 6, 5, 4);
        observer.movePieceIfLegal(WHITE, 3, 0, 5, 2);
        observer.movePieceIfLegal(BLACK, 6, 7, 5, 5);
        observer.movePieceIfLegal(WHITE, 5, 2, 5, 4);
        observer.movePieceIfLegal(BLACK, 5, 5, 6, 7);
        observer.movePieceIfLegal(WHITE, 5, 0, 2, 3);
        observer.movePieceIfLegal(BLACK, 6, 6, 6, 5);
        observer.movePieceIfLegal(WHITE, 5, 4, 5, 6);
        assertEquals(true, CMObserver.isKingInCheckMate(BLACK));
    }

    @Test
    public void foolsCheckMateAvoidedWithOutBishop() {
        observer.movePieceIfLegal(WHITE, 4, 1, 4, 2);
        observer.movePieceIfLegal(BLACK, 5, 6, 5, 4);
        observer.movePieceIfLegal(WHITE, 3, 0, 5, 2);
        observer.movePieceIfLegal(BLACK, 6, 7, 5, 5);
        observer.movePieceIfLegal(WHITE, 5, 2, 5, 4);
        observer.movePieceIfLegal(BLACK, 5, 5, 6, 7);
        observer.movePieceIfLegal(WHITE, 5, 0, 4, 1);
        observer.movePieceIfLegal(BLACK, 6, 6, 6, 5);
        observer.movePieceIfLegal(WHITE, 5, 4, 5, 6);
        assertEquals(false, CMObserver.isKingInCheckMate(BLACK));
    }

    // one below last one with 2 knights. Them being special case they are also tested

    @Test
    public void sevenTurnCheckMateForWhite() {
        observer.movePieceIfLegal(WHITE, 4, 1, 4, 3);
        observer.movePieceIfLegal(BLACK, 4, 6, 4, 4);
        observer.movePieceIfLegal(WHITE, 6, 0, 5, 2);
        observer.movePieceIfLegal(BLACK, 1, 7, 2, 5);
        observer.movePieceIfLegal(WHITE, 5, 0, 2, 3);
        observer.movePieceIfLegal(BLACK, 3, 6, 3, 5);
        observer.movePieceIfLegal(WHITE, 1, 0, 2, 2);
        observer.movePieceIfLegal(BLACK, 2, 7, 6, 3);
        observer.movePieceIfLegal(WHITE, 5, 2, 4, 4);
        observer.movePieceIfLegal(BLACK, 6, 3, 3, 0);
        observer.movePieceIfLegal(WHITE, 2, 3, 5, 6);
        observer.movePieceIfLegal(BLACK, 4, 7, 4, 6);
        observer.movePieceIfLegal(WHITE, 2, 2, 3, 4);
        assertEquals(true, CMObserver.isKingInCheckMate(BLACK));
    }
    
    @Test
    public void TryingCheckFromFarAndThereIsSomeOneToBlock() {
        board.initialize();
        ChessPiece wRook1 = new ChessPiece(WHITE, ROOK);
        ChessPiece wRook2 = new ChessPiece(WHITE, ROOK);
        ChessPiece wKing = new ChessPiece(WHITE, KING);
        ChessPiece bQueen = new ChessPiece(BLACK, QUEEN);
        board.attemptToPlacePieceOnBoard(bQueen, 7, 7);
        board.attemptToPlacePieceOnBoard(wRook1, 1, 0);
        board.attemptToPlacePieceOnBoard(wRook2, 0, 1);
        board.attemptToPlacePieceOnBoard(wKing, 0, 0);
        assertEquals(false, CMObserver.isKingInCheckMate(WHITE));
    }
    
    @Test
    public void TryingCheckFromFarAndThereIsSomeOneToBlockInMiddle() {
        board.initialize();
        ChessPiece wRook1 = new ChessPiece(WHITE, ROOK);
        ChessPiece wRook2 = new ChessPiece(WHITE, ROOK);
        ChessPiece wKing = new ChessPiece(WHITE, KING);
        ChessPiece wPawn = new ChessPiece(WHITE, PAWN);
        ChessPiece bQueen = new ChessPiece(BLACK, QUEEN);
        board.attemptToPlacePieceOnBoard(bQueen, 7, 0);
        board.attemptToPlacePieceOnBoard(wRook1, 0, 1);
        board.attemptToPlacePieceOnBoard(wPawn, 1, 1);
        board.attemptToPlacePieceOnBoard(wRook2, 6, 7);
        board.attemptToPlacePieceOnBoard(wKing, 0, 0);
        assertEquals(false, CMObserver.isKingInCheckMate(WHITE));
    }
    
    @Test
    public void TryingCheckFromFarAndThereIsSomeOneToEat() {
        board.initialize();
        ChessPiece wRook1 = new ChessPiece(WHITE, ROOK);
        ChessPiece wRook2 = new ChessPiece(WHITE, ROOK);
        ChessPiece wKing = new ChessPiece(WHITE, KING);
        ChessPiece wPawn = new ChessPiece(WHITE, PAWN);
        ChessPiece bQueen = new ChessPiece(BLACK, QUEEN);
        board.attemptToPlacePieceOnBoard(bQueen, 7, 0);
        board.attemptToPlacePieceOnBoard(wRook1, 0, 1);
        board.attemptToPlacePieceOnBoard(wPawn, 1, 1);
        board.attemptToPlacePieceOnBoard(wRook2, 7, 7);
        board.attemptToPlacePieceOnBoard(wKing, 0, 0);
        assertEquals(false, CMObserver.isKingInCheckMate(WHITE));
    }
}
