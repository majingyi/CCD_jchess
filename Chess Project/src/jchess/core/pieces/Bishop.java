package jchess.core.pieces;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Player;

/**
 * Class to represent a chess pawn bishop
 */
public class Bishop extends Piece {

	public static final String	SYMBOL	= "Bishop"; //$NON-NLS-1$

	public Bishop(Chessboard chessboard, Player player, ChessboardField field) throws Exception {
		super(chessboard, player, SYMBOL, field);
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new BishopMoveBehavior(getPlayer(), getChessboard(), getField());
	}
}
