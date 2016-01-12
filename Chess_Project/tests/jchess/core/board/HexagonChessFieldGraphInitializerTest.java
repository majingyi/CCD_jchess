package jchess.core.board;

import java.util.List;

import jchess.core.pieces.Bishop;
import jchess.core.pieces.King;
import jchess.core.pieces.Knight;
import jchess.core.pieces.Pawn;
import jchess.core.pieces.Piece;
import jchess.core.pieces.Queen;
import jchess.core.pieces.Rook;
import jchess.core.util.Game;
import jchess.core.util.Player;
import jchess.core.util.Player.PlayerColor;
import junit.framework.Assert;

import org.junit.Test;

/**
 * 
 * @author Maurice
 *
 */
public class HexagonChessFieldGraphInitializerTest {

	// All existing and allowed nodes on the hexagon chessboard
	private String[]	nodeIdentifier	= new String[] { "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9",
			"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11", "E1", "E2", "E3", "E4",
			"E5", "E6", "E7", "E8", "E9", "E10", "E11", "E12", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13", "G2", "G3", "G4",
			"G5", "G6", "G7", "G8", "G9", "G10", "G11", "G12", "G13", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "I4", "I5", "I6", "I7",
			"I8", "I9", "I10", "I11", "I12", "I13", "J5", "J6", "J7", "J8", "J9", "J10", "J11", "J12", "J13", "K6", "K7", "K8", "K9", "K10", "K11", "K12", "K13",
			"L7", "L8", "L9", "L10", "L11", "L12", "L13", "M8", "M9", "M10", "M11", "M12", "M13" };

