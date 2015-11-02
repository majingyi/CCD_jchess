package jchess.pieces;

import jchess.ui.ChessboardUI;
import jchess.util.Player;

public class PawnDoubleStep extends Pawn {

	public PawnDoubleStep(ChessboardUI chessboard, Player player) {
		super(chessboard, player);
	}

	@Override
	protected boolean isDoubleMoveAllowed() {
		// we always allow 2 square moves for pawns
		return true;
	}
}
