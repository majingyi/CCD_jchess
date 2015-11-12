package jchess.pieces;

import java.util.ArrayList;

import jchess.board.Chessboard;
import jchess.util.Player;
import jchess.util.Square;

public class PawnMoveBehavior extends MoveBehavior {

	public PawnMoveBehavior(Player player, Chessboard chessboard, Square square) {
		super(player, chessboard, square);
	}

	@Override
	public ArrayList<Square> allMoves() {
		ArrayList<Square> list = new ArrayList<Square>();
		Square sq;
		Square sq1;
		int first = this.square.pozY - 1;// number where to move
		int second = this.square.pozY - 2;// number where to move (only in first
		// move)
		if (this.player.goDown) {// check if player "go" down or up
			first = this.square.pozY + 1;// if yes, change value
			second = this.square.pozY + 2;// if yes, change value
		}
		if (Chessboard.isout(first, first)) {// out of bounds protection
			return list;// return empty list
		}
		sq = chessboard.squares[this.square.pozX][first];
		if (sq.piece == null) {// if next is free
			// list.add(sq);//add
			if (this.player.color == Player.colors.white) {// white

				if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][first])) {
					list.add(chessboard.squares[this.square.pozX][first]);
				}
			} else {// or black

				if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][first])) {
					list.add(chessboard.squares[this.square.pozX][first]);
				}
			}

			if (isDoubleMoveAllowed()) {
				sq1 = chessboard.squares[this.square.pozX][second];
				if (sq1.piece == null) {
					// list.add(sq1);//only in first move
					if (this.player.color == Player.colors.white) {// white

						if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][second])) {
							list.add(chessboard.squares[this.square.pozX][second]);
						}
					} else {// or black

						if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][second])) {
							list.add(chessboard.squares[this.square.pozX][second]);
						}
					}
				}
			}
		}
		if (Chessboard.isout(this.square.pozX - 1, this.square.pozY) == false) // out
																																						// of
		// bounds
		// protection
		{
			// capture
			sq = chessboard.squares[this.square.pozX - 1][first];
			if (sq.piece != null) {// check if can hit left
				if (this.player != sq.piece.player && (sq.piece.getSymbol() == King.SYMBOL) == false) {
					// list.add(sq);
					if (this.player.color == Player.colors.white) {// white

						if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX - 1][first])) {
							list.add(chessboard.squares[this.square.pozX - 1][first]);
						}
					} else {// or black

						if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX - 1][first])) {
							list.add(chessboard.squares[this.square.pozX - 1][first]);
						}
					}
				}
			}

			// En passant
			sq = chessboard.squares[this.square.pozX - 1][this.square.pozY];
			if (sq.piece != null && this.chessboard.twoSquareMovedPawn != null && sq == this.chessboard.twoSquareMovedPawn.square) {// check
				// if
				// can
				// hit
				// left
				if (this.player != sq.piece.player && (sq.piece.getSymbol() == King.SYMBOL) == false) {// unnecessary

					// list.add(sq);
					if (this.player.color == Player.colors.white) {// white

						if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX - 1][first])) {
							list.add(chessboard.squares[this.square.pozX - 1][first]);
						}
					} else {// or black

						if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX - 1][first])) {
							list.add(chessboard.squares[this.square.pozX - 1][first]);
						}
					}
				}
			}
		}
		if (Chessboard.isout(this.square.pozX + 1, this.square.pozY) == false) {// out
																																						// of
			// bounds
			// protection

			// capture
			sq = chessboard.squares[this.square.pozX + 1][first];
			if (sq.piece != null) {// check if can hit right
				if (this.player != sq.piece.player && (sq.piece.getSymbol() == King.SYMBOL) == false) {
					// list.add(sq);
					if (this.player.color == Player.colors.white) { // white

						if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX + 1][first])) {
							list.add(chessboard.squares[this.square.pozX + 1][first]);
						}
					} else {// or black

						if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX + 1][first])) {
							list.add(chessboard.squares[this.square.pozX + 1][first]);
						}
					}
				}
			}

			// En passant
			sq = chessboard.squares[this.square.pozX + 1][this.square.pozY];
			if (sq.piece != null && this.chessboard.twoSquareMovedPawn != null && sq == this.chessboard.twoSquareMovedPawn.square) {// check
				// if
				// can
				// hit
				// left
				if (this.player != sq.piece.player && (sq.piece.getSymbol() == King.SYMBOL) == false) {// unnecessary

					// list.add(sq);
					if (this.player.color == Player.colors.white) {// white

						if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX + 1][first])) {
							list.add(chessboard.squares[this.square.pozX + 1][first]);
						}
					} else {// or black

						if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX + 1][first])) {
							list.add(chessboard.squares[this.square.pozX + 1][first]);
						}
					}
				}
			}
		}

		return list;
	}

	/**
	 * 
	 * Check if pawn is allowed to move to squares at once.
	 * 
	 * Normally this is allowed for the first time of the current pawn only.
	 * 
	 * @return true, if pawn is allowed to move two squares at once.
	 */
	protected boolean isDoubleMoveAllowed() {
		return (player.goDown && this.square.pozY == 1) || (!player.goDown && this.square.pozY == 6);
	}

}