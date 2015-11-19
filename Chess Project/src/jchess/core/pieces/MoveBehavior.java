package jchess.core.pieces;

import jchess.core.board.Chessboard;
import jchess.core.board.Square;
import jchess.core.util.Player;

public abstract class MoveBehavior implements IMoveBehavior {

	protected Player			player			= null;
	protected Chessboard	chessboard	= null;
	protected Square			square			= null;

	public MoveBehavior(Player player, Chessboard chessboard, Square square) {
		this.player = player;
		this.chessboard = chessboard;
		this.square = square;
	}

	/**
	 * @param x
	 *          y position on chessboard
	 * @param y
	 *          y position on chessboard
	 * @return true if can move, false otherwise
	 * */
	protected boolean checkPiece(int x, int y) {
		if (chessboard.squares[x][y].piece != null && chessboard.squares[x][y].piece.getSymbol() == King.SYMBOL) {
			return false;
		}
		Piece piece = chessboard.squares[x][y].piece;
		if (piece == null || // if this sqhuare is empty
				piece.player != this.player) // or piece is another player
		{
			return true;
		}
		return false;
	}

	/**
	 * Method check if piece has other owner than calling piece
	 * 
	 * @param x
	 *          x position on chessboard
	 * @param y
	 *          y position on chessboard
	 * @return true if owner(player) is different
	 * */
	protected boolean otherOwner(int x, int y) {
		Square sq = chessboard.squares[x][y];
		if (sq.piece == null) {
			return false;
		}
		if (this.player != sq.piece.player) {
			return true;
		}
		return false;
	}

	public void setSquare(Square square) {
		this.square = square;
	}
}