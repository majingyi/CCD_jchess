package jchess.core.board.graph;

import java.util.List;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.pieces.Bishop;
import jchess.core.pieces.King;
import jchess.core.pieces.Knight;
import jchess.core.pieces.Pawn;
import jchess.core.pieces.Piece;
import jchess.core.pieces.Queen;
import jchess.core.pieces.Rook;
import jchess.core.util.Game;
import jchess.core.util.Player;
import jchess.core.util.Player.colors;
import junit.framework.Assert;

import org.junit.Test;

public class HexagonChessFieldGraphInitializerTest {

	private String[]	nodeIdentifier	= new String[] { "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9",
			"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11", "E1", "E2", "E3", "E4",
			"E5", "E6", "E7", "E8", "E9", "E10", "E11", "E12", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13", "G2", "G3", "G4",
			"G5", "G6", "G7", "G8", "G9", "G10", "G11", "G12", "G13", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "I4", "I5", "I6", "I7",
			"I8", "I9", "I10", "I11", "I12", "I13", "J5", "J6", "J7", "J8", "J9", "J10", "J11", "J12", "J13", "K6", "K7", "K8", "K9", "K10", "K11", "K12", "K13",
			"L7", "L8", "L9", "L10", "L11", "L12", "L13", "M8", "M9", "M10", "M11", "M12", "M13" };

	@Test
	public void testInitialise() throws Exception {
		Chessboard board = new Chessboard(null, null);
		new Game(board, null);

		// check number of nodes
		Assert.assertEquals(126, board.getAllNodes().size());

		for (String id : nodeIdentifier) {
			ChessboardField field = (ChessboardField) board.getNode(id);
			Assert.assertNotNull(field);
			Assert.assertEquals(id, field.getIdentifier());
			Assert.assertTrue("Field " + field.getIdentifier() + " fails this test: " + field.getAllNeighbors().size(), field.getAllNeighbors().size() <= 12);
			Assert.assertTrue("Field " + field.getIdentifier() + " fails this test: " + field.getAllNeighbors().size(), field.getAllNeighbors().size() >= 5);
		}

		checkField(board, "D5", new String[] { "D4", "D6", "C4", "C5", "E5", "E6" }, new String[] { "C3", "B4", "C6", "E4", "F6", "E7" });
		checkField(board, "H3", new String[] { "G2", "G3", "H4", "I4" }, new String[] { "F2", "G4", "I5" });

		checkField(board, "M10", new String[] { "M9", "M11", "L9", "L10" }, new String[] { "L8", "K9", "L11" });

		checkField(board, "A1", new String[] { "A2", "B1", "B2" }, new String[] { "C2", "B3" });
		checkField(board, "A8", new String[] { "A7", "B8", "B9" }, new String[] { "C9", "B7" });

		checkField(board, "F1", new String[] { "E1", "F2", "G2" }, new String[] { "E2", "G3" });
		checkField(board, "F13", new String[] { "G13", "F12", "E12" }, new String[] { "E11", "G12" });

		checkField(board, "M8", new String[] { "L7", "L8", "M9" }, new String[] { "K7", "L9" });
		checkField(board, "M13", new String[] { "L13", "M12", "L12" }, new String[] { "L11", "K12" });
	}

	private void checkField(Chessboard board, String fieldID, String[] straight, String[] diagonal) throws Exception {
		ChessboardField field = (ChessboardField) board.getNode(fieldID);
		List<ChessboardField> neighbors = field.getAllNeighbors();
		Assert.assertEquals(straight.length + diagonal.length, neighbors.size());

		for (String id : straight) {
			Assert.assertTrue(id, neighbors.contains(board.getNode(id)));
		}

		for (String id : diagonal) {
			Assert.assertTrue(id, neighbors.contains(board.getNode(id)));
		}

		neighbors = field.getDiagonalNeighbors();
		Assert.assertEquals(diagonal.length, neighbors.size());

		for (String id : diagonal) {
			Assert.assertTrue(id, neighbors.contains(board.getNode(id)));
		}

		neighbors = field.getStraightNeighbors();
		Assert.assertEquals(straight.length, neighbors.size());

		for (String id : straight) {
			Assert.assertTrue(id, neighbors.contains(board.getNode(id)));
		}
	}

