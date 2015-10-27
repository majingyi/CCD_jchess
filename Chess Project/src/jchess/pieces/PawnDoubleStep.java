package jchess.pieces;

import jchess.Player;
import jchess.ui.Chessboard;

public class PawnDoubleStep extends Pawn {

	public PawnDoubleStep(Chessboard chessboard, Player player) {
		super(chessboard, player);
	}

	@Override
	protected boolean isDoubleMoveAllowed() {
		// we always allow 2 square moves for pawns
		return true;
	}
}
