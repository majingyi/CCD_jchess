package jchess.core.board.graph;

/**
 * 
 * @author Maurice
 *
 */
public abstract class DirectedGraphEdge extends GraphEdge {

	public enum EdgeDirection {
		LEFT, RIGHT, UP, DOWN, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN, UNDEFINED
	}

	private EdgeDirection	m_Direction	= EdgeDirection.UNDEFINED;

	private GraphNode			m_StartNode	= null;
	private GraphNode			m_EndNode		= null;

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