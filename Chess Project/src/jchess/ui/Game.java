package jchess.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jchess.JChessApp;
import jchess.core.board.Square;
import jchess.core.pieces.King;
import jchess.core.util.Constants;
import jchess.core.util.Logging;
import jchess.core.util.Moves;
import jchess.core.util.Player;
import jchess.core.util.Settings;
import jchess.ui.lang.Language;

/**
 * Class responsible for the starts of new games, loading games, saving it, and
 * for ending it. This class is also responsible for appoing player with have a
 * move at the moment
 * 
 * TODO move the UI stuff to GUI
 */
public class Game extends JPanel implements MouseListener, ComponentListener {

	private static final long	serialVersionUID	= -8817954027095178257L;

	public Settings						settings					= null;
	public boolean						blockedChessboard	= false;
	public ChessboardUI				chessboard				= null;
	private Player						activePlayer			= null;
	public GameClock					gameClock					= null;
	public Moves							moves							= null;

	public Game() throws FileNotFoundException {
		this.setLayout(null);
		this.moves = new Moves(this);
		settings = new Settings();
		chessboard = new ChessboardUI(this.settings, this.moves);
		chessboard.setVisible(true);
		chessboard.setSize(ChessboardUI.img_height, ChessboardUI.img_widht);
		chessboard.addMouseListener(this);
		chessboard.setLocation(new Point(0, 0));
		this.add(chessboard);
		// this.chessboard.
		gameClock = new GameClock(this);
		gameClock.setSize(new Dimension(200, 100));
		gameClock.setLocation(new Point(500, 0));
		this.add(gameClock);

		JScrollPane movesHistory = this.moves.getScrollPane();
		movesHistory.setSize(new Dimension(180, 350));
		movesHistory.setLocation(new Point(500, 121));
		this.add(movesHistory);

		this.blockedChessboard = false;
		this.setLayout(null);
		this.addComponentListener(this);
		this.setDoubleBuffered(true);
	}

