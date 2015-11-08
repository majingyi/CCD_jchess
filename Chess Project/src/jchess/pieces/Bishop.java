package jchess.pieces;

import jchess.board.Chessboard;
import jchess.core.Theme;
import jchess.util.Player;

/**
 * Class to represent a chess pawn bishop
 */
public class Bishop extends Piece {

	public static final String	SYMBOL	= "Bishop";

	public Bishop(Chessboard chessboard, Player player) {
		super(chessboard, player);
		setSymbol(SYMBOL);
		setImage(Theme.getImageForPiece(player.color, this));
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new BishopMoveBehavior(player, chessboard, square);
	}

	@Override
	public String getSymbolForMoveHistory() {
		return "B";
	}
}
