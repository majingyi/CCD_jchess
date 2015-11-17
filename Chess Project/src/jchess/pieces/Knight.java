package jchess.pieces;

import jchess.board.Chessboard;
import jchess.util.Player;

/**
 * Class to represent a chess knight
 */
public class Knight extends Piece {

	public static final String	SYMBOL	= "Knight"; //$NON-NLS-1$

	public Knight(Chessboard chessboard, Player player) throws Exception {
		super(chessboard, player, SYMBOL);
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new KnightMoveBehavior(player, chessboard, square);
	}
}