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
    private final Color color;
    private boolean moved;
    private final PieceType type;
    private int x;
    private int y;
    
    public ChessPiece(Color color, PieceType type, int x, int y) {
        this.color = color;       
        this.type = type;
        this.moved = false;
        this.x = x;
        this.y = y;
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
    
    public void move(int x, int y) {
        moved = true;
        this.x = x;
        this.y = y;
    }
    
    public boolean hasMoved() {
        return moved;
    }
    
    public int getColumn() {
        return x;
    }
    
    public int getRow() {
        return y;
    }
    
    
    
}
