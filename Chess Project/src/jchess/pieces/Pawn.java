package jchess.pieces;

import jchess.board.Chessboard;
import jchess.core.Theme;
import jchess.util.Constants;
import jchess.util.Player;

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

	public static final String	SYMBOL	= Constants.EMPTY_STRING;

	public Pawn(Chessboard chessboard, Player player) {
		super(chessboard, player);
		this.symbol = SYMBOL;
		setImage(Theme.getImageForPiece(player.color, this));
	}

	public void promote(String newPiece) {
		if (newPiece.equals("Queen")) {
			setMoveBehavior(new QueenMoveBehavior(player, chessboard, square));
			this.symbol = Queen.SYMBOL;
			setImage(Theme.getImageForPiece(player.color, Queen.SYMBOL));
		} else if (newPiece.equals("Rook")) {
			setMoveBehavior(new RookMoveBehavior(player, chessboard, square));
			this.symbol = Rook.SYMBOL;
			setImage(Theme.getImageForPiece(player.color, Rook.SYMBOL));
		} else if (newPiece.equals("Bishop")) {
			setMoveBehavior(new BishopMoveBehavior(player, chessboard, square));
			this.symbol = Bishop.SYMBOL;
			setImage(Theme.getImageForPiece(player.color, Bishop.SYMBOL));
		} else // knight
		{
			setMoveBehavior(new KnightMoveBehavior(player, chessboard, square));
			this.symbol = Knight.SYMBOL;
			setImage(Theme.getImageForPiece(player.color, Knight.SYMBOL));
		}
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new PawnMoveBehavior(player, chessboard, square);
	}
}
