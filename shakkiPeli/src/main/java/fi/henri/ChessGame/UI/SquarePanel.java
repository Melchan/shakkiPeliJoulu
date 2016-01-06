/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.UI;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import java.awt.Color;
import static java.awt.Color.GRAY;
import static java.awt.Color.RED;
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
    
    private Color BackUpColor;
    private Color color = WHITE;
    private static boolean isBlack = false;
    private ChessPiecePicture picture;
    private final int panel;
    private final ChessBoardContent chessBoardContent;

    public SquarePanel(ChessBoard board, int panel, ChessBoardContent chessBoardContent) {
        this.panel = panel;
        this.chessBoardContent = chessBoardContent;
        this.setPreferredSize(new Dimension(80, 80));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        picture = new ChessPiecePicture(board, panel, chessBoardContent);
        paneNumberToRightColor();
        this.add(picture);
        this.addMouseListener(picture);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  
        checkForRightColor();
        g.setColor(color);
        g.fillRect(0, 0, 80, 80);

    }
    
    private void checkForRightColor() {
        if (chessBoardContent.getFirstPane() != null) {
            if (chessBoardContent.getFirstPane() == this.panel) {
                this.color = RED;
            }
        } else {
            color = BackUpColor;
        }
    }

    private void paneNumberToRightColor() {
        if (chessBoardContent.getFirstPane() != null) {
            if (chessBoardContent.getFirstPane() == this.panel) {
                this.color = RED;
            }
        } else {

            int x = panel % 8;

            setRightColorForCoordinate(x);
        }
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
        this.BackUpColor = color;
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
