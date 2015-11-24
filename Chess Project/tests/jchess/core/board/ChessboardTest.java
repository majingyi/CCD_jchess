package jchess.core.board;

import jchess.core.pieces.Bishop;
import jchess.core.pieces.King;
import jchess.core.pieces.Knight;
import jchess.core.pieces.Pawn;
import jchess.core.pieces.Queen;
import jchess.core.pieces.Rook;
import jchess.core.util.Constants;
import jchess.core.util.Player;

import org.junit.Assert;
import org.junit.Test;

public class ChessboardTest {

	@Test
	public void testSetPiecesPlayer1White() throws Exception {
		Chessboard board = new Chessboard(null, null);

		Player white = new Player("hans", Player.colors.white);
		Player black = new Player("wurst", Player.colors.black);

		board.setPieces(Constants.EMPTY_STRING, white, black);

		Assert.assertNotNull(board.getFields()[0][0]);
		Assert.assertNotNull(board.getFields()[1][0]);
		Assert.assertNotNull(board.getFields()[2][0]);
		Assert.assertNotNull(board.getFields()[3][0]);
		Assert.assertNotNull(board.getFields()[4][0]);
		Assert.assertNotNull(board.getFields()[5][0]);
		Assert.assertNotNull(board.getFields()[6][0]);
		Assert.assertNotNull(board.getFields()[7][0]);

		Assert.assertTrue((board.getFields()[0][0].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[1][0].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[2][0].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[3][0].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[4][0].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[5][0].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[6][0].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[7][0].getPiece().player.getColor() == Player.colors.black));

		Assert.assertTrue((board.getFields()[0][0].getPiece().getSymbol() == Rook.SYMBOL));
		Assert.assertTrue((board.getFields()[1][0].getPiece().getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.getFields()[2][0].getPiece().getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.getFields()[3][0].getPiece().getSymbol() == Queen.SYMBOL));
		Assert.assertTrue((board.getFields()[4][0].getPiece().getSymbol() == King.SYMBOL));
		Assert.assertTrue((board.getFields()[5][0].getPiece().getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.getFields()[6][0].getPiece().getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.getFields()[7][0].getPiece().getSymbol() == Rook.SYMBOL));

		Assert.assertNotNull(board.getFields()[0][1]);
		Assert.assertNotNull(board.getFields()[1][1]);
		Assert.assertNotNull(board.getFields()[2][1]);
		Assert.assertNotNull(board.getFields()[3][1]);
		Assert.assertNotNull(board.getFields()[4][1]);
		Assert.assertNotNull(board.getFields()[5][1]);
		Assert.assertNotNull(board.getFields()[6][1]);
		Assert.assertNotNull(board.getFields()[7][1]);

		Assert.assertTrue((board.getFields()[0][1].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[1][1].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[2][1].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[3][1].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[4][1].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[5][1].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[6][1].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[7][1].getPiece().player.getColor() == Player.colors.black));

		Assert.assertTrue((board.getFields()[0][1].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[1][1].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[2][1].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[3][1].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[4][1].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[5][1].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[6][1].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[7][1].getPiece().getSymbol() == Pawn.SYMBOL));

		Assert.assertNotNull(board.getFields()[0][6]);
		Assert.assertNotNull(board.getFields()[1][6]);
		Assert.assertNotNull(board.getFields()[2][6]);
		Assert.assertNotNull(board.getFields()[3][6]);
		Assert.assertNotNull(board.getFields()[4][6]);
		Assert.assertNotNull(board.getFields()[5][6]);
		Assert.assertNotNull(board.getFields()[6][6]);
		Assert.assertNotNull(board.getFields()[7][6]);

		Assert.assertTrue((board.getFields()[0][6].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[1][6].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[2][6].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[3][6].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[4][6].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[5][6].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[6][6].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[7][6].getPiece().player.getColor() == Player.colors.white));

		Assert.assertTrue((board.getFields()[0][6].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[1][6].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[2][6].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[3][6].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[4][6].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[5][6].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[6][6].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[7][6].getPiece().getSymbol() == Pawn.SYMBOL));

		Assert.assertNotNull(board.getFields()[0][7]);
		Assert.assertNotNull(board.getFields()[1][7]);
		Assert.assertNotNull(board.getFields()[2][7]);
		Assert.assertNotNull(board.getFields()[3][7]);
		Assert.assertNotNull(board.getFields()[4][7]);
		Assert.assertNotNull(board.getFields()[5][7]);
		Assert.assertNotNull(board.getFields()[6][7]);
		Assert.assertNotNull(board.getFields()[7][7]);

		Assert.assertTrue((board.getFields()[0][7].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[1][7].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[2][7].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[3][7].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[4][7].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[5][7].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[6][7].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[7][7].getPiece().player.getColor() == Player.colors.white));

		Assert.assertTrue((board.getFields()[0][7].getPiece().getSymbol() == Rook.SYMBOL));
		Assert.assertTrue((board.getFields()[1][7].getPiece().getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.getFields()[2][7].getPiece().getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.getFields()[3][7].getPiece().getSymbol() == Queen.SYMBOL));
		Assert.assertTrue((board.getFields()[4][7].getPiece().getSymbol() == King.SYMBOL));
		Assert.assertTrue((board.getFields()[5][7].getPiece().getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.getFields()[6][7].getPiece().getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.getFields()[7][7].getPiece().getSymbol() == Rook.SYMBOL));
	}

