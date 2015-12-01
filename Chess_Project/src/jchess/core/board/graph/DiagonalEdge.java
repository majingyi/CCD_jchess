package jchess.core.board.graph;

public class DiagonalEdge extends DirectedGraphEdge {

	public DiagonalEdge(GraphNode node1, GraphNode node2, direction dir) {
		super(node1, node2, dir);
	}

	@Override
	public EdgeType getEdgeType() {
		return EdgeType.diagonal;
	}
}