package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.board.Square;
import jchess.core.util.Constants;
import jchess.core.util.Player;
import jchess.core.util.Settings;
import jchess.ui.GameTab;

import org.junit.Assert;
import org.junit.Test;

public class PawnTest {

	@Test
	public void testCreateMoveBehavior() throws Exception {
		Pawn p = createPawn();
		IMoveBehavior move = p.createMoveBehavior();
		Assert.assertTrue(move instanceof PawnMoveBehavior);
	}

	@Test
	public void testGetSymbolForMoveHistory() throws Exception {
		Pawn p = createPawn();
		Assert.assertEquals("P", p.getSymbolForMoveHistory());
	}

	@Test
	public void testPawn() throws Exception {
		Pawn p = createPawn();
		Assert.assertEquals("P", p.getSymbolForMoveHistory());
		Assert.assertTrue(p.getMoveBehaviorClass().equals(PawnMoveBehavior.class));
		Assert.assertEquals("P", p.getSymbolForMoveHistory());
	}

	@Test
	public void testPromote() throws Exception {
		Pawn p = createPawn();
		p.promote(Queen.SYMBOL);
		Assert.assertEquals(Queen.SYMBOL, p.getSymbol());
		Assert.assertTrue(p.getMoveBehaviorClass().equals(QueenMoveBehavior.class));
		Assert.assertEquals("Q", p.getSymbolForMoveHistory());

		p = createPawn();
		boolean exception = false;
		try {
			p.promote(King.SYMBOL);
		} catch (Exception e) {
			Assert.assertEquals("Pawn can not be promoted to King.", e.getMessage());
			exception = true;
		}
		Assert.assertTrue(exception);

		p = createPawn();
		p.promote(Knight.SYMBOL);
		Assert.assertEquals(Knight.SYMBOL, p.getSymbol());
		Assert.assertTrue(p.getMoveBehaviorClass().equals(KnightMoveBehavior.class));
		Assert.assertEquals("Kn", p.getSymbolForMoveHistory());

		p = createPawn();
		p.promote(Bishop.SYMBOL);
		Assert.assertEquals(Bishop.SYMBOL, p.getSymbol());
		Assert.assertTrue(p.getMoveBehaviorClass().equals(BishopMoveBehavior.class));
		Assert.assertEquals("B", p.getSymbolForMoveHistory());

		p = createPawn();
		p.promote(Rook.SYMBOL);
		Assert.assertEquals(Rook.SYMBOL, p.getSymbol());
		Assert.assertTrue(p.getMoveBehaviorClass().equals(RookMoveBehavior.class));
		Assert.assertEquals("R", p.getSymbolForMoveHistory());
	}

	@Test
	public void testAllMoves() throws Exception {

		Settings.setWhitePlayersName("Hans");
		Settings.setBlackPlayersName("Wurst");

		GameTab game = new GameTab();
		Chessboard board = game.getChessboard();

		board.setPieces(Constants.EMPTY_STRING, game.getWhitePlayer(), game.getBlackPlayer());

		// first move
		Pawn p = (Pawn) board.getFields()[1][1].getPiece();
		ArrayList<ChessboardField> possibleMoves = p.allMoves();
		Assert.assertEquals(possibleMoves.size(), 2);

		Square first = (Square) possibleMoves.get(0);
		Square second = (Square) possibleMoves.get(1);

		Assert.assertEquals(first.pozX, 1);
		Assert.assertEquals(first.pozY, 2);
		Assert.assertEquals(second.pozX, 1);
		Assert.assertEquals(second.pozY, 3);

		// second move
		board.move(board.getFields()[1][1], second);

		Square newSquare = (Square) p.getField();
		Assert.assertEquals(1, newSquare.pozX);
		Assert.assertEquals(3, newSquare.pozY);

		possibleMoves = p.allMoves();
		Assert.assertEquals(possibleMoves.size(), 1);
		first = (Square) possibleMoves.get(0);

		Assert.assertEquals(first.pozX, 1);
		Assert.assertEquals(first.pozY, 4);

		// blocked
		board.move(board.getFields()[1][6], board.getFields()[1][4]);
		possibleMoves = p.allMoves();
		Assert.assertEquals(0, possibleMoves.size());

		// pawn to capture
		board.move(board.getFields()[2][1], board.getFields()[2][3]);
		possibleMoves = board.getFields()[2][3].getPiece().allMoves();
		Assert.assertEquals(2, possibleMoves.size());
		first = (Square) possibleMoves.get(0);
		Assert.assertEquals(first.pozX, 2);
		Assert.assertEquals(first.pozY, 4);
		second = (Square) possibleMoves.get(1);
		Assert.assertEquals(second.pozX, 1);
		Assert.assertEquals(second.pozY, 4);

		board.move(board.getFields()[2][6], board.getFields()[2][4]);
		possibleMoves = p.allMoves();
		Assert.assertEquals(1, possibleMoves.size());
		first = (Square) possibleMoves.get(0);
		Assert.assertEquals(first.pozX, 2);
		Assert.assertEquals(first.pozY, 4);

		possibleMoves = board.getFields()[2][3].getPiece().allMoves();
		Assert.assertEquals(1, possibleMoves.size());
		first = (Square) possibleMoves.get(0);
		Assert.assertEquals(first.pozX, 1);
		Assert.assertEquals(first.pozY, 4);

		// wrong en passante
		board.move(board.getFields()[5][6], board.getFields()[5][4]);
		board.move(board.getFields()[4][1], board.getFields()[4][3]);
		board.move(board.getFields()[5][4], board.getFields()[5][3]);

		possibleMoves = board.getFields()[4][3].getPiece().allMoves();
		Assert.assertEquals(1, possibleMoves.size());
		first = (Square) possibleMoves.get(0);
		Assert.assertEquals(first.pozX, 4);
		Assert.assertEquals(first.pozY, 4);
	}

	private Pawn createPawn() throws Exception {
		Player pl = new Player();
		Chessboard board = new Chessboard(null, null);
		Pawn p = new Pawn(board, pl);
		return p;
	}
}