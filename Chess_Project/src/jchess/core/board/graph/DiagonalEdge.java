package jchess.core.board.graph;

public class DiagonalEdge extends GraphEdge {

	public DiagonalEdge(GraphNode node1, GraphNode node2) {
		super(node1, node2);
	}

	@Override
	public EdgeType getEdgeType() {
		return EdgeType.diagonal;
	}
}