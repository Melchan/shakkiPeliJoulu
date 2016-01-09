/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.UI;

import fi.henri.ChessGame.Logic.LogicHandler;
import java.awt.Color;
import static java.awt.Color.GRAY;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;

/**
 * Invidual chess board square. Where layer where pieces rest are placed
 * invidually.
 *
 * @author manhenri
 */
public class SquarePanel extends JLayeredPane implements MouseListener {

    private Color BackUpColor;
    private Color color = WHITE;
    private static boolean isBlack = false;
    private final ChessPiecePicture picture;
    private final int panel;
    private final ChessBoardContent chessBoardContent;

    public SquarePanel(LogicHandler handler, int panel, ChessBoardContent chessBoardContent) {
        this.panel = panel;
        this.chessBoardContent = chessBoardContent;
        this.setPreferredSize(new Dimension(80, 80));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        picture = new ChessPiecePicture(handler, panel, chessBoardContent);
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
        if (hasBeenChosen()) {
            this.color = GREEN;

        } else if (isThreateningKing()) {
            this.color = RED;

        } else {
            color = BackUpColor;
        }
    }

    private void paneNumberToRightColor() {

        int x = panel % 8;
        setRightColorForCoordinate(x);

    }

    private boolean isThreateningKing() {
        ArrayList<Integer> threats = chessBoardContent.getKingThreateners();
        if (threats != null) {
            for (int i : threats) {
                if (i == this.panel) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasBeenChosen() {
        if (chessBoardContent.getFirstPane() != null) {
            if (chessBoardContent.getFirstPane() == this.panel) {
                return true;
            }
        }
        return false;
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
