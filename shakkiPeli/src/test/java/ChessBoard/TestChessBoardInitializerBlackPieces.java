/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessBoard;

import ChessGame.ChessBoard.ChessBoard;
import ChessGame.ChessBoard.ChessBoardInitializer;
import ChessGame.ChessPieces.ChessPiece;
import static ChessGame.ChessPieces.Color.BLACK;
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
public class TestChessBoardInitializerBlackPieces {
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
        assertEquals(PAWN, b[x][6].getPieceType());
        assertEquals(BLACK, b[x][6].getColor());
        }
        
    }
    
    @Test
    public void rooksLeftInPlace() {
        for(int i = 0; i < 2; i++) {
            assertEquals(ROOK, b[0][7].getPieceType());
            assertEquals(BLACK, b[0][7].getColor());
        }
    }
    
    @Test
    public void knightLeftInPlace() {
            assertEquals(KNIGHT, b[1][7].getPieceType());
            assertEquals(BLACK, b[1][7].getColor());
    }
    
    @Test
    public void bishopLeftInPlace() {
            assertEquals(BISHOP, b[2][7].getPieceType());
            assertEquals(BLACK, b[2][7].getColor());
    }
    
    @Test
    public void rooksRightInPlace() {
            assertEquals(ROOK, b[7][7].getPieceType());
            assertEquals(BLACK, b[7][7].getColor());
    }
    
    @Test
    public void knightRightInPlace() {
            assertEquals(KNIGHT, b[6][7].getPieceType());
            assertEquals(BLACK, b[6][7].getColor());
    }
    
    @Test
    public void bishopRightInPlace() {
            assertEquals(BISHOP, b[5][7].getPieceType());
            assertEquals(BLACK, b[5][7].getColor());
    }
    
    @Test
    public void QueensInPlace() {
        assertEquals(QUEEN, b[3][7].getPieceType());
        assertEquals(BLACK, b[3][7].getColor());
    }
    
    @Test 
    public void KingsInPlace() {
        assertEquals(KING, b[4][7].getPieceType());
        assertEquals(BLACK, b[4][7].getColor());
    }
}

