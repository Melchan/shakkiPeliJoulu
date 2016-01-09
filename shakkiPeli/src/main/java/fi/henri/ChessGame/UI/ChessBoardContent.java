/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.UI;

import fi.henri.ChessGame.Logic.LogicHandler;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Main component where every chessboard square is placed in gridlayout.
 *
 * @author manhenri
 */
public class ChessBoardContent extends JPanel implements Updatetable, MouseListener {

    private ArrayList<SquarePanel> squares;
    private ArrayList<Integer> kingThreateners;
    private Integer firstPaneNumber;
    private Integer secondPaneNumber;
    private LogicHandler handler;

    public ChessBoardContent(LogicHandler handler) {
        this.handler = handler;
        this.squares = new ArrayList();
        this.setLayout(new GridLayout(8, 8));
        this.setPreferredSize(new Dimension(640, 640));
        createSquaresInArrayList();
        for (SquarePanel sp : squares) {
            this.add(sp);
            this.addMouseListener(sp);
        }
    }

    private void createSquaresInArrayList() {
        for (int i = 0; i < 64; i++) {
            squares.add(new SquarePanel(handler, i, this));
        }
    }

    public Integer getFirstPane() {
        return this.firstPaneNumber;
    }

    @Override
    public void update(int paneNumber) {
        if (firstPaneNumber == null) {
            firstPaneNumber = paneNumber;
            this.kingThreateners = null;
        } else {
            secondPaneNumber = paneNumber;
            handleLogicAction();
            firstPaneNumber = null;
            secondPaneNumber = null;
            updateKingThreateners();
        }
        refresh();
    }
    
    private void updateKingThreateners() {
        this.kingThreateners = handler.getKingThreateners();
    }
    
    public ArrayList<Integer> getKingThreateners() {
        return kingThreateners;
    }

    private void refresh() {
        this.revalidate();
        this.repaint();

    }

    private void handleLogicAction() {
        int x = getXFromPaneNumber(firstPaneNumber);
        int y = getYFromPaneNumber(firstPaneNumber);
        int toX = getXFromPaneNumber(secondPaneNumber);
        int toY = getYFromPaneNumber(secondPaneNumber);

        if (handler.movePiece(x, y, toX, toY)) {
            refresh();
        }
    }

    private int getXFromPaneNumber(int n) {
        return n % 8;
    }

    private int getYFromPaneNumber(int n) {
        return 7 - n / 8;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
