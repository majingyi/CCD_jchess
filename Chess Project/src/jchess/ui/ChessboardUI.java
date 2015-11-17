/*
 * # This program is free software: you can redistribute it and/or modify # it
 * under the terms of the GNU General Public License as published by # the Free
 * Software Foundation, either version 3 of the License, or # (at your option)
 * any later version. # # This program is distributed in the hope that it will
 * be useful, # but WITHOUT ANY WARRANTY; without even the implied warranty of #
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the # GNU General
 * Public License for more details. # # You should have received a copy of the
 * GNU General Public License # along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

/*
 * Authors: Mateusz Sławomir Lach ( matlak, msl ) Damian Marciniak
 */
package jchess.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import jchess.Settings;
import jchess.board.Chessboard;
import jchess.core.Logging;
import jchess.core.Theme;
import jchess.pieces.Piece;
import jchess.resources.i18n.Language;
import jchess.util.Moves;
import jchess.util.Square;

/**
 * Class to represent chessboard. Chessboard is made from squares. It is setting
 * the squers of chessboard and sets the pieces(pawns) witch the owner is
 * current player on it.
 */
public class ChessboardUI extends JPanel {

	private static final long		serialVersionUID	= -1717218347823342830L;

	public static final int			top								= 0;
	public static final int			bottom						= 7;

	private Chessboard					board							= null;

	// image of chessboard
	private static Image				image							= null;
	// image of highlighted square
	private static final Image	org_sel_square		= Theme.getImage("sel_square.png"); //$NON-NLS-1$
	// image of highlighted square
	private static Image				sel_square				= org_sel_square;
	// image of square where piece can go
	private static final Image	org_able_square		= Theme.getImage("able_square.png"); //$NON-NLS-1$
	// image of square where piece can go
	private static Image				able_square				= org_able_square;

	private Image								upDownLabel				= null;
	private Image								LeftRightLabel		= null;
	private Point								topLeft						= new Point(0, 0);
	private float								square_height			= 0;

	public static final int			img_widht					= 480;
	public static final int			img_height				= img_widht;

	private ArrayList<Square>		moves;
	private Settings						settings;

	/**
	 * Chessboard class constructor
	 * 
	 * @param settings
	 *          reference to Settings class object for this chessboard
	 * @param moves_history
	 *          reference to Moves class object for this chessboard
	 */
	public ChessboardUI(Settings settings, Moves moves_history) {
		board = new Chessboard(this, settings, moves_history);
		this.settings = settings;
		board.activeSquare = null;
		this.square_height = img_height / 8;// we need to devide to know height
		// of field
		board.active_x_square = 0;
		board.active_y_square = 0;

		this.setDoubleBuffered(true);
		this.drawLabels((int) this.square_height);

	}/*--endOf-Chessboard--*/

	/**
	 * method to get reference to square from given x and y integeres
	 * 
	 * @param x
	 *          x position on chessboard
	 * @param y
	 *          y position on chessboard
	 * @return reference to searched square
	 */
	public Square getSquare(int x, int y) {
		if ((x > this.get_height()) || (y > this.get_widht())) // test if click
		// is out of
		// chessboard
		{
			Logging.log(Language.getString("ChessboardUI.2")); //$NON-NLS-1$
			return null;
		}
		if (this.settings.renderLabels) {
			x -= this.upDownLabel.getHeight(null);
			y -= this.upDownLabel.getHeight(null);
		}
		double square_x = x / square_height;// count which field in X was
		// clicked
		double square_y = y / square_height;// count which field in Y was
		// clicked

		if (square_x > (int) square_x) // if X is more than X parsed to Integer
		{
			square_x = (int) square_x + 1;// parse to integer and increment
		}
		if (square_y > (int) square_y) // if X is more than X parsed to Integer
		{
			square_y = (int) square_y + 1;// parse to integer and increment
		}
		// Square newActiveSquare =
		// this.squares[(int)square_x-1][(int)square_y-1];//4test
		Logging.log("square_x: " + square_x + " square_y: " + square_y + " \n"); // 4tests //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Square result;
		try {
			result = board.squares[(int) square_x - 1][(int) square_y - 1];
		} catch (java.lang.ArrayIndexOutOfBoundsException exc) {
			Logging.log(Language.getString("ChessboardUI.6"), exc); //$NON-NLS-1$
			return null;
		}
		return result;
	}

