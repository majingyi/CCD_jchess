package jchess.pieces;

import java.awt.Image;

import jchess.board.Chessboard;
import jchess.ui.GUI;
import jchess.util.Player;

/**
 * Class to represent a chess pawn bishop
 */
public class Bishop extends Piece {

	public static short						value				= 3;
	protected static final Image	imageWhite	= GUI.loadImage("Bishop-W.png");
	protected static final Image	imageBlack	= GUI.loadImage("Bishop-B.png");

	public Bishop(Chessboard chessboard, Player player) {
		super(chessboard, player); // call initializer of super type: Piece
		this.symbol = "B";
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
		return new BishopMoveBehavior(player, chessboard, square);
	}
}
