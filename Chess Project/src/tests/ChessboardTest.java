package tests;

import jchess.Settings;
import jchess.board.Chessboard;
import jchess.pieces.Bishop;
import jchess.pieces.King;
import jchess.pieces.Knight;
import jchess.pieces.Pawn;
import jchess.pieces.Queen;
import jchess.pieces.Rook;
import jchess.util.Constants;
import jchess.util.Player;

import org.junit.Assert;
import org.junit.Test;

public class ChessboardTest {

	@Test
	public void testSetPiecesPlayer1White() throws Exception {
		Chessboard board = new Chessboard(null, new Settings(), null);

		Player white = new Player("hans", Player.colors.white);
		Player black = new Player("wurst", Player.colors.black);

		board.setPieces(Constants.EMPTY_STRING, white, black);

		Assert.assertNotNull(board.squares[0][0]);
		Assert.assertNotNull(board.squares[1][0]);
		Assert.assertNotNull(board.squares[2][0]);
		Assert.assertNotNull(board.squares[3][0]);
		Assert.assertNotNull(board.squares[4][0]);
		Assert.assertNotNull(board.squares[5][0]);
		Assert.assertNotNull(board.squares[6][0]);
		Assert.assertNotNull(board.squares[7][0]);

		Assert.assertTrue((board.squares[0][0].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[1][0].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[2][0].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[3][0].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[4][0].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[5][0].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[6][0].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[7][0].piece.player.color == Player.colors.black));

		Assert.assertTrue((board.squares[0][0].piece.getSymbol() == Rook.SYMBOL));
		Assert.assertTrue((board.squares[1][0].piece.getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.squares[2][0].piece.getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.squares[3][0].piece.getSymbol() == Queen.SYMBOL));
		Assert.assertTrue((board.squares[4][0].piece.getSymbol() == King.SYMBOL));
		Assert.assertTrue((board.squares[5][0].piece.getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.squares[6][0].piece.getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.squares[7][0].piece.getSymbol() == Rook.SYMBOL));

		Assert.assertNotNull(board.squares[0][1]);
		Assert.assertNotNull(board.squares[1][1]);
		Assert.assertNotNull(board.squares[2][1]);
		Assert.assertNotNull(board.squares[3][1]);
		Assert.assertNotNull(board.squares[4][1]);
		Assert.assertNotNull(board.squares[5][1]);
		Assert.assertNotNull(board.squares[6][1]);
		Assert.assertNotNull(board.squares[7][1]);

		Assert.assertTrue((board.squares[0][1].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[1][1].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[2][1].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[3][1].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[4][1].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[5][1].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[6][1].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[7][1].piece.player.color == Player.colors.black));

		Assert.assertTrue((board.squares[0][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[1][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[2][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[3][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[4][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[5][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[6][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[7][1].piece.getSymbol() == Pawn.SYMBOL));

		Assert.assertNotNull(board.squares[0][6]);
		Assert.assertNotNull(board.squares[1][6]);
		Assert.assertNotNull(board.squares[2][6]);
		Assert.assertNotNull(board.squares[3][6]);
		Assert.assertNotNull(board.squares[4][6]);
		Assert.assertNotNull(board.squares[5][6]);
		Assert.assertNotNull(board.squares[6][6]);
		Assert.assertNotNull(board.squares[7][6]);

		Assert.assertTrue((board.squares[0][6].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[1][6].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[2][6].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[3][6].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[4][6].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[5][6].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[6][6].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[7][6].piece.player.color == Player.colors.white));

		Assert.assertTrue((board.squares[0][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[1][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[2][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[3][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[4][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[5][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[6][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[7][6].piece.getSymbol() == Pawn.SYMBOL));

		Assert.assertNotNull(board.squares[0][7]);
		Assert.assertNotNull(board.squares[1][7]);
		Assert.assertNotNull(board.squares[2][7]);
		Assert.assertNotNull(board.squares[3][7]);
		Assert.assertNotNull(board.squares[4][7]);
		Assert.assertNotNull(board.squares[5][7]);
		Assert.assertNotNull(board.squares[6][7]);
		Assert.assertNotNull(board.squares[7][7]);

		Assert.assertTrue((board.squares[0][7].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[1][7].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[2][7].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[3][7].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[4][7].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[5][7].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[6][7].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[7][7].piece.player.color == Player.colors.white));

		Assert.assertTrue((board.squares[0][7].piece.getSymbol() == Rook.SYMBOL));
		Assert.assertTrue((board.squares[1][7].piece.getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.squares[2][7].piece.getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.squares[3][7].piece.getSymbol() == Queen.SYMBOL));
		Assert.assertTrue((board.squares[4][7].piece.getSymbol() == King.SYMBOL));
		Assert.assertTrue((board.squares[5][7].piece.getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.squares[6][7].piece.getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.squares[7][7].piece.getSymbol() == Rook.SYMBOL));
	}

	@Test
	public void testSetPiecesPlayer1WhiteUpsideDown() throws Exception {
		Settings settings = new Settings();
		settings.upsideDown = true;
		Chessboard board = new Chessboard(null, settings, null);

		Player white = new Player("hans", Player.colors.white);
		Player black = new Player("wurst", Player.colors.black);

		board.setPieces(Constants.EMPTY_STRING, white, black);

		Assert.assertNotNull(board.squares[0][0]);
		Assert.assertNotNull(board.squares[1][0]);
		Assert.assertNotNull(board.squares[2][0]);
		Assert.assertNotNull(board.squares[3][0]);
		Assert.assertNotNull(board.squares[4][0]);
		Assert.assertNotNull(board.squares[5][0]);
		Assert.assertNotNull(board.squares[6][0]);
		Assert.assertNotNull(board.squares[7][0]);

		Assert.assertTrue((board.squares[0][0].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[1][0].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[2][0].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[3][0].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[4][0].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[5][0].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[6][0].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[7][0].piece.player.color == Player.colors.white));

		Assert.assertTrue((board.squares[0][0].piece.getSymbol() == Rook.SYMBOL));
		Assert.assertTrue((board.squares[1][0].piece.getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.squares[2][0].piece.getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.squares[3][0].piece.getSymbol() == King.SYMBOL));
		Assert.assertTrue((board.squares[4][0].piece.getSymbol() == Queen.SYMBOL));
		Assert.assertTrue((board.squares[5][0].piece.getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.squares[6][0].piece.getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.squares[7][0].piece.getSymbol() == Rook.SYMBOL));

		Assert.assertNotNull(board.squares[0][1]);
		Assert.assertNotNull(board.squares[1][1]);
		Assert.assertNotNull(board.squares[2][1]);
		Assert.assertNotNull(board.squares[3][1]);
		Assert.assertNotNull(board.squares[4][1]);
		Assert.assertNotNull(board.squares[5][1]);
		Assert.assertNotNull(board.squares[6][1]);
		Assert.assertNotNull(board.squares[7][1]);

		Assert.assertTrue((board.squares[0][1].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[1][1].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[2][1].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[3][1].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[4][1].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[5][1].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[6][1].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[7][1].piece.player.color == Player.colors.white));

		Assert.assertTrue((board.squares[0][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[1][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[2][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[3][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[4][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[5][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[6][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[7][1].piece.getSymbol() == Pawn.SYMBOL));

		Assert.assertNotNull(board.squares[0][6]);
		Assert.assertNotNull(board.squares[1][6]);
		Assert.assertNotNull(board.squares[2][6]);
		Assert.assertNotNull(board.squares[3][6]);
		Assert.assertNotNull(board.squares[4][6]);
		Assert.assertNotNull(board.squares[5][6]);
		Assert.assertNotNull(board.squares[6][6]);
		Assert.assertNotNull(board.squares[7][6]);

		Assert.assertTrue((board.squares[0][6].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[1][6].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[2][6].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[3][6].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[4][6].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[5][6].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[6][6].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[7][6].piece.player.color == Player.colors.black));

		Assert.assertTrue((board.squares[0][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[1][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[2][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[3][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[4][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[5][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[6][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[7][6].piece.getSymbol() == Pawn.SYMBOL));

		Assert.assertNotNull(board.squares[0][7]);
		Assert.assertNotNull(board.squares[1][7]);
		Assert.assertNotNull(board.squares[2][7]);
		Assert.assertNotNull(board.squares[3][7]);
		Assert.assertNotNull(board.squares[4][7]);
		Assert.assertNotNull(board.squares[5][7]);
		Assert.assertNotNull(board.squares[6][7]);
		Assert.assertNotNull(board.squares[7][7]);

		Assert.assertTrue((board.squares[0][7].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[1][7].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[2][7].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[3][7].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[4][7].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[5][7].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[6][7].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[7][7].piece.player.color == Player.colors.black));

		Assert.assertTrue((board.squares[0][7].piece.getSymbol() == Rook.SYMBOL));
		Assert.assertTrue((board.squares[1][7].piece.getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.squares[2][7].piece.getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.squares[3][7].piece.getSymbol() == King.SYMBOL));
		Assert.assertTrue((board.squares[4][7].piece.getSymbol() == Queen.SYMBOL));
		Assert.assertTrue((board.squares[5][7].piece.getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.squares[6][7].piece.getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.squares[7][7].piece.getSymbol() == Rook.SYMBOL));
	}

	@Test
	public void testSetPiecesPlayer2White() throws Exception {
		Chessboard board = new Chessboard(null, new Settings(), null);

		Player white = new Player("hans", Player.colors.white);
		Player black = new Player("wurst", Player.colors.black);

		board.setPieces(Constants.EMPTY_STRING, black, white);

		Assert.assertNotNull(board.squares[0][0]);
		Assert.assertNotNull(board.squares[1][0]);
		Assert.assertNotNull(board.squares[2][0]);
		Assert.assertNotNull(board.squares[3][0]);
		Assert.assertNotNull(board.squares[4][0]);
		Assert.assertNotNull(board.squares[5][0]);
		Assert.assertNotNull(board.squares[6][0]);
		Assert.assertNotNull(board.squares[7][0]);

		Assert.assertTrue((board.squares[0][0].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[1][0].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[2][0].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[3][0].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[4][0].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[5][0].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[6][0].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[7][0].piece.player.color == Player.colors.white));

		Assert.assertTrue((board.squares[0][0].piece.getSymbol() == Rook.SYMBOL));
		Assert.assertTrue((board.squares[1][0].piece.getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.squares[2][0].piece.getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.squares[3][0].piece.getSymbol() == Queen.SYMBOL));
		Assert.assertTrue((board.squares[4][0].piece.getSymbol() == King.SYMBOL));
		Assert.assertTrue((board.squares[5][0].piece.getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.squares[6][0].piece.getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.squares[7][0].piece.getSymbol() == Rook.SYMBOL));

		Assert.assertNotNull(board.squares[0][1]);
		Assert.assertNotNull(board.squares[1][1]);
		Assert.assertNotNull(board.squares[2][1]);
		Assert.assertNotNull(board.squares[3][1]);
		Assert.assertNotNull(board.squares[4][1]);
		Assert.assertNotNull(board.squares[5][1]);
		Assert.assertNotNull(board.squares[6][1]);
		Assert.assertNotNull(board.squares[7][1]);

		Assert.assertTrue((board.squares[0][1].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[1][1].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[2][1].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[3][1].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[4][1].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[5][1].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[6][1].piece.player.color == Player.colors.white));
		Assert.assertTrue((board.squares[7][1].piece.player.color == Player.colors.white));

		Assert.assertTrue((board.squares[0][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[1][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[2][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[3][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[4][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[5][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[6][1].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[7][1].piece.getSymbol() == Pawn.SYMBOL));

		Assert.assertNotNull(board.squares[0][6]);
		Assert.assertNotNull(board.squares[1][6]);
		Assert.assertNotNull(board.squares[2][6]);
		Assert.assertNotNull(board.squares[3][6]);
		Assert.assertNotNull(board.squares[4][6]);
		Assert.assertNotNull(board.squares[5][6]);
		Assert.assertNotNull(board.squares[6][6]);
		Assert.assertNotNull(board.squares[7][6]);

		Assert.assertTrue((board.squares[0][6].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[1][6].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[2][6].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[3][6].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[4][6].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[5][6].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[6][6].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[7][6].piece.player.color == Player.colors.black));

		Assert.assertTrue((board.squares[0][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[1][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[2][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[3][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[4][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[5][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[6][6].piece.getSymbol() == Pawn.SYMBOL));
		Assert.assertTrue((board.squares[7][6].piece.getSymbol() == Pawn.SYMBOL));

		Assert.assertNotNull(board.squares[0][7]);
		Assert.assertNotNull(board.squares[1][7]);
		Assert.assertNotNull(board.squares[2][7]);
		Assert.assertNotNull(board.squares[3][7]);
		Assert.assertNotNull(board.squares[4][7]);
		Assert.assertNotNull(board.squares[5][7]);
		Assert.assertNotNull(board.squares[6][7]);
		Assert.assertNotNull(board.squares[7][7]);

		Assert.assertTrue((board.squares[0][7].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[1][7].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[2][7].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[3][7].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[4][7].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[5][7].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[6][7].piece.player.color == Player.colors.black));
		Assert.assertTrue((board.squares[7][7].piece.player.color == Player.colors.black));

		Assert.assertTrue((board.squares[0][7].piece.getSymbol() == Rook.SYMBOL));
		Assert.assertTrue((board.squares[1][7].piece.getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.squares[2][7].piece.getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.squares[3][7].piece.getSymbol() == Queen.SYMBOL));
		Assert.assertTrue((board.squares[4][7].piece.getSymbol() == King.SYMBOL));
		Assert.assertTrue((board.squares[5][7].piece.getSymbol() == Bishop.SYMBOL));
		Assert.assertTrue((board.squares[6][7].piece.getSymbol() == Knight.SYMBOL));
		Assert.assertTrue((board.squares[7][7].piece.getSymbol() == Rook.SYMBOL));

	}

	@Test
	public void testSetPiecesAllPlayerSameColor() throws Exception {
		Chessboard board = new Chessboard(null, new Settings(), null);

		Player white = new Player("hans", Player.colors.white);
		Player black = new Player("wurst", Player.colors.white);

		boolean exception = false;

		try {
			board.setPieces(Constants.EMPTY_STRING, white, black);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().equals("Both Players have same color"));
			exception = true;
		}

		Assert.assertTrue(exception);
	}

	// @Test
	// public void testMoveIntIntIntInt() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testMoveSquareSquareBoolean() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testSelect() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testUnselect() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testRedo() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testRedoBoolean() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testMoveSquareSquare() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testMoveSquareSquareBooleanBoolean() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testUndo() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testUndoBoolean() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testGetChessboardUI() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testIsout() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }

}
