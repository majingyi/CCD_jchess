package jchess.core.board;

import jchess.core.pieces.Piece;

public abstract class ChessboardField {

	private Piece				m_Piece				= null;
	private String			m_Identifier	= null;
	private Chessboard	m_Board				= null;

	public ChessboardField(String identifier, Chessboard board) {
		m_Identifier = identifier;
		setChessBoard(board);
	}

	public Piece getPiece() {
		return m_Piece;
	}

	public void setPiece(Piece piece) throws Exception {
		m_Piece = piece;
	}

	public String getIdentifier() {
		return m_Identifier;
	}

	public Chessboard getChessBoard() {
		return m_Board;
	}

	public void setChessBoard(Chessboard m_Board) {
		this.m_Board = m_Board;
	}
}