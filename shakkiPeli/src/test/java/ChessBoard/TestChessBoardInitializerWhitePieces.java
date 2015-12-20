/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessBoard;

import ChessGame.ChessBoard.ChessBoard;
import ChessGame.ChessBoard.ChessBoardInitializer;
import ChessGame.ChessPieces.ChessPiece;
import static ChessGame.ChessPieces.Color.WHITE;
import static ChessGame.ChessPieces.PieceType.BISHOP;
import static ChessGame.ChessPieces.PieceType.KING;
import static ChessGame.ChessPieces.PieceType.KNIGHT;
import static ChessGame.ChessPieces.PieceType.PAWN;
import static ChessGame.ChessPieces.PieceType.QUEEN;
import static ChessGame.ChessPieces.PieceType.ROOK;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Melchan
 */
public class TestChessBoardInitializerWhitePieces {
    private ChessBoard board;
    private ChessPiece[][] b;
    
    @Before
    public void setUp() {
        board = new ChessBoard();
        new ChessBoardInitializer(board);
        b = board.getChessBoard();
    }
    
    @Test
    public void pawnsInPlace() {
        for (int x = 0; x < 8; x++) {
        assertEquals(PAWN, b[x][1].getPieceType());
        assertEquals(WHITE, b[x][1].getColor());
        }
        
    }
    
    @Test
    public void rooksLeftInPlace() {
            assertEquals(ROOK, b[0][0].getPieceType());
            assertEquals(WHITE, b[0][0].getColor());
    }
    
    @Test
    public void knightLeftInPlace() {
            assertEquals(KNIGHT, b[1][0].getPieceType());
            assertEquals(WHITE, b[1][0].getColor());
    }
    
    @Test
    public void bishopLeftInPlace() {
            assertEquals(BISHOP, b[2][0].getPieceType());
            assertEquals(WHITE, b[2][0].getColor());
    }
    
    @Test
    public void rooksRightInPlace() {
            assertEquals(ROOK, b[7][0].getPieceType());
            assertEquals(WHITE, b[7][0].getColor());
    }
    
    @Test
    public void knightRightInPlace() {
            assertEquals(KNIGHT, b[6][0].getPieceType());
            assertEquals(WHITE, b[6][0].getColor());
    }
    
    @Test
    public void bishopRightInPlace() {
            assertEquals(BISHOP, b[5][0].getPieceType());
            assertEquals(WHITE, b[5][0].getColor());
    }
    
    @Test
    public void QueensInPlace() {
        assertEquals(QUEEN, b[3][0].getPieceType());
        assertEquals(WHITE, b[3][0].getColor());
    }
    
    @Test 
    public void KingsInPlace() {
        assertEquals(KING, b[4][0].getPieceType());
        assertEquals(WHITE, b[4][0].getColor());
    }
}
