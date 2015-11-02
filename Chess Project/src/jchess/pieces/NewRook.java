/**NewRook: Create a new chess figure based on the rook, 
 * who is only allowed to travel up to 5 fields in a horizontal or 
 * vertical direction*/

package jchess.pieces;

import java.util.ArrayList;

import jchess.ui.ChessboardUI;
import jchess.util.Player;
import jchess.util.Square;

/**
 * Class to represent a chess pawn rook
 * Rook can move:
 *       |_|_|_|X|_|_|_|_|7
         |_|_|_|X|_|_|_|_|6
         |_|_|_|X|_|_|_|_|5
         |_|_|_|X|_|_|_|_|4
         |X|X|X|B|X|X|X|X|3
         |_|_|_|X|_|_|_|_|2
         |_|_|_|X|_|_|_|_|1
         |_|_|_|X|_|_|_|_|0
          0 1 2 3 4 5 6 7
 *
 */
public class NewRook extends Rook {

	NewRook(ChessboardUI chessboard, Player player) {
		super(chessboard, player);// call initializer of super type: Piece
	}

	@Override
	public ArrayList<Square> allMoves() {
		ArrayList<Square> list = new ArrayList<Square>();

		for (int i = this.square.pozY + 1; i <= 7; ++i) {// up
			if (i - this.square.pozY > 5) {

				if (this.checkPiece(this.square.pozX, i)) {// if on this square isn't
																										// piece

					if (this.player.color == Player.colors.white) {// white

						if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i])) {
							list.add(chessboard.squares[this.square.pozX][i]);
						}
					} else {// or black

						if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i])) {
							list.add(chessboard.squares[this.square.pozX][i]);
						}
					}

					if (this.otherOwner(this.square.pozX, i)) {
						break;
					}
				} else {
					break;// we've to break because we cannot go beside other piece!!
				}

			}
		}

		for (int i = this.square.pozY - 1; i >= 0; --i) {// down
			if (this.square.pozY - i > 5) {
				if (this.checkPiece(this.square.pozX, i)) {// if on this square isn't
																										// piece

					if (this.player.color == Player.colors.white) {// white

						if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i])) {
							list.add(chessboard.squares[this.square.pozX][i]);
						}
					} else {// or black

						if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i])) {
							list.add(chessboard.squares[this.square.pozX][i]);
						}
					}

					if (this.otherOwner(this.square.pozX, i)) {
						break;
					}
				} else {
					break;// we've to break because we cannot go beside other piece!!
				}
			}
		}

		for (int i = this.square.pozX - 1; i >= 0; --i) {// left
			if (this.square.pozY - i > 5) {
				if (this.checkPiece(i, this.square.pozY)) {// if on this square isn't
					// piece

					if (this.player.color == Player.colors.white) {// white

						if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY])) {
							list.add(chessboard.squares[i][this.square.pozY]);
						}
					} else {// or black

						if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY])) {
							list.add(chessboard.squares[i][this.square.pozY]);
						}
					}

					if (this.otherOwner(i, this.square.pozY)) {
						break;
					}
				} else {
					break;// we've to break because we cannot go beside other piece!!
				}
			}
		}

		for (int i = this.square.pozX + 1; i <= 7; ++i) {// right
			if (i - this.square.pozY > 5) {
				if (this.checkPiece(i, this.square.pozY)) {// if on this square isn't
					// piece

					if (this.player.color == Player.colors.white) {// white

						if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY])) {
							list.add(chessboard.squares[i][this.square.pozY]);
						}
					} else {// or black

						if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY])) {
							list.add(chessboard.squares[i][this.square.pozY]);
						}
					}

					if (this.otherOwner(i, this.square.pozY)) {
						break;
					}
				} else {
					break;// we've to break because we cannot go beside other piece!!
				}
			}
		}

		return list;
	}
}
