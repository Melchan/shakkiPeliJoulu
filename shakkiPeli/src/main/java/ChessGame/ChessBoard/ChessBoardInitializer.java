/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessGame.ChessBoard;

import ChessGame.ChessPieces.ChessPiece;
import ChessGame.ChessPieces.Color;
import static ChessGame.ChessPieces.Color.BLACK;
import static ChessGame.ChessPieces.Color.WHITE;
import ChessGame.ChessPieces.PieceType;
import static ChessGame.ChessPieces.PieceType.BISHOP;
import static ChessGame.ChessPieces.PieceType.KING;
import static ChessGame.ChessPieces.PieceType.KNIGHT;
import static ChessGame.ChessPieces.PieceType.PAWN;
import static ChessGame.ChessPieces.PieceType.QUEEN;
import static ChessGame.ChessPieces.PieceType.ROOK;
import java.util.HashMap;

/**
 *
 * @author Melchan
 */
public class ChessBoardInitializer {

    private ChessBoard board;
    private HashMap<ChessPiece, ChessPiece> chessPieces;
    private final Color[] colors = {BLACK, WHITE};
    private final PieceType[] types = {ROOK, KNIGHT, BISHOP};

    public ChessBoardInitializer(ChessBoard board) {
        this.board = board;
        this.chessPieces = new HashMap<ChessPiece, ChessPiece>();
        placeAllPieces();
    }
    
    //Every piece type could have their own method, but it would be copy-paste.
    
    private void placeAllPieces() {
        for (int i = 0; i < colors.length; i++) {
            placePawns(colors[i]);
            int y = giveRow(colors[i]);
            placeKingAndQueen(colors[i], y);
            for (int n = 0; n < types.length; n++) {
                placeLieutenants(colors[i],types[i] ,y, n);
            }
        }
    }

    private void placePawns(Color color) {
        int y = 1;
        if (color == BLACK) {
            y = 6;
        }
        for (int x = 0; x < 8; x++) {
            placePiece(color, PAWN, x, y);
        }
    }

    private void placeLieutenants(Color color, PieceType type, int y, int modifier) {
        placePiece(color, type, 0 + modifier, y);
        placePiece(color, type, 7 - modifier, y);      
    }

    private void placeKingAndQueen(Color color, int y) {
        int x = 4;
        if (color == BLACK) {
            placePiece(color, KING, x, y);
            placePiece(color, QUEEN, x + 1, y);
        }
        placePiece(color, QUEEN, x, y);
        placePiece(color, KING, x + 1, y);
        
    }

    private void placePiece(Color color, PieceType type, int x, int y) {
        ChessPiece piece = new ChessPiece(color, type, x, y);
        board.placePieceOnBoard(piece, x, y);
        chessPieces.put(piece, piece);
    }
    
    private int giveRow(Color color) {
        if (color == BLACK) {
            return 7;
        }
        return 0;
    }
}
