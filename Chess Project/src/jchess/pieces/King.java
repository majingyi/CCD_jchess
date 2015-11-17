package jchess.pieces;

/**
 * Class to represent a chess pawn king. King is the most important piece for
 * the game. Loose of king is the and of game. When king is in danger by the
 * opponent then it's a Checked, and when have no other escape then stay on a
 * square "in danger" by the opponent then it's a CheckedMate, and the game is
 * over.
 */
import jchess.board.Chessboard;
import jchess.util.Player;
import jchess.util.Square;

public class King extends Piece {

	public static final String	SYMBOL		= "King";
	public boolean							wasMotion	= false;	// maybe
																									// change
																									// to:
																									// 'wasMotioned'

	public King(Chessboard chessboard, Player player) throws Exception {
		super(chessboard, player, SYMBOL);
	}

	/**
	 * Method to check is the king is checked
	 * 
	 * @return bool true if king is not save, else returns false
	 */
	public boolean isChecked() {
		return isSafe(this.square) == false;
	}

	/**
	 * Method to check is the king is checked or stalemated
	 * 
	 * @return int 0 if nothing, 1 if checkmate, else returns 2
	 */
	public int isCheckmatedOrStalemated() {
		/*
		 * returns: 0-nothing, 1-checkmate, 2-stalemate
		 */
		if (this.allMoves().size() == 0) {
			for (int i = 0; i < 8; ++i) {
				for (int j = 0; j < 8; ++j) {
					if (chessboard.squares[i][j].piece != null && chessboard.squares[i][j].piece.player == this.player
							&& chessboard.squares[i][j].piece.allMoves().size() != 0) {
						return 0;
					}
				}
			}

			if (this.isChecked()) {
				return 1;
			} else {
				return 2;
			}
		} else {
			return 0;
		}
	}

