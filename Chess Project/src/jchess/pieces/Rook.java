package jchess.pieces;

import jchess.board.Chessboard;
import jchess.core.Theme;
import jchess.util.Player;

/**
 * Class to represent a chess pawn rook.
 * 
 * A rook move horizontal and vertical no restrictions on the distance.
 * 
 */
public class Rook extends Piece {

	public static final String	SYMBOL		= "Rook";

	public boolean							wasMotion	= false;

	public Rook(Chessboard chessboard, Player player) throws Exception {
		super(chessboard, player, SYMBOL);
		setImage(Theme.getImageForPiece(player.color, SYMBOL));
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new RookMoveBehavior(player, chessboard, square);
	}
}