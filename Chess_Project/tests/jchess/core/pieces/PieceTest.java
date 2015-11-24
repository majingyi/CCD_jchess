package jchess.core.pieces;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.board.Square;
import jchess.core.util.Player;
import junit.framework.Assert;

import org.junit.Test;

public class PieceTest {

	private class TestPiece extends Piece {

		public TestPiece(Chessboard chessboard, Player player, ChessboardField field) throws Exception {
			super(chessboard, player, "TestPiece", field);
		}

		@Override
		public IMoveBehavior createMoveBehavior() {
			return new PawnMoveBehavior(getPlayer(), getChessboard(), getField());
		}
	}

	@Test
	public void testSetGetSymbol() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Piece p = new TestPiece(board, new Player("hans", Player.colors.white), null);
		Assert.assertEquals("T", p.getSymbolForMoveHistory());

		p.setSymbol("K");
		Assert.assertEquals("K", p.getSymbol());

		p.setSymbol("Kn");
		Assert.assertEquals("Kn", p.getSymbol());

		boolean exception = false;
		try {
			p.setSymbol(null);
		} catch (Exception e) {
			Assert.assertEquals("Symbol is not allowed to be null or empty string.", e.getMessage());
			exception = true;
		}
		Assert.assertTrue(exception);

		p.setSymbol("hdwf");
		Assert.assertEquals("hdwf", p.getSymbol());
	}

	@Test
	public void testGetSquare() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Piece p = new TestPiece(board, new Player("hans", Player.colors.white), null);

		Assert.assertEquals(null, p.getField());

		// setSquare outside the board left
		boolean exception = false;
		try {
			p.setField(new Square(8, 0, null, board));
		} catch (Exception e) {
			Assert.assertEquals("Given square is outside the board borders.", e.getMessage());
			exception = true;
		}
		Assert.assertTrue(exception);

		// setSquare outside the board right
		exception = false;
		try {
			p.setField(new Square(-1, 0, null, board));
		} catch (Exception e) {
			Assert.assertEquals("Given square is outside the board borders.", e.getMessage());
			exception = true;
		}
		Assert.assertTrue(exception);

		// setSquare outside the board top
		exception = false;
		try {
			p.setField(new Square(0, -1, null, board));
		} catch (Exception e) {
			Assert.assertEquals("Given square is outside the board borders.", e.getMessage());
			exception = true;
		}
		Assert.assertTrue(exception);

		// setSquare outside the board bottom
		exception = false;
		try {
			p.setField(new Square(0, 8, null, board));
		} catch (Exception e) {
			Assert.assertEquals("Given square is outside the board borders.", e.getMessage());
			exception = true;
		}
		Assert.assertTrue(exception);

		// set valid square
		p.setField(new Square(3, 5, null, board));
		Square sq = (Square) p.getField();
		Assert.assertNotNull(sq);
		Assert.assertEquals(5, sq.pozY);
		Assert.assertEquals(3, sq.pozX);
	}

	@Test
	public void testGetSymbolForMoveHistory() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Piece p = new TestPiece(board, new Player("hans", Player.colors.white), null);
		Assert.assertEquals("T", p.getSymbolForMoveHistory());

		p.setSymbol("K");
		Assert.assertEquals("K", p.getSymbolForMoveHistory());

		p.setSymbol("Kn");
		Assert.assertEquals("Kn", p.getSymbolForMoveHistory());

		p.setSymbol("hdwf");
		Assert.assertEquals("h", p.getSymbolForMoveHistory());
	}
}