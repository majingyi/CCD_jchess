package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Player;

public class BishopMoveBehavior extends MoveBehavior {

	public BishopMoveBehavior(Player player, Chessboard chessboard, ChessboardField field) {
		super(player, chessboard, field);
	}

	public ArrayList<ChessboardField> allMoves() throws Exception {
		ArrayList<ChessboardField> list = new ArrayList<ChessboardField>();

		for (int h = this.m_Field.pozX - 1, i = this.m_Field.pozY + 1; Chessboard.isout(h, i) == false; --h, ++i) // left-up
		{
			if (this.checkPiece(h, i)) // if on this square isn't piece
			{
				if (this.m_Player.getColor() == Player.colors.white) // white
				{
					if (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
					}
				} else // or black
				{
					if (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
					}
				}

				if (this.otherOwner(h, i)) {
					break;
				}
			} else {
				break;// we've to break because we cannot go beside other piece!!
			}
		}

		for (int h = this.m_Field.pozX - 1, i = this.m_Field.pozY - 1; Chessboard.isout(h, i) == false; --h, --i) // left-down
		{
			if (this.checkPiece(h, i)) // if on this square isn't piece
			{
				if (this.m_Player.getColor() == Player.colors.white) // white
				{
					if (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
					}
				} else // or black
				{
					if (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
					}
				}

				if (this.otherOwner(h, i)) {
					break;
				}
			} else {
				break;// we've to break because we cannot go beside other piece!!
			}
		}

		for (int h = this.m_Field.pozX + 1, i = m_Field.pozY + 1; Chessboard.isout(h, i) == false; ++h, ++i) // right-up
		{
			if (this.checkPiece(h, i)) // if on this square isn't piece
			{
				if (this.m_Player.getColor() == Player.colors.white) // white
				{
					if (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
					}
				} else // or black
				{
					if (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
					}
				}

				if (this.otherOwner(h, i)) {
					break;
				}
			} else {
				break;// we've to break because we cannot go beside other piece!!
			}
		}

		for (int h = this.m_Field.pozX + 1, i = m_Field.pozY - 1; Chessboard.isout(h, i) == false; ++h, --i) // right-down
		{
			if (this.checkPiece(h, i)) // if on this square isn't piece
			{
				if (this.m_Player.getColor() == Player.colors.white) // white
				{
					if (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
					}
				} else // or black
				{
					if (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
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