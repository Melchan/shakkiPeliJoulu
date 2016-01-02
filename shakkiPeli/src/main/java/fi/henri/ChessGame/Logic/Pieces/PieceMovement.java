/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
    */
    package fi.henri.ChessGame.Logic.Pieces;

    import fi.henri.ChessGame.ChessBoard.ChessBoard;
    import fi.henri.ChessGame.ChessPieces.ChessPiece;

    /**
    * Abstract class for piece movement and rules.
    * @author Melchan
    */
    public abstract class PieceMovement {

    private ChessBoard cB;
    private ChessPiece[][] board;

    public PieceMovement(ChessBoard board) {

        this.cB = board;
        this.board = board.getChessBoard();
    }

    /**
     * method will tell if proposed move is legal for class in question.
     *
     * @param p in start square.
     * @param a starting x axis.
     * @param b starting y axis.
     * @param toA end point x axis.
     * @param toB end point y axis.
     * @return boolean
     */
    abstract public boolean isMoveLegal(ChessPiece p, int a, int b, int toA, int toB);

    /**
     * method will move piece according to class rules.
     *
     * @param p in start square occupying chesspiece.
     * @param a starting x axis.
     * @param b starting y axis.
     * @param toA end point x axis.
     * @param toB end point y axis.
     * @return boolean.
     */
    public boolean commitMoveIfLegal(ChessPiece p, int a, int b, int toA, int toB) {
        if (isMoveLegal(p, a, b, toA, toB)) {
            cB.attemptToMovePieceOnBoard(a, b, toA, toB);
            return true;
        }
        return false;
    }

    /**
     * method will check if the path is clear between two pieces.
     *
     * @param a starting x axis.
     * @param b starting y axis.
     * @param toA end point x axis.
     * @param toB end point y axis.
     * @return boolean.
     */
    public boolean isThePathClear(int a, int b, int toA, int toB) {
        int xChange = change(a, toA);
        int yChange = change(b, toB);
        int x = a + xChange;
        int y = b + yChange;
        while (notEndPoint(toA, toB, x, y)) {
            if (!cB.allowedCoordinates(x, y)) {
                return false;
            }
            if (squareOccupied(x, y)) {
                return false;
            }
            x += xChange;
            y += yChange;
        }
        return true;
    }

    /**
     * method will tell if two pieces are enemies.
     *
     * @param actor is piece currently moving
     * @param target is piece who is checked.
     * @return boolean
     */
    public boolean isThePieceEnemy(ChessPiece actor, ChessPiece target) {
        if (target == null) {
            return true;
        } else if (actor.getColor() == target.getColor()) {
            return false;
        }
        return true;
    }

    /**
     * method will give positive or negative number 1 according to change
     * between two numbers.
     *
     * @param a starting number.
     * @param toA ending number.
     * @return will return 1 if change is between numbers is rising will return
     * -1 if change between numbers is decreasing. will return 0 if there is no
     * change *
     */
    public int change(int a, int toA) {
        if (toA - a > 0) {
            return 1;
        } else if (toA - a < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * will check if proposed move between two coordinates has allowed slope of 1.
     * @param a starting x axis.
     * @param b starting y axis.
     * @param toA end point x axis.
     * @param toB end point y axis.
     * @return boolean.
     */
    public boolean isAllowedSlope(int a, int b, int toA, int toB) {
        if (!cB.allowedCoordinates(a, b) || !cB.allowedCoordinates(toA, toB)) {
            return false;
        } else if (change(a, toA) == 0 || change(b, toB) == 0) {
            return true;
        } else if ((a - toA) / (b - toB) == 1 || (a - toA) / (b - toB) == -1) {
            return true;
        } else {
            return false;
        }
    }

    public ChessPiece[][] getBoard() {
        return board;
    }

    private boolean squareOccupied(int x, int y) {
        if (board[x][y] != null) {
            return true;
        }
        return false;
    }

    private boolean notEndPoint(int toA, int toB, int x, int y) {
        if (toA == x && toB == y) {
            return false;
        }
        return true;
    }
}
