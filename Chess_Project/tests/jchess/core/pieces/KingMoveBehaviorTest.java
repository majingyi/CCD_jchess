package jchess.core.pieces;

import java.util.ArrayList;

import org.junit.Test;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Game;
import jchess.core.util.Player;
import jchess.core.util.Player.PlayerColor;
import junit.framework.Assert;

public class KingMoveBehaviorTest {

	@Test
	public void testAllMoves() throws Exception {

		// King - C5
		Player playerl = new Player("player1", PlayerColor.BLACK);
		Chessboard board = new Chessboard(null, null);

		Game game = new Game(board, null);
		game.startNewGame();

		// adding pieces for checking of castling
		// pawn as obstacle
		Pawn p = new Pawn(board, playerl, null);
		board.getField("H5").setPiece(p);
		// we have to use original rooks
		board.getField("F1").getPiece().setField(board.getField("E2"), board);
		board.getField("M8").getPiece().setField(board.getField("L9"), board);

		// calculating allowed moves
		King k = new King(board, playerl, null);
		KingMoveBehavior kmb = new KingMoveBehavior(playerl, board, board.getField("I6"), k);

		// creating array list of expected fields
		String[] expectedIdentifiers = new String[] { "H6", "I7", "J7", "G5", "H7", "J8", "L9" };
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
