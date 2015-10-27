package jchess.pieces;

import jchess.ui.Chessboard;
import jchess.util.Player;

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
