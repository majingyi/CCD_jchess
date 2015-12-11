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
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JPanel;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.pieces.King;
import jchess.core.util.Logging;
import jchess.core.util.Player.PlayerColor;

import jchess.core.board.HexagonChessFieldGraphInitializer;
import jchess.core.pieces.Piece;

/**
 * Class to represent chessboard. Chessboard is made from squares. It is setting
 * the squares of chessboard and sets the pieces(pawns) witch the owner is
 * current player on it.
 */
public class ChessboardUI extends JPanel {

	private static final long						serialVersionUID			= -1717218347823342830L;

	public static final int							top										= 0;
	public static final int							bottom								= 7;

	private Chessboard									board									= null;

	// image of chessboard
	private static Image								boardBackgroundImage	= null;

	// image of highlighted hexagon
	private static Image								sel_hexagon						= null;

	// image of square where piece can go
	private static Image								able_hexagon					= null;

	private Image												upDownLabel						= null;
	private Image												LeftRightLabel				= null;
	private Point												topLeft								= new Point(0, 0);
	// private float hexagon_height = 0;
	private float												hexagon_height				= 0;
	private float												hexagon_width					= 0;

	public static final int							img_width							= 1024;
	public static final int							img_height						= 768;
	private float												field_offset					= 0;
	private float												deviation_height			= 14;
	private float												deviation_width				= 6 + 22;

	private ArrayList<ChessboardField>	moves									= null;

	/**
	 * Chessboard class constructor
	 * 
	 * @param settings
	 *          reference to Settings class object for this chessboard
	 * @param moves_history
	 *          reference to Moves class object for this chessboard
	 * @throws Exception 
	 */
	public ChessboardUI(GameTab gt, MoveHistoryUI moves_history) throws Exception {
		board = new Chessboard(gt, moves_history);
		board.unselect();

		this.hexagon_height = (img_height - deviation_height) / 13;// we need to
																																// devide to
																																// know height
		// of field
		this.hexagon_width = (img_width - deviation_width) / 13;
		this.setDoubleBuffered(true);
		// this.drawLabels((int) this.hexagon_height);
		sel_hexagon = Theme.getImage("sel_hexagon.jpg"); // change image
		able_hexagon = Theme.getImage("able_hexagon.png"); // the image should
																												// be a hexagon ??

		boardBackgroundImage = Theme.getImage("chessboard.jpg");

	}/*--endOf-Chessboard--*/

	public int get_width() { // get width of the whole component, the image with
														// labels
		return this.getWidth();
	}

	public int get_height() {
		return this.getHeight();
	}

	// public int get_width(boolean withoutLables) {
	// return this.getHeight();
	// }

	// int get_height(boolean includeLabels) {
	// return ChessboardUI.boardBackgroundImage.getHeight(null) +
	// upDownLabel.getHeight(null);
	// }

	public int get_hexagon_height() {
		int result = (int) this.hexagon_height;
		return result;
	}

	/**
	 * Method to draw Chessboard and their elements (pieces etc.)
	 */
	public void draw() {
		this.getGraphics().drawImage(boardBackgroundImage, this.topLeft.x, this.topLeft.y, null);// draw
		// an
		// Image
		// of
		// chessboard
		// this.drawLabels();
		this.repaint();
	}/*--endOf-draw--*/

	/**
	 * Annotations to superclass Game updateing and painting the crossboard
	 */
	@Override
	public void update(Graphics g) {
		repaint();
	}

	// public Point getTopLeftPoint() {
	// return new Point(this.topLeft.x + this.upDownLabel.getHeight(null),
	// this.topLeft.y + this.upDownLabel.getHeight(null));
	// return new Point(this.topLeft.x , this.topLeft.y);
	// }

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		/*
		 * Point topLeftPoint = this.getTopLeftPoint(); if (topLeft.x <= 0 &&
		 * topLeft.y <= 0) // if renderLabels // and (0,0), than // draw it! (for //
		 * first run) { this.drawLabels(); } g2d.drawImage(this.upDownLabel, 0, 0,
		 * null); g2d.drawImage(this.upDownLabel, 0,
		 * ChessboardUI.boardBackgroundImage.getHeight(null) + topLeftPoint.y,
		 * null); g2d.drawImage(this.LeftRightLabel, 0, 0, null);
		 * g2d.drawImage(this.LeftRightLabel,
		 * ChessboardUI.boardBackgroundImage.getWidth(null) + topLeftPoint.x, 0,
		 * null);
		 */
		g2d.drawImage(boardBackgroundImage, topLeft.x, topLeft.y, null);// draw

