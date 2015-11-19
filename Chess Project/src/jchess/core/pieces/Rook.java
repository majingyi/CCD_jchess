package jchess.core.pieces;

import jchess.core.board.Chessboard;
import jchess.core.util.Player;

/**
 * Class to represent a chess pawn rook.
 * 
 * A rook move horizontal and vertical no restrictions on the distance.
 * 
 */
public class Rook extends Piece {

	public static final String	SYMBOL		= "Rook"; //$NON-NLS-1$

	public boolean							wasMotion	= false;

	public Rook(Chessboard chessboard, Player player) throws Exception {
		super(chessboard, player, SYMBOL);
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new RookMoveBehavior(player, chessboard, square);
	}
}