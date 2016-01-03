/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.henri.ChessGame.UI;

import static fi.henri.ChessGame.ChessPieces.ChessColor.*;
import fi.henri.ChessGame.ChessPieces.ChessPiece;
import fi.henri.ChessGame.ChessPieces.PieceType;
import static fi.henri.ChessGame.ChessPieces.PieceType.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;

/**
 *
 * @author manhenri
 */
public class ChessPiecePicture extends JLayeredPane {

    private final HashMap<PieceType, String> blackPictures;
    private final HashMap<PieceType, String> whitePictures;
    private BufferedImage image;

    public ChessPiecePicture(ChessPiece piece) {
        this.blackPictures = new HashMap<>();
        this.whitePictures = new HashMap<>();
        setImage(piece);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters      
    }

    private void setImage(ChessPiece piece) {
        String imagePath = findImagePath(piece);
        if (imagePath != null) {
            System.out.println("kekek");
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

    private void initializeBlackImageLibrary() {
        blackPictures.put(KING, "src/main/resources/blackking.png");
        blackPictures.put(QUEEN, "src/main/resources/blackqueen.png");
        blackPictures.put(BISHOP, "src/main/resources/blackbishop.png");
        blackPictures.put(KNIGHT, "src/main/resources/blackknight.png");
        blackPictures.put(ROOK, "src/main/resources/blackrook.png");
        blackPictures.put(PAWN, "src/main/resources/blackpawn.png");
    }

    private void initializeWhiteImageLibrary() {
        whitePictures.put(KING, "src/main/resources/whiteking.png");
        whitePictures.put(QUEEN, "src/main/resources/whitequeen.png");
        whitePictures.put(BISHOP, "src/main/resources/whitebishop.png");
        whitePictures.put(KNIGHT, "src/main/resources/whiteknight.png");
        whitePictures.put(ROOK, "src/main/resources/whiterook.png");
        whitePictures.put(PAWN, "src/main/resources/whitepawn.png");
    }
}
