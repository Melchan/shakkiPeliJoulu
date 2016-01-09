/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Logic.Observer;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import static fi.henri.ChessGame.ChessPieces.ChessColor.*;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import fi.henri.ChessGame.Logic.Observers.CheckObserver;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author manhenri
 */
public class CheckObserverTest {

    private CheckObserver observer;
    private ChessBoard board;
    private ChessPiece bKing;
    private ChessPiece wKing;
    private ChessPiece bQueen;
    private ChessPiece wQueen;

    @Before

    public void setUp() {
        this.board = new ChessBoard();
        this.observer = new CheckObserver(board);
        this.bKing = new ChessPiece(BLACK, KING);
        this.wKing = new ChessPiece(WHITE, KING);
        this.bQueen = new ChessPiece(BLACK, QUEEN);
        this.wQueen = new ChessPiece(WHITE, QUEEN);
    }

    @Test
    public void canDetectIfKingIsThreatened() {
        board.attemptToPlacePieceOnBoard(bKing, 0, 4);
        board.attemptToPlacePieceOnBoard(wQueen, 0, 0);
        assertEquals(false, observer.movePieceIfLegal(BLACK, 0, 4, 0, 1));
    }

    @Test
    public void kingCanEatWhenInCheckToSaveHimself() {
        board.attemptToPlacePieceOnBoard(bKing, 4, 0);
        board.attemptToPlacePieceOnBoard(wQueen, 4, 1);
        assertEquals(true, observer.movePieceIfLegal(BLACK, 4, 0, 4, 1));
    }

    @Test
    public void cantMovePiecesIfKingInCheck() {
        board.attemptToPlacePieceOnBoard(bKing, 0, 4);
        board.attemptToPlacePieceOnBoard(wQueen, 0, 0);
        board.attemptToPlacePieceOnBoard(bQueen, 1, 1);
        assertEquals(false, observer.movePieceIfLegal(BLACK, 1, 1, 2, 2));
    }

    @Test
    public void canMovePieceToDefendKing() {
        board.attemptToPlacePieceOnBoard(bKing, 0, 4);
        board.attemptToPlacePieceOnBoard(wQueen, 0, 0);
        board.attemptToPlacePieceOnBoard(bQueen, 1, 1);
        assertEquals(true, observer.movePieceIfLegal(BLACK, 1, 1, 0, 1));
    }

    @Test
    public void canEnPassantIfItCanExposeEnemyKing() {
        board.attemptToPlacePieceOnBoard(new ChessPiece(WHITE, PAWN), 1, 1);
        board.attemptToPlacePieceOnBoard(new ChessPiece(BLACK, PAWN), 2, 3);
        board.attemptToPlacePieceOnBoard(bQueen, 0, 4);
        board.attemptToPlacePieceOnBoard(wKing, 4, 0);
        board.attemptToMovePieceOnBoard(1, 1, 1, 3);
        assertEquals(true, observer.movePieceIfLegal(BLACK, 2, 3, 1, 2));
    }

    @Test
    public void cantEnPassantIfItExposesOwnKing() {
        board.attemptToPlacePieceOnBoard(new ChessPiece(WHITE, PAWN), 2, 4);
        board.attemptToPlacePieceOnBoard(new ChessPiece(BLACK, PAWN), 1, 4);
        board.attemptToPlacePieceOnBoard(bQueen, 0, 4);
        board.attemptToPlacePieceOnBoard(wKing, 4, 4);
        board.attemptToMovePieceOnBoard(1, 6, 1, 4);
        assertEquals(false, observer.movePieceIfLegal(WHITE, 2, 4, 1, 5));
    }

    @Test
    public void kingCanMoveToSafetyFromThreat() {
        board.attemptToPlacePieceOnBoard(wKing, 4, 0);
        board.attemptToPlacePieceOnBoard(bQueen, 0, 4);
        assertEquals(true, observer.movePieceIfLegal(WHITE, 0, 4, 0, 5));
    }

    @Test
    public void movementResetsCorrectlyWhenEnPassantMoveIsCancelled() {
        ChessPiece wPawn = new ChessPiece(WHITE, PAWN);
        ChessPiece bPawn = new ChessPiece(BLACK, PAWN);

        board.attemptToPlacePieceOnBoard(wPawn, 2, 4);
        board.attemptToPlacePieceOnBoard(bPawn, 1, 4);
        board.attemptToPlacePieceOnBoard(wKing, 4, 4);
        board.attemptToPlacePieceOnBoard(bQueen, 0, 4);
        board.attemptToMovePieceOnBoard(1, 6, 1, 4);
        observer.movePieceIfLegal(WHITE, 2, 4, 1, 5);
        assertEquals(wPawn, board.getChessBoard()[2][4]);
        assertEquals(bPawn, board.getChessBoard()[1][4]);
        assertEquals(null, board.getChessBoard()[1][5]);
    }

    @Test
    public void whenResetingBoardSituationPiecesDontHaveStatusMoved() {
        board.attemptToPlacePieceOnBoard(bKing, 0, 4);
        board.attemptToPlacePieceOnBoard(wQueen, 0, 0);
        board.attemptToPlacePieceOnBoard(bQueen, 1, 1);
        observer.movePieceIfLegal(BLACK, 1, 1, 2, 2);
        assertEquals(false, board.getChessBoard()[1][1].hasMoved());
    }

    @Test
    public void recordsKingsThreatsAccordinglyWithOneEnemy() {
        board.attemptToPlacePieceOnBoard(bKing, 4, 7);
        board.attemptToPlacePieceOnBoard(wQueen, 4, 0);
        assertEquals(false, observer.movePieceIfLegal(BLACK, 4, 7, 4, 6));

        int[] t = board.integerToCoordinate(observer.getThreateners().get(0));
        assertEquals(wQueen, board.getChessBoard()[t[0]][t[1]]);

    }
    
    @Test
    public void recorsThreatsAccordinglyWithMultipleEnemies() {
        ChessPiece wRook = new ChessPiece(WHITE, ROOK);
        
        board.attemptToPlacePieceOnBoard(bKing, 4, 7);
        board.attemptToPlacePieceOnBoard(wQueen, 4, 0);
        board.attemptToPlacePieceOnBoard(wRook, 5, 6);
        assertEquals(false, observer.movePieceIfLegal(BLACK, 4, 7, 4, 6));
        assertEquals(2, observer.getThreateners().size()); 
    }
}
