package jchess.core.pieces;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Game;
import jchess.core.util.Player;
import jchess.core.util.Player.PlayerColor;

public class QueenMoveBehaviorTest {

	@Test
	public void testAllMoves() throws Exception {

		// Queen in the center - F3
		Player playerl = new Player("player1", PlayerColor.BLACK);
		Player player2 = new Player("player2", PlayerColor.WHITE);
		Chessboard board = new Chessboard(null, null);

		Game game = new Game(board, null);
		game.startNewGame();

		// adding the obstacles
		// friend pawns on the way
		Pawn p1 = new Pawn(board, playerl, null);
		board.getField("G5").setPiece(p1);
		Pawn p6 = new Pawn(board, playerl, null);
		board.getField("F5").setPiece(p6);
		Pawn p7 = new Pawn(board, playerl, null);
		board.getField("I6").setPiece(p7);
		// friend pawn blocks only 1 of 2 straight fields on the way
		Pawn p2 = new Pawn(board, playerl, null);
		board.getField("E5").setPiece(p2);
		// two foe pawns on the way block both straight fields on the way
		Pawn p3 = new Pawn(board, player2, null);
		board.getField("C1").setPiece(p3);
		Pawn p4 = new Pawn(board, player2, null);
		board.getField("C2").setPiece(p4);
		// foe pawns on the way
		Pawn p5 = new Pawn(board, player2, null);
		board.getField("B7").setPiece(p5);
		Pawn p8 = new Pawn(board, player2, null);
		board.getField("B3").setPiece(p8);

		// calculating allowed moves
		QueenMoveBehavior qmb = new QueenMoveBehavior(playerl, board, board.getField("F3"));

		// creating array list of expected fields
		String[] expectedIdentifiers = new String[] { "G4", "H5", "F4", "E3", "D3", "C3", "B3", "E2", "D1", "E4", "D5", "C6", "B7", "D2" };
		ArrayList<ChessboardField> expectedFields = new ArrayList<ChessboardField>();
		for (String expID : expectedIdentifiers) {
			expectedFields.add(board.getField(expID));
		}

		// comparison
		for (ChessboardField field : expectedFields) {
			Assert.assertTrue(qmb.allMoves().contains(field));
		}

		for (ChessboardField field : qmb.allMoves()) {
			Assert.assertTrue(expectedFields.contains(field));
		}
	}

}
