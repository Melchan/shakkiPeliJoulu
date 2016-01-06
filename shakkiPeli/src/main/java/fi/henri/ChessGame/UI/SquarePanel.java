/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.UI;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import java.awt.Color;
import static java.awt.Color.GRAY;
import static java.awt.Color.WHITE;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;

/**
 *
 * @author manhenri
 */
public class SquarePanel extends JLayeredPane implements MouseListener {

    private Color color = WHITE;
    private static boolean isBlack = false;
    private ChessPiecePicture picture;
    private ChessBoard board;
    private int panel;
    private Updatetable updater;

    public SquarePanel(ChessBoard board, int panel, Updatetable updater) {
        this.board = board;
        this.panel = panel;
        this.updater = updater;
        this.setPreferredSize(new Dimension(80, 80));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        paneNumberToRightColor(panel);
        this.setOpaque(true);
        setPicture();
        
        this.add(picture);
        this.addMouseListener(picture);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color);
        g.fillRect(0, 0, 80, 80);
    }
    
    private void setPicture() {
        picture = new ChessPiecePicture(board, panel, updater);
    }

    private void paneNumberToRightColor(int n) {
        int x = n % 8;

        setRightColorForCoordinate(x);
    }

    private void setRightColorForCoordinate(int x) {
        if (isBlack) {
            this.color = GRAY;
            this.isBlack = false;
        } else {
            this.color = WHITE;
            this.isBlack = true;
        }
        if (x == 7) {
            isBlack = !isBlack;
        }
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
