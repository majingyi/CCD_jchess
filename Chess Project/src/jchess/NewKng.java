package jchess;

import java.awt.Image;
/**
 * Class to represent a chess pawn NewKing. NewKing is a chess
 * figure based on the King, who is only allowed to to travel 
 * in horizontal and vertical directions.
 *      |_|_|_|_|_|_|_|_|7
        |_|_|_|_|_|_|_|_|6
        |_|_|_|_|_|_|_|_|5
        |_|_|_|X|_|_|_|_|4
        |_|_|X|K|X|_|_|_|3
        |_|_|_|X|_|_|_|_|2
        |_|_|_|_|_|_|_|_|1
        |_|_|_|_|_|_|_|_|0
        0 1 2 3 4 5 6 7
 */
import java.util.ArrayList;

public class NewKng extends King {

	public boolean							wasMotion		= false;													// maybe
																																						// change
																																						// to:
																																						// 'wasMotioned'
	// public boolean checked = false;
	public static short					value				= 99;
	private static final Image	imageWhite	= GUI.loadImage("NewKing-W.png");
	private static final Image	imageBlack	= GUI.loadImage("NewKing-B.png");

	NewKng(Chessboard chessboard, Player player) {
		super(chessboard, player);
		// this.setImages("NewKing-W.png", "NewKing-B.png");
		this.symbol = "K";
		this.setImage();
		// this.image = imageWhite;

	}

	@Override
	void setImage() {
		if (this.player.color == this.player.color.black) {
			image = imageBlack;
		} else {
			image = imageWhite;
		}
		orgImage = image;
	}

	/**
	 * Annotation to superclass Piece changing pawns location
	 * @return  ArrayList with new position of piece
	 */
	@Override
	public ArrayList allMoves() {
		ArrayList list = new ArrayList();
		Square sq;
		Square sq1;
		for (int i = this.square.pozX - 1; i <= this.square.pozX + 1; i++) {
			int y = this.square.pozY;
			if (!this.isout(i, y)) {// out of bounds protection
				sq = this.chessboard.squares[i][y];
				if (this.square == sq) {// if we're checking square on which is King
					continue;
				}
				if (this.checkPiece(i, y)) {// if square is empty
					if (isSafe(sq)) {
						list.add(sq);
					}
				}
			}
		}
		for (int y = this.square.pozY - 1; y <= this.square.pozY + 1; y++) {
			int i = this.square.pozX;
			if (!this.isout(i, y)) {// out of bounds protection
				sq = this.chessboard.squares[i][y];
				if (this.square == sq) {// if we're checking square on which is King
					continue;
				}
				if (this.checkPiece(i, y)) {// if square is empty
					if (isSafe(sq)) {
						list.add(sq);
					}
				}
			}
		}
		if (!this.wasMotion && !this.isChecked()) {// check if king was not moved
																								// before

			if (chessboard.squares[0][this.square.pozY].piece != null && chessboard.squares[0][this.square.pozY].piece.name.equals("Rook")) {
				boolean canCastling = true;

				Rook rook = (Rook) chessboard.squares[0][this.square.pozY].piece;
				if (!rook.wasMotion) {
					for (int i = this.square.pozX - 1; i > 0; i--) {// go left
						if (chessboard.squares[i][this.square.pozY].piece != null) {
							canCastling = false;
							break;
						}
					}
					sq = this.chessboard.squares[this.square.pozX - 2][this.square.pozY];
					sq1 = this.chessboard.squares[this.square.pozX - 1][this.square.pozY];
					if (canCastling && this.isSafe(sq) && this.isSafe(sq1)) { // can do
																																		// castling
																																		// when none
																																		// of Sq,sq1
																																		// is
																																		// checked
						list.add(sq);
					}
				}
			}
			if (chessboard.squares[7][this.square.pozY].piece != null && chessboard.squares[7][this.square.pozY].piece.name.equals("Rook")) {
				boolean canCastling = true;
				Rook rook = (Rook) chessboard.squares[7][this.square.pozY].piece;
				if (!rook.wasMotion) {// if king was not moves before and is not checked
					for (int i = this.square.pozX + 1; i < 7; i++) {// go right
						if (chessboard.squares[i][this.square.pozY].piece != null) {// if
																																				// square
																																				// is
																																				// not
																																				// empty
							canCastling = false;// cannot castling
							break; // exit
						}
					}
					sq = this.chessboard.squares[this.square.pozX + 2][this.square.pozY];
					sq1 = this.chessboard.squares[this.square.pozX + 1][this.square.pozY];
					if (canCastling && this.isSafe(sq) && this.isSafe(sq1)) {// can do
																																		// castling
																																		// when none
																																		// of Sq,sq1
																																		// is
																																		// checked
						list.add(sq);
					}
				}
			}
		}
		return list;
	}

	/** Method to check is the king is checked
	 *  @return bool true if king is not save, else returns false
	 */
	public boolean isChecked() {
		return !isSafe(this.square);
	}

