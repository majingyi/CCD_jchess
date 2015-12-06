package jchess.core.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.junit.Test;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Player;
import jchess.core.util.Player.PlayerColor;
import junit.framework.Assert;

public class BishopMoveBehaviorTest {

	@Test
	public void testAllMoves() throws Exception {
		// Bishop in the center
		Player playerl = new Player("redPl", PlayerColor.RED);
		Player player2 = new Player("blackPl", PlayerColor.BLACK);
		Chessboard board = new Chessboard(null, null);

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
		board.getField("B7").setPiece(p4);

		// calculating allowed moves
		BishopMoveBehavior bmb = new BishopMoveBehavior(playerl, board, board.getField("F3"));

		// creating array list of expected fields
		String[] expectedIdentifiers = new String[] { "G2", "H4", "J5", "E4", "D5", "C6", "B7", "D2", "E1" };
		ArrayList<ChessboardField> expectedFields = new ArrayList<ChessboardField>();
		for (String expID : expectedIdentifiers) {
			expectedFields.add(board.getField(expID));
		}

		// sorting of both array lists
		Collections.sort(expectedFields, new Comparator<ChessboardField>() {
			public int compare(ChessboardField field1, ChessboardField field2) {
				return field1.getIdentifier().compareTo(field2.getIdentifier());
			}
		});
		Collections.sort(bmb.allMoves(), new Comparator<ChessboardField>() {
			public int compare(ChessboardField field1, ChessboardField field2) {
				return field1.getIdentifier().compareTo(field2.getIdentifier());
			}
		});

		// comparison
		Assert.assertEquals(expectedFields, bmb.allMoves());
		;
	}

}
