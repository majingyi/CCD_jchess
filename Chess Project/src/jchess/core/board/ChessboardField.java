package jchess.core.board;

import jchess.core.pieces.Piece;

public class ChessboardField {

	private Piece	m_Piece	= null;

	public ChessboardField(Piece piece) {
		m_Piece = piece;
	}

	public Piece getPiece() {
		return m_Piece;
	}

	public void setPiece(Piece piece) throws Exception {
		m_Piece = piece;
		m_Piece.setSquare(this);
	}
}