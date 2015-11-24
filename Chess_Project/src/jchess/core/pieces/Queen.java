package jchess.core.pieces;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Player;

/**
 * Class to represent a queen piece Queen can move almost in every way.
 * 
 * The only move not allowed is the move of the bishop
 */
public class Queen extends Piece {

	public static final String	SYMBOL	= "Queen";	//$NON-NLS-1$

	public Queen(Chessboard chessboard, Player player, ChessboardField field) throws Exception {
		super(chessboard, player, SYMBOL, field);
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new QueenMoveBehavior(getPlayer(), getChessboard(), getField());
	}
}