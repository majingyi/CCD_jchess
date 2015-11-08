package jchess.pieces;

import java.awt.Image;

import jchess.board.Chessboard;
import jchess.ui.GUI;
import jchess.util.Player;

/**
 * Class to represent a pawn piece.
 * 
 * Pawn can move only forward and can beat only diagonal.
 * 
 * On first move pawn can move 2 squares.
 * 
 * A pawn can be upgraded to rook, knight, bishop, Queen if these pieces of the
 * opponent are already captured.
 */
public class Pawn extends Piece {

	boolean												down;
	protected static final Image	imageWhite	= GUI.loadImage("Pawn-W.png");
	protected static final Image	imageBlack	= GUI.loadImage("Pawn-B.png");
	public static short						value				= 1;

	Pawn(Chessboard chessboard, Player player) {
		super(chessboard, player);
		// this.setImages("Pawn-W.png", "Pawn-B.png");
		this.symbol = "";
		this.setImage();
	}

	@Override
	void setImage() {
		if (this.player.color == Player.colors.black) {
			image = imageBlack;
		} else {
			image = imageWhite;
		}
		orgImage = image;
	}

	void promote(Piece newPiece) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new PawnMoveBehavior(player, chessboard, square);
	}
}
