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
import fi.henri.ChessGame.Logic.Observers.MoveHandler;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author manhenri
 */
public class MoveObserverTest {

    private MoveHandler observer;
    private ChessBoard board;
    private ChessPiece bKing;
    private ChessPiece wKing;
    private ChessPiece bQueen;
    private ChessPiece wQueen;
    private ChessPiece wRook;
    private ChessPiece bRook;

    @Before

    public void setUp() {
        this.board = new ChessBoard();
        this.observer = new MoveHandler(board);
        this.bKing = new ChessPiece(BLACK, KING);
        this.wKing = new ChessPiece(WHITE, KING);
        this.bQueen = new ChessPiece(BLACK, QUEEN);
        this.wQueen = new ChessPiece(WHITE, QUEEN);
        this.wRook = new ChessPiece(WHITE, ROOK);
        this.bRook = new ChessPiece(BLACK, ROOK);
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
        board.attemptToPlacePieceOnBoard(bQueen, 5, 0);
        assertEquals(true, observer.movePieceIfLegal(WHITE, 4, 0, 3, 1));
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
        board.attemptToPlacePieceOnBoard(bKing, 4, 7);
        board.attemptToPlacePieceOnBoard(wQueen, 4, 0);
        board.attemptToPlacePieceOnBoard(wRook, 5, 6);
        assertEquals(false, observer.movePieceIfLegal(BLACK, 4, 7, 4, 6));
        assertEquals(2, observer.getThreateners().size());
    }

    @Test
    public void moveResetsCorrectlyEnPassantMoveIsCancelledWhiteMoved() {
        ChessPiece wPawn = new ChessPiece(WHITE, PAWN);
        ChessPiece bPawn = new ChessPiece(BLACK, PAWN);

        board.attemptToPlacePieceOnBoard(wPawn, 2, 4);
        board.attemptToPlacePieceOnBoard(bPawn, 1, 6);
        board.attemptToPlacePieceOnBoard(wKing, 4, 4);
        board.attemptToPlacePieceOnBoard(bQueen, 0, 4);
        observer.movePieceIfLegal(BLACK, 1, 6, 1, 4);
        observer.movePieceIfLegal(WHITE, 2, 4, 1, 5);
        assertEquals(wPawn, board.getChessBoard()[2][4]);
        assertEquals(bPawn, board.getChessBoard()[1][4]);
        assertEquals(null, board.getChessBoard()[1][5]);
    }

    @Test
    public void moveResetsCorrectlyEnPassantMoveIsCancelledBlackMoved() {
        ChessPiece wPawn = new ChessPiece(WHITE, PAWN);
        ChessPiece bPawn = new ChessPiece(BLACK, PAWN);

        board.attemptToPlacePieceOnBoard(wPawn, 6, 1);
        board.attemptToPlacePieceOnBoard(bPawn, 5, 3);
        board.attemptToPlacePieceOnBoard(bKing, 4, 3);
        board.attemptToPlacePieceOnBoard(wQueen, 7, 3);
        observer.movePieceIfLegal(WHITE, 6, 1, 6, 3);
        observer.movePieceIfLegal(BLACK, 5, 3, 6, 2);
        assertEquals(wPawn, board.getChessBoard()[6][3]);
        assertEquals(bPawn, board.getChessBoard()[5][3]);
        assertEquals(null, board.getChessBoard()[6][2]);
    }
    
    @Test
    public void moveResetWorksOnPartOfPieceLocationLibraryHashMapEnPassant() {
        ChessPiece wPawn = new ChessPiece(WHITE, PAWN);
        ChessPiece bPawn = new ChessPiece(BLACK, PAWN);

        board.attemptToPlacePieceOnBoard(wPawn, 6, 1);
        board.attemptToPlacePieceOnBoard(bPawn, 5, 3);
        board.attemptToPlacePieceOnBoard(bKing, 4, 3);
        board.attemptToPlacePieceOnBoard(wQueen, 7, 3);
        observer.movePieceIfLegal(WHITE, 6, 1, 6, 3);
        
        int wPiecesBefore = board.getChessPiecesByColor(WHITE).values().size();
        int bPiecesBefore = board.getChessPiecesByColor(BLACK).values().size();
        observer.movePieceIfLegal(BLACK, 5, 3, 6, 2);
        
        assertEquals(wPiecesBefore, board.getChessPiecesByColor(WHITE).values().size());
       assertEquals(bPiecesBefore, board.getChessPiecesByColor(BLACK).values().size());      
    }
    
    @Test
    public void moveResetWorksOnPieceLocationLibraryAfterKingMoveReset() {
        board.attemptToPlacePieceOnBoard(bKing, 4, 7);
        board.attemptToPlacePieceOnBoard(wQueen, 4, 0);
        board.attemptToPlacePieceOnBoard(wRook, 5, 6);
        
        int wPiecesBefore = board.getChessPiecesByColor(WHITE).values().size();
        int bPiecesBefore = board.getChessPiecesByColor(BLACK).values().size();
        
        observer.movePieceIfLegal(BLACK, 4, 7, 4, 6);
        
        assertEquals(wPiecesBefore, board.getChessPiecesByColor(WHITE).values().size());
        assertEquals(bPiecesBefore, board.getChessPiecesByColor(BLACK).values().size());      
    }   
    
    @Test
    public void moveResetPutsActorBackToRightLocationInHashMap() {
        board.attemptToPlacePieceOnBoard(bKing, 0, 4);
        board.attemptToPlacePieceOnBoard(wQueen, 0, 1);
        board.attemptToPlacePieceOnBoard(bQueen, 7, 7);
        Integer queenLocationBefore = board.getChessPiecesByColor(BLACK).get(bQueen);
        
        observer.movePieceIfLegal(BLACK, 7, 7, 0, 7);
        
        assertEquals(queenLocationBefore, board.getChessPiecesByColor(BLACK).get(bQueen));      
    }
    
