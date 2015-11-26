package jchess.core.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import jchess.core.board.Chessboard;
import jchess.ui.GameTab;

/**
 * Class responsible for the starts of new games, loading games, saving it, and
 * for ending it. This class is also responsible for appoing player with have a
 * move at the moment
 * 
 * TODO move the UI stuff to GUIand Move to core
 */
public class Game {

	private Player			activePlayer	= null;
	private Chessboard	chessboard		= null;

	private Player			playerWhite		= null;
	private Player			playerBlack		= null;
	private Player			playerRed			= null;

	private List<Clock>	playerClocks	= new ArrayList<Clock>();
	private int					runningClock	= 1;

	public Game(Chessboard board, GameTab gui) throws Exception {

		playerWhite = new Player(Constants.EMPTY_STRING, Player.colors.white);
		playerBlack = new Player(Constants.EMPTY_STRING, Player.colors.black);
		playerRed = new Player(Constants.EMPTY_STRING, Player.colors.red);

		playerClocks.add(new Clock(Settings.getTimeForGame()));
		playerClocks.add(new Clock(Settings.getTimeForGame()));
		playerClocks.add(new Clock(Settings.getTimeForGame()));

		chessboard = board;
		chessboard.initChessBoard(playerWhite, playerBlack, playerRed);
	}

	/**
	 * Method to Start new game
	 * 
	 * @throws Exception
	 * 
	 */
	public void newGame() throws Exception {
		chessboard.setPieces(Constants.EMPTY_STRING, playerWhite, playerBlack);
		activePlayer = playerWhite;
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
	 */
	public void switchActive() {
		if (activePlayer == playerWhite) {
			activePlayer = playerBlack;
		} else {
			activePlayer = playerWhite;
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

	public Player getWhitePlayer() {
		return playerWhite;
	}

	public Player getBlackPlayer() {
		return playerBlack;
	}

	public void setWhitePlayer(Player playerWhite) {
		this.playerWhite = playerWhite;
	}

	public void setBlackPlayer(Player playerBlack) {
		this.playerBlack = playerBlack;
	}

	public Chessboard getChessboard() {
		return chessboard;
	}

	public Clock getClockForPlayer(int player) {
		return playerClocks.get(player - 1);
	}

	public void stopPlayerClocks() {
		for (Clock clock : playerClocks) {
			clock.stop();
		}
	}

	public void switch_clocks() {
		runningClock++;
		if (runningClock > playerClocks.size()) {
			runningClock = 1;
		}

		for (int i = 0; i < playerClocks.size(); i++) {
			if (i == (runningClock - 1)) {// is next clock
				playerClocks.get(i).resume();
			} else {
				playerClocks.get(i).pause();
			}
		}
	}

	public void setTimeForPlayerClocks(int timeForGame) {
		for (Clock clock : playerClocks) {
			clock.setTime(timeForGame);
		}
	}
}