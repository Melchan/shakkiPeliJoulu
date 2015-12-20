/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessPieces;

import ChessGame.ChessPieces.ChessPiece;
import static ChessGame.ChessPieces.Color.BLACK;
import static ChessGame.ChessPieces.Color.WHITE;
import static ChessGame.ChessPieces.PieceType.PAWN;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Melchan
 */
public class TestChessPiece {
    ChessPiece one;
    ChessPiece two;
    
    @Before
    public void setUp() {
        one = new ChessPiece(WHITE, PAWN);
    }
    
    @Test
    public void sameColorComparison() {
        two = new ChessPiece(WHITE, PAWN);

        assertEquals(true, one.sameColor(two));
    }
    
    @Test
    public void differentColorComparison() {
        two = new ChessPiece(BLACK, PAWN);
        
        assertEquals(false, one.equals(two));
    }
    
    @Test
    public void hasNotMovedBeforeMoved(){
        assertEquals(false, one.hasMoved());
    }
    
    @Test
    public void hasMovedWhenMoved() {
        one.move();
        
        assertEquals(true, one.hasMoved());
    }
}
