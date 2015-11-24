package jchess.core.pieces;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
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

	public Rook(Chessboard chessboard, Player player, ChessboardField field) throws Exception {
		super(chessboard, player, SYMBOL, field);
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new RookMoveBehavior(getPlayer(), getChessboard(), getField());
	}
}