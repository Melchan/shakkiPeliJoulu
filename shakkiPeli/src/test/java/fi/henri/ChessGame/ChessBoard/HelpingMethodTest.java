/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.ChessBoard;

import static fi.henri.ChessGame.ChessPieces.ChessColor.*;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author manhenri
 */
public class HelpingMethodTest {
    private ChessBoard board;
    private ChessPiece rook;
    private ChessPiece pawn;
    
    @Before
    public void setUp() {
        this.board = new ChessBoard();
        this.rook = new ChessPiece(BLACK, ROOK);
        this.pawn = new ChessPiece(WHITE, PAWN);
    }
    
    @Test
    public void coordinateToIntegerWorks() {
        assertEquals(52, board.coordinateToInteger(4, 1));
    }
    
    @Test
    public void integerToCoordinateWorks() {
        int[] coordinates = {4, 1};
        assertEquals(coordinates[0], board.integerToCoordinate(52)[0]);
        assertEquals(coordinates[1], board.integerToCoordinate(52)[1]);
    }
    
    @Test
    public void arePlacingPositionsRecorder() {
        board.attemptToPlacePieceOnBoard(rook, 4, 2);
        int n = board.getChessPiecesByColor(BLACK).get(rook);
        int[] c = board.integerToCoordinate(n);
        assertEquals(rook, board.getChessBoard()[c[0]][c[1]]);
    }
    
    @Test
    public void arePlacingPositionsRemovedWhenEaten() {
        board.attemptToPlacePieceOnBoard(rook, 4, 2);
        board.attemptToPlacePieceOnBoard(pawn, 4, 4);
        board.attemptToMovePieceOnBoard(4, 4, 4, 2);
        int n = board.coordinateToInteger(4, 2);
        assertEquals(new Integer(n), board.getChessPiecesByColor(WHITE).get(pawn));
        assertEquals(false, board.getChessPiecesByColor(BLACK).containsKey(rook));
    }
    
    @Test
    public void cloningWholeBoardDoesMakeUniqueBoards() {
        board.attemptToPlacePieceOnBoard(rook, 4, 2);
        board.attemptToPlacePieceOnBoard(pawn, 4, 4);
        ChessBoard clone = board.getClone();
        board.attemptToMovePieceOnBoard(4, 4, 4, 2);
        assertEquals(rook, clone.getChessBoard()[4][2]);
        assertEquals(pawn, clone.getChessBoard()[4][4]);
    }
    
    @Test
    public void chessPieceMovementIsCorrecltyRecordedInHashMap() {
        board.attemptToPlacePieceOnBoard(pawn, 2, 2);
        board.attemptToMovePieceOnBoard(2, 2, 5, 5);
        assertEquals(1, board.getChessPiecesByColor(WHITE).values().size());
    }
    
    @Test
    public void chessPieceMovementIsCorrectlyRecordedInHashMapWhenPieceIsEaten() {
        board.attemptToPlacePieceOnBoard(pawn, 2, 2);
        board.attemptToPlacePieceOnBoard(rook, 5, 5);
        board.attemptToMovePieceOnBoard(2, 2, 5, 5);        
        assertEquals(1, board.getChessPiecesByColor(WHITE).values().size());
        assertEquals(0, board.getChessPiecesByColor(BLACK).values().size());
    }
    
    @Test
    public void chessPieceMovementIsCorrectlyRecordedAfterConsecutivePlacements() {
        board.attemptToPlacePieceOnBoard(pawn, 2, 2);
        board.attemptToPlacePieceOnBoard(rook, 5, 5);
        board.attemptToMovePieceOnBoard(2, 2, 5, 5);      
        board.attemptToPlacePieceOnBoard(null, 2, 2);
        board.attemptToPlacePieceOnBoard(pawn, 7, 7);
        board.attemptToPlacePieceOnBoard(null, 7, 7);
        board.attemptToPlacePieceOnBoard(pawn, 3, 3);
        board.attemptToPlacePieceOnBoard(null, 3, 3);
        board.attemptToPlacePieceOnBoard(pawn, 2, 4);
        board.attemptToPlacePieceOnBoard(null, 2, 4);
        board.attemptToPlacePieceOnBoard(pawn, 1, 3);
        assertEquals((Integer) board.coordinateToInteger(1, 3), board.getChessPiecesByColor(WHITE).get(pawn));
    }
}
