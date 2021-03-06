package jchess.core.pieces;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Game;
import jchess.core.util.Player;
import jchess.core.util.Player.PlayerColor;

public class RookMoveBehaviorTest {

	@Test
	public void testAllMoves() throws Exception {

		// Rook in the center - G5
		Player playerl = new Player("player1", PlayerColor.RED);
		Player player2 = new Player("player2", PlayerColor.BLACK);
		Chessboard board = new Chessboard(null, null);

		Game game = new Game(board, null);
		game.startNewGame();

		// adding the obstacles
		// friend pawn on the way
		Pawn p1 = new Pawn(board, playerl, null);
		board.getField("G7").setPiece(p1);
		// foe pawn on the way
		Pawn p2 = new Pawn(board, player2, null);
		board.getField("E5").setPiece(p2);

		// calculating allowed moves
		RookMoveBehavior rmb = new RookMoveBehavior(playerl, board, board.getField("G5"));

		// creating array list of expected fields
		String[] expectedIdentifiers = new String[] { "G4", "G3", "H5", "I5", "H6", "I7", "J8", "K9", "L10", "M11", "G6", "F5", "E5", "F4", "E3", "D2", "C1" };
		ArrayList<ChessboardField> expectedFields = new ArrayList<ChessboardField>();
		for (String expID : expectedIdentifiers) {
			expectedFields.add(board.getField(expID));
		}

		// comparison
		for (ChessboardField field : expectedFields) {
			Assert.assertTrue(rmb.allMoves().contains(field));
		}

		for (ChessboardField field : rmb.allMoves()) {
			Assert.assertTrue(expectedFields.contains(field));
		}

	}

}
