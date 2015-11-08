package jchess.pieces;

import jchess.board.Chessboard;
import jchess.util.Player;

/**
 * Class to represent a chess rook.
 * 
 * A rook move vertical and horizontal, without any restrictions on the
 * distance.
 * 
 * This special implementation is only allowed to move 5 fields
 */
public class NewRook extends Rook {

	NewRook(Chessboard chessboard, Player player) {
		super(chessboard, player);
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new NewRookMoveBehavior(player, chessboard, square);
	}
}