/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.ChessBoard;
import fi.henriChessGame.ChessBoard.ChessBoard;
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
public class SettingChessPiecesOnBoardTest {
    ChessBoard board;
    ChessPiece piece;
    ChessPiece[][] b;
    
    @Before
    public void setUp() {
        board = new ChessBoard();
        piece = new ChessPiece(WHITE, PAWN);
        b = board.getChessBoard();
    }
    
    @Test
    public void setPieceToAllowedSquareBooleanTest() {
        assertEquals(true, board.AttemptToPlacePieceOnBoard(piece, 2, 3));
    }
    
    @Test
    public void setNullPieceToAllowedSquareBooleanTest() {
        assertEquals(false, board.AttemptToPlacePieceOnBoard(null, 3, 3));
    }
    
    @Test
    public void setPieceToNegativeRowBooleanTest() {
        assertEquals(false, board.AttemptToPlacePieceOnBoard(piece, 0, -1));
    }
    
    @Test
    public void setPieceToNegativeColumnBooleanTest() {
        assertEquals(false, board.AttemptToPlacePieceOnBoard(piece, -1, 0));
    }
    
    @Test 
    public void setPieceColumnTooLargeValueBooleanTest() {
        assertEquals(false, board.AttemptToPlacePieceOnBoard(piece, 8, 7));
    }
    
    @Test
    public void SetPieceRowTooLargeValueBooleanTest() {
        assertEquals(false, board.AttemptToPlacePieceOnBoard(piece, 7, 8));
    }
    
    @Test
    public void setPieceOnBoard() {
        board.AttemptToPlacePieceOnBoard(piece, 3, 2);
        assertEquals(b[3][2], piece);
    }
    
    @Test
    public void setPieceOnOtherPiece() {
        ChessPiece pawn = new ChessPiece(WHITE, PAWN);
        board.AttemptToPlacePieceOnBoard(piece, 3, 2);
        board.AttemptToPlacePieceOnBoard(pawn, 3, 2);
        assertEquals(pawn, b[3][2]);
    }
    
    @Test
    public void whenPlacingPieceOnBoardItWillStartAsUnMoved() {
        board.AttemptToPlacePieceOnBoard(piece, 2, 3);
        assertEquals(false, piece.hasMoved());
    }
}
