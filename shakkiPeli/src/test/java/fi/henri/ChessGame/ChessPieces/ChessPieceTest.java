/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.ChessPieces;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fi.henri.ChessGame.ChessPieces.ChessPiece;
import static fi.henri.ChessGame.ChessPieces.ChessColor.BLACK;
import static fi.henri.ChessGame.ChessPieces.ChessColor.WHITE;
import static fi.henri.ChessGame.ChessPieces.PieceType.PAWN;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Melchan
 */
public class ChessPieceTest {
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
        
        assertEquals(false, one.sameColor(two));
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