	/**
	 * Checks the initial board layout of the hexagon chess board class.
	 * 
	 * Since this board is implemented as a graph, it is checked, that the board has the expected nodes, 
	 * and that the nodes are connected to each other like expected. 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInitialise() throws Exception {
		// create board and initialize it
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		// check number of nodes
		Assert.assertEquals(126, board.getAllNodes().size());

		// check, that the correct nodes are created
		for (String id : nodeIdentifier) {
			// get the field with the current identifier
			ChessboardField field = (ChessboardField) board.getNode(id);
			Assert.assertNotNull(field);

			// check the identifier is correct
			Assert.assertEquals(id, field.getIdentifier());

			/*
			 * Check, that the filed has a maximum of 12 edges and a minimum of 5.
			 * 
			 * Everything in between occurs on the filed, but never more or less.
			 */
			Assert.assertTrue("Field " + field.getIdentifier() + " fails this test: " + field.getAllNeighbors().size(), field.getAllNeighbors().size() <= 12);
			Assert.assertTrue("Field " + field.getIdentifier() + " fails this test: " + field.getAllNeighbors().size(), field.getAllNeighbors().size() >= 5);
		}

		/*
		 * Check some middle fields, if they are correctly connected with neighbor
		 * fields.
		 */
		checkField(board, "D5", new String[] { "D4", "D6", "C4", "C5", "E5", "E6" }, new String[] { "C3", "B4", "C6", "E4", "F6", "E7" });
		checkField(board, "H3", new String[] { "G2", "G3", "H4", "I4" }, new String[] { "F2", "G4", "I5" });

		// check lover bound
		checkField(board, "M10", new String[] { "M9", "M11", "L9", "L10" }, new String[] { "L8", "K9", "L11" });

		// check upper corners
		checkField(board, "A1", new String[] { "A2", "B1", "B2" }, new String[] { "C2", "B3" });
		checkField(board, "A8", new String[] { "A7", "B8", "B9" }, new String[] { "C9", "B7" });

		// check left and right corner
		checkField(board, "F1", new String[] { "E1", "F2", "G2" }, new String[] { "E2", "G3" });
		checkField(board, "F13", new String[] { "G13", "F12", "E12" }, new String[] { "E11", "G12" });

		// check lower corners
		checkField(board, "M8", new String[] { "L7", "L8", "M9" }, new String[] { "K7", "L9" });
		checkField(board, "M13", new String[] { "L13", "M12", "L12" }, new String[] { "L11", "K12" });
	}

	/**
	 * Checks whether a field is correctly connected to the intended neighbor fields.
	 * 
	 * @param board
	 * 						The current chessboard instance.
	 * @param fieldID
	 * 						The ID of the field, which is to be checked
	 * @param straight 
	 * 						expected fields connected with a straight edge
	 * @param diagonal
	 * 						expected fields connected with a diagonal edge
	 * @throws Exception
	 */
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

	/**
	 * Checks if all pieces are located in the correct fields after board initialization
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInitialBoardSetup() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		// Test white pieces
		checkPiece(board, "A1", Player.PlayerColor.WHITE, Rook.class);
		checkPiece(board, "A2", Player.PlayerColor.WHITE, Knight.class);
		checkPiece(board, "A3", Player.PlayerColor.WHITE, Bishop.class);
		checkPiece(board, "A4", Player.PlayerColor.WHITE, Queen.class);
		checkPiece(board, "A5", Player.PlayerColor.WHITE, King.class);
		checkPiece(board, "A6", Player.PlayerColor.WHITE, Bishop.class);
		checkPiece(board, "A7", Player.PlayerColor.WHITE, Knight.class);
		checkPiece(board, "A8", Player.PlayerColor.WHITE, Rook.class);

		checkPiece(board, "B1", Player.PlayerColor.WHITE, Pawn.class);
		checkPiece(board, "B2", Player.PlayerColor.WHITE, Pawn.class);
		checkPiece(board, "B3", Player.PlayerColor.WHITE, Pawn.class);
		checkPiece(board, "B4", Player.PlayerColor.WHITE, Pawn.class);
		checkPiece(board, "B5", Player.PlayerColor.WHITE, Pawn.class);
		checkPiece(board, "B6", Player.PlayerColor.WHITE, Pawn.class);
		checkPiece(board, "B7", Player.PlayerColor.WHITE, Pawn.class);
		checkPiece(board, "B8", Player.PlayerColor.WHITE, Pawn.class);
		checkPiece(board, "B9", Player.PlayerColor.WHITE, Pawn.class);

		// Test black pieces
		checkPiece(board, "F1", Player.PlayerColor.BLACK, Rook.class);
		checkPiece(board, "G2", Player.PlayerColor.BLACK, Knight.class);
		checkPiece(board, "H3", Player.PlayerColor.BLACK, Bishop.class);
		checkPiece(board, "I4", Player.PlayerColor.BLACK, Queen.class);
		checkPiece(board, "J5", Player.PlayerColor.BLACK, King.class);
		checkPiece(board, "K6", Player.PlayerColor.BLACK, Bishop.class);
		checkPiece(board, "L7", Player.PlayerColor.BLACK, Knight.class);
		checkPiece(board, "M8", Player.PlayerColor.BLACK, Rook.class);

		checkPiece(board, "E1", Player.PlayerColor.BLACK, Pawn.class);
		checkPiece(board, "F2", Player.PlayerColor.BLACK, Pawn.class);
		checkPiece(board, "G3", Player.PlayerColor.BLACK, Pawn.class);
		checkPiece(board, "H4", Player.PlayerColor.BLACK, Pawn.class);
		checkPiece(board, "I5", Player.PlayerColor.BLACK, Pawn.class);
		checkPiece(board, "J6", Player.PlayerColor.BLACK, Pawn.class);
		checkPiece(board, "K7", Player.PlayerColor.BLACK, Pawn.class);
		checkPiece(board, "L8", Player.PlayerColor.BLACK, Pawn.class);
		checkPiece(board, "M9", Player.PlayerColor.BLACK, Pawn.class);

		// Test red pieces
		checkPiece(board, "F13", Player.PlayerColor.RED, Rook.class);
		checkPiece(board, "G13", Player.PlayerColor.RED, Knight.class);
		checkPiece(board, "H13", Player.PlayerColor.RED, Bishop.class);
		checkPiece(board, "I13", Player.PlayerColor.RED, Queen.class);
		checkPiece(board, "J13", Player.PlayerColor.RED, King.class);
		checkPiece(board, "K13", Player.PlayerColor.RED, Bishop.class);
		checkPiece(board, "L13", Player.PlayerColor.RED, Knight.class);
		checkPiece(board, "M13", Player.PlayerColor.RED, Rook.class);

		checkPiece(board, "E12", Player.PlayerColor.RED, Pawn.class);
		checkPiece(board, "F12", Player.PlayerColor.RED, Pawn.class);
		checkPiece(board, "G12", Player.PlayerColor.RED, Pawn.class);
		checkPiece(board, "H12", Player.PlayerColor.RED, Pawn.class);
		checkPiece(board, "I12", Player.PlayerColor.RED, Pawn.class);
		checkPiece(board, "J12", Player.PlayerColor.RED, Pawn.class);
		checkPiece(board, "K12", Player.PlayerColor.RED, Pawn.class);
		checkPiece(board, "L12", Player.PlayerColor.RED, Pawn.class);
		checkPiece(board, "M12", Player.PlayerColor.RED, Pawn.class);

	}

	/**
	 * Checks, if the correct piece is located on the checked field. This includes checking for color of the piece.
	 * 
	 * @param board
	 * 						The current chessboard instance.
	 * @param identifier
	 * 						The ID of the field, which is to be checked
	 * @param color
	 * 						Color of the player, who owns this piece
	 * @param clazz
	 * 						The class of the piece, which should be located on the field
	 */
	private void checkPiece(Chessboard board, String identifier, PlayerColor color, Class<? extends Piece> clazz) {
		Piece piece = ((ChessboardField) board.getNode(identifier)).getPiece();
		Assert.assertEquals(color, piece.getPlayer().getColor());
		Assert.assertEquals(piece.getClass(), clazz);
	}
}