		// TODO drwing of pieces
		// an
		// // Image of
		// // chessboard
		int numberofFields = 8;
		int start = 1;
		for (int identifierLetter = 1; identifierLetter <= 13; identifierLetter++) { // i
																																									// is
																																									// row,
																																									// y
																																									// is
																																									// column
			for (int identifierNum = start; identifierNum <= numberofFields + start; identifierNum++) {
				String identifier = HexagonChessFieldGraphInitializer.getIdentifierLetter(identifierLetter) + (identifierNum);
				if (board.getField(identifier).getPiece() != null) {
					try {
						drawPieceImage(g, board.getField(identifier).getPiece()); // check
																																			// if
																																			// there
					} catch (FileNotFoundException e) {
						Logging.log(e);
						// // TODO tell user
					}
				}
			}
			if (identifierLetter < 7) {
				numberofFields++;
			} else {
				numberofFields--;
				start++;
			}
		}
		if (board.getActiveField() != null) // if some square is active
		{
			g2d.drawImage(sel_hexagon, ((board.getActiveField() - 1) * (int) hexagon_width) + topLeft.x,
					((board.getActive_y_square() - 1) * (int) hexagon_height) + topLeftPoint.y, null);// draw
			// image of selected square
			ChessboardField tmpSquare = board.getFields()[(int) (board.getActiveField() - 1)][(int) (board.getActive_y_square() - 1)];
			if (tmpSquare.getPiece() != null) {
				try {
					this.moves = board.getFields()[(int) (board.getActiveField() - 1)][(int) (board.getActive_y_square() - 1)].getPiece().allMoves();
				} catch (Exception e) {
					Logging.log(e);
				}
			}

			for (Iterator<ChessboardField> it = moves.iterator(); moves != null && it.hasNext();) {
				Hexagon hexa = (Hexagon) it.next();
				g2d.drawImage(able_hexagon, (hexa.pozX * (int) hexagon_height) + topLeftPoint.x, (hexa.pozY * (int) hexagon_height) + topLeftPoint.y, null);
			}
		}
	}/*--endOf-paint--*/

	private void drawPieceImage(Graphics g, Piece piece) throws
  FileNotFoundException {
	try {
	   Graphics2D g2d = (Graphics2D) g;
	   g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	   RenderingHints.VALUE_ANTIALIAS_ON);
	   Point topLeft = getTopLeftPoint();
	   int height = get_hexagon_height();
	   ChessboardField sq = (ChessboardField) piece.getField();

	 int x = 1;
	 int y = 1;
	
 if (g != null) {
	 Image tempImage = Theme.getImageForPiece(piece.getPlayer().getColor(),
	 piece.getSymbol());
	 //BufferedImage resized = new BufferedImage(height, height,
	 BufferedImage.TYPE_INT_ARGB_PRE);
	Graphics2D imageGr = (Graphics2D) resized.createGraphics();
	 imageGr.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	 RenderingHints.VALUE_ANTIALIAS_ON);
	// imageGr.drawImage(tempImage, 0, 0, height, height, null);
	// imageGr.dispose();
	// Image img = resized.getScaledInstance(height, height, 0);
	// g2d.drawImage(img, x, y, null);
	// } else {
	//				Logging.logError(Language.getString("ChessboardUI.7")); //$NON-NLS-1$
	// }
	// } catch (java.lang.NullPointerException exc) {
	//			Logging.log(Language.getString("ChessboardUI.8"), exc); //$NON-NLS-1$
	// }
	// }

	public void resizeChessboard(int height) throws FileNotFoundException {
		BufferedImage resized = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics g = resized.createGraphics();
		g.drawImage(Theme.getImage("chessboard1.png"), 0, 0, height, height, null); //$NON-NLS-1$
		g.dispose();
		boardBackgroundImage = resized.getScaledInstance(height, height, 0);
		this.hexagon_height = (float) (height / 8);
		height += 2 * (this.upDownLabel.getHeight(null));
		this.setSize(height, height);

		resized = new BufferedImage((int) hexagon_height, (int) hexagon_height, BufferedImage.TYPE_INT_ARGB_PRE);
		g = resized.createGraphics();
		g.drawImage(org_able_hexagon, 0, 0, (int) hexagon_height, (int) hexagon_height, null);
		g.dispose();
		able_hexagon = resized.getScaledInstance((int) hexagon_height, (int) hexagon_height, 0);

		resized = new BufferedImage((int) hexagon_height, (int) hexagon_height, BufferedImage.TYPE_INT_ARGB_PRE);
		g = resized.createGraphics();
		g.drawImage(org_sel_hexagon, 0, 0, (int) hexagon_height, (int) hexagon_height, null);
		g.dispose();
		sel_hexagon = resized.getScaledInstance((int) hexagon_height, (int) hexagon_height, 0);
		this.drawLabels();
	}

	protected void drawLabels() {
		this.drawLabels((int) this.hexagon_height);
	}

	/*
	 * protected final void drawLabels(int hexagon_height) { // BufferedImage uDL
	 * = new BufferedImage(800, 800, // BufferedImage.TYPE_3BYTE_BGR); int
	 * min_label_height = 20; int labelHeight = (int) Math.ceil(hexagon_height /
	 * 4); labelHeight = (labelHeight < min_label_height) ? min_label_height :
	 * labelHeight; int labelWidth = (int) Math.ceil(hexagon_height * 8 + (2 *
	 * labelHeight)); BufferedImage uDL = new BufferedImage(labelWidth +
	 * min_label_height, labelHeight, BufferedImage.TYPE_3BYTE_BGR); Graphics2D
	 * uDL2D = (Graphics2D) uDL.createGraphics();
	 * uDL2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	 * RenderingHints.VALUE_ANTIALIAS_ON); uDL2D.setColor(Color.white);
	 * 
	 * uDL2D.fillRect(0, 0, labelWidth + min_label_height, labelHeight);
	 * uDL2D.setColor(Color.black); uDL2D.setFont(new Font("Arial", Font.BOLD,
	 * 12)); //$NON-NLS-1$ int addX = (hexagon_height / 2); addX += labelHeight;
	 * 
	 * String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h" };
	 * //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	 * //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ int j = 1; for (int i =
	 * letters.length; i > 0; i--, j++) { uDL2D.drawString(letters[i - 1],
	 * (hexagon_height * (j - 1)) + addX, 10 + (labelHeight / 3)); }
	 * 
	 * uDL2D.dispose(); this.upDownLabel = uDL;
	 * 
	 * uDL = new BufferedImage(labelHeight, labelWidth + min_label_height,
	 * BufferedImage.TYPE_3BYTE_BGR); uDL2D = (Graphics2D) uDL.createGraphics();
	 * uDL2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	 * RenderingHints.VALUE_ANTIALIAS_ON); uDL2D.setColor(Color.white); //
	 * uDL2D.fillRect(0, 0, 800, 800); uDL2D.fillRect(0, 0, labelHeight,
	 * labelWidth + min_label_height); uDL2D.setColor(Color.black);
	 * uDL2D.setFont(new Font("Arial", Font.BOLD, 12)); //$NON-NLS-1$
	 * 
	 * j = 1; for (int i = 8; i > 0; i--, j++) { uDL2D.drawString(new
	 * Integer(i).toString(), 3 + (labelHeight / 3), (hexagon_height * (j - 1)) +
	 * addX); }
	 * 
	 * uDL2D.dispose(); this.LeftRightLabel = uDL; }
	 */
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

	public King getKingForColor(PlayerColor color) {
		return board.getKingForColor(color);
	}

	public ChessboardField getField(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}
}