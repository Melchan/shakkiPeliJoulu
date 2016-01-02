/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.UI;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author manhenri
 */
public class UI implements Runnable{
    
    public UI () {
        
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("ChessGame");
        
        frame.setPreferredSize(new Dimension(900, 600));
        frame.setResizable(false);
       
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        createComponents(frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public void createComponents(Container container) {
        
    }
    
}
