package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Chessboard;
import jchess.core.util.Player;
import jchess.core.util.Square;

public class KnightMoveBehavior extends MoveBehavior {

	public KnightMoveBehavior(Player player, Chessboard chessboard, Square square) {
		super(player, chessboard, square);
	}

	public ArrayList<Square> allMoves() {
		ArrayList<Square> list = new ArrayList<Square>();

		int newX, newY;

		// 1
		newX = this.square.pozX - 2;
		newY = this.square.pozY + 1;

		if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
			if (this.player.color == Player.colors.white) // white
			{
				if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			} else // or black
			{
				if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			}
		}

		// 2
		newX = this.square.pozX - 1;
		newY = this.square.pozY + 2;

		if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
			if (this.player.color == Player.colors.white) // white
			{
				if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			} else // or black
			{
				if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			}
		}

		// 3
		newX = this.square.pozX + 1;
		newY = this.square.pozY + 2;

		if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
			if (this.player.color == Player.colors.white) // white
			{
				if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			} else // or black
			{
				if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			}
		}

		// 4
		newX = this.square.pozX + 2;
		newY = this.square.pozY + 1;

		if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
			if (this.player.color == Player.colors.white) // white
			{
				if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			} else // or black
			{
				if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			}
		}

		// 5
		newX = this.square.pozX + 2;
		newY = this.square.pozY - 1;

		if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
			if (this.player.color == Player.colors.white) // white
			{
				if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			} else // or black
			{
				if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			}
		}

		// 6
		newX = this.square.pozX + 1;
		newY = this.square.pozY - 2;

		if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
			if (this.player.color == Player.colors.white) // white
			{
				if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			} else // or black
			{
				if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			}
		}

		// 7
		newX = this.square.pozX - 1;
		newY = this.square.pozY - 2;

		if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
			if (this.player.color == Player.colors.white) // white
			{
				if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			} else // or black
			{
				if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			}
		}

		// 8
		newX = this.square.pozX - 2;
		newY = this.square.pozY - 1;

		if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
			if (this.player.color == Player.colors.white) // white
			{
				if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			} else // or black
			{
				if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY])) {
					list.add(chessboard.squares[newX][newY]);
				}
			}
		}

		return list;
	}
}
