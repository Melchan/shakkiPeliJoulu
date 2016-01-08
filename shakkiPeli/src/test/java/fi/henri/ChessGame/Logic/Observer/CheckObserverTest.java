/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Logic.Observer;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import static fi.henri.ChessGame.ChessPieces.ChessColor.*;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import fi.henri.ChessGame.Logic.Observers.CheckObserver;
import fi.henri.ChessGame.Logic.Pieces.MoveLibrary;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author manhenri
 */
public class CheckObserverTest {
    private CheckObserver observer;
    private MoveLibrary library;
    private ChessBoard board;
    private ChessPiece bKing;
    private ChessPiece wKing;
    private ChessPiece bQueen;
    private ChessPiece wQueen;

    @Before

    public void setUp() {
        this.board = new ChessBoard();
        this.library = new MoveLibrary(board);
        this.observer = new CheckObserver(board);
        this.bKing = new ChessPiece(BLACK, KING);
        this.wKing = new ChessPiece(WHITE, KING);
        this.bQueen = new ChessPiece(BLACK, QUEEN);
        this.wQueen = new ChessPiece(WHITE, QUEEN);
    }

    
    @Test
    public void canDetectIfKingIsThreatened() {
        board.attemptToPlacePieceOnBoard(bKing, 0, 4);
        board.attemptToPlacePieceOnBoard(wQueen, 0, 0);
        assertEquals(false, observer.movePieceIfLegal(BLACK, 0, 4, 0, 1));
    }
}
