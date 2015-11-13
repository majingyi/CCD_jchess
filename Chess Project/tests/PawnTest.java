import jchess.Settings;
import jchess.board.Chessboard;
import jchess.pieces.Bishop;
import jchess.pieces.BishopMoveBehavior;
import jchess.pieces.IMoveBehavior;
import jchess.pieces.King;
import jchess.pieces.Knight;
import jchess.pieces.KnightMoveBehavior;
import jchess.pieces.Pawn;
import jchess.pieces.PawnMoveBehavior;
import jchess.pieces.Queen;
import jchess.pieces.QueenMoveBehavior;
import jchess.pieces.Rook;
import jchess.pieces.RookMoveBehavior;
import jchess.util.Player;

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
		try {
			p.promote(King.SYMBOL);
		} catch (Exception e) {
			Assert.assertEquals("Pawn can not be promoted to King.", e.getMessage());
		}
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

	private Pawn createPawn() {
		Player pl = new Player();
		Chessboard board = new Chessboard(null, new Settings(), null);
		Pawn p = new Pawn(board, pl);
		return p;
	}
}