import jchess.Settings;
import jchess.board.Chessboard;
import jchess.pieces.IMoveBehavior;
import jchess.pieces.PawnMoveBehavior;
import jchess.pieces.Piece;
import jchess.util.Player;
import jchess.util.Square;
import junit.framework.Assert;

import org.junit.Test;

public class PieceTest {

	private class TestPiece extends Piece {

		public TestPiece(Chessboard chessboard, Player player) throws Exception {
			super(chessboard, player, "TestPiece");
		}

		@Override
		public IMoveBehavior createMoveBehavior() {
			return new PawnMoveBehavior(player, chessboard, null);
		}
	}

	@Test
	public void testSetGetSymbol() throws Exception {
		Chessboard board = new Chessboard(null, new Settings(), null);
		Piece p = new TestPiece(board, new Player("hans", Player.colors.white));
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
		Chessboard board = new Chessboard(null, new Settings(), null);
		Piece p = new TestPiece(board, new Player("hans", Player.colors.white));

		Assert.assertEquals(null, p.getSquare());

		// setSquare outside the board left
		boolean exception = false;
		try {
			p.setSquare(new Square(8, 0, null));
		} catch (Exception e) {
			Assert.assertEquals("Given square is outside the board borders.", e.getMessage());
			exception = true;
		}
		Assert.assertTrue(exception);

		// setSquare outside the board right
		exception = false;
		try {
			p.setSquare(new Square(-1, 0, null));
		} catch (Exception e) {
			Assert.assertEquals("Given square is outside the board borders.", e.getMessage());
			exception = true;
		}
		Assert.assertTrue(exception);

		// setSquare outside the board top
		exception = false;
		try {
			p.setSquare(new Square(0, -1, null));
		} catch (Exception e) {
			Assert.assertEquals("Given square is outside the board borders.", e.getMessage());
			exception = true;
		}
		Assert.assertTrue(exception);

		// setSquare outside the board bottom
		exception = false;
		try {
			p.setSquare(new Square(0, 8, null));
		} catch (Exception e) {
			Assert.assertEquals("Given square is outside the board borders.", e.getMessage());
			exception = true;
		}
		Assert.assertTrue(exception);

		// set valid square
		p.setSquare(new Square(3, 5, null));
		Square sq = p.getSquare();
		Assert.assertNotNull(sq);
		Assert.assertEquals(5, sq.pozY);
		Assert.assertEquals(3, sq.pozX);

	}

	@Test
	public void testGetSymbolForMoveHistory() throws Exception {
		Chessboard board = new Chessboard(null, new Settings(), null);
		Piece p = new TestPiece(board, new Player("hans", Player.colors.white));
		Assert.assertEquals("T", p.getSymbolForMoveHistory());

		p.setSymbol("K");
		Assert.assertEquals("K", p.getSymbolForMoveHistory());

		p.setSymbol("Kn");
		Assert.assertEquals("Kn", p.getSymbolForMoveHistory());

		p.setSymbol("hdwf");
		Assert.assertEquals("h", p.getSymbolForMoveHistory());
	}
}