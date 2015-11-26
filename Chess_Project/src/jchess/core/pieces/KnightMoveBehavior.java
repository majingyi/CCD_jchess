package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Player;

public class KnightMoveBehavior extends MoveBehavior {

	public KnightMoveBehavior(Player player, Chessboard m_Chessboard, ChessboardField field) {
		super(player, m_Chessboard, field);
	}

	public ArrayList<ChessboardField> allMoves() throws Exception {
		ArrayList<ChessboardField> list = new ArrayList<ChessboardField>();

		// int newX, newY;
		//
		// // 1
		// newX = this.m_Field.pozX - 2;
		// newY = this.m_Field.pozY + 1;
		//
		// if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
		// if (this.m_Player.getColor() == Player.colors.white) // white
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// } else // or black
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// }
		// }
		//
		// // 2
		// newX = this.m_Field.pozX - 1;
		// newY = this.m_Field.pozY + 2;
		//
		// if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
		// if (this.m_Player.getColor() == Player.colors.white) // white
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// } else // or black
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// }
		// }
		//
		// // 3
		// newX = this.m_Field.pozX + 1;
		// newY = this.m_Field.pozY + 2;
		//
		// if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
		// if (this.m_Player.getColor() == Player.colors.white) // white
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// } else // or black
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// }
		// }
		//
		// // 4
		// newX = this.m_Field.pozX + 2;
		// newY = this.m_Field.pozY + 1;
		//
		// if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
		// if (this.m_Player.getColor() == Player.colors.white) // white
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// } else // or black
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// }
		// }
		//
		// // 5
		// newX = this.m_Field.pozX + 2;
		// newY = this.m_Field.pozY - 1;
		//
		// if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
		// if (this.m_Player.getColor() == Player.colors.white) // white
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// } else // or black
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// }
		// }
		//
		// // 6
		// newX = this.m_Field.pozX + 1;
		// newY = this.m_Field.pozY - 2;
		//
		// if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
		// if (this.m_Player.getColor() == Player.colors.white) // white
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// } else // or black
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// }
		// }
		//
		// // 7
		// newX = this.m_Field.pozX - 1;
		// newY = this.m_Field.pozY - 2;
		//
		// if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
		// if (this.m_Player.getColor() == Player.colors.white) // white
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// } else // or black
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// }
		// }
		//
		// // 8
		// newX = this.m_Field.pozX - 2;
		// newY = this.m_Field.pozY - 1;
		//
		// if ((Chessboard.isout(newX, newY) == false) && checkPiece(newX, newY)) {
		// if (this.m_Player.getColor() == Player.colors.white) // white
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// } else // or black
		// {
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[newX][newY])) {
		// list.add(m_Chessboard.getFields()[newX][newY]);
		// }
		// }
		// }

		return list;
	}
}