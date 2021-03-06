/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.ChessBoard;

import fi.henri.ChessGame.ChessPieces.ChessPiece;
import fi.henri.ChessGame.ChessPieces.ChessColor;
import static fi.henri.ChessGame.ChessPieces.ChessColor.*;
import fi.henri.ChessGame.ChessPieces.PieceType;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;

/**
 *
 *
 * @author Melchan
 */
public class ChessBoardInitializer {

    private ChessBoard board;
    private final ChessColor[] colors = {BLACK, WHITE};
    private final PieceType[] types = {ROOK, KNIGHT, BISHOP};

    /**
     * creating new ChessBoardInitializer just puts every piece on their
     * starting position.
     *
     * @param board
     */
    public ChessBoardInitializer(ChessBoard board) {
        this.board = board;
        board.initialize();
        placeAllPieces();
    }

    private void placeAllPieces() {
        for (int i = 0; i < colors.length; i++) {
            placePawns(colors[i]);
            int y = giveRow(colors[i]);
            placeKingAndQueen(colors[i], y);
            for (int n = 0; n < 3; n++) {
                placeLieutenants(colors[i], types[n], y, n);
            }
        }
    }

    private void placePawns(ChessColor color) {
        int y = 1;
        if (color == BLACK) {
            y = 6;
        }
        for (int x = 0; x < 8; x++) {
            placePiece(color, PAWN, x, y);
        }
    }

    private void placeLieutenants(ChessColor color, PieceType type, int y, int modifier) {
        placePiece(color, type, 0 + modifier, y);
        placePiece(color, type, 7 - modifier, y);
    }

    private void placeKingAndQueen(ChessColor color, int y) {
        int x = 3;
        placePiece(color, KING, x + 1, y);
        placePiece(color, QUEEN, x, y);

    }

    private void placePiece(ChessColor color, PieceType type, int x, int y) {
        ChessPiece piece = new ChessPiece(color, type);
        board.attemptToPlacePieceOnBoard(piece, x, y);
    }

    private int giveRow(ChessColor color) {
        if (color == BLACK) {
            return 7;
        }
        return 0;
    }
}
