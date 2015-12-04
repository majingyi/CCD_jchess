package jchess.core.board;

import java.util.List;

import jchess.core.board.graph.DirectedGraphEdge.EdgeDirection;
import jchess.core.pieces.King;
import jchess.core.pieces.Rook;
import jchess.core.util.Constants;
import jchess.core.util.Game;
import jchess.core.util.Player;
import jchess.core.util.Player.PlayerColor;

import org.junit.Assert;
import org.junit.Test;

public class ChessboardTest {

	@Test
	public void testGetKingForColor() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		King red = board.getKingForColor(PlayerColor.RED);
		Assert.assertNotNull(red);
		Assert.assertEquals(PlayerColor.RED, red.getPlayer().getColor());

		King black = board.getKingForColor(PlayerColor.BLACK);
		Assert.assertNotNull(black);
		Assert.assertEquals(PlayerColor.BLACK, black.getPlayer().getColor());

		King white = board.getKingForColor(PlayerColor.WHITE);
		Assert.assertNotNull(white);
		Assert.assertEquals(PlayerColor.WHITE, white.getPlayer().getColor());

		King nothing = board.getKingForColor(null);
		Assert.assertNull(nothing);
	}

	@Test
	public void testGetRooksForColor() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		List<Rook> red = board.getRooksForColor(PlayerColor.RED);
		Assert.assertNotNull(red);
		Assert.assertEquals(2, red.size());

		Rook first = red.get(0);
		Assert.assertEquals("F13", first.getField().getIdentifier());
		Assert.assertEquals(PlayerColor.RED, first.getPlayer().getColor());
		Rook second = red.get(1);
		Assert.assertEquals("M13", second.getField().getIdentifier());
		Assert.assertEquals(PlayerColor.RED, second.getPlayer().getColor());

		List<Rook> black = board.getRooksForColor(PlayerColor.BLACK);
		Assert.assertNotNull(black);
		Assert.assertEquals(2, black.size());

		first = black.get(0);
		Assert.assertEquals("F1", first.getField().getIdentifier());
		Assert.assertEquals(PlayerColor.BLACK, first.getPlayer().getColor());
		second = black.get(1);
		Assert.assertEquals("M8", second.getField().getIdentifier());
		Assert.assertEquals(PlayerColor.BLACK, second.getPlayer().getColor());

		List<Rook> white = board.getRooksForColor(PlayerColor.WHITE);
		Assert.assertNotNull(white);
		Assert.assertEquals(2, white.size());

		first = white.get(0);
		Assert.assertEquals("A1", first.getField().getIdentifier());
		Assert.assertEquals(PlayerColor.WHITE, first.getPlayer().getColor());
		second = white.get(1);
		Assert.assertEquals("A8", second.getField().getIdentifier());
		Assert.assertEquals(PlayerColor.WHITE, second.getPlayer().getColor());
	}

	@Test
	public void testAddKing() throws Exception {
		Chessboard board = new Chessboard(null, null);

		Player red = new Player("hdwf", PlayerColor.RED);
		board.addKing(new King(board, red, null));

		Player black = new Player("hdwf", PlayerColor.BLACK);
		board.addKing(new King(board, black, null));

		Player white = new Player("hdwf", PlayerColor.WHITE);
		board.addKing(new King(board, white, null));

		boolean exception = false;
		try {
			red = new Player("hdwf", PlayerColor.RED);
			board.addKing(new King(board, red, null));
		} catch (Exception e) {
			Assert.assertEquals("King with color red is already existing on this borad.", e.getMessage());
			exception = true;
		}

		Assert.assertTrue(exception);

	}

	@Test
	public void testIsValidField() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		Assert.assertFalse(Chessboard.isValidField(board, new Hexagon("A1", board)));
		Assert.assertTrue(Chessboard.isValidField(board, (Hexagon) board.getNode("A1")));
	}

	@Test
	public void testGetStraightFields() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		ChessboardField g7 = board.getField("G7");
		List<ChessboardField> fields = board.getStraightFields(g7, null);
		String[] expectedFields = new String[] { "B7", "C7", "D7", "E7", "F7", "H7", "I7", "J7", "K7", "B2", "C3", "D4", "E5", "F6", "H8", "I9", "J10", "K11",
				"L12", "G3", "G4", "G5", "G6", "G8", "G9", "G10", "G11", "G12" };
		Assert.assertEquals(28, fields.size());
		checkFieldsInList(fields, expectedFields, board);

		ChessboardField m13 = board.getField("M13");
		fields = board.getStraightFields(m13, null);
		expectedFields = new String[] { "M12", "L12", "L13" };
		Assert.assertEquals(3, fields.size());
		checkFieldsInList(fields, expectedFields, board);

		g7 = board.getField("G7");
		fields = board.getStraightFields(g7, PlayerColor.WHITE);
		expectedFields = new String[] { "C7", "D7", "E7", "F7", "H7", "I7", "J7", "K7", "C3", "D4", "E5", "F6", "H8", "I9", "J10", "K11", "L12", "G3", "G4", "G5",
				"G6", "G8", "G9", "G10", "G11", "G12" };
		Assert.assertEquals(26, fields.size());
		checkFieldsInList(fields, expectedFields, board);

		m13 = board.getField("M13");
		fields = board.getStraightFields(m13, PlayerColor.RED);
		expectedFields = new String[] {};
		Assert.assertEquals(0, fields.size());
		checkFieldsInList(fields, expectedFields, board);
	}

	@Test
	public void testGetStraightFieldsWithMaxDepth() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		ChessboardField g7 = board.getField("G7");
		List<ChessboardField> fields = board.getStraightFields(g7, 2, null);
		String[] expectedFields = new String[] { "E7", "F7", "H7", "I7", "E5", "F6", "H8", "I9", "G5", "G6", "G8", "G9" };
		Assert.assertEquals(12, fields.size());
		checkFieldsInList(fields, expectedFields, board);

		g7 = board.getField("G7");
		fields = board.getStraightFields(g7, 1, null);
		expectedFields = new String[] { "F7", "H7", "F6", "H8", "G6", "G8" };
		Assert.assertEquals(6, fields.size());
		checkFieldsInList(fields, expectedFields, board);
	}

	@Test
	public void testGetDiagonalFields() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		ChessboardField g7 = board.getField("G7");
		List<ChessboardField> fields = board.getDiagonalFields(g7, null);
		String[] expectedFields = new String[] { "D1", "E3", "F5", "H9", "I11", "I5", "H6", "F8", "E9", "D10", "C5", "E6", "I8", "K9", "M10" };
		Assert.assertEquals(15, fields.size());
		checkFieldsInList(fields, expectedFields, board);

		ChessboardField m13 = board.getField("M13");
		fields = board.getDiagonalFields(m13, null);
		expectedFields = new String[] {};
		Assert.assertEquals(0, fields.size());
		checkFieldsInList(fields, expectedFields, board);

		g7 = board.getField("G7");
		fields = board.getDiagonalFields(g7, PlayerColor.WHITE);
		expectedFields = new String[] { "D1", "E3", "F5", "H9", "I11", "I5", "H6", "F8", "E9", "D10", "C5", "E6", "I8", "K9", "M10" };
		Assert.assertEquals(15, fields.size());
		checkFieldsInList(fields, expectedFields, board);
	}

	@Test
	public void testGetDiagonalFieldsMaxDepth() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		ChessboardField g7 = board.getField("G7");
		List<ChessboardField> fields = board.getDiagonalFields(g7, 3, null);
		String[] expectedFields = new String[] { "D1", "E3", "F5", "H9", "I11", "I5", "H6", "F8", "E9", "D10", "C5", "E6", "I8", "K9", "M10" };
		Assert.assertEquals(15, fields.size());
		checkFieldsInList(fields, expectedFields, board);

		ChessboardField m13 = board.getField("M13");
		fields = board.getDiagonalFields(m13, null);
		expectedFields = new String[] {};
		Assert.assertEquals(0, fields.size());
		checkFieldsInList(fields, expectedFields, board);

		g7 = board.getField("G7");
		fields = board.getDiagonalFields(g7, 2, PlayerColor.WHITE);
		expectedFields = new String[] { "E3", "F5", "H9", "I11", "I5", "H6", "F8", "E9", "C5", "E6", "I8", "K9" };
		Assert.assertEquals(12, fields.size());
		checkFieldsInList(fields, expectedFields, board);

		g7 = board.getField("G7");
		fields = board.getDiagonalFields(g7, 1, PlayerColor.WHITE);
		expectedFields = new String[] { "F5", "H9", "H6", "F8", "E6", "I8" };
		Assert.assertEquals(6, fields.size());
		checkFieldsInList(fields, expectedFields, board);
	}

	private void checkFieldsInList(List<ChessboardField> fields, String[] expectedFields, Chessboard board) {
		Assert.assertEquals(fields.size(), expectedFields.length);
		for (String expectedField : expectedFields) {
			ChessboardField field = board.getField(expectedField);
			Assert.assertTrue("Expected: " + expectedField + ", but was: " + dumpExpectedFields(fields), fields.contains(field));
		}
	}

	private String dumpExpectedFields(List<ChessboardField> fields) {
		StringBuffer buffer = new StringBuffer();

		for (ChessboardField exprected : fields) {
			buffer.append(exprected.getIdentifier() + Constants.COMMA_STRING);
		}

		return buffer.toString();
	}

	@Test
	public void testGetStraightFieldsExact() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		ChessboardField g7 = board.getField("G7");
		List<ChessboardField> fields = board.getStraightFieldsExact(g7, 3);
		String[] expectedFields = new String[] { "G4", "D4", "J7", "D7", "G10", "J10" };
		Assert.assertEquals(6, fields.size());
		checkFieldsInList(fields, expectedFields, board);
	}

	@Test
	public void testGetDiagonalFieldsExact() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		ChessboardField g7 = board.getField("G7");
		List<ChessboardField> fields = board.getDiagonalFieldsExact(g7, 3);
		String[] expectedFields = new String[] { "D1", "M10", "D10", "A4", "J13" };
		Assert.assertEquals(5, fields.size());
		checkFieldsInList(fields, expectedFields, board);
	}

	@Test
	public void testGetJumpStraightPlusDiagonalFields() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		ChessboardField g7 = board.getField("G7");
		List<ChessboardField> fields = board.getJumpStraightPlusDiagonalFields(g7, PlayerColor.RED, 1, 1);
		String[] expectedFields = new String[] { "D5", "D6", "E4", "E8", "F4", "F9", "H5", "H10", "I6", "I10", "J8", "J9" };
		Assert.assertEquals(12, fields.size());
		checkFieldsInList(fields, expectedFields, board);

		ChessboardField g10 = board.getField("G10");
		fields = board.getJumpStraightPlusDiagonalFields(g10, PlayerColor.RED, 1, 1);
		expectedFields = new String[] { "D8", "D9", "E7", "E11", "F7", "H8", "I9", "J11" };
		Assert.assertEquals(8, fields.size());
		checkFieldsInList(fields, expectedFields, board);
	}

	@Test
	public void testGetDirectionFromPlayersPOV() throws Exception {
		Chessboard board = new Chessboard(null, null);

		Assert.assertEquals(EdgeDirection.UP, board.getDirectionFromPlayersPOV(PlayerColor.WHITE, EdgeDirection.DOWN));
		Assert.assertEquals(EdgeDirection.DOWN, board.getDirectionFromPlayersPOV(PlayerColor.WHITE, EdgeDirection.UP));
		Assert.assertEquals(EdgeDirection.RIGHT, board.getDirectionFromPlayersPOV(PlayerColor.WHITE, EdgeDirection.LEFT));
		Assert.assertEquals(EdgeDirection.RIGHT_UP, board.getDirectionFromPlayersPOV(PlayerColor.WHITE, EdgeDirection.LEFT_DOWN));
		Assert.assertEquals(EdgeDirection.RIGHT_DOWN, board.getDirectionFromPlayersPOV(PlayerColor.WHITE, EdgeDirection.LEFT_UP));
		Assert.assertEquals(EdgeDirection.LEFT, board.getDirectionFromPlayersPOV(PlayerColor.WHITE, EdgeDirection.RIGHT));
		Assert.assertEquals(EdgeDirection.LEFT_UP, board.getDirectionFromPlayersPOV(PlayerColor.WHITE, EdgeDirection.RIGHT_DOWN));
		Assert.assertEquals(EdgeDirection.LEFT_DOWN, board.getDirectionFromPlayersPOV(PlayerColor.WHITE, EdgeDirection.RIGHT_UP));

		Assert.assertEquals(EdgeDirection.RIGHT_DOWN, board.getDirectionFromPlayersPOV(PlayerColor.BLACK, EdgeDirection.DOWN));
		Assert.assertEquals(EdgeDirection.LEFT_UP, board.getDirectionFromPlayersPOV(PlayerColor.BLACK, EdgeDirection.UP));
		Assert.assertEquals(EdgeDirection.LEFT_DOWN, board.getDirectionFromPlayersPOV(PlayerColor.BLACK, EdgeDirection.LEFT));
		Assert.assertEquals(EdgeDirection.RIGHT_DOWN, board.getDirectionFromPlayersPOV(PlayerColor.BLACK, EdgeDirection.LEFT_DOWN));
		Assert.assertEquals(EdgeDirection.LEFT, board.getDirectionFromPlayersPOV(PlayerColor.BLACK, EdgeDirection.LEFT_UP));
		Assert.assertEquals(EdgeDirection.RIGHT_UP, board.getDirectionFromPlayersPOV(PlayerColor.BLACK, EdgeDirection.RIGHT));
		Assert.assertEquals(EdgeDirection.RIGHT, board.getDirectionFromPlayersPOV(PlayerColor.BLACK, EdgeDirection.RIGHT_DOWN));
		Assert.assertEquals(EdgeDirection.LEFT_UP, board.getDirectionFromPlayersPOV(PlayerColor.BLACK, EdgeDirection.RIGHT_UP));

		Assert.assertEquals(EdgeDirection.LEFT_DOWN, board.getDirectionFromPlayersPOV(PlayerColor.RED, EdgeDirection.DOWN));
		Assert.assertEquals(EdgeDirection.RIGHT_UP, board.getDirectionFromPlayersPOV(PlayerColor.RED, EdgeDirection.UP));
		Assert.assertEquals(EdgeDirection.LEFT_UP, board.getDirectionFromPlayersPOV(PlayerColor.RED, EdgeDirection.LEFT));
		Assert.assertEquals(EdgeDirection.LEFT, board.getDirectionFromPlayersPOV(PlayerColor.RED, EdgeDirection.LEFT_DOWN));
		Assert.assertEquals(EdgeDirection.RIGHT_UP, board.getDirectionFromPlayersPOV(PlayerColor.RED, EdgeDirection.LEFT_UP));
		Assert.assertEquals(EdgeDirection.RIGHT_DOWN, board.getDirectionFromPlayersPOV(PlayerColor.RED, EdgeDirection.RIGHT));
		Assert.assertEquals(EdgeDirection.LEFT_DOWN, board.getDirectionFromPlayersPOV(PlayerColor.RED, EdgeDirection.RIGHT_DOWN));
		Assert.assertEquals(EdgeDirection.RIGHT, board.getDirectionFromPlayersPOV(PlayerColor.RED, EdgeDirection.RIGHT_UP));

	}
}