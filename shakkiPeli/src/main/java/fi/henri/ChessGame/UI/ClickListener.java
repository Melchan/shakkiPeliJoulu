/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.UI;

import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import fi.henri.ChessGame.Logic.LogicHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;

/**
 *
 * @author manhenri
 */
public class ClickListener implements ActionListener {

    private LogicHandler handler;
    private JLabel label;

    public ClickListener(LogicHandler handler, JLabel label) {
        this.handler = handler;
        this.label = label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (label.equals("bishop")) {
            handler.pawnPromotion(BISHOP);
        } else if (label.equals("knight")) {
            handler.pawnPromotion(KNIGHT);
        } else if (label.equals("rook")) {
            handler.pawnPromotion(ROOK);
        } else {
            handler.pawnPromotion(QUEEN);
        }
        
    }
}
