package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.board.Square;
import jchess.core.util.Player;

public class PawnMoveBehavior extends MoveBehavior {

	public PawnMoveBehavior(Player player, Chessboard chessboard, ChessboardField field) {
		super(player, chessboard, field);
	}

	@Override
	public ArrayList<Square> allMoves() throws Exception {
		ArrayList<Square> list = new ArrayList<Square>();
		Square sq;
		Square sq1;
		int first = this.m_Field.pozY - 1;// number where to move
		int second = this.m_Field.pozY - 2;// number where to move (only in first
		// move)
		if (this.m_Player.isGoDown()) {// check if player "go" down or up
			first = this.m_Field.pozY + 1;// if yes, change value
			second = this.m_Field.pozY + 2;// if yes, change value
		}
		if (Chessboard.isout(first, first)) {// out of bounds protection
			return list;// return empty list
		}
		sq = m_Chessboard.getFields()[this.m_Field.pozX][first];
		if (sq.getPiece() == null) {// if next is free
			// list.add(sq);//add
			if (this.m_Player.getColor() == Player.colors.white) {// white

				if (this.m_Chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX][first])) {
					list.add(m_Chessboard.getFields()[this.m_Field.pozX][first]);
				}
			} else {// or black

				if (this.m_Chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX][first])) {
					list.add(m_Chessboard.getFields()[this.m_Field.pozX][first]);
				}
			}

			if (isDoubleMoveAllowed()) {
				sq1 = m_Chessboard.getFields()[this.m_Field.pozX][second];
				if (sq1.getPiece() == null) {
					// list.add(sq1);//only in first move
					if (this.m_Player.getColor() == Player.colors.white) {// white

						if (this.m_Chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX][second])) {
							list.add(m_Chessboard.getFields()[this.m_Field.pozX][second]);
						}
					} else {// or black

						if (this.m_Chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX][second])) {
							list.add(m_Chessboard.getFields()[this.m_Field.pozX][second]);
						}
					}
				}
			}
		}
		if (Chessboard.isout(this.m_Field.pozX - 1, this.m_Field.pozY) == false) // out
																																							// of
		// bounds
		// protection
		{
			// capture
			sq = m_Chessboard.getFields()[this.m_Field.pozX - 1][first];
			if (sq.getPiece() != null) {// check if can hit left
				if (this.m_Player != sq.getPiece().player && (sq.getPiece().getSymbol() == King.SYMBOL) == false) {
					// list.add(sq);
					if (this.m_Player.getColor() == Player.colors.white) {// white

						if (this.m_Chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX - 1][first])) {
							list.add(m_Chessboard.getFields()[this.m_Field.pozX - 1][first]);
						}
					} else {// or black

						if (this.m_Chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX - 1][first])) {
							list.add(m_Chessboard.getFields()[this.m_Field.pozX - 1][first]);
						}
					}
				}
			}

			// En passant
			sq = m_Chessboard.getFields()[this.m_Field.pozX - 1][this.m_Field.pozY];
			if (sq.getPiece() != null && this.m_Chessboard.getTwoSquareMovedPawn() != null && sq == this.m_Chessboard.getTwoSquareMovedPawn().field) {// check
				// if
				// can
				// hit
				// left
				if (this.m_Player != sq.getPiece().player && (sq.getPiece().getSymbol() == King.SYMBOL) == false) {// unnecessary

					// list.add(sq);
					if (this.m_Player.getColor() == Player.colors.white) {// white

						if (this.m_Chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX - 1][first])) {
							list.add(m_Chessboard.getFields()[this.m_Field.pozX - 1][first]);
						}
					} else {// or black

						if (this.m_Chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX - 1][first])) {
							list.add(m_Chessboard.getFields()[this.m_Field.pozX - 1][first]);
						}
					}
				}
			}
		}
		if (Chessboard.isout(this.m_Field.pozX + 1, this.m_Field.pozY) == false) {// out
			// of
			// bounds
			// protection

			// capture
			sq = m_Chessboard.getFields()[this.m_Field.pozX + 1][first];
			if (sq.getPiece() != null) {// check if can hit right
				if (this.m_Player != sq.getPiece().player && (sq.getPiece().getSymbol() == King.SYMBOL) == false) {
					// list.add(sq);
					if (this.m_Player.getColor() == Player.colors.white) { // white

						if (this.m_Chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX + 1][first])) {
							list.add(m_Chessboard.getFields()[this.m_Field.pozX + 1][first]);
						}
					} else {// or black

						if (this.m_Chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX + 1][first])) {
							list.add(m_Chessboard.getFields()[this.m_Field.pozX + 1][first]);
						}
					}
				}
			}

			// En passant
			sq = m_Chessboard.getFields()[this.m_Field.pozX + 1][this.m_Field.pozY];
			if (sq.getPiece() != null && this.m_Chessboard.getTwoSquareMovedPawn() != null && sq == this.m_Chessboard.getTwoSquareMovedPawn().field) {// check
				// if
				// can
				// hit
				// left
				if (this.m_Player != sq.getPiece().player && (sq.getPiece().getSymbol() == King.SYMBOL) == false) {// unnecessary

					// list.add(sq);
					if (this.m_Player.getColor() == Player.colors.white) {// white

						if (this.m_Chessboard.getWhiteKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX + 1][first])) {
							list.add(m_Chessboard.getFields()[this.m_Field.pozX + 1][first]);
						}
					} else {// or black

						if (this.m_Chessboard.getBlackKing().willBeSafeWhenMoveOtherPiece(this.m_Field, m_Chessboard.getFields()[this.m_Field.pozX + 1][first])) {
							list.add(m_Chessboard.getFields()[this.m_Field.pozX + 1][first]);
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
		return (m_Player.isGoDown() && this.m_Field.pozY == 1) || (!m_Player.isGoDown() && this.m_Field.pozY == 6);
	}
}