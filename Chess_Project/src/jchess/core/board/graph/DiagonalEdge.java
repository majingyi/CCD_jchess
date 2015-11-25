package jchess.core.board.graph;

public class DiagonalEdge extends GraphEdge {

	@Override
	public EdgeType getEdgeType() {
		return EdgeType.diagonal;
	}

}
