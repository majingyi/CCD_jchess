package jchess.core.board.graph;


public abstract class DirectedGraphEdge extends GraphEdge {

	public enum direction {
		left, right, up, down, leftUp, leftDown, rightUp, rightDown, undefined
	}

	private direction	m_Direction	= direction.undefined;

	private GraphNode	m_StartNode	= null;
	private GraphNode	m_EndNode		= null;

	public DirectedGraphEdge(GraphNode start, GraphNode end, direction dir) {
		super(start, end);

		m_Direction = dir;

		m_StartNode = start;
		m_EndNode = end;
	}

	public direction getDirection() {
		return m_Direction;
	}

	public GraphNode getStartNode() {
		return m_StartNode;
	}

	public GraphNode getEndNode() {
		return m_EndNode;
	}
}