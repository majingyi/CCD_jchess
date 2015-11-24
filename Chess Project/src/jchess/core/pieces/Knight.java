package jchess.core.pieces;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Player;

/**
 * Class to represent a chess knight
 */
public class Knight extends Piece {

	public static final String	SYMBOL	= "Knight"; //$NON-NLS-1$

	public Knight(Chessboard chessboard, Player player, ChessboardField field) throws Exception {
		super(chessboard, player, SYMBOL, field);
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new KnightMoveBehavior(getPlayer(), getChessboard(), getField());
	}
}