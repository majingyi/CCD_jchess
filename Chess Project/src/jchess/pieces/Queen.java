package jchess.pieces;

import java.awt.Image;

import jchess.board.Chessboard;
import jchess.ui.GUI;
import jchess.util.Player;

/**
 * Class to represent a queen piece Queen can move almost in every way.
 * 
 * The only move not allowed is the move of the bishop
 */
public class Queen extends Piece {

	public static short						value				= 9;
	protected static final Image	imageWhite	= GUI.loadImage("Queen-W.png");
	protected static final Image	imageBlack	= GUI.loadImage("Queen-B.png");

	public Queen(Chessboard chessboard, Player player) {
		super(chessboard, player);
		this.symbol = "Q";
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

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new QueenMoveBehavior(player, chessboard, square);
	}
}