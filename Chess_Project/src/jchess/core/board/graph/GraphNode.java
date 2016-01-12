package jchess.core.board.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Maurice
 *
 */
public class GraphNode {

	private String					m_Identifier	= null;
	private List<GraphEdge>	m_Edges				= new ArrayList<GraphEdge>();

	public GraphNode(String identifier) {
		m_Identifier = identifier;
	}

	public String getIdentifier() {
		return m_Identifier;
	}

	public void addEdge(GraphEdge edge) {
		m_Edges.add(edge);
	}

	public List<GraphEdge> getEdges() {
		return m_Edges;
	}
}