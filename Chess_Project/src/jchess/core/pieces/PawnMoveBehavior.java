package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.board.graph.DirectedGraphEdge.EdgeDirection;
import jchess.core.board.graph.GraphEdge.EdgeType;
import jchess.core.util.Player;

public class PawnMoveBehavior extends MoveBehavior {

	public PawnMoveBehavior(Player player, Chessboard chessboard, ChessboardField field) {
		super(player, chessboard, field);
	}

	@Override
	public ArrayList<ChessboardField> allMoves() throws Exception {

		ArrayList<ChessboardField> allMoves = new ArrayList<ChessboardField>();
		ChessboardField field;
		Player.PlayerColor color = this.m_Player.getColor();
		EdgeDirection leftUp = this.m_Chessboard.getDirectionFromPlayersPOV(color, EdgeDirection.LEFT_UP, EdgeType.STRAIGHT);
		EdgeDirection leftUpDiag = this.m_Chessboard.getDirectionFromPlayersPOV(color, EdgeDirection.LEFT_UP, EdgeType.DIAGONAL);
		EdgeDirection rightUp = this.m_Chessboard.getDirectionFromPlayersPOV(color, EdgeDirection.RIGHT_UP, EdgeType.STRAIGHT);
		EdgeDirection rightUpDiag = this.m_Chessboard.getDirectionFromPlayersPOV(color, EdgeDirection.RIGHT_UP, EdgeType.DIAGONAL);
		EdgeDirection upDiag = this.m_Chessboard.getDirectionFromPlayersPOV(color, EdgeDirection.UP, EdgeType.DIAGONAL);

		// ordinary moves
		// left one move
		field = this.m_Field.getNextField(leftUp, EdgeType.STRAIGHT);
		if (field.getPiece() == null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
			allMoves.add(field);
		}
		// left double move
		field = field.getNextField(leftUp, EdgeType.STRAIGHT);
		if (this.isDoubleMoveAllowed() && field.getPiece() == null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
			allMoves.add(field);
		}
		// right one move
		field = this.m_Field.getNextField(rightUp, EdgeType.STRAIGHT);
		if (field.getPiece() == null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
			allMoves.add(field);
		}
		// right double move
		field = field.getNextField(rightUp, EdgeType.STRAIGHT);
		if (this.isDoubleMoveAllowed() && field.getPiece() == null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
			allMoves.add(field.getNextField(rightUp, EdgeType.STRAIGHT));
		}

		// capture moves
		// left
		field = this.m_Field.getNextField(leftUpDiag, EdgeType.DIAGONAL);
		if (field.getPiece() != null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
			allMoves.add(field);
		}
		// middle
		field = this.m_Field.getNextField(upDiag, EdgeType.DIAGONAL);
		if (field.getPiece() != null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
			allMoves.add(field);
		}
		// right
		field = this.m_Field.getNextField(rightUpDiag, EdgeType.DIAGONAL);
		if (field.getPiece() != null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
			allMoves.add(field);
		}

		// en passant :TODO

		// // En passant
		// sq = m_Chessboard.getFields()[this.m_Field.pozX - 1][this.m_Field.pozY];
		// if (sq.getPiece() != null && this.m_Chessboard.getTwoSquareMovedPawn() !=
		// null && sq == this.m_Chessboard.getTwoSquareMovedPawn().getField()) {//
		// check
		// // if
		// // can
		// // hit
		// // left
		// if (this.m_Player != sq.getPiece().getPlayer() &&
		// (sq.getPiece().getSymbol() == King.SYMBOL) == false) {// unnecessary
		//
		// // list.add(sq);
		// if (this.m_Player.getColor() == Player.colors.white) {// white
		//
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[this.m_Field.pozX - 1][first])) {
		// list.add(m_Chessboard.getFields()[this.m_Field.pozX - 1][first]);
		// }
		// } else {// or black
		//
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[this.m_Field.pozX - 1][first])) {
		// list.add(m_Chessboard.getFields()[this.m_Field.pozX - 1][first]);
		// }
		// }
		// }
		// }
		// }
		// if (Chessboard.isout(this.m_Field.pozX + 1, this.m_Field.pozY) == false)
		// {// out
		// // of
		// // bounds
		// // protection
		//
		// // capture
		// sq = m_Chessboard.getFields()[this.m_Field.pozX + 1][first];
		// if (sq.getPiece() != null) {// check if can hit right
		// if (this.m_Player != sq.getPiece().getPlayer() &&
		// (sq.getPiece().getSymbol() == King.SYMBOL) == false) {
		// // list.add(sq);
		// if (this.m_Player.getColor() == Player.colors.white) { // white
		//
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[this.m_Field.pozX + 1][first])) {
		// list.add(m_Chessboard.getFields()[this.m_Field.pozX + 1][first]);
		// }
		// } else {// or black
		//
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[this.m_Field.pozX + 1][first])) {
		// list.add(m_Chessboard.getFields()[this.m_Field.pozX + 1][first]);
		// }
		// }
		// }
		// }
		//
		// // En passant
		// sq = m_Chessboard.getFields()[this.m_Field.pozX + 1][this.m_Field.pozY];
		// if (sq.getPiece() != null && this.m_Chessboard.getTwoSquareMovedPawn() !=
		// null && sq == this.m_Chessboard.getTwoSquareMovedPawn().getField()) {//
		// check
		// // if
		// // can
		// // hit
		// // left
		// if (this.m_Player != sq.getPiece().getPlayer() &&
		// (sq.getPiece().getSymbol() == King.SYMBOL) == false) {// unnecessary
		//
		// // list.add(sq);
		// if (this.m_Player.getColor() == Player.colors.white) {// white
		//
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[this.m_Field.pozX + 1][first])) {
		// list.add(m_Chessboard.getFields()[this.m_Field.pozX + 1][first]);
		// }
		// } else {// or black
		//
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[this.m_Field.pozX + 1][first])) {
		// list.add(m_Chessboard.getFields()[this.m_Field.pozX + 1][first]);
		// }
		// }
		// }
		// }
		// }

		return allMoves;
	}

	/**
	 * 
	 * Check if pawn is allowed to move to getFields() at once.
	 * 
	 * Normally this is allowed for the first time of the current pawn only.
	 * 
	 * @return true, if pawn is allowed to move two getFields() at once.
	 */
	protected boolean isDoubleMoveAllowed() {
		return true;// return (m_Player.isGoDown() && this.m_Field.pozY == 1) ||
								// (!m_Player.isGoDown() && this.m_Field.pozY == 6);
	}
}