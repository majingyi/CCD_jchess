package jchess.pieces;

import jchess.board.Chessboard;
import jchess.util.Player;

/**
 * Class to represent a queen piece Queen can move almost in every way.
 * 
 * The only move not allowed is the move of the bishop
 */
public class Queen extends Piece {

	public static final String	SYMBOL	= "Queen"; //$NON-NLS-1$

	public Queen(Chessboard chessboard, Player player) throws Exception {
		super(chessboard, player, SYMBOL);
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new QueenMoveBehavior(player, chessboard, square);
	}
}