	/** Method to check is the king is checked or stalemated
	 *  @return int 0 if nothing, 1 if checkmate, else returns 2
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

	/** Method to check is the king is checked by an opponent
	 * @param s Squere where is a king
	 * @return bool true if king is save, else returns false
	 */
	private boolean isSafe(Square s) // A bit confusing code.
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
				if (this.chessboard.squares[s.pozX][i].piece.name.equals("Rook") || this.chessboard.squares[s.pozX][i].piece.name.equals("Queen")) {
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
				if (this.chessboard.squares[s.pozX][i].piece.name.equals("Rook") || this.chessboard.squares[s.pozX][i].piece.name.equals("Queen")) {
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
				if (this.chessboard.squares[i][s.pozY].piece.name.equals("Rook") || this.chessboard.squares[i][s.pozY].piece.name.equals("Queen")) {
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
				if (this.chessboard.squares[i][s.pozY].piece.name.equals("Rook") || this.chessboard.squares[i][s.pozY].piece.name.equals("Queen")) {
					return false;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		// Bishop & Queen
		for (int h = s.pozX - 1, i = s.pozY + 1; !isout(h, i); --h, ++i) // left-up
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
				if (this.chessboard.squares[h][i].piece.name.equals("Bishop") || this.chessboard.squares[h][i].piece.name.equals("Queen")) {
					return false;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		for (int h = s.pozX - 1, i = s.pozY - 1; !isout(h, i); --h, --i) // left-down
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
				if (this.chessboard.squares[h][i].piece.name.equals("Bishop") || this.chessboard.squares[h][i].piece.name.equals("Queen")) {
					return false;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		for (int h = s.pozX + 1, i = s.pozY + 1; !isout(h, i); ++h, ++i) // right-up
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
				if (this.chessboard.squares[h][i].piece.name.equals("Bishop") || this.chessboard.squares[h][i].piece.name.equals("Queen")) {
					return false;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		for (int h = s.pozX + 1, i = s.pozY - 1; !isout(h, i); ++h, --i) // right-down
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
				if (this.chessboard.squares[h][i].piece.name.equals("Bishop") || this.chessboard.squares[h][i].piece.name.equals("Queen")) {
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

		if (!isout(newX, newY)) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight")) {
				return false;
			}
		}

		// 2
		newX = s.pozX - 1;
		newY = s.pozY + 2;

		if (!isout(newX, newY)) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight")) {
				return false;
			}
		}

		// 3
		newX = s.pozX + 1;
		newY = s.pozY + 2;

		if (!isout(newX, newY)) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight")) {
				return false;
			}
		}

		// 4
		newX = s.pozX + 2;
		newY = s.pozY + 1;

		if (!isout(newX, newY)) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight")) {
				return false;
			}
		}

		// 5
		newX = s.pozX + 2;
		newY = s.pozY - 1;

		if (!isout(newX, newY)) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight")) {
				return false;
			}
		}

		// 6
		newX = s.pozX + 1;
		newY = s.pozY - 2;

		if (!isout(newX, newY)) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight")) {
				return false;
			}
		}

		// 7
		newX = s.pozX - 1;
		newY = s.pozY - 2;

		if (!isout(newX, newY)) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight")) {
				return false;
			}
		}

		// 8
		newX = s.pozX - 2;
		newY = s.pozY - 1;

		if (!isout(newX, newY)) {
			if (this.chessboard.squares[newX][newY].piece == null) // if on this
																															// sqhuare isn't
																															// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																									// is
																																									// our
																																									// piece
			{
			} else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight")) {
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
		{// System.out.println("go down");
			newX = s.pozX - 1;
			newY = s.pozY + 1;
			if (!isout(newX, newY)) {
				if (this.chessboard.squares[newX][newY].piece == null) // if on this
																																// sqhuare isn't
																																// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																										// is
																																										// our
																																										// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.name.equals("Pawn")) {
					return false;
				}
			}
			newX = s.pozX + 1;
			if (!isout(newX, newY)) {
				if (this.chessboard.squares[newX][newY].piece == null) // if on this
																																// sqhuare isn't
																																// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																										// is
																																										// our
																																										// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.name.equals("Pawn")) {
					return false;
				}
			}
		} else {// System.out.println("go up");
			newX = s.pozX - 1;
			newY = s.pozY - 1;
			if (!isout(newX, newY)) {
				if (this.chessboard.squares[newX][newY].piece == null) // if on this
																																// sqhuare isn't
																																// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																										// is
																																										// our
																																										// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.name.equals("Pawn")) {
					return false;
				}
			}
			newX = s.pozX + 1;
			if (!isout(newX, newY)) {
				if (this.chessboard.squares[newX][newY].piece == null) // if on this
																																// sqhuare isn't
																																// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.player == this.player) // if
																																										// is
																																										// our
																																										// piece
				{
				} else if (this.chessboard.squares[newX][newY].piece.name.equals("Pawn")) {
					return false;
				}
			}
		}

		return true;
	}

	/** Method to check will the king be safe when move
	 *  @return bool true if king is save, else returns false
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
}