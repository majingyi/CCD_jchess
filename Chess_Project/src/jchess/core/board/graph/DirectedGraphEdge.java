package jchess.core.board.graph;

public abstract class DirectedGraphEdge extends GraphEdge {

	public enum direction {
		left, right, up, down, leftUp, leftDown, rightUp, rightDown, undefined
	}

	private direction	m_Direction	= direction.undefined;

	public DirectedGraphEdge(GraphNode start, GraphNode end, direction dir) {
		super(start, end);

		m_Direction = dir;
	}

	public direction getDirection() {
		return m_Direction;
	}
}