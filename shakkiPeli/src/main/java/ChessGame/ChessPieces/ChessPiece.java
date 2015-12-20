/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessGame.ChessPieces;

/**
 *
 * @author Melchan
 */
public class ChessPiece {
    private Color color;
    private boolean moved;
    private PieceType type;
    
    public ChessPiece(Color color, PieceType type) {
        this.color = color;       
        this.type = type;
        this.moved = false;
    }
    
    public Color getColor() {
        return color;
    }
    
    public boolean sameColor(ChessPiece piece) {
        if (piece.getColor() == color) {
            return true;
        }
        return false;
    }
    
    public PieceType getPieceType() {
        return type;
    }
    
    public void move() {
        this.moved = true;
    }
    
    public boolean hasMoved() {
        return moved;
    }
    
    
}
