package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Player;

/**
 * Class for creating a behavior of Queen.
 * Calculates allowed fields for movement of this piece.
 * 
 * @author Serhii
 */
public class QueenMoveBehavior extends MoveBehavior {

	public QueenMoveBehavior(Player player, Chessboard m_Chessboard, ChessboardField field) {
		super(player, m_Chessboard, field);
	}

	/**
	 * This method calculates allowed fields for movement of the classic chess Queen
	 * 
	 * @author Serhii
	 * 
	 * @param allMoves array list of allowed fields
	 * @throws Exception 
	 */
	@Override
	public ArrayList<ChessboardField> allMoves() throws Exception {

		ArrayList<ChessboardField> allMoves = new ArrayList<ChessboardField>();

		// adding all straight fields
		for (ChessboardField straightField : this.m_Chessboard.getStraightFields(this.m_Field, this.m_Player.getColor())) {

			if (this.m_Chessboard.getKingForColor(this.m_Player.getColor()).willBeSafeWhenMoveOtherPiece(this.m_Field, straightField)) {
				allMoves.add(straightField);
			} else {
				continue;
			}
		}
		// adding all diagonal fields
		for (ChessboardField diagonalField : this.m_Chessboard.getDiagonalFields(this.m_Field, this.m_Player.getColor())) {

			if (this.m_Chessboard.getKingForColor(this.m_Player.getColor()).willBeSafeWhenMoveOtherPiece(this.m_Field, diagonalField)) {
				allMoves.add(diagonalField);
			} else {
				continue;
			}
		}

		return allMoves;
	}
}