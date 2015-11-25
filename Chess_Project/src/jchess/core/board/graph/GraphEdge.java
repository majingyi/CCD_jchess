package jchess.core.board.graph;

public abstract class GraphEdge {

	// TODO

	enum EdgeType {
		diagonal, straight
	}

	/**
	 * Return the type of this edge. In the current implementation, there are two types available.
	 * 
	 * @return the edge type
	 */
	public abstract EdgeType getEdgeType();
}