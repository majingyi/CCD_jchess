package jchess.pieces;

import jchess.board.Chessboard;
import jchess.util.Player;
import jchess.util.Square;

public class PawnDoubleStepMoveBehavior extends PawnMoveBehavior {

	public PawnDoubleStepMoveBehavior(Player player, Chessboard chessboard, Square square) {
		super(player, chessboard, square);
	}

	/**
	 * 
	 * Check if pawn is allowed to move to squares at once.
	 * 
	 * Normally this is allowed for the first time of the current pawn only.
	 * 
	 * @return true, if pawn is allowed to move two squares at once.
	 */
	@Override
	protected boolean isDoubleMoveAllowed() {
		// we always allow 2 square moves for pawns
		return true;
	}
}