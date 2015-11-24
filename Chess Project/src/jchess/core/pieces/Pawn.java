package jchess.core.pieces;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Player;
import jchess.ui.lang.Language;

/**
 * Class to represent a pawn piece.
 * 
 * Pawn can move only forward and can beat only diagonal.
 * 
 * On first move pawn can move 2 squares.
 * 
 * A pawn can be upgraded to rook, knight, bishop, Queen if these pieces of the
 * opponent are already captured.
 */
public class Pawn extends Piece {

	public static final String	SYMBOL	= "Pawn"; //$NON-NLS-1$

	public Pawn(Chessboard chessboard, Player player, ChessboardField field) throws Exception {
		super(chessboard, player, SYMBOL, field);
	}

	/**
	 * Implements the promotion of a pawn.
	 * 
	 * It does not take into account, that the promotion is only allowed to pieces
	 * already captured from the opponent.
	 * 
	 * @param newPiece
	 * @throws Exception
	 */
	public void promote(String newPiece) throws Exception {
		if (newPiece.equals(Queen.SYMBOL)) {
			setMoveBehavior(new QueenMoveBehavior(getPlayer(), getChessboard(), getField()));
			setSymbol(Queen.SYMBOL);
		} else if (newPiece.equals(Rook.SYMBOL)) {
			setMoveBehavior(new RookMoveBehavior(getPlayer(), getChessboard(), getField()));
			setSymbol(Rook.SYMBOL);
		} else if (newPiece.equals(Bishop.SYMBOL)) {
			setMoveBehavior(new BishopMoveBehavior(getPlayer(), getChessboard(), getField()));
			setSymbol(Bishop.SYMBOL);
		} else if (newPiece.equals(King.SYMBOL)) {
			throw new Exception(Language.getString("Pawn.1")); //$NON-NLS-1$
		} else // knight
		{
			setMoveBehavior(new KnightMoveBehavior(getPlayer(), getChessboard(), getField()));
			setSymbol(Knight.SYMBOL);
		}
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new PawnMoveBehavior(getPlayer(), getChessboard(), getField());
	}
}
