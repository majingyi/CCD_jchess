package jchess.core.board;

import java.util.ArrayList;
import java.util.List;

import jchess.core.board.graph.GraphNode;
import jchess.core.pieces.Piece;

//TODO documentation
public abstract class ChessboardField extends GraphNode {

	private Piece				m_Piece	= null;
	private Chessboard	m_Board	= null;

	public ChessboardField(String identifier, Chessboard board) {
		super(identifier);
		setChessBoard(board);
	}

	public Piece getPiece() {
		return m_Piece;
	}

	public void setPiece(Piece piece) throws Exception {
		m_Piece = piece;
	}

	public Chessboard getChessBoard() {
		return m_Board;
	}

	public void setChessBoard(Chessboard m_Board) {
		this.m_Board = m_Board;
	}

	public List<ChessboardField> getDiagonalNeighbors() {
		List<ChessboardField> result = new ArrayList<ChessboardField>();

		// TODO

		return result;
	}

	public List<ChessboardField> getStraightNeighbors() {
		List<ChessboardField> result = new ArrayList<ChessboardField>();

		// TODO

		return result;
	}

	public List<ChessboardField> getAllNeighbors() {
		List<ChessboardField> result = new ArrayList<ChessboardField>();

		// TODO

		return result;
	}
}