package jchess.core.board.graph;


public class StraightEdge extends GraphEdge {

	@Override
	public EdgeType getEdgeType() {
		return EdgeType.straight;
	}
}