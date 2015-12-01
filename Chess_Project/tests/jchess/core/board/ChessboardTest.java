package jchess.core.board;

import jchess.core.util.Game;

import org.junit.Assert;
import org.junit.Test;

public class ChessboardTest {

	// @Test
	// public void testGetKingForColor() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testGetActiveField() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testGetStraightFieldsChessboardFieldColors() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testGetStraightFieldsChessboardFieldIntColors() throws
	// Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testGetDiagonalFieldsChessboardFieldColors() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testGetDiagonalFieldsChessboardFieldIntColors() throws
	// Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testGetStraightFieldsExact() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testGetDiagonalFieldsExact() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testGetJumpStraightPlusDiagonalFields() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }

	@Test
	public void testIsValidField() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		Assert.assertFalse(Chessboard.isValidField(board, new Hexagon("A1", board)));
		Assert.assertTrue(Chessboard.isValidField(board, (Hexagon) board.getNode("A1")));
	}
}