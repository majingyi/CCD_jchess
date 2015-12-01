package jchess.core.board;

import jchess.core.pieces.King;
import jchess.core.util.Game;
import jchess.core.util.Player;
import jchess.core.util.Player.colors;

import org.junit.Assert;
import org.junit.Test;

public class ChessboardTest {

	@Test
	public void testGetKingForColor() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		King red = board.getKingForColor(colors.red);
		Assert.assertNotNull(red);
		Assert.assertEquals(colors.red, red.getPlayer().getColor());

		King black = board.getKingForColor(colors.black);
		Assert.assertNotNull(black);
		Assert.assertEquals(colors.black, black.getPlayer().getColor());

		King white = board.getKingForColor(colors.white);
		Assert.assertNotNull(white);
		Assert.assertEquals(colors.white, white.getPlayer().getColor());

		King nothing = board.getKingForColor(null);
		Assert.assertNull(nothing);
	}

	@Test
	public void testAddKing() throws Exception {
		Chessboard board = new Chessboard(null, null);

		Player red = new Player("hdwf", colors.red);
		board.addKing(new King(board, red, null));

		Player black = new Player("hdwf", colors.black);
		board.addKing(new King(board, black, null));

		Player white = new Player("hdwf", colors.white);
		board.addKing(new King(board, white, null));

		boolean exception = false;
		try {
			red = new Player("hdwf", colors.red);
			board.addKing(new King(board, red, null));
		} catch (Exception e) {
			Assert.assertEquals("King with color red is already existing on this borad.", e.getMessage());
			exception = true;
		}

		Assert.assertTrue(exception);

	}

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