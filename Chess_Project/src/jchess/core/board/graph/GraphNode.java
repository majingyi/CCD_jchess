package jchess.core.board.graph;

public class GraphNode {

	// TODO add edges

	private String	m_Identifier	= null;

	public GraphNode(String identifier) {
		m_Identifier = identifier;
	}

	public String getIdentifier() {
		return m_Identifier;
	}
}