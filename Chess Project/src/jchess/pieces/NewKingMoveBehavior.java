package jchess.pieces;

import java.util.ArrayList;

import jchess.board.Chessboard;
import jchess.util.Player;
import jchess.util.Square;

public class NewKingMoveBehavior extends MoveBehavior {

	private King	king	= null;

	public NewKingMoveBehavior(Player player, Chessboard chessboard, Square square, King king) {
		super(player, chessboard, square);
		this.king = king;
	}

	@Override
	public ArrayList<Square> allMoves() {
		ArrayList<Square> list = new ArrayList<Square>();
		Square sq;
		Square sq1;
		for (int i = this.square.pozX - 1; i <= this.square.pozX + 1; i++) {
			int y = this.square.pozY;
			if (Chessboard.isout(i, y) == false) {// out of bounds protection
				sq = this.chessboard.squares[i][y];
				if (this.square == sq) {// if we're checking square on which is King
					continue;
				}
				if (this.checkPiece(i, y)) {// if square is empty
					if (king.isSafe(sq)) {
						list.add(sq);
					}
				}
			}
		}
		for (int y = this.square.pozY - 1; y <= this.square.pozY + 1; y++) {
			int i = this.square.pozX;
			if (Chessboard.isout(i, y) == false) {// out of bounds protection
				sq = this.chessboard.squares[i][y];
				if (this.square == sq) {// if we're checking square on which is King
					continue;
				}
				if (this.checkPiece(i, y)) {// if square is empty
					if (king.isSafe(sq)) {
						list.add(sq);
					}
				}
			}
		}
		if ((king.wasMotion == false) && (king.isChecked() == false)) {// check if
																																		// king was
																																		// not moved
			// before

			if (chessboard.squares[0][this.square.pozY].piece != null && chessboard.squares[0][this.square.pozY].piece.symbol == Rook.SYMBOL) {
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
					if (canCastling && king.isSafe(sq) && king.isSafe(sq1)) { // can do
																																		// castling
																																		// when none
																																		// of Sq,sq1
																																		// is
																																		// checked
						list.add(sq);
					}
				}
			}
			if (chessboard.squares[7][this.square.pozY].piece != null && chessboard.squares[7][this.square.pozY].piece.symbol == Rook.SYMBOL) {
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
					if (canCastling && king.isSafe(sq) && king.isSafe(sq1)) {// can do
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
}