	/**
	 * Method to save actual state of game
	 * 
	 * @param path
	 *          address of place where game will be saved
	 */
	public void saveGame(File path) {
		File file = path;
		FileWriter fileW = null;
		try {
			fileW = new FileWriter(file);
		} catch (java.io.IOException exc) {
			Logging.log(Language.getString("Game.0"), exc); //$NON-NLS-1$
			JOptionPane.showMessageDialog(this, Language.getString("Game.1") + ": " + exc); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}
		Calendar cal = Calendar.getInstance();
		String str = new String(""); //$NON-NLS-1$
		String info = new String("[Event \"Game\"]\n[Date \"" + cal.get(Calendar.YEAR) + "." + (cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.DAY_OF_MONTH) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ "\"]\n" + "[White \"" + this.settings.playerWhite.name + "\"]\n[Black \"" + this.settings.playerBlack.name + "\"]\n\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		str += info;
		str += this.moves.getMovesInString();
		try {
			fileW.write(str);
			fileW.flush();
			fileW.close();
		} catch (java.io.IOException exc) {
			Logging.log(Language.getString("Game.11"), exc); //$NON-NLS-1$
			JOptionPane.showMessageDialog(this, Language.getString(Language.getString("Game.12")) + ": " + exc); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}
		JOptionPane.showMessageDialog(this, Language.getString("game_saved_properly")); //$NON-NLS-1$
	}

	/**
	 * Loading game method(loading game state from the earlier saved file)
	 * 
	 * @param file
	 *          File where is saved game
	 * @throws Exception
	 */

	public static void loadGame(File file) throws Exception {
		FileReader fileR = null;
		try {
			fileR = new FileReader(file);
		} catch (java.io.IOException exc) {
			Logging.log(Language.getString("Game.2"), exc); //$NON-NLS-1$
			return;
		}
		BufferedReader br = new BufferedReader(fileR);
		String tempStr = new String();
		String blackName, whiteName;
		try {
			tempStr = getLineWithVar(br, new String("[White")); //$NON-NLS-1$
			whiteName = getValue(tempStr);
			tempStr = getLineWithVar(br, new String("[Black")); //$NON-NLS-1$
			blackName = getValue(tempStr);
			tempStr = getLineWithVar(br, new String("1.")); //$NON-NLS-1$
		} catch (ReadGameError err) {
			Logging.log(Language.getString("Game.19"), err); //$NON-NLS-1$
			return;
		}
		Game newGUI = JChessApp.jcv.addNewTab(whiteName + " vs. " + blackName); //$NON-NLS-1$
		Settings locSetts = newGUI.settings;
		locSetts.playerBlack.name = blackName;
		locSetts.playerWhite.name = whiteName;
		locSetts.playerBlack.setType(Player.playerTypes.localUser);
		locSetts.playerWhite.setType(Player.playerTypes.localUser);
		locSetts.gameMode = Settings.gameModes.loadGame;
		locSetts.gameType = Settings.gameTypes.local;

		newGUI.newGame();
		newGUI.blockedChessboard = true;
		newGUI.moves.setMoves(tempStr);
		newGUI.blockedChessboard = false;
		newGUI.chessboard.repaint();
	}

	/**
	 * Method checking in with of line there is an error
	 * 
	 * @param br
	 *          BufferedReader class object to operate on
	 * @param srcStr
	 *          String class object with text which variable you want to get in
	 *          file
	 * @return String with searched variable in file (whole line)
	 * @throws ReadGameError
	 *           class object when something goes wrong when reading file
	 */
	public static String getLineWithVar(BufferedReader br, String srcStr) throws ReadGameError {
		String str = new String();
		while (true) {
			try {
				str = br.readLine();
			} catch (java.io.IOException exc) {
				Logging.log(Language.getString("Game.21"), exc); //$NON-NLS-1$
			}
			if (str == null) {
				throw new ReadGameError();
			}
			if (str.startsWith(srcStr)) {
				return str;
			}
		}
	}

	/**
	 * Method to get value from loaded txt line
	 * 
	 * @param line
	 *          Line which is readed
	 * @return result String with loaded value
	 * @throws ReadGameError
	 *           object class when something goes wrong
	 */
	static public String getValue(String line) throws ReadGameError {
		int from = line.indexOf("\""); //$NON-NLS-1$
		int to = line.lastIndexOf("\""); //$NON-NLS-1$
		int size = line.length() - 1;
		String result = new String();
		if (to < from || from > size || to > size || to < 0 || from < 0) {
			throw new ReadGameError();
		}
		try {
			result = line.substring(from + 1, to);
		} catch (java.lang.StringIndexOutOfBoundsException exc) {
			Logging.log(Language.getString("Game.24"), exc); //$NON-NLS-1$
			return "none"; //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Method to Start new game
	 * 
	 * @throws Exception
	 * 
	 */
	public void newGame() throws Exception {
		chessboard.getChessboard().setPieces(Constants.EMPTY_STRING, settings.playerWhite, settings.playerBlack);

		activePlayer = settings.playerWhite;
		if (activePlayer.playerType != Player.playerTypes.localUser) {
			this.blockedChessboard = true;
		}
		Game activeGame = JChessApp.jcv.getActiveTabGame();
		if (activeGame != null && JChessApp.jcv.getNumberOfOpenedTabs() == 0) {
			activeGame.chessboard.resizeChessboard(activeGame.chessboard.get_height(false));
			activeGame.chessboard.repaint();
			activeGame.repaint();
		}
		chessboard.repaint();
		this.repaint();
	}

	/**
	 * Method to end game
	 * 
	 * @param message
	 *          what to show player(s) at end of the game (for example "draw",
	 *          "black wins" etc.)
	 */
	public void endGame(String massage) {
		this.blockedChessboard = true;
		Logging.log(massage);
		JOptionPane.showMessageDialog(null, massage);
	}

	/**
	 * Method to swich active players after move
	 */
	public void switchActive() {
		if (activePlayer == settings.playerWhite) {
			activePlayer = settings.playerBlack;
		} else {
			activePlayer = settings.playerWhite;
		}

		this.gameClock.switch_clocks();
	}

	/**
	 * Method of getting accualy active player
	 * 
	 * @return player The player which have a move
	 */
	public Player getActivePlayer() {
		return this.activePlayer;
	}

	/**
	 * Method to go to next move (checks if game is local/network etc.)
	 */
	public void nextMove() {
		switchActive();

		Logging.log("next move, active player: " + activePlayer.name + ", color: " + activePlayer.color.name() + ", type: " + activePlayer.playerType.name()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if (activePlayer.playerType == Player.playerTypes.localUser) {
			this.blockedChessboard = false;
		}
	}

	/**
	 * Method to simulate Move to check if it's correct etc. (usable for network
	 * game).
	 * 
	 * @param beginX
	 *          from which X (on chessboard) move starts
	 * @param beginY
	 *          from which Y (on chessboard) move starts
	 * @param endX
	 *          to which X (on chessboard) move go
	 * @param endY
	 *          to which Y (on chessboard) move go
	 * @throws Exception
	 * */
	public boolean simulateMove(int beginX, int beginY, int endX, int endY) throws Exception {
		try {
			chessboard.getChessboard().select(chessboard.getChessboard().squares[beginX][beginY]);
			if (chessboard.getChessboard().activeSquare.piece.allMoves().indexOf(chessboard.getChessboard().squares[endX][endY]) != -1) // move
			{
				chessboard.getChessboard().move(chessboard.getChessboard().squares[beginX][beginY], chessboard.getChessboard().squares[endX][endY]);
			} else {
				Logging.log(Language.getString("Game.29")); //$NON-NLS-1$
				return false;
			}
			chessboard.getChessboard().unselect();
			nextMove();

			return true;

		} catch (StringIndexOutOfBoundsException exc) {
			return false;
		} catch (ArrayIndexOutOfBoundsException exc) {
			return false;
		} catch (NullPointerException exc) {
			return false;
		}
	}

	// MouseListener:
	public void mouseClicked(MouseEvent arg0) {
	}

	public boolean undo() throws Exception {
		boolean status = false;

		if (this.settings.gameType == Settings.gameTypes.local) {
			status = chessboard.getChessboard().undo();
			if (status) {
				this.switchActive();
			} else {
				chessboard.repaint();// repaint for sure
			}
		}
		return status;
	}

	public boolean rewindToBegin() throws Exception {
		boolean result = false;

		if (this.settings.gameType == Settings.gameTypes.local) {
			while (chessboard.getChessboard().undo()) {
				result = true;
			}
		} else {
			throw new UnsupportedOperationException(Language.getString("operation_supported_only_in_local_game")); //$NON-NLS-1$
		}

		return result;
	}

	public boolean rewindToEnd() throws Exception {
		boolean result = false;

		if (this.settings.gameType == Settings.gameTypes.local) {
			while (chessboard.getChessboard().redo()) {
				result = true;
			}
		} else {
			throw new UnsupportedOperationException(Language.getString("operation_supported_only_in_local_game")); //$NON-NLS-1$
		}

		return result;
	}

	public boolean redo() throws Exception {
		boolean status = chessboard.getChessboard().redo();
		if (this.settings.gameType == Settings.gameTypes.local) {
			if (status) {
				this.nextMove();
			} else {
				chessboard.repaint();// repaint for sure
			}
		} else {
			throw new UnsupportedOperationException(Language.getString("operation_supported_only_in_local_game")); //$NON-NLS-1$
		}
		return status;
	}

	public void mousePressed(MouseEvent event) {
		try {
			if (event.getButton() == MouseEvent.BUTTON3) // right button
			{
				this.undo();
			} else if (event.getButton() == MouseEvent.BUTTON2 && settings.gameType == Settings.gameTypes.local) {
				this.redo();
			} else if (event.getButton() == MouseEvent.BUTTON1) // left button
			{

				if (blockedChessboard == false) {
					int x = event.getX();// get X position of mouse
					int y = event.getY();// get Y position of mouse

					Square sq = chessboard.getSquare(x, y);
					if ((sq == null || sq.piece == null && chessboard.getChessboard().activeSquare == null)
							|| (this.chessboard.getChessboard().activeSquare == null && sq.piece != null && sq.piece.player != this.activePlayer)) {
						return;
					}

					if (sq.piece != null && sq.piece.player == this.activePlayer && sq != chessboard.getChessboard().activeSquare) {
						chessboard.getChessboard().unselect();
						chessboard.getChessboard().select(sq);
					} else if (chessboard.getChessboard().activeSquare == sq) // unselect
					{
						chessboard.getChessboard().unselect();
					} else if (chessboard.getChessboard().activeSquare != null && chessboard.getChessboard().activeSquare.piece != null
							&& chessboard.getChessboard().activeSquare.piece.allMoves().indexOf(sq) != -1) // move
					{
						if (settings.gameType == Settings.gameTypes.local) {
							chessboard.getChessboard().move(chessboard.getChessboard().activeSquare, sq);
						}

						chessboard.getChessboard().unselect();

						// switch player
						this.nextMove();

						// checkmate or stalemate
						King king;
						if (this.activePlayer == settings.playerWhite) {
							king = chessboard.getChessboard().kingWhite;
						} else {
							king = chessboard.getChessboard().kingBlack;
						}

						switch (king.isCheckmatedOrStalemated()) {
							case 1:
								this.endGame("Checkmate! " + king.player.color.toString() + " player lose!"); //$NON-NLS-1$ //$NON-NLS-2$
								break;
							case 2:
								this.endGame(Language.getString("Game.35")); //$NON-NLS-1$
								break;
						}
					}
				} else if (blockedChessboard) {
					Logging.log(Language.getString("Game.36")); //$NON-NLS-1$
				}
			}
		} catch (Exception e) {
			// TODO Inform User
			Logging.log(e);
			chessboard.repaint();
			return;
		}
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void componentResized(ComponentEvent e) {
		try {
			int height = this.getHeight() >= this.getWidth() ? this.getWidth() : this.getHeight();
			int chess_height = (int) Math.round((height * 0.8) / 8) * 8;
			this.chessboard.resizeChessboard((int) chess_height);
			chess_height = this.chessboard.getHeight();
			this.moves.getScrollPane().setLocation(new Point(chess_height + 5, 100));
			this.moves.getScrollPane().setSize(this.moves.getScrollPane().getWidth(), chess_height - 100);
			this.gameClock.setLocation(new Point(chess_height + 5, 0));
		} catch (FileNotFoundException e1) {
			Logging.log(e1);
		}
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}
}

class ReadGameError extends Exception {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6815308611805061580L;
}
