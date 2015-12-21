/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.ChessBoard;

import fi.henri.ChessGame.ChessPieces.ChessPiece;
import java.util.HashSet;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Melchan
 */
public class BoardInitializerArePiecesUniqueTest {
    private ChessBoard board;
    private ChessPiece[][] b;
    private HashSet<ChessPiece> pieces;
    private int[] row;
    
    @Before
    public void setUp() {
        board = new ChessBoard();
        new ChessBoardInitializer(board);
        b = board.getChessBoard();
        pieces = new HashSet<ChessPiece>();
        row = new int[2];
        row[1] = 7;
    }
    
    @Test
    public void allPawnsAreUnique() {
        int[] r = {1, 6};
        for (int x = 0; x < 8; x++) {
            for (int i = 0; i < 2; i++) {
                ChessPiece piece = b[x][r[i]];
                assertEquals(false, pieces.contains(piece));
                pieces.add(piece);
            }
        }
    }
    
    @Test
    public void allRooksAreUnique() {
        int[] c = {0, 7};
        for (int i = 0; i < 2; i++) {
            for (int n = 0; n < 2; n++) {
                ChessPiece piece = b[c[i]][row[n]];
                assertEquals(false, pieces.contains(piece));
                pieces.add(piece);
            }
        }
    }
    
    @Test
    public void allKnightssAreUnique() {
        int[] c = {1, 6};
        for (int i = 0; i < 2; i++) {
            for (int n = 0; n < 2; n++) {
                ChessPiece piece = b[c[i]][row[n]];
                assertEquals(false, pieces.contains(piece));
                pieces.add(piece);
            }
        }
    }
    
    @Test
    public void allBishopssAreUnique() {
        int[] c = {2, 5};
        for (int i = 0; i < 2; i++) {
            for (int n = 0; n < 2; n++) {
                ChessPiece piece = b[c[i]][row[n]];
                assertEquals(false, pieces.contains(piece));
                pieces.add(piece);
            }
        }
    }
    /**Because there is only one instance of king and queen on both sides
    and we check if there is black and white one We know they are unique
     */
}
