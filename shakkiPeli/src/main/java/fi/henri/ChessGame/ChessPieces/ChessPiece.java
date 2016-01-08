/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.ChessPieces;

/**
 * basic chess piece and it's information.
 * @author Melchan
 */
public class ChessPiece {

    private final ChessColor color;
    private boolean moved;
    private final PieceType type;
    private String id;
    
    /**
     * Constructor for test use.
     * @param color
     * @param type 
     */

    public ChessPiece(ChessColor color, PieceType type) {
        this.color = color;
        this.type = type;
        this.moved = false;
    }
    
    /**
     * Constructor to be used in game.
     * @param color
     * @param type
     * @param id 
     */
    public ChessPiece(ChessColor color, PieceType type, String id) {
        this(color, type);
        this.id = id;
    }

    public ChessColor getColor() {
        return color;
    }

    /**
     * method will tell if two pieces are same color
     * @param piece which this piece is compared to.
     * @return boolean.
     */
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
        moved = true;
    }

    public boolean hasMoved() {
        return moved;
    }
    
    @Override
    public String toString() {
        return id;
    }
}