	public int get_widht() {
		return this.get_widht(false);
	}

	public int get_height() {
		return this.get_height(false);
	}

	public int get_widht(boolean includeLables) {
		return this.getHeight();
	}/*--endOf-get_widht--*/

	int get_height(boolean includeLabels) {
		if (this.settings.renderLabels) {
			return ChessboardUI.image.getHeight(null) + upDownLabel.getHeight(null);
		}
		return ChessboardUI.image.getHeight(null);
	}/*--endOf-get_height--*/

	public int get_square_height() {
		int result = (int) this.square_height;
		return result;
	}

	/**
	 * Method to draw Chessboard and their elements (pieces etc.)
	 */
	public void draw() {
		this.getGraphics().drawImage(image, this.getTopLeftPoint().x, this.getTopLeftPoint().y, null);// draw
																																																	// an
																																																	// Image
																																																	// of
																																																	// chessboard
		this.drawLabels();
		this.repaint();
	}/*--endOf-draw--*/

	/**
	 * Annotations to superclass Game updateing and painting the crossboard
	 */
	@Override
	public void update(Graphics g) {
		repaint();
	}

	public Point getTopLeftPoint() {
		if (this.settings.renderLabels) {
			return new Point(this.topLeft.x + this.upDownLabel.getHeight(null), this.topLeft.y + this.upDownLabel.getHeight(null));
		}
		return this.topLeft;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Point topLeftPoint = this.getTopLeftPoint();
		if (this.settings.renderLabels) {
			if (topLeftPoint.x <= 0 && topLeftPoint.y <= 0) // if renderLabels
			// and (0,0), than
			// draw it! (for
			// first run)
			{
				this.drawLabels();
			}
			g2d.drawImage(this.upDownLabel, 0, 0, null);
			g2d.drawImage(this.upDownLabel, 0, ChessboardUI.image.getHeight(null) + topLeftPoint.y, null);
			g2d.drawImage(this.LeftRightLabel, 0, 0, null);
			g2d.drawImage(this.LeftRightLabel, ChessboardUI.image.getHeight(null) + topLeftPoint.x, 0, null);
		}
		g2d.drawImage(image, topLeftPoint.x, topLeftPoint.y, null);// draw an
		// Image of
		// chessboard
		for (int i = 0; i < 8; i++) // drawPiecesOnSquares
		{
			for (int y = 0; y < 8; y++) {
				if (board.squares[i][y].piece != null) {
					drawPieceImage(g, board.squares[i][y].piece);
				}
			}
		}// --endOf--drawPiecesOnSquares
		if ((board.active_x_square != 0) && (board.active_y_square != 0)) // if
		// some
		// square
		// is
		// active
		{
			g2d.drawImage(sel_square, ((board.active_x_square - 1) * (int) square_height) + topLeftPoint.x, ((board.active_y_square - 1) * (int) square_height)
					+ topLeftPoint.y, null);// draw image of selected
			// square
			Square tmpSquare = board.squares[(int) (board.active_x_square - 1)][(int) (board.active_y_square - 1)];
			if (tmpSquare.piece != null) {
				this.moves = board.squares[(int) (board.active_x_square - 1)][(int) (board.active_y_square - 1)].piece.allMoves();
			}

			for (Iterator<Square> it = moves.iterator(); moves != null && it.hasNext();) {
				Square sq = (Square) it.next();
				g2d.drawImage(able_square, (sq.pozX * (int) square_height) + topLeftPoint.x, (sq.pozY * (int) square_height) + topLeftPoint.y, null);
			}
		}
	}/*--endOf-paint--*/

