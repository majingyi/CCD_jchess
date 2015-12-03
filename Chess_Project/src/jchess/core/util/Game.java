package jchess.core.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import jchess.core.board.Chessboard;
import jchess.core.util.Player.PlayerColor;
import jchess.ui.GameTab;

/**
 * Class responsible for the starts of new games, loading games, saving it, and
 * for ending it. 
 * 
 * 
 */
public class Game {

	private Clock																activeClock		= null;
	private Player															activePlayer	= null;
	private Chessboard													chessboard		= null;

	private LinkedHashMap<PlayerColor, Player>	player				= new LinkedHashMap<Player.PlayerColor, Player>();
	private LinkedHashMap<PlayerColor, Clock>		playerClocks	= new LinkedHashMap<Player.PlayerColor, Clock>();

	public Game(Chessboard board, GameTab gui) throws Exception {

		// do not change order, it is the turn order of the players
		player.put(PlayerColor.WHITE, new Player(Constants.EMPTY_STRING, Player.PlayerColor.WHITE));
		player.put(PlayerColor.RED, new Player(Constants.EMPTY_STRING, Player.PlayerColor.RED));
		player.put(PlayerColor.BLACK, new Player(Constants.EMPTY_STRING, Player.PlayerColor.BLACK));

		// do not change order, it is the turn order of the players
		playerClocks.put(PlayerColor.WHITE, new Clock(Settings.getTimeForGame()));
		playerClocks.put(PlayerColor.RED, new Clock(Settings.getTimeForGame()));
		playerClocks.put(PlayerColor.BLACK, new Clock(Settings.getTimeForGame()));

		chessboard = board;
	}

	/**
	 * Method to Start new game
	 * 
	 * @throws Exception
	 * 
	 */
	public void startNewGame() throws Exception {
		chessboard.initChessBoard(player);

		// white is first player
		activePlayer = player.get(PlayerColor.WHITE);
		activeClock = playerClocks.get(PlayerColor.WHITE);
	}

	/**
	 * Method to end game
	 * 
	 * @param message
	 *          what to show player(s) at end of the game (for example "draw",
	 *          "black wins" etc.)
	 */
	public void endGame(String massage) {
		Logging.log(massage);
		JOptionPane.showMessageDialog(null, massage);
	}

	/**
	 * Method to switch active players after move
	 * @throws Exception 
	 */
	public void switchActive() throws Exception {
		Iterator<Entry<PlayerColor, Player>> iter = player.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<PlayerColor, Player> entry = iter.next();
			if (entry.getValue() == activePlayer) {
				Entry<PlayerColor, Player> nextEntry = null;
				if (iter.hasNext()) {
					nextEntry = iter.next();
				} else {// next is first one
					nextEntry = player.entrySet().iterator().next();
				}
				activePlayer = nextEntry.getValue();
			}
		}

		switch_clocks();
	}

	/**
	 * Method of getting accualy active player
	 * 
	 * @return player The player which have a move
	 */
	public Player getActivePlayer() {
		return this.activePlayer;
	}

	public Chessboard getChessboard() {
		return chessboard;
	}

	public Clock getClockForPlayer(PlayerColor color) {
		return playerClocks.get(color);
	}

	public void stopPlayerClocks() {
		for (Clock clock : playerClocks.values()) {
			clock.stop();
		}
	}

	private void switch_clocks() throws Exception {
		activeClock.pause();
		Iterator<Entry<PlayerColor, Clock>> iter = playerClocks.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<PlayerColor, Clock> entry = iter.next();
			if (entry.getValue() == activeClock) {
				Entry<PlayerColor, Clock> nextEntry = null;
				if (iter.hasNext()) {
					nextEntry = iter.next();
				} else {// next is first one
					nextEntry = playerClocks.entrySet().iterator().next();
				}
				activeClock = nextEntry.getValue();

				if (activeClock.isStarted() == false) {
					activeClock.start();
				} else {
					activeClock.resume();
				}
			}
		}
	}

	public void setTimeForPlayerClocks(int timeForGame) {
		for (Clock clock : playerClocks.values()) {
			clock.setTime(timeForGame);
		}
	}

	public Player getPlayer(PlayerColor color) {
		return player.get(color);
	}

	public void setPlayer(PlayerColor color, Player newPlayer) {
		player.put(color, newPlayer);
	}

	public Clock getActiveClock() {
		return activeClock;
	}
}