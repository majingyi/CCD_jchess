package jchess.pieces;

import jchess.board.Chessboard;
import jchess.util.Player;

/**
 * Class to represent a chess pawn bishop
 */
public class Bishop extends Piece {

	public static final String	SYMBOL	= "Bishop";

	public Bishop(Chessboard chessboard, Player player) throws Exception {
		super(chessboard, player, SYMBOL);
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new BishopMoveBehavior(player, chessboard, square);
	}
}
