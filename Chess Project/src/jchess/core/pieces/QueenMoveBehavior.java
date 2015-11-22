package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Chessboard;
import jchess.core.board.Square;
import jchess.core.util.Player;

public class QueenMoveBehavior extends MoveBehavior {

	public QueenMoveBehavior(Player player, Chessboard chessboard, Square square) {
		super(player, chessboard, square);
	}

	@Override
	public ArrayList<Square> allMoves() {
		ArrayList<Square> list = new ArrayList<Square>();

		for (int i = this.square.pozY + 1; i <= 7; ++i) {// up
			if (this.checkPiece(this.square.pozX, i)) {// if on this square isn't
																									// piece
				if (this.player.getColor() == Player.colors.white) {// white
					if (this.chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i])) {
						list.add(chessboard.squares[this.square.pozX][i]);
					}
				} else {// or black
					if (this.chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i])) {
						list.add(chessboard.squares[this.square.pozX][i]);
					}
				}

				if (this.otherOwner(this.square.pozX, i)) {
					break;
				}
			} else // if on this square is piece
			{
				break;// we've to break becouse we cannot go beside other piece!!
			}
		}

		for (int i = this.square.pozY - 1; i >= 0; --i) {// down

			if (this.checkPiece(this.square.pozX, i)) {// if on this sqhuare isn't
																									// piece

				if (this.player.getColor() == Player.colors.white) {// white

					if (this.chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i])) {
						list.add(chessboard.squares[this.square.pozX][i]);
					}
				} else {// or black

					if (this.chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i])) {
						list.add(chessboard.squares[this.square.pozX][i]);
					}
				}

				if (this.otherOwner(this.square.pozX, i)) {
					break;
				}
			} else {// if on this square is piece

				break;// we've to break becouse we cannot go beside other piece!!
			}
		}

		for (int i = this.square.pozX - 1; i >= 0; --i) {// left
			if (this.checkPiece(i, this.square.pozY)) {// if on this sqhuare isn't
																									// piece
				if (this.player.getColor() == Player.colors.white) {// white
					if (this.chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY])) {
						list.add(chessboard.squares[i][this.square.pozY]);
					}
				} else {// or black
					if (this.chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY])) {
						list.add(chessboard.squares[i][this.square.pozY]);
					}
				}

				if (this.otherOwner(i, this.square.pozY)) {
					break;
				}
			} else {// if on this square is piece
				break;// we've to break becouse we cannot go beside other piece!!
			}
		}

		for (int i = this.square.pozX + 1; i <= 7; ++i) {// right
			if (this.checkPiece(i, this.square.pozY)) {// if on this sqhuare isn't
																									// piece
				if (this.player.getColor() == Player.colors.white) {// white
					if (this.chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY])) {
						list.add(chessboard.squares[i][this.square.pozY]);
					}
				} else {// or black
					if (this.chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY])) {
						list.add(chessboard.squares[i][this.square.pozY]);
					}
				}

				if (this.otherOwner(i, this.square.pozY)) {
					break;
				}
			} else {// if on this square is piece
				break;// we've to break becouse we cannot go beside other piece!!
			}
		}

		for (int h = this.square.pozX - 1, i = this.square.pozY + 1; Chessboard.isout(h, i) == false; --h, ++i) {// left-up
			if (this.checkPiece(h, i)) {// if on this sqhuare isn't piece
				if (this.player.getColor() == Player.colors.white) {// white
					if (this.chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				} else {// or black
					if (this.chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				}

				if (this.otherOwner(h, i)) {
					break;
				}
			} else {// if on this square is piece
				break;// we've to break becouse we cannot go beside other piece!!
			}
		}

		for (int h = this.square.pozX - 1, i = this.square.pozY - 1; Chessboard.isout(h, i) == false; --h, --i) {// left-down
			if (this.checkPiece(h, i)) {// if on this sqhuare isn't piece
				if (this.player.getColor() == Player.colors.white) {// white
					if (this.chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				} else {// or black
					if (this.chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				}

				if (this.otherOwner(h, i)) {
					break;
				}
			} else {// if on this square is piece
				break;// we've to break becouse we cannot go beside other piece!!
			}
		}

		for (int h = this.square.pozX + 1, i = this.square.pozY + 1; Chessboard.isout(h, i) == false; ++h, ++i) {// right-up
			if (this.checkPiece(h, i)) {// if on this sqhuare isn't piece
				if (this.player.getColor() == Player.colors.white) {// white
					if (this.chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				} else {// or black
					if (this.chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				}

				if (this.otherOwner(h, i)) {
					break;
				}
			} else {// if on this square is piece
				break;// we've to break becouse we cannot go beside other piece!!
			}
		}

		for (int h = this.square.pozX + 1, i = this.square.pozY - 1; Chessboard.isout(h, i) == false; ++h, --i) {// right-down
			if (this.checkPiece(h, i)) {// if on this sqhuare isn't piece
				if (this.player.getColor() == Player.colors.white) {// white
					if (this.chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				} else {// or black
					if (this.chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				}

				if (this.otherOwner(h, i)) {
					break;
				}
			} else { // if on this square is piece
				break;// we've to break becouse we cannot go beside other piece!!
			}
		}

		return list;
	}
}