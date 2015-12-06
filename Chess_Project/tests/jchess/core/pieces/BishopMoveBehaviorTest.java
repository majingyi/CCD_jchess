package jchess.core.pieces;

import java.util.ArrayList;

import org.junit.Test;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Game;
import jchess.core.util.Player;
import jchess.core.util.Player.PlayerColor;
import junit.framework.Assert;

public class BishopMoveBehaviorTest {

	@Test
	public void testAllMoves() throws Exception {

		// Bishop in the center - F3
		Player playerl = new Player("player1", PlayerColor.WHITE);
		Player player2 = new Player("player2", PlayerColor.RED);
		Chessboard board = new Chessboard(null, null);

		Game game = new Game(board, null);
		game.startNewGame();

		// adding the obstacles
		// friend pawn on the way
		Pawn p1 = new Pawn(board, playerl, null);
		board.getField("G5").setPiece(p1);
		// friend pawn blocks only 1 of 2 straight fields on the way
		Pawn p2 = new Pawn(board, playerl, null);
		board.getField("E5").setPiece(p2);
		// two foe pawns on the way block both straight fields on the way
		Pawn p3 = new Pawn(board, player2, null);
		board.getField("C1").setPiece(p3);
		Pawn p4 = new Pawn(board, player2, null);
		board.getField("C2").setPiece(p4);
		// foe pawn on the way
		Pawn p5 = new Pawn(board, player2, null);
		board.getField("B7").setPiece(p5);

		// calculating allowed moves
		BishopMoveBehavior bmb = new BishopMoveBehavior(playerl, board, board.getField("F3"));

		// creating array list of expected fields
		String[] expectedIdentifiers = new String[] { "G2", "H4", "J5", "E4", "D5", "C6", "B7", "D2", "E1" };
		ArrayList<ChessboardField> expectedFields = new ArrayList<ChessboardField>();
		for (String expID : expectedIdentifiers) {
			expectedFields.add(board.getField(expID));
		}

		// comparison
		for (ChessboardField field : expectedFields) {
			Assert.assertTrue(bmb.allMoves().contains(field));
		}
		for (ChessboardField field : bmb.allMoves()) {
			Assert.assertTrue(expectedFields.contains(field));
		}
	}

}
