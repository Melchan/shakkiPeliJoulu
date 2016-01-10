/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.UI;

import fi.henri.ChessGame.Logic.LogicHandler;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 *
 * @author manhenri
 */
public class ChoosingWindow implements Runnable {

    private LogicHandler handler;
    private JFrame frame;

    public ChoosingWindow(LogicHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        frame = new JFrame("Choose your promotion");
        frame.setLayout(new GridLayout(2, 2));
        frame.setPreferredSize(new Dimension(200, 200));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        this.addBishopButton(container);
        this.addKnightButton(container);
        this.addQueenButton(container);
        this.addRookButton(container);
    }

    private void addKnightButton(Container container) {
        JLabel knightLabel = new JLabel();
        knightLabel.setText("knight");
        JButton knightButton = new JButton();
        knightButton.setText("Knight");
        knightButton.addActionListener(new ClickListener(handler, knightLabel));
        container.add(knightButton);

    }

    private void addRookButton(Container container) {
        JLabel rookLabel = new JLabel();
        rookLabel.setText("rook");

        JButton rookButton = new JButton();
        rookButton.setText("Rook");

        rookButton.addActionListener(new ClickListener(handler, rookLabel));

        container.add(rookButton);
    }

    private void addBishopButton(Container container) {
        JLabel bishopLabel = new JLabel();
        bishopLabel.setText("bishop");
        JButton bishopButton = new JButton();
        bishopButton.setText("Bishop");
        bishopButton.addActionListener(new ClickListener(handler, bishopLabel));
        container.add(bishopButton);
    }

    private void addQueenButton(Container container) {
        JLabel queenLabel = new JLabel();
        queenLabel.setText("queen");
        JButton queenButton = new JButton();
        queenButton.setText("Queen");
        queenButton.addActionListener(new ClickListener(handler, queenLabel));
        container.add(queenButton);

    }
}
