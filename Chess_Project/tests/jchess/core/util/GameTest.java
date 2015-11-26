package jchess.core.util;

import jchess.core.board.Chessboard;
import jchess.core.util.Player.colors;

import org.junit.Assert;
import org.junit.Test;

public class GameTest {

	@Test
	public void testSwitchActive() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		Assert.assertEquals(colors.white, game.getActivePlayer().getColor());
		game.switchActive();
		Assert.assertEquals(colors.red, game.getActivePlayer().getColor());
		game.switchActive();
		Assert.assertEquals(colors.black, game.getActivePlayer().getColor());
		game.switchActive();
		Assert.assertEquals(colors.white, game.getActivePlayer().getColor());
	}

	@Test
	public void testGetActivePlayer() throws Exception {
		Chessboard board = new Chessboard(null, null);
		Game game = new Game(board, null);
		game.startNewGame();

		Assert.assertEquals(colors.white, game.getActivePlayer().getColor());
		game.switchActive();
		Assert.assertEquals(colors.red, game.getActivePlayer().getColor());
		game.switchActive();
		Assert.assertEquals(colors.black, game.getActivePlayer().getColor());
		game.switchActive();
		Assert.assertEquals(colors.white, game.getActivePlayer().getColor());
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
		Assert.assertEquals(true, game.getClockForPlayer(colors.white).isClockRunning());
		Assert.assertEquals(false, game.getClockForPlayer(colors.red).isClockRunning());
		Assert.assertEquals(false, game.getClockForPlayer(colors.black).isClockRunning());
		game.switchActive();
		Thread.sleep(100);
		Assert.assertEquals(false, game.getClockForPlayer(colors.white).isClockRunning());
		Assert.assertEquals(true, game.getClockForPlayer(colors.red).isClockRunning());
		Assert.assertEquals(false, game.getClockForPlayer(colors.black).isClockRunning());
		game.switchActive();
		Thread.sleep(100);
		Assert.assertEquals(false, game.getClockForPlayer(colors.white).isClockRunning());
		Assert.assertEquals(false, game.getClockForPlayer(colors.red).isClockRunning());
		Assert.assertEquals(true, game.getClockForPlayer(colors.black).isClockRunning());
		game.switchActive();
		Thread.sleep(100);
		Assert.assertEquals(true, game.getClockForPlayer(colors.white).isClockRunning());
		Assert.assertEquals(false, game.getClockForPlayer(colors.red).isClockRunning());
		Assert.assertEquals(false, game.getClockForPlayer(colors.black).isClockRunning());
	}
}