	/**
	 * Method to check will the king be safe when move
	 * 
	 * @return bool true if king is save, else returns false
	 */
	public boolean isSafe(Square s) // A bit confusing code.
	{
		// Rook & Queen
		for (int i = s.pozY + 1; i <= 7; ++i) // up
		{
			if (this.chessboard.squares[s.pozX][i].piece == null || this.chessboard.squares[s.pozX][i].piece == this) // if
																																																								// on
																																																								// this
																																																								// sqhuare
																																																								// isn't
																																																								// piece
			{
				continue;
			} else if (this.chessboard.squares[s.pozX][i].piece.player != this.player) // if
																																									// isn't
																																									// our
																																									// piece
			{
				if (this.chessboard.squares[s.pozX][i].piece.getSymbol() == Rook.SYMBOL || this.chessboard.squares[s.pozX][i].piece.getSymbol() == Queen.SYMBOL) {
					return false;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		for (int i = s.pozY - 1; i >= 0; --i) // down
		{
			if (this.chessboard.squares[s.pozX][i].piece == null || this.chessboard.squares[s.pozX][i].piece == this) // if
																																																								// on
																																																								// this
																																																								// sqhuare
																																																								// isn't
																																																								// piece
			{
				continue;
			} else if (this.chessboard.squares[s.pozX][i].piece.player != this.player) // if
																																									// isn't
																																									// our
																																									// piece
			{
				if (this.chessboard.squares[s.pozX][i].piece.getSymbol() == Rook.SYMBOL || this.chessboard.squares[s.pozX][i].piece.getSymbol() == Queen.SYMBOL) {
					return false;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		for (int i = s.pozX - 1; i >= 0; --i) // left
		{
			if (this.chessboard.squares[i][s.pozY].piece == null || this.chessboard.squares[i][s.pozY].piece == this) // if
																																																								// on
																																																								// this
																																																								// sqhuare
																																																								// isn't
																																																								// piece
			{
				continue;
			} else if (this.chessboard.squares[i][s.pozY].piece.player != this.player) // if
																																									// isn't
																																									// our
																																									// piece
			{
				if (this.chessboard.squares[i][s.pozY].piece.getSymbol() == Rook.SYMBOL || this.chessboard.squares[i][s.pozY].piece.getSymbol() == Queen.SYMBOL) {
					return false;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		for (int i = s.pozX + 1; i <= 7; ++i) // right
		{
			if (this.chessboard.squares[i][s.pozY].piece == null || this.chessboard.squares[i][s.pozY].piece == this) // if
																																																								// on
																																																								// this
																																																								// sqhuare
																																																								// isn't
																																																								// piece
			{
				continue;
			} else if (this.chessboard.squares[i][s.pozY].piece.player != this.player) // if
																																									// isn't
																																									// our
																																									// piece
			{
				if (this.chessboard.squares[i][s.pozY].piece.getSymbol() == Rook.SYMBOL || this.chessboard.squares[i][s.pozY].piece.getSymbol() == Queen.SYMBOL) {
					return false;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		// Bishop & Queen
		for (int h = s.pozX - 1, i = s.pozY + 1; Chessboard.isout(h, i) == false; --h, ++i) // left-up
		{
			if (this.chessboard.squares[h][i].piece == null || this.chessboard.squares[h][i].piece == this) // if
																																																			// on
																																																			// this
																																																			// sqhuare
																																																			// isn't
																																																			// piece
			{
				continue;
			} else if (this.chessboard.squares[h][i].piece.player != this.player) // if
																																						// isn't
																																						// our
																																						// piece
			{
				if (this.chessboard.squares[h][i].piece.getSymbol() == Bishop.SYMBOL || this.chessboard.squares[h][i].piece.getSymbol() == Queen.SYMBOL) {
					return false;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		for (int h = s.pozX - 1, i = s.pozY - 1; Chessboard.isout(h, i) == false; --h, --i) // left-down
		{
			if (this.chessboard.squares[h][i].piece == null || this.chessboard.squares[h][i].piece == this) // if
																																																			// on
																																																			// this
																																																			// sqhuare
																																																			// isn't
																																																			// piece
			{
				continue;
			} else if (this.chessboard.squares[h][i].piece.player != this.player) // if
																																						// isn't
																																						// our
																																						// piece
			{
				if (this.chessboard.squares[h][i].piece.getSymbol() == Bishop.SYMBOL || this.chessboard.squares[h][i].piece.getSymbol() == Queen.SYMBOL) {
					return false;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		for (int h = s.pozX + 1, i = s.pozY + 1; Chessboard.isout(h, i) == false; ++h, ++i) // right-up
		{
			if (this.chessboard.squares[h][i].piece == null || this.chessboard.squares[h][i].piece == this) // if
																																																			// on
																																																			// this
																																																			// sqhuare
																																																			// isn't
																																																			// piece
			{
				continue;
			} else if (this.chessboard.squares[h][i].piece.player != this.player) // if
																																						// isn't
																																						// our
																																						// piece
			{
				if (this.chessboard.squares[h][i].piece.getSymbol() == Bishop.SYMBOL || this.chessboard.squares[h][i].piece.getSymbol() == Queen.SYMBOL) {
					return false;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		for (int h = s.pozX + 1, i = s.pozY - 1; Chessboard.isout(h, i) == false; ++h, --i) // right-down
		{
			if (this.chessboard.squares[h][i].piece == null || this.chessboard.squares[h][i].piece == this) // if
																																																			// on
																																																			// this
																																																			// sqhuare
																																																			// isn't
																																																			// piece
			{
				continue;
			} else if (this.chessboard.squares[h][i].piece.player != this.player) // if
																																						// isn't
																																						// our
																																						// piece
			{
				if (this.chessboard.squares[h][i].piece.getSymbol() == Bishop.SYMBOL || this.chessboard.squares[h][i].piece.getSymbol() == Queen.SYMBOL) {
					return false;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		// Knight
		int newX, newY;

		// 1
		newX = s.pozX - 2;
		newY = s.pozY + 1;

		if (Chessboard.isout(newX, newY) == false) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.getSymbol() == Knight.SYMBOL) {
				return false;
			}
		}

		// 2
		newX = s.pozX - 1;
		newY = s.pozY + 2;

		if (Chessboard.isout(newX, newY) == false) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.getSymbol() == Knight.SYMBOL) {
				return false;
			}
		}

		// 3
		newX = s.pozX + 1;
		newY = s.pozY + 2;

		if (Chessboard.isout(newX, newY) == false) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.getSymbol() == Knight.SYMBOL) {
				return false;
			}
		}

		// 4
		newX = s.pozX + 2;
		newY = s.pozY + 1;

		if (Chessboard.isout(newX, newY) == false) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.getSymbol() == Knight.SYMBOL) {
				return false;
			}
		}

		// 5
		newX = s.pozX + 2;
		newY = s.pozY - 1;

		if (Chessboard.isout(newX, newY) == false) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.getSymbol() == Knight.SYMBOL) {
				return false;
			}
		}

		// 6
		newX = s.pozX + 1;
		newY = s.pozY - 2;

		if (Chessboard.isout(newX, newY) == false) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.getSymbol() == Knight.SYMBOL) {
				return false;
			}
		}

		// 7
		newX = s.pozX - 1;
		newY = s.pozY - 2;

		if (Chessboard.isout(newX, newY) == false) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.getSymbol() == Knight.SYMBOL) {
				return false;
			}
		}

		// 8
		newX = s.pozX - 2;
		newY = s.pozY - 1;

		if (Chessboard.isout(newX, newY) == false) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.getSymbol() == Knight.SYMBOL) {
				return false;
			}
		}

		// King
		King otherKing;
		if (this == chessboard.kingWhite) {
			otherKing = chessboard.kingBlack;
		} else {
			otherKing = chessboard.kingWhite;
		}

		if (s.pozX <= otherKing.square.pozX + 1 && s.pozX >= otherKing.square.pozX - 1 && s.pozY <= otherKing.square.pozY + 1
				&& s.pozY >= otherKing.square.pozY - 1) {
			return false;
		}

		// Pawn
		if (this.player.goDown) // check if player "go" down or up
		{
			newX = s.pozX - 1;
			newY = s.pozY + 1;
			if (Chessboard.isout(newX, newY) == false) {
				if (this.chessboard.squares[newX][newY].piece == null) // if on this
																																// sqhuare isn't
																																// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																										// is
																																										// our
																																										// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.getSymbol() == Pawn.SYMBOL) {
					return false;
				}
			}
			newX = s.pozX + 1;
			if (Chessboard.isout(newX, newY) == false) {
				if (this.chessboard.squares[newX][newY].piece == null) // if on this
																																// sqhuare isn't
																																// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																										// is
																																										// our
																																										// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.getSymbol() == Pawn.SYMBOL) {
					return false;
				}
			}
		} else {
			newX = s.pozX - 1;
			newY = s.pozY - 1;
			if (Chessboard.isout(newX, newY) == false) {
				if (this.chessboard.squares[newX][newY].piece == null) // if on this
																																// sqhuare isn't
																																// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																										// is
																																										// our
																																										// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.getSymbol() == Pawn.SYMBOL) {
					return false;
				}
			}
			newX = s.pozX + 1;
			if (Chessboard.isout(newX, newY) == false) {
				if (this.chessboard.squares[newX][newY].piece == null) // if on this
																																// sqhuare isn't
																																// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																										// is
																																										// our
																																										// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.getSymbol() == Pawn.SYMBOL) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Method to check will the king be safe when move
	 * 
	 * @return bool true if king is save, else returns false
	 */
	public boolean willBeSafeWhenMoveOtherPiece(Square sqIsHere, Square sqWillBeThere) // long
																																											// name
																																											// ;)
	{
		Piece tmp = sqWillBeThere.piece;
		sqWillBeThere.piece = sqIsHere.piece; // move without redraw
		sqIsHere.piece = null;

		boolean ret = isSafe(this.square);

		sqIsHere.piece = sqWillBeThere.piece;
		sqWillBeThere.piece = tmp;

		return ret;
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new KingMoveBehavior(player, chessboard, square, this);
	}
}