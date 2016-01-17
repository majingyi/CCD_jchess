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
import jchess.core.util.Player.PlayerColor;
import jchess.core.util.Settings;
import jchess.ui.lang.Language;

/**
 * Method to init game clock
 * 
 * @throws Exception
 * @author Jingyi Ma
 */
public class GameClock extends JPanel implements IClockListener {

	private static final long	serialVersionUID	= 1748377192145910384L;

	private Game							game							= null;
	private String						white_clock				= null;
	private String						black_clock				= null;
	private String						red_clock					= null;
	private BufferedImage			background				= null;

	public GameClock(Game game) throws Exception {
		super();
		this.game = game;
		this.background = new BufferedImage(850, 850, BufferedImage.TYPE_INT_ARGB);

		this.drawBackground();
		this.setDoubleBuffered(true);
	}

	public void start() throws Exception {
		if (Settings.isTimeLimitSet()) {
			game.getClockForPlayer(PlayerColor.WHITE).start();
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
		Font font = new Font("Serif", Font.ITALIC, 14); //$NON-NLS-1$

		g2d.setColor(Color.WHITE);
		g2d.fillRect(5, 30, 60, 30);
		g2d.setColor(Color.RED);
		g2d.fillRect(65, 30, 60, 30);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(125, 30, 60, 30);

		g2d.setColor(Color.BLACK);
		g2d.drawRect(5, 30, 180, 30);
		g2d.drawRect(5, 60, 180, 30);
		g2d.drawLine(65, 30, 65, 90);
		g2d.drawLine(125, 30, 125, 90);

		font = new Font("Serif", Font.ITALIC, 14); //$NON-NLS-1$
		g2d.setColor(Color.BLACK);
		g2d.drawString(Settings.getPlayerNameForColor(PlayerColor.WHITE), 10, 50);
		g2d.drawString(Settings.getPlayerNameForColor(PlayerColor.RED), 70, 50);
		g2d.setColor(Color.WHITE);
		g2d.drawString(Settings.getPlayerNameForColor(PlayerColor.BLACK), 130, 50);

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		white_clock = game.getClockForPlayer(PlayerColor.WHITE).toString();

		black_clock = game.getClockForPlayer(PlayerColor.BLACK).toString();

		red_clock = game.getClockForPlayer(PlayerColor.RED).toString();

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.background, 0, 0, this);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		/*
		 * Font font = new Font("Serif", Font.ITALIC, 14); //$NON-NLS-1$
		 * g2d.drawImage(this.background, 0, 0, this); g2d.setColor(Color.WHITE);
		 * g2d.fillRect(5, 30, 60, 30); g2d.setFont(font);
		 * 
		 * g2d.setColor(Color.BLACK); g2d.fillRect(65, 30, 60, 30); g2d.drawRect(5,
		 * 30, 180, 30); g2d.drawRect(5, 60, 180, 30); g2d.drawLine(65, 30, 65, 90);
		 * g2d.drawLine(125, 30, 125, 90); font = new Font("Serif", Font.ITALIC,
		 * 14); //$NON-NLS-1$ g2d.drawImage(this.background, 0, 0, this);
		 * g2d.setFont(font);
		 **/

		g.setColor(Color.BLACK);
		g2d.drawString(white_clock, 10, 80);
		g2d.drawString(black_clock, 70, 80);
		g2d.drawString(red_clock, 130, 80);

	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public void timeOver(Clock clock) {
		String color = Constants.EMPTY_STRING;

		if (clock == game.getClockForPlayer(PlayerColor.WHITE)) {
			color = Player.PlayerColor.WHITE.toString();
		}
		if (clock == game.getClockForPlayer(PlayerColor.BLACK)) {
			color = Player.PlayerColor.BLACK.toString();
		} else {
			color = Player.PlayerColor.RED.toString();
		}

		this.game.endGame(Language.getString("GameClock.8") + color + Language.getString("GameClock.9")); //$NON-NLS-1$ //$NON-NLS-2$
		this.stop();
	}

	public void setTime(int timeForGame) {
		game.setTimeForPlayerClocks(timeForGame);
	}
}
