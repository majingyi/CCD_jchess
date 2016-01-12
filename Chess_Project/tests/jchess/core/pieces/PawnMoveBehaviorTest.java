package jchess.core.pieces;

import java.util.ArrayList;

import org.junit.Test;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Game;
import jchess.core.util.Player;
import jchess.core.util.Player.PlayerColor;
import junit.framework.Assert;

public class PawnMoveBehaviorTest {

	@Test
	public void testAllMoves() throws Exception {

		// Pawn - C5
		Player playerl = new Player("player1", PlayerColor.RED);
		Chessboard board = new Chessboard(null, null);

		Game game = new Game(board, null);
		game.startNewGame();

		// calculating allowed moves
		PawnMoveBehavior pmb1 = new PawnMoveBehavior(playerl, board, board.getField("C5"));

		// creating array list of expected fields
		String[] expectedIdentifiers = new String[] { "C3", "C4", "B3", "A4" };
		ArrayList<ChessboardField> expectedFields = new ArrayList<ChessboardField>();
		for (String expID : expectedIdentifiers) {
			expectedFields.add(board.getField(expID));
		}

		for (ChessboardField field : pmb1.allMoves()) {
			System.out.println(field.getIdentifier());
		}

		// comparison
		for (ChessboardField field : expectedFields) {
			Assert.assertTrue(pmb1.allMoves().contains(field));
		}

		for (ChessboardField field : pmb1.allMoves()) {
			Assert.assertTrue(expectedFields.contains(field));
		}

	}

}
