package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.board.Square;
import jchess.core.util.Player;

public class QueenMoveBehavior extends MoveBehavior {

	public QueenMoveBehavior(Player player, Chessboard chessboard, ChessboardField field) {
		super(player, chessboard, field);
	}

	@Override
	public ArrayList<Square> allMoves() throws Exception {
		ArrayList<Square> list = new ArrayList<Square>();

		for (int i = this.m_Field.pozY + 1; i <= 7; ++i) {// up
			if (this.checkPiece(this.m_Field.pozX, i)) {// if on this square isn't
																									// piece
				if (this.m_Player.getColor() == Player.colors.white) {// white
					if (this.m_Chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX][i])) {
						list.add(m_Chessboard.getFields()[this.m_Field.pozX][i]);
					}
				} else {// or black
					if (this.m_Chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX][i])) {
						list.add(m_Chessboard.getFields()[this.m_Field.pozX][i]);
					}
				}

				if (this.otherOwner(this.m_Field.pozX, i)) {
					break;
				}
			} else // if on this square is piece
			{
				break;// we've to break becouse we cannot go beside other piece!!
			}
		}

		for (int i = this.m_Field.pozY - 1; i >= 0; --i) {// down

			if (this.checkPiece(this.m_Field.pozX, i)) {// if on this sqhuare isn't
																									// piece

				if (this.m_Player.getColor() == Player.colors.white) {// white

					if (this.m_Chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX][i])) {
						list.add(m_Chessboard.getFields()[this.m_Field.pozX][i]);
					}
				} else {// or black

					if (this.m_Chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX][i])) {
						list.add(m_Chessboard.getFields()[this.m_Field.pozX][i]);
					}
				}

				if (this.otherOwner(this.m_Field.pozX, i)) {
					break;
				}
			} else {// if on this square is piece

				break;// we've to break becouse we cannot go beside other piece!!
			}
		}

		for (int i = this.m_Field.pozX - 1; i >= 0; --i) {// left
			if (this.checkPiece(i, this.m_Field.pozY)) {// if on this sqhuare isn't
																									// piece
				if (this.m_Player.getColor() == Player.colors.white) {// white
					if (this.m_Chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[i][this.m_Field.pozY])) {
						list.add(m_Chessboard.getFields()[i][this.m_Field.pozY]);
					}
				} else {// or black
					if (this.m_Chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[i][this.m_Field.pozY])) {
						list.add(m_Chessboard.getFields()[i][this.m_Field.pozY]);
					}
				}

				if (this.otherOwner(i, this.m_Field.pozY)) {
					break;
				}
			} else {// if on this square is piece
				break;// we've to break becouse we cannot go beside other piece!!
			}
		}

		for (int i = this.m_Field.pozX + 1; i <= 7; ++i) {// right
			if (this.checkPiece(i, this.m_Field.pozY)) {// if on this sqhuare isn't
																									// piece
				if (this.m_Player.getColor() == Player.colors.white) {// white
					if (this.m_Chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[i][this.m_Field.pozY])) {
						list.add(m_Chessboard.getFields()[i][this.m_Field.pozY]);
					}
				} else {// or black
					if (this.m_Chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[i][this.m_Field.pozY])) {
						list.add(m_Chessboard.getFields()[i][this.m_Field.pozY]);
					}
				}

				if (this.otherOwner(i, this.m_Field.pozY)) {
					break;
				}
			} else {// if on this square is piece
				break;// we've to break becouse we cannot go beside other piece!!
			}
		}

		for (int h = this.m_Field.pozX - 1, i = this.m_Field.pozY + 1; Chessboard.isout(h, i) == false; --h, ++i) {// left-up
			if (this.checkPiece(h, i)) {// if on this sqhuare isn't piece
				if (this.m_Player.getColor() == Player.colors.white) {// white
					if (this.m_Chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
					}
				} else {// or black
					if (this.m_Chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
					}
				}

				if (this.otherOwner(h, i)) {
					break;
				}
			} else {// if on this square is piece
				break;// we've to break becouse we cannot go beside other piece!!
			}
		}

		for (int h = this.m_Field.pozX - 1, i = this.m_Field.pozY - 1; Chessboard.isout(h, i) == false; --h, --i) {// left-down
			if (this.checkPiece(h, i)) {// if on this sqhuare isn't piece
				if (this.m_Player.getColor() == Player.colors.white) {// white
					if (this.m_Chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
					}
				} else {// or black
					if (this.m_Chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
					}
				}

				if (this.otherOwner(h, i)) {
					break;
				}
			} else {// if on this square is piece
				break;// we've to break becouse we cannot go beside other piece!!
			}
		}

		for (int h = this.m_Field.pozX + 1, i = this.m_Field.pozY + 1; Chessboard.isout(h, i) == false; ++h, ++i) {// right-up
			if (this.checkPiece(h, i)) {// if on this sqhuare isn't piece
				if (this.m_Player.getColor() == Player.colors.white) {// white
					if (this.m_Chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
					}
				} else {// or black
					if (this.m_Chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
					}
				}

				if (this.otherOwner(h, i)) {
					break;
				}
			} else {// if on this square is piece
				break;// we've to break becouse we cannot go beside other piece!!
			}
		}

		for (int h = this.m_Field.pozX + 1, i = this.m_Field.pozY - 1; Chessboard.isout(h, i) == false; ++h, --i) {// right-down
			if (this.checkPiece(h, i)) {// if on this sqhuare isn't piece
				if (this.m_Player.getColor() == Player.colors.white) {// white
					if (this.m_Chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
					}
				} else {// or black
					if (this.m_Chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[h][i])) {
						list.add(m_Chessboard.getFields()[h][i]);
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