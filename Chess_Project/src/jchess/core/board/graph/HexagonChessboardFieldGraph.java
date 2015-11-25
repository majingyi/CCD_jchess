package jchess.core.board.graph;

import java.util.HashMap;
import java.util.Map;

public abstract class HexagonChessboardFieldGraph {

	private Map<String, GraphNode>	m_NodeMap	= new HashMap<String, GraphNode>();

	/**
	 * Return the node identified with the given identifier.
	 * 
	 * @param identifier
	 * @return the node identified with the given identifier or null;
	 */
	public GraphNode getNode(String identifier) {
		return m_NodeMap.get(identifier);
	}

	/**
	 * Adds a node to this graph.
	 * 
	 * @param node
	 */
	public void addNode(GraphNode node) {
		m_NodeMap.put(node.getIdentifier(), node);
	}
}