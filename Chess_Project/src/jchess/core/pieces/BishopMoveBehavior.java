package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Player;

public class BishopMoveBehavior extends MoveBehavior {

	public BishopMoveBehavior(Player player, Chessboard chessboard, ChessboardField field) {
		super(player, chessboard, field);
	}

	/**
	 * This method calculates allowed fields for movement of the classic chess Bishop
	 * 
	 * @author Serhii
	 * 
	 * @param allMoves array list of allowed fields
	 * @throws Exception 
	 */
	@Override
	public ArrayList<ChessboardField> allMoves() throws Exception {
		ArrayList<ChessboardField> allMoves = new ArrayList<ChessboardField>();

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