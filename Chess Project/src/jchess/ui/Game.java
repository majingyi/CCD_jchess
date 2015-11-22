package jchess.ui;

import javax.swing.JOptionPane;

import jchess.core.board.Chessboard;
import jchess.core.util.Constants;
import jchess.core.util.Logging;
import jchess.core.util.Player;

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
	private GameTab			gameGui				= null;

	// TODO add player gray
	private Player			playerWhite		= null;
	private Player			playerBlack		= null;

	public Game(Chessboard board, GameTab gui) throws Exception {

		playerWhite = new Player(Constants.EMPTY_STRING, Player.colors.white);
		playerBlack = new Player(Constants.EMPTY_STRING, Player.colors.black);

		chessboard = board;
		gameGui = gui;
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
}