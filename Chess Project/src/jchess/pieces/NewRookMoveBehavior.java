package jchess.pieces;

import java.util.ArrayList;

import jchess.board.Chessboard;
import jchess.util.Player;
import jchess.util.Square;

public class NewRookMoveBehavior extends MoveBehavior {

	public NewRookMoveBehavior(Player player, Chessboard chessboard, Square square) {
		super(player, chessboard, square);
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
