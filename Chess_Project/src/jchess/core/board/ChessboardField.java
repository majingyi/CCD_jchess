package jchess.core.board;

import java.util.ArrayList;
import java.util.List;

import jchess.core.board.graph.GraphEdge;
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

	public List<ChessboardField> getDiagonalNeighbors() throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();
		List<GraphEdge> edges = getEdges();
		for (GraphEdge edge : edges) {
			if (edge.getEdgeType() == GraphEdge.EdgeType.diagonal) {
				GraphNode node = edge.getOtherNode(this);
				if (node instanceof ChessboardField) {
					result.add((ChessboardField) node);
				}
			}
		}

		return result;
	}

	public List<ChessboardField> getStraightNeighbors() throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();
		List<GraphEdge> edges = getEdges();
		for (GraphEdge edge : edges) {
			if (edge.getEdgeType() == GraphEdge.EdgeType.straight) {
				GraphNode node = edge.getOtherNode(this);
				if (node instanceof ChessboardField) {
					result.add((ChessboardField) node);
				}
			}
		}

		return result;
	}

	public List<ChessboardField> getAllNeighbors() throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();
		List<GraphEdge> edges = getEdges();
		for (GraphEdge edge : edges) {
			GraphNode node = edge.getOtherNode(this);
			if (node instanceof ChessboardField) {
				result.add((ChessboardField) node);
			}
		}

		return result;
	}
}