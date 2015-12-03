package jchess.core.board.graph;


public abstract class DirectedGraphEdge extends GraphEdge {

	public enum EdgeDirection {
		left, right, up, down, leftUp, leftDown, rightUp, rightDown, undefined
	}

	private EdgeDirection	m_Direction	= EdgeDirection.undefined;

	private GraphNode	m_StartNode	= null;
	private GraphNode	m_EndNode		= null;

	public DirectedGraphEdge(GraphNode start, GraphNode end, EdgeDirection dir) {
		super(start, end);

		m_Direction = dir;

		m_StartNode = start;
		m_EndNode = end;
	}

	public EdgeDirection getDirection() {
		return m_Direction;
	}

	public GraphNode getStartNode() {
		return m_StartNode;
	}

	public GraphNode getEndNode() {
		return m_EndNode;
	}
}