package jchess.core.util;

import jchess.core.board.Square;
import jchess.core.pieces.Pawn;
import jchess.core.pieces.Piece;
import jchess.core.util.MoveHistory.castling;
import jchess.ui.ChessboardUI;

public class Move {

	private Square		from									= null;
	private Square		to										= null;
	private Piece			movedPiece						= null;
	private Piece			takenPiece						= null;
	private Piece			promotedTo						= null;
	private boolean		wasEnPassant					= false;
	private castling	castlingMove					= castling.none;
	private boolean		wasPawnTwoFieldsMove	= false;

	public Move(Square from, Square to, Piece movedPiece, Piece takenPiece, castling castlingMove, boolean wasEnPassant, Piece promotedPiece) {
		this.from = from;
		this.to = to;

		this.movedPiece = movedPiece;
		this.takenPiece = takenPiece;

		this.castlingMove = castlingMove;
		this.wasEnPassant = wasEnPassant;

		if (movedPiece.getSymbol() == Pawn.SYMBOL && Math.abs(to.pozY - from.pozY) == 2) {
			this.wasPawnTwoFieldsMove = true;
		} else if (movedPiece.getSymbol() == Pawn.SYMBOL && to.pozY == ChessboardUI.bottom || to.pozY == ChessboardUI.top && promotedPiece != null) {
			this.promotedTo = promotedPiece;
		}
	}

	public Square getFrom() {
		return this.from;
	}

	public Square getTo() {
		return this.to;
	}

	public Piece getMovedPiece() {
		return this.movedPiece;
	}

	public Piece getTakenPiece() {
		return this.takenPiece;
	}

	public boolean wasEnPassant() {
		return this.wasEnPassant;
	}

	public boolean wasPawnTwoFieldsMove() {
		return this.wasPawnTwoFieldsMove;
	}

	public castling getCastlingMove() {
		return this.castlingMove;
	}

	public Piece getPromotedPiece() {
		return this.promotedTo;
	}
}
