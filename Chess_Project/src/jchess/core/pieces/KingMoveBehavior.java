package jchess.core.pieces;

import java.util.ArrayList;
import java.util.List;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.board.graph.DirectedGraphEdge.EdgeDirection;
import jchess.core.board.graph.GraphEdge.EdgeType;
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
		Player.PlayerColor color = this.m_Player.getColor();

		// adding all straight fields
		for (ChessboardField straightField : this.m_Chessboard.getStraightFields(this.m_Field, 1, this.m_Player.getColor())) {

			if (this.m_Chessboard.getKingForColor(color).isSafe(straightField)) {
				allMoves.add(straightField);
			} else {
				continue;
			}
		}

		// adding all diagonal fields
		for (ChessboardField diagonalField : this.m_Chessboard.getDiagonalFields(this.m_Field, 1, this.m_Player.getColor())) {

			if (this.m_Chessboard.getKingForColor(color).isSafe(diagonalField)) {
				allMoves.add(diagonalField);
			} else {
				continue;
			}
		}

		// checking possibility for castling
		EdgeDirection toLeft = this.m_Chessboard.getDirectionFromPlayersPOV(color, EdgeDirection.LEFT);
		EdgeDirection toRight = this.m_Chessboard.getDirectionFromPlayersPOV(color, EdgeDirection.RIGHT);

		// if that's the first move of the king and it's not checked
		if (this.king.wasMotion == false && this.king.isChecked() == false) {

			List<Rook> rooks = this.m_Chessboard.getRooksForColor(color);
			// leftside castling (long)
			for (ChessboardField field = this.m_Field; field == rooks.get(0).getField()
					|| field == rooks.get(1).getField(); field = field.getNextField(toLeft, EdgeType.STRAIGHT)) {
				if (field.getPiece() != null) {
					break;
				}
				if ((field == rooks.get(0).getField() && rooks.get(0).wasMotion == false) || (field == rooks.get(1).getField() && rooks.get(1).wasMotion == false)) {
					allMoves.add(field);
				}
			}
			for (ChessboardField field = this.m_Field; field == rooks.get(0).getField()
					|| field == rooks.get(1).getField(); field = field.getNextField(toRight, EdgeType.STRAIGHT)) {
				if (field.getPiece() != null) {
					break;
				}
				if ((field == rooks.get(0).getField() && rooks.get(0).wasMotion == false) || (field == rooks.get(1).getField() && rooks.get(1).wasMotion == false)) {
					allMoves.add(field);
				}
			}
			if (true) { // if that's the first move of left rook
				// three fields to the left has to be without pieces
				ChessboardField field1 = this.m_Field.getNextField(toLeft, EdgeType.STRAIGHT);
				ChessboardField field2 = field1.getNextField(toLeft, EdgeType.STRAIGHT);
				ChessboardField field3 = field2.getNextField(toLeft, EdgeType.STRAIGHT);
				if (field1.getPiece() == null && field2.getPiece() == null && field3.getPiece() == null) {
					allMoves.add(field2);
				}
			}

			// rightside castling (short)
			if (true) { // if that's the first move of right rook
				// two fields to the right has to be without pieces
				ChessboardField field1 = this.m_Field.getNextField(toRight, EdgeType.STRAIGHT);
				ChessboardField field2 = field1.getNextField(toRight, EdgeType.STRAIGHT);
				if (field1.getPiece() == null && field2.getPiece() == null) {
					allMoves.add(field2);
				}
			}
		}

		return allMoves;
	}
}
