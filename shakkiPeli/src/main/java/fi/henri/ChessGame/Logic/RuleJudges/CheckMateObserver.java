/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.Logic.RuleJudges;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import fi.henri.ChessGame.ChessPieces.ChessColor;
import static fi.henri.ChessGame.ChessPieces.PieceType.KNIGHT;
import java.util.ArrayList;

/**
 *
 * @author manhenri
 */
public class CheckMateObserver {

    private final ChessBoard board;
    private ArrayList<Integer> threats;
    private final MoveHandler mHandler;
    
    /**
     * Class to Check if game is in Check Mate
     * @param board
     * @param mHandler 
     */

    public CheckMateObserver(ChessBoard board, MoveHandler mHandler) {
        this.board = board;
        this.mHandler = mHandler;
    }
    
    /**
     * Will see if side whose color is given is in check mate situation.
     * @param color
     * @return 
     */

    public boolean isKingInCheckMate(ChessColor color) {
        if (mHandler.isKingThreatened(color)) {
            this.threats = (ArrayList<Integer>) mHandler.getThreateners().clone();
            if (kingCanNotBeSaved(color)) {
                return true;
            }
        }
        return false;
    }

    private boolean kingCanNotBeSaved(ChessColor color) {

        if (kingCanNotMoveToSafety(color)) {
            if (mHandler.getThreateners().size() > 1) {
                return true;
            } else if (threatenerCanNotBeBlocked(color)) {
                if (threatenerCanNotBeEaten(color)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean kingCanNotMoveToSafety(ChessColor color) {
        int[] king = kingLocation(color);
        int x = king[0];
        int y = king[1];
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (mHandler.movePieceIfLegal(color, x, y, x + i, y + j)) {        
                    mHandler.resetMovement();
                    return false;
                }
            }
        }
        return true;
    }

    private int[] kingLocation(ChessColor color) {
        return board.integerToCoordinate(mHandler.getOwnKingLocation(color));
    }

    private boolean threatenerCanNotBeBlocked(ChessColor color) {
        int[] t = board.integerToCoordinate(threats.get(0));
        int[] k = kingLocation(color);
        
        if (board.getChessBoard()[t[0]][t[1]].getPieceType() != KNIGHT) {
            for (Integer i : blockingPath(t, k)) {
                for (Integer actor : board.getChessPiecesByColor(color).values()) {
                    if (actor != null) {
                        int[] a = board.integerToCoordinate(actor);
                        int[] b = board.integerToCoordinate(i);
                        if (mHandler.movePieceIfLegal(color, a[0], a[1], b[0], b[1])) {
                            mHandler.resetMovement();
                            return false;
                        }
                    } 
                }
            }
        }
        return true;
    }

    private ArrayList<Integer> blockingPath(int[] t, int[] k) {
        ArrayList<Integer> result;
        if (t[0] > k[0]) {
            if (t[1] > k[1]) {
                result = recordCoordinatesOnLine(k[0], k[1], t[0], t[1]);
            } else {
                result = recordCoordinatesOnLine(k[0], t[1], t[0], k[1]);
            }
        } else {
            if (t[1] > k[1]) {
                result = recordCoordinatesOnLine(t[0], k[1], k[0], t[1]);
            } else {
                result = recordCoordinatesOnLine(t[0], t[1], k[0], k[1]);
            }
        }
        return result;
    }

    private ArrayList<Integer> recordCoordinatesOnLine(int x, int y, int toX, int toY) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        while (x != toX || y != toY) {
            result.add(board.coordinateToInteger(x, y));
            if (x != toX) {
                x++;
            }
            if (y != toY) {
                y++;
            }
        }
        return result;
    }

    private boolean threatenerCanNotBeEaten(ChessColor color) {
        int[] t = board.integerToCoordinate(threats.get(0));
        for (Integer actor : board.getChessPiecesByColor(color).values()) {
            if (actor != null) {
                int[] a = board.integerToCoordinate(actor);
                if (mHandler.movePieceIfLegal(color, a[0], a[0], t[0], t[1])) {
                    mHandler.resetMovement();
                    return false;
                }
            }
        }
        return true;
    }
}
