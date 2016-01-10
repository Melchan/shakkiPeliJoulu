 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Logic;

import fi.henri.ChessGame.ChessBoard.*;
import fi.henri.ChessGame.ChessPieces.*;
import static fi.henri.ChessGame.ChessPieces.ChessColor.*;
import static fi.henri.ChessGame.ChessPieces.PieceType.PAWN;
import fi.henri.ChessGame.Logic.RuleJudges.CheckMateObserver;
import fi.henri.ChessGame.Logic.RuleJudges.MoveHandler;
import java.util.ArrayList;

/**
 * Class that will handle logic of the game. pawnX tells x-axis for pawn needing
 * promotion. pawnY tells y-axis for pawn needing promotion.
 *
 * @author Melchan
 */
public class LogicHandler {

    private ChessBoard board;
    private ChessColor turn;
    private boolean checkMate;
    private MoveHandler mHandler;
    private CheckMateObserver CMObserver;
    private ArrayList<Integer> kingThreateners;
    private boolean pawnGotInOtherEndWithLastMove;
    private int pawnX;
    private int pawnY;

    /**
     * Will over see basic logic actions when game is running.
     *
     * @param board
     */
    public LogicHandler(ChessBoard board) {
        this.board = board;
        this.turn = WHITE;
        this.checkMate = false;
        this.pawnGotInOtherEndWithLastMove = false;
        this.mHandler = new MoveHandler(board);
        this.CMObserver = new CheckMateObserver(board, mHandler);
        this.kingThreateners = new ArrayList<Integer>();
    }

    public ChessBoard getChessBoard() {
        return board;
    }

    public void newGame() {
        new ChessBoardInitializer(board);
        this.turn = WHITE;
    }

    public void setTurn(ChessColor color) {
        this.turn = color;
    }

    /**
     * method will move piece if it is legal for chess piece in question and
     * change turn. If CheckMate is Noticed It will not take more commands and
     * it will determine after every turn if game is in checkMate.
     *
     * @param a starting x axis.
     * @param b starting y axis.
     * @param toA end point x axis.
     * @param toB end point y axis.
     * @return true if action happened.
     */
    public boolean movePiece(int a, int b, int toA, int toB) {
        pawnGotInOtherEndWithLastMove = false;
        if (!checkMate) {
            if (mHandler.movePieceIfLegal(turn, a, b, toA, toB)) {
                changeTurn();
                validateCheckMate();
                if (checkMate) {
                    changeTurn();
                }
                pawnPromotionHandler(toA, toB);
                return true;
            }
            kingThreateners = mHandler.getThreateners();
        }
        return false;
    }

    public boolean getPawnPromotionIndicator() {
        return this.pawnGotInOtherEndWithLastMove;
    }

    public ChessColor getTurn() {
        return turn;
    }

    public boolean getIsCheckMate() {
        return checkMate;
    }

    private void changeTurn() {
        if (turn == WHITE) {
            turn = BLACK;
        } else if (turn == BLACK) {
            turn = WHITE;
        }
    }

    public void pawnPromotion(PieceType type) {
        if (this.pawnGotInOtherEndWithLastMove) {
            ChessPiece promotee = board.getChessBoard()[pawnX][pawnY];
            promotee.setChessPieceType(type);
        }
    }

    private void validateCheckMate() {
        this.checkMate = CMObserver.isKingInCheckMate(turn);
    }

    public ArrayList<Integer> getKingThreateners() {
        return kingThreateners;
    }

    private void pawnPromotionHandler(int toA, int toB) {
        if (toB == 0 || toB == 7) {
            if (board.getChessBoard()[toA][toB].getPieceType() == PAWN) {
                this.pawnX = toA;
                this.pawnY = toB;
                this.pawnGotInOtherEndWithLastMove = true;
            }
        }
    }
}
