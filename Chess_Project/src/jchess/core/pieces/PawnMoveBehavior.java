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
		Player.colors color = this.m_Player.getColor();
		switch (color) {
			// for white pawns
			case white:
				// ordinary moves
				// left one move
				field = this.m_Field.getNextField(EdgeDirection.leftDown, EdgeType.straight);
				if (field.getPiece() == null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				// left double move
				if (this.isDoubleMoveAllowed() && field.getNextField(EdgeDirection.leftDown, EdgeType.straight).getPiece() == null
						&& this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				// right one move
				field = this.m_Field.getNextField(EdgeDirection.rightDown, EdgeType.straight);
				if (field.getPiece() == null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				// right double move
				if (this.isDoubleMoveAllowed() && field.getNextField(EdgeDirection.rightDown, EdgeType.straight).getPiece() == null
						&& this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field.getNextField(EdgeDirection.rightDown, EdgeType.straight));
				}

				// capture moves
				// left
				field = this.m_Field.getNextField(EdgeDirection.leftDown, EdgeType.diagonal);
				if (field.getPiece() != null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				// middle
				field = this.m_Field.getNextField(EdgeDirection.down, EdgeType.diagonal);
				if (field.getPiece() != null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				// right
				field = this.m_Field.getNextField(EdgeDirection.rightDown, EdgeType.diagonal);
				if (field.getPiece() != null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				break;

			// for black pawns
			case black:
				// ordinary moves
				// left one move
				field = this.m_Field.getNextField(EdgeDirection.rightUp, EdgeType.straight);
				if (field.getPiece() == null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				// left double move
				if (this.isDoubleMoveAllowed() && field.getNextField(EdgeDirection.rightUp, EdgeType.straight).getPiece() == null
						&& this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field.getNextField(EdgeDirection.rightUp, EdgeType.straight));
				}
				// right one move
				field = this.m_Field.getNextField(EdgeDirection.right, EdgeType.straight);
				if (field.getPiece() == null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				// right double move
				if (this.isDoubleMoveAllowed() && field.getNextField(EdgeDirection.right, EdgeType.straight).getPiece() == null
						&& this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field.getNextField(EdgeDirection.right, EdgeType.straight));
				}

				// capture moves
				// left
				field = this.m_Field.getNextField(EdgeDirection.up, EdgeType.diagonal);
				if (field.getPiece() != null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				// middle
				field = this.m_Field.getNextField(EdgeDirection.rightUp, EdgeType.diagonal);
				if (field.getPiece() != null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				// right
				field = this.m_Field.getNextField(EdgeDirection.rightDown, EdgeType.diagonal);
				if (field.getPiece() != null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				break;

			// for red pawns
			case red:
				// ordinary moves
				// left one move
				field = this.m_Field.getNextField(EdgeDirection.left, EdgeType.straight);
				if (field.getPiece() == null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				// left double move
				if (this.isDoubleMoveAllowed() && field.getNextField(EdgeDirection.left, EdgeType.straight).getPiece() == null
						&& this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field.getNextField(EdgeDirection.left, EdgeType.straight));
				}
				// right one move
				field = this.m_Field.getNextField(EdgeDirection.leftUp, EdgeType.straight);
				if (field.getPiece() == null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				// right double move
				if (this.isDoubleMoveAllowed() && field.getNextField(EdgeDirection.leftUp, EdgeType.straight).getPiece() == null
						&& this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field.getNextField(EdgeDirection.leftUp, EdgeType.straight));
				}

				// capture moves
				// left
				field = this.m_Field.getNextField(EdgeDirection.leftDown, EdgeType.diagonal);
				if (field.getPiece() != null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				// middle
				field = this.m_Field.getNextField(EdgeDirection.leftUp, EdgeType.diagonal);
				if (field.getPiece() != null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				// right
				field = this.m_Field.getNextField(EdgeDirection.up, EdgeType.diagonal);
				if (field.getPiece() != null && this.m_Chessboard.getKingForColor(color).willBeSafeWhenMoveOtherPiece(this.m_Field, field)) {
					allMoves.add(field);
				}
				break;
		}

		// ChessboardField sq;
		// ChessboardField sq1;
		// int first = this.m_Field.pozY - 1;// number where to move
		// int second = this.m_Field.pozY - 2;// number where to move (only in first
		// // move)
		// if (this.m_Player.isGoDown()) {// check if player "go" down or up
		// first = this.m_Field.pozY + 1;// if yes, change value
		// second = this.m_Field.pozY + 2;// if yes, change value
		// }
		// if (Chessboard.isout(first, first)) {// out of bounds protection
		// return list;// return empty list
		// }
		// sq = m_Chessboard.getFields()[this.m_Field.pozX][first];
		// if (sq.getPiece() == null) {// if next is free
		// // list.add(sq);//add
		// if (this.m_Player.getColor() == Player.colors.white) {// white
		//
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[this.m_Field.pozX][first])) {
		// list.add(m_Chessboard.getFields()[this.m_Field.pozX][first]);
		// }
		// } else {// or black
		//
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[this.m_Field.pozX][first])) {
		// list.add(m_Chessboard.getFields()[this.m_Field.pozX][first]);
		// }
		// }
		//
		// if (isDoubleMoveAllowed()) {
		// sq1 = m_Chessboard.getFields()[this.m_Field.pozX][second];
		// if (sq1.getPiece() == null) {
		// // list.add(sq1);//only in first move
		// if (this.m_Player.getColor() == Player.colors.white) {// white
		//
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.white).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[this.m_Field.pozX][second])) {
		// list.add(m_Chessboard.getFields()[this.m_Field.pozX][second]);
		// }
		// } else {// or black
		//
		// if
		// (this.m_Chessboard.getKingForColor(Player.colors.black).willBeSafeWhenMoveOtherPiece(this.m_Field,
		// m_Chessboard.getFields()[this.m_Field.pozX][second])) {
		// list.add(m_Chessboard.getFields()[this.m_Field.pozX][second]);
		// }
		// }
		// }
		// }
		// }
		// if (Chessboard.isout(this.m_Field.pozX - 1, this.m_Field.pozY) == false)
		// // out
		// // of
		// // bounds
		// // protection
		// {
		// // capture
		// sq = m_Chessboard.getFields()[this.m_Field.pozX - 1][first];
		// if (sq.getPiece() != null) {// check if can hit left
		// if (this.m_Player != sq.getPiece().getPlayer() &&
		// (sq.getPiece().getSymbol() == King.SYMBOL) == false) {
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
		//
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