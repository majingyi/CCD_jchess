package jchess.core.board.graph;

/**
 * 
 * @author Maurice
 *
 */
public abstract class GraphEdge {

	private GraphNode	m_Node1	= null;
	private GraphNode	m_Node2	= null;

	public enum EdgeType {
		DIAGONAL, STRAIGHT
	}

	public GraphEdge(GraphNode node1, GraphNode node2) {
		m_Node1 = node1;
		m_Node2 = node2;
	}

	public GraphNode getOtherNode(GraphNode node) throws Exception {
		GraphNode result = null;
		if (node == m_Node1) {
			result = m_Node2;
		} else if (node == m_Node2) {
			result = m_Node1;
		} else {
			throw new Exception("Given node is not part of this edge.");
		}
		return result;
	}

	/**
	 * Return the type of this edge. In the current implementation, there are two types available.
	 * 
	 * @return the edge type
	 */
	public abstract EdgeType getEdgeType();

}