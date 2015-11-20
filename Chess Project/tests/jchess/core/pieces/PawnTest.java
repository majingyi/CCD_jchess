package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Chessboard;
import jchess.core.board.Square;
import jchess.core.util.Constants;
import jchess.core.util.Player;
import jchess.ui.Game;

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
		Game game = new Game();
		Chessboard board = game.chessboard.getChessboard();

		Player white = new Player("hans", Player.colors.white);
		Player black = new Player("wurst", Player.colors.black);

		board.setPieces(Constants.EMPTY_STRING, white, black);

		// first move
		Pawn p = (Pawn) board.squares[1][1].piece;
		ArrayList<Square> possibleMoves = p.allMoves();
		Assert.assertEquals(possibleMoves.size(), 2);

		Square first = possibleMoves.get(0);
		Square second = possibleMoves.get(1);

		Assert.assertEquals(first.pozX, 1);
		Assert.assertEquals(first.pozY, 2);
		Assert.assertEquals(second.pozX, 1);
		Assert.assertEquals(second.pozY, 3);

		// second move
		board.move(board.squares[1][1], second);

		Square newSquare = p.getSquare();
		Assert.assertEquals(1, newSquare.pozX);
		Assert.assertEquals(3, newSquare.pozY);

		possibleMoves = p.allMoves();
		Assert.assertEquals(possibleMoves.size(), 1);
		first = possibleMoves.get(0);

		Assert.assertEquals(first.pozX, 1);
		Assert.assertEquals(first.pozY, 4);

		// blocked
		board.move(board.squares[1][6], board.squares[1][4]);
		possibleMoves = p.allMoves();
		Assert.assertEquals(0, possibleMoves.size());

		// pawn to capture
		board.move(board.squares[2][1], board.squares[2][3]);
		possibleMoves = board.squares[2][3].piece.allMoves();
		Assert.assertEquals(2, possibleMoves.size());
		first = possibleMoves.get(0);
		Assert.assertEquals(first.pozX, 2);
		Assert.assertEquals(first.pozY, 4);
		second = possibleMoves.get(1);
		Assert.assertEquals(second.pozX, 1);
		Assert.assertEquals(second.pozY, 4);

		board.move(board.squares[2][6], board.squares[2][4]);
		possibleMoves = p.allMoves();
		Assert.assertEquals(1, possibleMoves.size());
		first = possibleMoves.get(0);
		Assert.assertEquals(first.pozX, 2);
		Assert.assertEquals(first.pozY, 4);

		possibleMoves = board.squares[2][3].piece.allMoves();
		Assert.assertEquals(1, possibleMoves.size());
		first = possibleMoves.get(0);
		Assert.assertEquals(first.pozX, 1);
		Assert.assertEquals(first.pozY, 4);

		// wrong en passante
		board.move(board.squares[5][6], board.squares[5][4]);
		board.move(board.squares[4][1], board.squares[4][3]);
		board.move(board.squares[5][4], board.squares[5][3]);

		possibleMoves = board.squares[4][3].piece.allMoves();
		Assert.assertEquals(1, possibleMoves.size());
		first = possibleMoves.get(0);
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