    @Test
    public void moveResetPutsTargetBackToRightLocationInHashMap() {
        board.attemptToPlacePieceOnBoard(bKing, 4, 0);
        board.attemptToPlacePieceOnBoard(wQueen, 4, 1);
        board.attemptToPlacePieceOnBoard(bQueen, 2, 1);
        board.attemptToPlacePieceOnBoard(wRook, 2, 2);
              
        Integer rookLocationBefore = board.getChessPiecesByColor(WHITE).get(ROOK);
        
        observer.movePieceIfLegal(BLACK, 2, 1, 2, 2);
        
        assertEquals(rookLocationBefore, board.getChessPiecesByColor(WHITE).get(ROOK));      
    }
    
    @Test
    public void cantCastleBlackWhenKingIsInCheck() {
        board.attemptToPlacePieceOnBoard(bKing, 4, 7);
        board.attemptToPlacePieceOnBoard(bRook, 0, 7);
        board.attemptToPlacePieceOnBoard(wRook, 4, 0);
        assertEquals(false, observer.movePieceIfLegal(BLACK, 4, 7, 2, 7));
    }
    
    @Test
    public void cantCastleWhitekWhenEndPointIsInCheckWithRightMove() {
        board.attemptToPlacePieceOnBoard(wKing, 4, 0);
        board.attemptToPlacePieceOnBoard(wRook, 7, 0);
        board.attemptToPlacePieceOnBoard(bRook, 6, 4);
        assertEquals(false, observer.movePieceIfLegal(BLACK, 4, 0, 4, 6));
    }
    
    @Test
    public void cantCastleWhitekWhenEndPointIsInCheckLeftMove() {
        board.attemptToPlacePieceOnBoard(bKing, 4, 7);
        board.attemptToPlacePieceOnBoard(bRook, 0, 7);
        board.attemptToPlacePieceOnBoard(wRook, 2, 0);
        assertEquals(false, observer.movePieceIfLegal(BLACK, 4, 7, 2, 7));
    }
    
    @Test
    public void threatsAreRecordedCorrectlyWithConsecutiveTriesToDifferentSituations() {
        board.attemptToPlacePieceOnBoard(bKing, 4, 7);
        board.attemptToPlacePieceOnBoard(wQueen, 4, 0);
        board.attemptToPlacePieceOnBoard(wRook, 7, 7);
        assertEquals(false, observer.movePieceIfLegal(BLACK, 4, 7, 4, 6));
        assertEquals(false, observer.movePieceIfLegal(BLACK, 4, 7, 5, 7));
        int[] t = board.integerToCoordinate(observer.getThreateners().get(0));
        assertEquals(wRook, board.getChessBoard()[t[0]][t[1]]);
    }
    
    @Test
    public void castlingMoveHappensWhenCastling() {
        board.attemptToPlacePieceOnBoard(bKing, 4, 7);
        board.attemptToPlacePieceOnBoard(bRook, 0, 7);
        assertEquals(true, observer.movePieceIfLegal(BLACK, 4, 7, 2, 7));
    }
    
    @Test
    public void ownPiecesWontThreatenTheKing() {
        ChessPiece bKnight = new ChessPiece(BLACK, KNIGHT);
        ChessPiece wBishop = new ChessPiece(WHITE, BISHOP);
        board.attemptToPlacePieceOnBoard(bKing, 4, 7);
        board.attemptToPlacePieceOnBoard(bRook, 0, 7);
        board.attemptToPlacePieceOnBoard(wBishop, 3, 4);
        board.attemptToPlacePieceOnBoard(bKnight, 1, 7);
        observer.movePieceIfLegal(WHITE, 3, 4, 2, 5);
        observer.movePieceIfLegal(BLACK, 1, 7, 2, 5);
        assertEquals(true, observer.movePieceIfLegal(BLACK, 4, 7, 2, 7));
        
    }
    
    @Test
    public void movementCanBeReseted() {
        board.attemptToPlacePieceOnBoard(bKing, 0, 4);
        board.attemptToPlacePieceOnBoard(wQueen, 0, 0);
        board.attemptToPlacePieceOnBoard(bQueen, 1, 1);
        observer.movePieceIfLegal(BLACK, 1, 1, 0, 1);
        observer.resetMovement();
        assertEquals(bQueen, board.getChessBoard()[1][1]);
    }
    
    @Test
    public void kingCanNotMoveOutOfCheckMate() {
        new ChessBoardInitializer(board);
        observer.movePieceIfLegal(WHITE, 5, 1, 5, 3);
        observer.movePieceIfLegal(BLACK, 4, 6, 4, 4);
        observer.movePieceIfLegal(WHITE, 6, 1, 6, 3);
        observer.movePieceIfLegal(BLACK, 3, 7, 7, 3);
        observer.movePieceIfLegal(WHITE, 4, 0, 3, -1);
        observer.movePieceIfLegal(WHITE, 4, 0, 3, 0);
        observer.movePieceIfLegal(WHITE, 4, 0, 3, 1);
        observer.movePieceIfLegal(WHITE, 4, 0, 4, -1);
        observer.movePieceIfLegal(WHITE, 4, 0, 4, 0);
        observer.movePieceIfLegal(WHITE, 4, 0, 4, 1);
        observer.movePieceIfLegal(WHITE, 4, 0, 5, -1);
        observer.movePieceIfLegal(WHITE, 4, 0, 5, 0);
        assertEquals(false, observer.movePieceIfLegal(WHITE, 4, 0, 5, 1));
    }
}
