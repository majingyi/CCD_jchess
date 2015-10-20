package jchess;

public class PawnDoubleStep extends Pawn {

	PawnDoubleStep(Chessboard chessboard, Player player) {
		super(chessboard, player);
	}

	@Override
	protected boolean isDoubleMoveAllowed() {
		// we always allow 2 square moves for pawns
		return true;
	}
}
