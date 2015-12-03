package jchess.core.util;

import jchess.core.board.Chessboard;
import jchess.core.util.Player.PlayerColor;

import org.junit.Assert;
import org.junit.Test;

public class GameTest {

	@Test
	public void testSwitchActive() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		Assert.assertEquals(PlayerColor.WHITE, game.getActivePlayer().getColor());
		game.switchActive();
		Assert.assertEquals(PlayerColor.RED, game.getActivePlayer().getColor());
		game.switchActive();
		Assert.assertEquals(PlayerColor.BLACK, game.getActivePlayer().getColor());
		game.switchActive();
		Assert.assertEquals(PlayerColor.WHITE, game.getActivePlayer().getColor());
	}

	@Test
	public void testGetActivePlayer() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		Assert.assertEquals(PlayerColor.WHITE, game.getActivePlayer().getColor());
		game.switchActive();
		Assert.assertEquals(PlayerColor.RED, game.getActivePlayer().getColor());
		game.switchActive();
		Assert.assertEquals(PlayerColor.BLACK, game.getActivePlayer().getColor());
		game.switchActive();
		Assert.assertEquals(PlayerColor.WHITE, game.getActivePlayer().getColor());
	}

	@Test
	public void testGetClockForPlayer() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.setTimeForPlayerClocks(120);
		game.startNewGame();
		game.getActiveClock().start();

		// give clock chance to start
		Thread.sleep(100);
		Assert.assertEquals(true, game.getClockForPlayer(PlayerColor.WHITE).isClockRunning());
		Assert.assertEquals(false, game.getClockForPlayer(PlayerColor.RED).isClockRunning());
		Assert.assertEquals(false, game.getClockForPlayer(PlayerColor.BLACK).isClockRunning());
		game.switchActive();
		Thread.sleep(100);
		Assert.assertEquals(false, game.getClockForPlayer(PlayerColor.WHITE).isClockRunning());
		Assert.assertEquals(true, game.getClockForPlayer(PlayerColor.RED).isClockRunning());
		Assert.assertEquals(false, game.getClockForPlayer(PlayerColor.BLACK).isClockRunning());
		game.switchActive();
		Thread.sleep(100);
		Assert.assertEquals(false, game.getClockForPlayer(PlayerColor.WHITE).isClockRunning());
		Assert.assertEquals(false, game.getClockForPlayer(PlayerColor.RED).isClockRunning());
		Assert.assertEquals(true, game.getClockForPlayer(PlayerColor.BLACK).isClockRunning());
		game.switchActive();
		Thread.sleep(100);
		Assert.assertEquals(true, game.getClockForPlayer(PlayerColor.WHITE).isClockRunning());
		Assert.assertEquals(false, game.getClockForPlayer(PlayerColor.RED).isClockRunning());
		Assert.assertEquals(false, game.getClockForPlayer(PlayerColor.BLACK).isClockRunning());
	}
}