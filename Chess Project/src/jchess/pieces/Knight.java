package jchess.pieces;

import jchess.board.Chessboard;
import jchess.core.Theme;
import jchess.util.Player;

/**
 * Class to represent a chess knight
 */
public class Knight extends Piece {

	public static final String	SYMBOL	= "Knight";

	public Knight(Chessboard chessboard, Player player) {
		super(chessboard, player);
		setSymbol(SYMBOL);
		setImage(Theme.getImageForPiece(player.color, this));
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new KnightMoveBehavior(player, chessboard, square);
	}
}