	@Test
	public void testSetPiecesPlayer2White() throws Exception {
		Chessboard board = new Chessboard(null, null);

		Player white = new Player("hans", Player.colors.white);
		Player black = new Player("wurst", Player.colors.black);

		board.setPieces(Constants.EMPTY_STRING, black, white);

		Assert.assertNotNull(board.getFields()[0][0]);
		Assert.assertNotNull(board.getFields()[1][0]);
		Assert.assertNotNull(board.getFields()[2][0]);
		Assert.assertNotNull(board.getFields()[3][0]);
		Assert.assertNotNull(board.getFields()[4][0]);
		Assert.assertNotNull(board.getFields()[5][0]);
		Assert.assertNotNull(board.getFields()[6][0]);
		Assert.assertNotNull(board.getFields()[7][0]);

		Assert.assertTrue((board.getFields()[0][0].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[1][0].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[2][0].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[3][0].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[4][0].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[5][0].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[6][0].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[7][0].getPiece().player.getColor() == Player.colors.white));

		Assert.assertTrue((board.getFields()[0][0].getPiece().getSymbol() == Rook.SYMBOL));
		Assert.assertTrue((board.getFields()[1][0].getPiece().getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.getFields()[2][0].getPiece().getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.getFields()[3][0].getPiece().getSymbol() == Queen.SYMBOL));
		Assert.assertTrue((board.getFields()[4][0].getPiece().getSymbol() == King.SYMBOL));
		Assert.assertTrue((board.getFields()[5][0].getPiece().getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.getFields()[6][0].getPiece().getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.getFields()[7][0].getPiece().getSymbol() == Rook.SYMBOL));

		Assert.assertNotNull(board.getFields()[0][1]);
		Assert.assertNotNull(board.getFields()[1][1]);
		Assert.assertNotNull(board.getFields()[2][1]);
		Assert.assertNotNull(board.getFields()[3][1]);
		Assert.assertNotNull(board.getFields()[4][1]);
		Assert.assertNotNull(board.getFields()[5][1]);
		Assert.assertNotNull(board.getFields()[6][1]);
		Assert.assertNotNull(board.getFields()[7][1]);

		Assert.assertTrue((board.getFields()[0][1].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[1][1].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[2][1].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[3][1].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[4][1].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[5][1].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[6][1].getPiece().player.getColor() == Player.colors.white));
		Assert.assertTrue((board.getFields()[7][1].getPiece().player.getColor() == Player.colors.white));

		Assert.assertTrue((board.getFields()[0][1].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[1][1].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[2][1].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[3][1].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[4][1].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[5][1].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[6][1].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[7][1].getPiece().getSymbol() == Pawn.SYMBOL));

		Assert.assertNotNull(board.getFields()[0][6]);
		Assert.assertNotNull(board.getFields()[1][6]);
		Assert.assertNotNull(board.getFields()[2][6]);
		Assert.assertNotNull(board.getFields()[3][6]);
		Assert.assertNotNull(board.getFields()[4][6]);
		Assert.assertNotNull(board.getFields()[5][6]);
		Assert.assertNotNull(board.getFields()[6][6]);
		Assert.assertNotNull(board.getFields()[7][6]);

		Assert.assertTrue((board.getFields()[0][6].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[1][6].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[2][6].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[3][6].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[4][6].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[5][6].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[6][6].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[7][6].getPiece().player.getColor() == Player.colors.black));

		Assert.assertTrue((board.getFields()[0][6].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[1][6].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[2][6].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[3][6].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[4][6].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[5][6].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[6][6].getPiece().getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.getFields()[7][6].getPiece().getSymbol() == Pawn.SYMBOL));

		Assert.assertNotNull(board.getFields()[0][7]);
		Assert.assertNotNull(board.getFields()[1][7]);
		Assert.assertNotNull(board.getFields()[2][7]);
		Assert.assertNotNull(board.getFields()[3][7]);
		Assert.assertNotNull(board.getFields()[4][7]);
		Assert.assertNotNull(board.getFields()[5][7]);
		Assert.assertNotNull(board.getFields()[6][7]);
		Assert.assertNotNull(board.getFields()[7][7]);

		Assert.assertTrue((board.getFields()[0][7].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[1][7].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[2][7].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[3][7].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[4][7].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[5][7].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[6][7].getPiece().player.getColor() == Player.colors.black));
		Assert.assertTrue((board.getFields()[7][7].getPiece().player.getColor() == Player.colors.black));

		Assert.assertTrue((board.getFields()[0][7].getPiece().getSymbol() == Rook.SYMBOL));
		Assert.assertTrue((board.getFields()[1][7].getPiece().getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.getFields()[2][7].getPiece().getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.getFields()[3][7].getPiece().getSymbol() == Queen.SYMBOL));
		Assert.assertTrue((board.getFields()[4][7].getPiece().getSymbol() == King.SYMBOL));
		Assert.assertTrue((board.getFields()[5][7].getPiece().getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.getFields()[6][7].getPiece().getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.getFields()[7][7].getPiece().getSymbol() == Rook.SYMBOL));

	}

	@Test
	public void testSetPiecesAllPlayerSameColor() throws Exception {
		Chessboard board = new Chessboard(null, null);

		Player white = new Player("hans", Player.colors.white);
		Player black = new Player("wurst", Player.colors.white);

		boolean exception = false;

		try {
			board.setPieces(Constants.EMPTY_STRING, white, black);
		} catch (Exception e) {
			Assert.assertEquals("Both player have the same color.", e.getMessage());
			exception = true;
		}

		Assert.assertTrue(exception);
	}
}