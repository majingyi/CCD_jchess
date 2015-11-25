package jchess.core.board.graph;

public class StraightEdge extends GraphEdge {

	public StraightEdge(GraphNode node1, GraphNode node2) {
		super(node1, node2);
	}

	@Override
	public EdgeType getEdgeType() {
		return EdgeType.straight;
	}
}