	@Test
	public void testInitialBoardSetup() throws Exception {
		Chessboard board = new Chessboard(null, null);
		new Game(board, null);

		// Test white pieces
		checkPiece(board, "A1", Player.colors.white, Rook.class);
		checkPiece(board, "A2", Player.colors.white, Knight.class);
		checkPiece(board, "A3", Player.colors.white, Bishop.class);
		checkPiece(board, "A4", Player.colors.white, Queen.class);
		checkPiece(board, "A5", Player.colors.white, King.class);
		checkPiece(board, "A6", Player.colors.white, Bishop.class);
		checkPiece(board, "A7", Player.colors.white, Knight.class);
		checkPiece(board, "A8", Player.colors.white, Rook.class);

		checkPiece(board, "B1", Player.colors.white, Pawn.class);
		checkPiece(board, "B2", Player.colors.white, Pawn.class);
		checkPiece(board, "B3", Player.colors.white, Pawn.class);
		checkPiece(board, "B4", Player.colors.white, Pawn.class);
		checkPiece(board, "B5", Player.colors.white, Pawn.class);
		checkPiece(board, "B6", Player.colors.white, Pawn.class);
		checkPiece(board, "B7", Player.colors.white, Pawn.class);
		checkPiece(board, "B8", Player.colors.white, Pawn.class);
		checkPiece(board, "B9", Player.colors.white, Pawn.class);

		// Test black pieces
		checkPiece(board, "F1", Player.colors.black, Rook.class);
		checkPiece(board, "G2", Player.colors.black, Knight.class);
		checkPiece(board, "H3", Player.colors.black, Bishop.class);
		checkPiece(board, "I4", Player.colors.black, Queen.class);
		checkPiece(board, "J5", Player.colors.black, King.class);
		checkPiece(board, "K6", Player.colors.black, Bishop.class);
		checkPiece(board, "L7", Player.colors.black, Knight.class);
		checkPiece(board, "M8", Player.colors.black, Rook.class);

		checkPiece(board, "E1", Player.colors.black, Pawn.class);
		checkPiece(board, "F2", Player.colors.black, Pawn.class);
		checkPiece(board, "G3", Player.colors.black, Pawn.class);
		checkPiece(board, "H4", Player.colors.black, Pawn.class);
		checkPiece(board, "I5", Player.colors.black, Pawn.class);
		checkPiece(board, "J6", Player.colors.black, Pawn.class);
		checkPiece(board, "K7", Player.colors.black, Pawn.class);
		checkPiece(board, "L8", Player.colors.black, Pawn.class);
		checkPiece(board, "M9", Player.colors.black, Pawn.class);

		// Test red pieces
		checkPiece(board, "F13", Player.colors.red, Rook.class);
		checkPiece(board, "G13", Player.colors.red, Knight.class);
		checkPiece(board, "H13", Player.colors.red, Bishop.class);
		checkPiece(board, "I13", Player.colors.red, Queen.class);
		checkPiece(board, "J13", Player.colors.red, King.class);
		checkPiece(board, "K13", Player.colors.red, Bishop.class);
		checkPiece(board, "L13", Player.colors.red, Knight.class);
		checkPiece(board, "M13", Player.colors.red, Rook.class);

		checkPiece(board, "E12", Player.colors.red, Pawn.class);
		checkPiece(board, "F12", Player.colors.red, Pawn.class);
		checkPiece(board, "G12", Player.colors.red, Pawn.class);
		checkPiece(board, "H12", Player.colors.red, Pawn.class);
		checkPiece(board, "I12", Player.colors.red, Pawn.class);
		checkPiece(board, "J12", Player.colors.red, Pawn.class);
		checkPiece(board, "K12", Player.colors.red, Pawn.class);
		checkPiece(board, "L12", Player.colors.red, Pawn.class);
		checkPiece(board, "M12", Player.colors.red, Pawn.class);

	}

	private void checkPiece(Chessboard board, String identifier, colors color, Class<? extends Piece> clazz) {
		Piece piece = ((ChessboardField) board.getNode(identifier)).getPiece();
		Assert.assertEquals(color, piece.getPlayer().getColor());
		Assert.assertEquals(piece.getClass(), clazz);
	}
}