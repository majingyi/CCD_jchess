package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Player;

public class KingMoveBehavior extends MoveBehavior {

	private King king = null;

	public KingMoveBehavior(Player player, Chessboard chessboard, ChessboardField field, King king) {
		super(player, chessboard, field);
		this.king = king;
	}

	@Override
	public ArrayList<ChessboardField> allMoves() throws Exception {

		ArrayList<ChessboardField> allMoves = new ArrayList<ChessboardField>();

		// adding all straight fields
		for (ChessboardField straightField : this.m_Chessboard.getStraightFields(this.m_Field, 1, this.m_Player.getColor())) {

			if (this.m_Chessboard.getKingForColor(this.m_Player.getColor()).isSafe(straightField)) {
				allMoves.add(straightField);
			} else {
				continue;
			}
		}
		// adding all diagonal fields
		for (ChessboardField diagonalField : this.m_Chessboard.getDiagonalFields(this.m_Field, 1, this.m_Player.getColor())) {

			if (this.m_Chessboard.getKingForColor(this.m_Player.getColor()).isSafe(diagonalField)) {
				allMoves.add(diagonalField);
			} else {
				continue;
			}
		}

		// ChessboardField sq;
		// ChessboardField sq1;
		// for (int i = this.m_Field.pozX - 1; i <= this.m_Field.pozX + 1; i++) {
		// for (int y = this.m_Field.pozY - 1; y <= this.m_Field.pozY + 1; y++) {
		// if (Chessboard.isout(i, y) == false) {// out of bounds protection
		// sq = this.m_Chessboard.getFields()[i][y];
		// if (this.m_Field == sq) {// if we're checking square on which is King
		// continue;
		// }
		// if (this.checkPiece(i, y)) {// if square is empty
		// if (king.isSafe(sq)) {
		// list.add(sq);
		// }
		// }
		// }
		// }
		// }
		//
		// if (!king.wasMotion && !king.isChecked()) {// check if king was not moved
		// // before
		//
		// if (m_Chessboard.getFields()[0][this.m_Field.pozY].getPiece() != null
		// && m_Chessboard.getFields()[0][this.m_Field.pozY].getPiece().getSymbol()
		// == Rook.SYMBOL) {
		// boolean canCastling = true;
		//
		// Rook rook = (Rook)
		// m_Chessboard.getFields()[0][this.m_Field.pozY].getPiece();
		// if (!rook.wasMotion) {
		// for (int i = this.m_Field.pozX - 1; i > 0; i--) {// go left
		// if (m_Chessboard.getFields()[i][this.m_Field.pozY].getPiece() != null) {
		// canCastling = false;
		// break;
		// }
		// }
		// sq = this.m_Chessboard.getFields()[this.m_Field.pozX -
		// 2][this.m_Field.pozY];
		// sq1 = this.m_Chessboard.getFields()[this.m_Field.pozX -
		// 1][this.m_Field.pozY];
		// if (canCastling && king.isSafe(sq) && king.isSafe(sq1)) { // can do
		// // castling
		// // when none
		// // of Sq,sq1
		// // is
		// // checked
		// list.add(sq);
		// }
		// }
		// }
		// if (m_Chessboard.getFields()[7][this.m_Field.pozY].getPiece() != null
		// && m_Chessboard.getFields()[7][this.m_Field.pozY].getPiece().getSymbol()
		// == Rook.SYMBOL) {
		// boolean canCastling = true;
		// Rook rook = (Rook)
		// m_Chessboard.getFields()[7][this.m_Field.pozY].getPiece();
		// if (!rook.wasMotion) {// if king was not moves before and is not checked
		// for (int i = this.m_Field.pozX + 1; i < 7; i++) {// go right
		// if (m_Chessboard.getFields()[i][this.m_Field.pozY].getPiece() != null)
		// {// if
		// // square
		// // is
		// // not
		// // empty
		// canCastling = false;// cannot castling
		// break; // exit
		// }
		// }
		// sq = this.m_Chessboard.getFields()[this.m_Field.pozX +
		// 2][this.m_Field.pozY];
		// sq1 = this.m_Chessboard.getFields()[this.m_Field.pozX +
		// 1][this.m_Field.pozY];
		// if (canCastling && king.isSafe(sq) && king.isSafe(sq1)) {// can do
		// // castling
		// // when none
		// // of Sq,sq1
		// // is
		// // checked
		// list.add(sq);
		// }
		// }
		// }
		// }
		return allMoves;
	}

}
