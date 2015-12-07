package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Player;

public class RookMoveBehavior extends MoveBehavior {

	public RookMoveBehavior(Player player, Chessboard chessboard, ChessboardField field) {
		super(player, chessboard, field);
	}

	/**
	 * This method calculates allowed fields for movement of the classic chess Rook
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

		return allMoves;
	}
}