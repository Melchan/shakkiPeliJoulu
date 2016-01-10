/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.UI;

import fi.henri.ChessGame.ChessBoard.ChessBoard;
import static fi.henri.ChessGame.ChessPieces.ChessColor.*;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import fi.henri.ChessGame.ChessPieces.PieceType;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import fi.henri.ChessGame.Logic.LogicHandler;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;

/**
 * Top most layer where pieces are drawn. Also layer where mouse is listened
 * from this component.
 *
 * @author manhenri
 */
public class ChessPiecePicture extends JLayeredPane implements MouseListener {

    private final HashMap<PieceType, String> blackPictures;
    private final HashMap<PieceType, String> whitePictures;
    private BufferedImage image;
    private ChessBoard board;
    private int paneNumber;
    private Updatetable updater;

    public ChessPiecePicture(LogicHandler handler, int paneNumber, Updatetable updater) {
        this.board = handler.getChessBoard();
        this.updater = updater;
        this.paneNumber = paneNumber;
        this.blackPictures = new HashMap<>();
        this.whitePictures = new HashMap<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        image = null;
        setImage(paneNumber);
        super.paintComponent(g);

        g.drawImage(image, 0, 0, null);      
    }

    private void setImage(int paneNumber) {
        ChessPiece piece = paneNumberToChessBoardSquareContent(paneNumber);
        String imagePath = findImagePath(piece);
        if (imagePath != null) {
            try {
                image = ImageIO.read(new File(imagePath));
            } catch (IOException ex) {
                System.out.println("Chess Piece image was not found.");
            }
        }
    }

    private String findImagePath(ChessPiece piece) {
        if (piece == null) {
            return null;
        } else if (piece.getColor() == BLACK) {
            initializeBlackImageLibrary();
            return blackPictures.get(piece.getPieceType());
        } else {
            initializeWhiteImageLibrary();
            return whitePictures.get(piece.getPieceType());
        }
    }

    private ChessPiece paneNumberToChessBoardSquareContent(int n) {
        ChessPiece[][] chessBoard = board.getChessBoard();
        int y = 7 - n / 8;
        int x = n % 8;
        return chessBoard[x][y];
    }

    private void initializeBlackImageLibrary() {
        blackPictures.put(KING, "/resources/blackking.png");
        blackPictures.put(QUEEN, "/resources/blackqueen.png");
        blackPictures.put(BISHOP, "/resources/blackbishop.png");
        blackPictures.put(KNIGHT, "/resources/blackknight.png");
        blackPictures.put(ROOK, "/resources/blackrook.png");
        blackPictures.put(PAWN, "/resources/blackpawn.png");
    }

    private void initializeWhiteImageLibrary() {
        whitePictures.put(KING, "/resources/whiteking.png");
        whitePictures.put(QUEEN, "/resources/whitequeen.png");
        whitePictures.put(BISHOP, "/resources/whitebishop.png");
        whitePictures.put(KNIGHT, "/resources/whiteknight.png");
        whitePictures.put(ROOK, "/resources/whiterook.png");
        whitePictures.put(PAWN, "/resources/whitepawn.png");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        updater.update(paneNumber);
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
