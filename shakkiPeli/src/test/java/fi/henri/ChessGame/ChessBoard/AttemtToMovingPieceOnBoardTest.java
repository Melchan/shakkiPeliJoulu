/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import static fi.henri.ChessGame.ChessPieces.Color.WHITE;
import static fi.henri.ChessGame.ChessPieces.PieceType.PAWN;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
/**
 *
 * @author Melchan
 */
public class AttemtToMovingPieceOnBoardTest {
    ChessBoard board;
    ChessPiece pawn;
    ChessPiece[][] b;
    
    @Before
    public void setUp() {
        board = new ChessBoard();
        pawn = new ChessPiece(WHITE, PAWN);
        b = board.getChessBoard();
    }
    
    @Test
    public void whenMovingNoDuplicates() {
        board.AttemptToPlacePieceOnBoard(pawn, 2, 2);
        board.attemptToMovePieceOnBoard(2, 2, 5, 5);
        assertEquals(null, b[2][2]);
    }
    
    @Test
    public void whenMovingPieceMoves() {
        board.AttemptToPlacePieceOnBoard(pawn, 2, 2);
        board.attemptToMovePieceOnBoard(2, 2, 5, 5);
        assertEquals(pawn, b[5][5]);
    }
    
    @Test
    public void pieceChangesToMovedWhenMoved() {
        board.AttemptToPlacePieceOnBoard(pawn, 2, 2);
        board.attemptToMovePieceOnBoard(2, 2, 5, 5);
        assertEquals(true, pawn.hasMoved());
    }
    
    @Test
    public void cannotMovePieceFromOutsideBoardBooleanTest() {
        assertEquals(false, board.attemptToMovePieceOnBoard(-1, -1, 2, 2));
        assertEquals(false, board.attemptToMovePieceOnBoard(8, 8, 2, 2));
    }
    
    @Test
    public void cannotMovePieceOutsideBoardBooleanTest() {
        assertEquals(false, board.attemptToMovePieceOnBoard(2, 2, -1, -1));
        assertEquals(false, board.attemptToMovePieceOnBoard(2, 2, 8, 8));
    }
}
