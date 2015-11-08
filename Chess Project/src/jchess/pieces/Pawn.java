package jchess.pieces;

import jchess.board.Chessboard;
import jchess.core.Theme;
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

	public static final String	SYMBOL	= "Pawn";

	public Pawn(Chessboard chessboard, Player player) {
		super(chessboard, player);
		setSymbol(SYMBOL);
		setImage(Theme.getImageForPiece(player.color, this));
	}

	/**
	 * Implements the promotion of a pawn.
	 * 
	 * It does not take into account, that the promotion is only allowed to pieces
	 * already captured from the opponent.
	 * 
	 * @param newPiece
	 */
	public void promote(String newPiece) {
		if (newPiece.equals(Queen.SYMBOL)) {
			setMoveBehavior(new QueenMoveBehavior(player, chessboard, square));
			setSymbol(Queen.SYMBOL);
			setImage(Theme.getImageForPiece(player.color, Queen.SYMBOL));
		} else if (newPiece.equals(Rook.SYMBOL)) {
			setMoveBehavior(new RookMoveBehavior(player, chessboard, square));
			setSymbol(Rook.SYMBOL);
			setImage(Theme.getImageForPiece(player.color, Rook.SYMBOL));
		} else if (newPiece.equals(Bishop.SYMBOL)) {
			setMoveBehavior(new BishopMoveBehavior(player, chessboard, square));
			setSymbol(Bishop.SYMBOL);
			setImage(Theme.getImageForPiece(player.color, Bishop.SYMBOL));
		} else // knight
		{
			setMoveBehavior(new KnightMoveBehavior(player, chessboard, square));
			setSymbol(Knight.SYMBOL);
			setImage(Theme.getImageForPiece(player.color, Knight.SYMBOL));
		}
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new PawnMoveBehavior(player, chessboard, square);
	}

	@Override
	public String getSymbolForMoveHistory() {
		return "P";
	}
}
