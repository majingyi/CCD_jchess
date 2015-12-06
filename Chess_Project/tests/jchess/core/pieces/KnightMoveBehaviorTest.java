package jchess.core.pieces;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Game;
import jchess.core.util.Player;
import jchess.core.util.Player.PlayerColor;

public class KnightMoveBehaviorTest {

	@Test
	public void testAllMoves() throws Exception {

		// Rook in the center - D3
		Player playerl = new Player("player1", PlayerColor.WHITE);
		Player player2 = new Player("player2", PlayerColor.RED);
		Chessboard board = new Chessboard(null, null);

		Game game = new Game(board, null);
		game.startNewGame();

		// adding the obstacles
		// checking the jump
		Pawn p1 = new Pawn(board, playerl, null);
		board.getField("D5").setPiece(p1);
		Pawn p2 = new Pawn(board, player2, null);
		board.getField("E5").setPiece(p2);
		Pawn p3 = new Pawn(board, player2, null);
		board.getField("F5").setPiece(p3);

		// calculating allowed moves
		KnightMoveBehavior kmb = new KnightMoveBehavior(playerl, board, board.getField("D3"));

		// creating array list of expected fields
		String[] expectedIdentifiers = new String[] { "E1", "F2", "G4", "G5", "F6", "E6", "C5" };
		ArrayList<ChessboardField> expectedFields = new ArrayList<ChessboardField>();
		for (String expID : expectedIdentifiers) {
			expectedFields.add(board.getField(expID));
		}

		// comparison
		for (ChessboardField field : expectedFields) {
			Assert.assertTrue(kmb.allMoves().contains(field));
		}

		for (ChessboardField field : kmb.allMoves()) {
			Assert.assertTrue(expectedFields.contains(field));
		}

	}

}
