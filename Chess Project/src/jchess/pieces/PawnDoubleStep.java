package jchess.pieces;

import jchess.board.Chessboard;
import jchess.util.Player;

public class PawnDoubleStep extends Pawn {

	public PawnDoubleStep(Chessboard chessboard, Player player) {
		super(chessboard, player);
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new PawnDoubleStepMoveBehavior(player, chessboard, square);
	}
}