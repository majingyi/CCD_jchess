package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Chessboard;
import jchess.core.util.Player;
import jchess.core.util.Square;

public class BishopMoveBehavior extends MoveBehavior {

	public BishopMoveBehavior(Player player, Chessboard chessboard, Square square) {
		super(player, chessboard, square);
	}

	public ArrayList<Square> allMoves() {
		ArrayList<Square> list = new ArrayList<Square>();

		for (int h = this.square.pozX - 1, i = this.square.pozY + 1; Chessboard.isout(h, i) == false; --h, ++i) // left-up
		{
			if (this.checkPiece(h, i)) // if on this square isn't piece
			{
				if (this.player.color == Player.colors.white) // white
				{
					if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				} else // or black
				{
					if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				}

				if (this.otherOwner(h, i)) {
					break;
				}
			} else {
				break;// we've to break because we cannot go beside other piece!!
			}
		}

		for (int h = this.square.pozX - 1, i = this.square.pozY - 1; Chessboard.isout(h, i) == false; --h, --i) // left-down
		{
			if (this.checkPiece(h, i)) // if on this square isn't piece
			{
				if (this.player.color == Player.colors.white) // white
				{
					if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				} else // or black
				{
					if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				}

				if (this.otherOwner(h, i)) {
					break;
				}
			} else {
				break;// we've to break because we cannot go beside other piece!!
			}
		}

		for (int h = this.square.pozX + 1, i = this.square.pozY + 1; Chessboard.isout(h, i) == false; ++h, ++i) // right-up
		{
			if (this.checkPiece(h, i)) // if on this square isn't piece
			{
				if (this.player.color == Player.colors.white) // white
				{
					if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				} else // or black
				{
					if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				}

				if (this.otherOwner(h, i)) {
					break;
				}
			} else {
				break;// we've to break because we cannot go beside other piece!!
			}
		}

		for (int h = this.square.pozX + 1, i = this.square.pozY - 1; Chessboard.isout(h, i) == false; ++h, --i) // right-down
		{
			if (this.checkPiece(h, i)) // if on this square isn't piece
			{
				if (this.player.color == Player.colors.white) // white
				{
					if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				} else // or black
				{
					if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i])) {
						list.add(chessboard.squares[h][i]);
					}
				}

				if (this.otherOwner(h, i)) {
					break;
				}
			} else {
				break;// we've to break because we cannot go beside other piece!!
			}
		}

		return list;
	}
}