	private void drawPieceImage(Graphics g, Piece piece) {
		try {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Point topLeft = getTopLeftPoint();
			int height = get_square_height();
			Square sq = piece.getSquare();
			int x = (sq.pozX * height) + topLeft.x;
			int y = (sq.pozY * height) + topLeft.y;

			if (g != null) {
				Image tempImage = Theme.getImageForPiece(piece.player.color, piece.getSymbol());
				BufferedImage resized = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB_PRE);
				Graphics2D imageGr = (Graphics2D) resized.createGraphics();
				imageGr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				imageGr.drawImage(tempImage, 0, 0, height, height, null);
				imageGr.dispose();
				Image img = resized.getScaledInstance(height, height, 0);
				g2d.drawImage(img, x, y, null);
			} else {
				Logging.logError(Language.getString("ChessboardUI.7")); //$NON-NLS-1$
			}
		} catch (java.lang.NullPointerException exc) {
			Logging.log(Language.getString("ChessboardUI.8"), exc); //$NON-NLS-1$
		}
	}

	public void resizeChessboard(int height) {
		BufferedImage resized = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics g = resized.createGraphics();
		g.drawImage(Theme.getImage("chessboard.png"), 0, 0, height, height, null); //$NON-NLS-1$
		g.dispose();
		image = resized.getScaledInstance(height, height, 0);
		this.square_height = (float) (height / 8);
		if (this.settings.renderLabels) {
			height += 2 * (this.upDownLabel.getHeight(null));
		}
		this.setSize(height, height);

		resized = new BufferedImage((int) square_height, (int) square_height, BufferedImage.TYPE_INT_ARGB_PRE);
		g = resized.createGraphics();
		g.drawImage(org_able_square, 0, 0, (int) square_height, (int) square_height, null);
		g.dispose();
		able_square = resized.getScaledInstance((int) square_height, (int) square_height, 0);

		resized = new BufferedImage((int) square_height, (int) square_height, BufferedImage.TYPE_INT_ARGB_PRE);
		g = resized.createGraphics();
		g.drawImage(org_sel_square, 0, 0, (int) square_height, (int) square_height, null);
		g.dispose();
		sel_square = resized.getScaledInstance((int) square_height, (int) square_height, 0);
		this.drawLabels();
	}

	protected void drawLabels() {
		this.drawLabels((int) this.square_height);
	}

	protected final void drawLabels(int square_height) {
		// BufferedImage uDL = new BufferedImage(800, 800,
		// BufferedImage.TYPE_3BYTE_BGR);
		int min_label_height = 20;
		int labelHeight = (int) Math.ceil(square_height / 4);
		labelHeight = (labelHeight < min_label_height) ? min_label_height : labelHeight;
		int labelWidth = (int) Math.ceil(square_height * 8 + (2 * labelHeight));
		BufferedImage uDL = new BufferedImage(labelWidth + min_label_height, labelHeight, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D uDL2D = (Graphics2D) uDL.createGraphics();
		uDL2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		uDL2D.setColor(Color.white);

		uDL2D.fillRect(0, 0, labelWidth + min_label_height, labelHeight);
		uDL2D.setColor(Color.black);
		uDL2D.setFont(new Font("Arial", Font.BOLD, 12)); //$NON-NLS-1$
		int addX = (square_height / 2);
		if (this.settings.renderLabels) {
			addX += labelHeight;
		}

		String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		if (!this.settings.upsideDown) {
			for (int i = 1; i <= letters.length; i++) {
				uDL2D.drawString(letters[i - 1], (square_height * (i - 1)) + addX, 10 + (labelHeight / 3));
			}
		} else {
			int j = 1;
			for (int i = letters.length; i > 0; i--, j++) {
				uDL2D.drawString(letters[i - 1], (square_height * (j - 1)) + addX, 10 + (labelHeight / 3));
			}
		}
		uDL2D.dispose();
		this.upDownLabel = uDL;

		uDL = new BufferedImage(labelHeight, labelWidth + min_label_height, BufferedImage.TYPE_3BYTE_BGR);
		uDL2D = (Graphics2D) uDL.createGraphics();
		uDL2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		uDL2D.setColor(Color.white);
		// uDL2D.fillRect(0, 0, 800, 800);
		uDL2D.fillRect(0, 0, labelHeight, labelWidth + min_label_height);
		uDL2D.setColor(Color.black);
		uDL2D.setFont(new Font("Arial", Font.BOLD, 12)); //$NON-NLS-1$

		if (this.settings.upsideDown) {
			for (int i = 1; i <= 8; i++) {
				uDL2D.drawString(new Integer(i).toString(), 3 + (labelHeight / 3), (square_height * (i - 1)) + addX);
			}
		} else {
			int j = 1;
			for (int i = 8; i > 0; i--, j++) {
				uDL2D.drawString(new Integer(i).toString(), 3 + (labelHeight / 3), (square_height * (j - 1)) + addX);
			}
		}
		uDL2D.dispose();
		this.LeftRightLabel = uDL;
	}

	public void componentMoved(ComponentEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	public void componentShown(ComponentEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	public void componentHidden(ComponentEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	public Chessboard getChessboard() {
		return board;
	}
}