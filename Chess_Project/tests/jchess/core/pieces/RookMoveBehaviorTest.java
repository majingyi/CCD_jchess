package jchess.core.pieces;

import java.util.ArrayList;

import org.junit.Test;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Player;
import jchess.core.util.Player.PlayerColor;
import junit.framework.Assert;

public class RookMoveBehaviorTest {

	@Test
	public void testAllMoves() throws Exception {
		// Rook in the center
		Player playerl = new Player("redPl", PlayerColor.RED);
		Player player2 = new Player("blackPl", PlayerColor.BLACK);
		Chessboard board = new Chessboard(null, null);
		// friend pawn on the way
		Pawn p1 = new Pawn(board, playerl, null);
		board.getField("G7").setPiece(p1);
		// foe pawn on the way
		Pawn p2 = new Pawn(board, player2, null);
		board.getField("E5").setPiece(p2);
		// calculating allowed moves
		RookMoveBehavior rmb = new RookMoveBehavior(playerl, board, board.getField("G5"));

		// creating arraylist of expected fields
		String[] expectedIdentifiers = new String[] { "G4", "G3", "G2", "G1", "H5", "I5", "J5", "K5", "H6", "I7", "J8", "K9", "L10", "M11", "G6", "F5", "E5", "F4",
				"E3", "D2", "C1" };
		ArrayList<ChessboardField> expectedFields = new ArrayList<ChessboardField>();
		for (String expID : expectedIdentifiers) {
			expectedFields.add(rmb.m_Chessboard.getField(expID));
		}
		// compairison
		for (ChessboardField field : expectedFields) {
			Assert.assertTrue(rmb.allMoves().contains(field));
		}

	}

}
