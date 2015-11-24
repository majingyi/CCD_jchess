package jchess.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import jchess.core.util.Clock;
import jchess.core.util.Constants;
import jchess.core.util.Game;
import jchess.core.util.IClockListener;
import jchess.core.util.Player;
import jchess.core.util.Settings;
import jchess.ui.lang.Language;

public class GameClock extends JPanel implements IClockListener {

	private static final long	serialVersionUID	= 1748377192145910384L;

	private Clock							runningClock			= null;
	private Game							game							= null;
	private String						white_clock				= null;
	private String						black_clock				= null;
	private BufferedImage			background				= null;

	public GameClock(Game game) throws Exception {
		super();
		this.game = game;

		this.runningClock = game.getClockForPlayer(1);// running/active clock
		this.background = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);

		this.drawBackground();
		this.setDoubleBuffered(true);
	}

	/**
	 * Method to init game clock
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception {
		if (Settings.isTimeLimitSet()) {
			game.getClockForPlayer(1).start();
		}
	}

	/**
	 * Method to stop game clock
	 */
	public void stop() {
		game.stopPlayerClocks();
	}

	void drawBackground() {
		Graphics gr = this.background.getGraphics();
		Graphics2D g2d = (Graphics2D) gr;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Font font = new Font("Serif", Font.ITALIC, 20); //$NON-NLS-1$

		g2d.setColor(Color.WHITE);
		g2d.fillRect(5, 30, 80, 30);
		g2d.setFont(font);

		g2d.setColor(Color.BLACK);
		g2d.fillRect(85, 30, 90, 30);
		g2d.drawRect(5, 30, 170, 30);
		g2d.drawRect(5, 60, 170, 30);
		g2d.drawLine(85, 30, 85, 90);
		font = new Font("Serif", Font.ITALIC, 16); //$NON-NLS-1$
		g2d.drawString(Settings.getWhitePlayersName(), 10, 50);
		g2d.setColor(Color.WHITE);
		g2d.drawString(Settings.getBlackPlayersName(), 100, 50);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		white_clock = game.getClockForPlayer(1).toString();
		black_clock = game.getClockForPlayer(2).toString();

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.background, 0, 0, this);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Font font = new Font("Serif", Font.ITALIC, 20); //$NON-NLS-1$
		g2d.drawImage(this.background, 0, 0, this);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(5, 30, 80, 30);
		g2d.setFont(font);

		g2d.setColor(Color.BLACK);
		g2d.fillRect(85, 30, 90, 30);
		g2d.drawRect(5, 30, 170, 30);
		g2d.drawRect(5, 60, 170, 30);
		g2d.drawLine(85, 30, 85, 90);
		font = new Font("Serif", Font.ITALIC, 14); //$NON-NLS-1$
		g2d.drawImage(this.background, 0, 0, this);
		g2d.setFont(font);
		// g.drawString(Settings.getWhitePlayersName(), 10, 50);
		// g.setColor(Color.WHITE);
		// g.drawString(Settings.getBlackPlayersName(), 100, 50);
		g2d.setFont(font);
		g.setColor(Color.BLACK);
		g2d.drawString(white_clock, 10, 80);
		g2d.drawString(black_clock, 90, 80);
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public void timeOver(Clock clock) {
		String color = Constants.EMPTY_STRING;

		if (clock == game.getClockForPlayer(1)) {
			color = Player.colors.white.toString();
		} else if (clock == game.getClockForPlayer(2)) {
			color = Player.colors.black.toString();
		}
		// TODO add third player

		this.game.endGame(Language.getString("GameClock.8") + color + Language.getString("GameClock.9")); //$NON-NLS-1$ //$NON-NLS-2$
		this.stop();
	}

	public void setTime(int timeForGame) {
		game.setTimeForPlayerClocks(timeForGame);
	}
}