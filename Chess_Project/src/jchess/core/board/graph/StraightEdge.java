package jchess.core.board.graph;

public class StraightEdge extends DirectedGraphEdge {

	public StraightEdge(GraphNode node1, GraphNode node2, EdgeDirection dir) {
		super(node1, node2, dir);
	}

	@Override
	public EdgeType getEdgeType() {
		return EdgeType.straight;
	}
}