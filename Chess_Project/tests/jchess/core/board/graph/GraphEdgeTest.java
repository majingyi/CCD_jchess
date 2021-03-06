package jchess.core.board.graph;

import jchess.core.board.graph.DirectedGraphEdge.EdgeDirection;
import junit.framework.Assert;

import org.junit.Test;

/**
 * 
 * @author Maurice
 *
 */
public class GraphEdgeTest {

	@Test
	public void testGetOtherNode() throws Exception {
		GraphNode first = new GraphNode("H2");
		GraphNode second = new GraphNode("F5");
		GraphNode third = new GraphNode("F5");
		DiagonalEdge edge = new DiagonalEdge(first, second, EdgeDirection.LEFT);

		Assert.assertEquals(first, edge.getOtherNode(second));
		Assert.assertEquals(second, edge.getOtherNode(first));
		boolean exception = false;
		try {
			Assert.assertEquals(null, edge.getOtherNode(null));
		} catch (Exception e) {
			exception = true;
			Assert.assertEquals("Given node is not part of this edge.", e.getMessage());
		}
		Assert.assertTrue(exception);

		exception = false;
		try {
			Assert.assertEquals(first, edge.getOtherNode(third));
		} catch (Exception e) {
			exception = true;
			Assert.assertEquals("Given node is not part of this edge.", e.getMessage());
		}
		Assert.assertTrue(exception);

	}

	@Test
	public void testGetEdgeType() throws Exception {
		DiagonalEdge edge = new DiagonalEdge(new GraphNode("H2"), new GraphNode("F5"), EdgeDirection.LEFT);
		Assert.assertEquals(GraphEdge.EdgeType.DIAGONAL, edge.getEdgeType());

		StraightEdge edqe1 = new StraightEdge(new GraphNode("H2"), new GraphNode("F5"), EdgeDirection.LEFT);
		Assert.assertEquals(GraphEdge.EdgeType.STRAIGHT, edqe1.getEdgeType());
	}
}