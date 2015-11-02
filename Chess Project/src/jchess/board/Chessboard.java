package jchess.board;

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
import jchess.pieces.Bishop;
import jchess.pieces.King;
import jchess.pieces.Knight;
import jchess.pieces.Pawn;
import jchess.pieces.PawnDoubleStep;
import jchess.pieces.Piece;
import jchess.pieces.Queen;
import jchess.pieces.Rook;
import jchess.ui.GUI;
import jchess.util.Move;
import jchess.util.Moves;
import jchess.util.Moves.castling;
import jchess.util.Player;
import jchess.util.Square;

/** Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squers of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */

public class Chessboard extends JPanel {
	private static final long		serialVersionUID		= -1717218347823342830L;

	public static final int			top									= 0;
	public static final int			bottom							= 7;

	// squares of chessboard
	public Square								squares[][];
	// image of chessboard
	private static final Image	orgImage						= GUI.loadImage("chessboard.png");
	// image of chessboard

	private static Image				image								= Chessboard.orgImage;

	// image of highlighted square
	private static final Image	org_sel_square			= GUI.loadImage("sel_square.png");
	// image of highlighted square
	private static Image				sel_square					= org_sel_square;
	// image of square where piece can go
	private static final Image	org_able_square			= GUI.loadImage("able_square.png");
	// image of square where piece can go
	private static Image				able_square					= org_able_square;
	public Square								activeSquare;
	private Image								upDownLabel					= null;
	private Image								LeftRightLabel			= null;
	private Point								topLeft							= new Point(0, 0);
	private int									active_x_square;
	private int									active_y_square;
	private float								square_height;

	public static final int			img_x								= 5;
	public static final int			img_y								= img_x;
	public static final int			img_widht						= 480;
	public static final int			img_height					= img_widht;

	private ArrayList<Square>		moves;
	private Settings						settings;
	public King									kingWhite;
	public King									kingBlack;

	public Pawn									twoSquareMovedPawn	= null;
	public Pawn									twoSquareMovedPawn2	= null;
	private Moves								moves_history;

	/** Chessboard class constructor
	 * @param settings reference to Settings class object for this chessboard
	 * @param moves_history reference to Moves class object for this chessboard 
	 */

	public Chessboard(Settings settings, Moves moves_history) {
		this.settings = settings;
		this.activeSquare = null;
		this.square_height = img_height / 8;// we need to devide to know height
		// of field
		this.squares = new Square[8][8];// initalization of 8x8 chessboard
		this.active_x_square = 0;
		this.active_y_square = 0;
		for (int i = 0; i < 8; i++) {// create object for each square
			for (int y = 0; y < 8; y++) {
				this.squares[i][y] = new Square(i, y, null);
			}
		} // --endOf--create object for each square
		this.moves_history = moves_history;
		this.setDoubleBuffered(true);
		this.drawLabels((int) this.square_height);
	}/*--endOf-Chessboard--*/

	/** Method setPieces on begin of new game or loaded game
	 * @param places string with pieces to set on chessboard
	 * @param plWhite reference to white player
	 * @param plBlack reference to black player
	 */

	public void setPieces(String places, Player plWhite, Player plBlack) {

		if (places.equals("")) // if newGame
		{
			if (this.settings.upsideDown) {
				this.setPieces4NewGame(true, plWhite, plBlack);
			} else {
				this.setPieces4NewGame(false, plWhite, plBlack);
			}

		} else // if loadedGame
		{
			return;
		}
	}/*--endOf-setPieces--*/

	private void setPieces4NewGame(boolean upsideDown, Player plWhite, Player plBlack) {

		/* WHITE PIECES */
		Player player = plBlack;
		Player player1 = plWhite;
		if (upsideDown) // if white on Top
		{
			player = plWhite;
			player1 = plBlack;
		}
		this.setFigures4NewGame(0, player, upsideDown);
		this.setPawns4NewGame(1, player);
		this.setFigures4NewGame(7, player1, upsideDown);
		this.setPawns4NewGame(6, player1);
	}/*--endOf-setPieces(boolean upsideDown)--*/

	/**  method set Figures in row (and set Queen and King to right position)
	 *  @param i row where to set figures (Rook, Knight etc.)
	 *  @param player which is owner of pawns
	 *  @param upsideDown if true white pieces will be on top of chessboard
	 * */
	private void setFigures4NewGame(int i, Player player, boolean upsideDown) {

		if (i != 0 && i != 7) {
			System.out.println("error setting figures like rook etc.");
			return;
		} else if (i == 0) {
			player.goDown = true;
		}

		this.squares[0][i].setPiece(new Rook(this, player));
		this.squares[7][i].setPiece(new Rook(this, player));
		this.squares[1][i].setPiece(new Knight(this, player));
		this.squares[6][i].setPiece(new Knight(this, player));
		this.squares[2][i].setPiece(new Bishop(this, player));
		this.squares[5][i].setPiece(new Bishop(this, player));

		if (upsideDown) {
			this.squares[4][i].setPiece(new Queen(this, player));
			if (player.color == Player.colors.white) {
				this.squares[3][i].setPiece(kingWhite = new King(this, player));
			} else {
				this.squares[3][i].setPiece(kingBlack = new King(this, player));
			}
		} else {
			this.squares[3][i].setPiece(new Queen(this, player));
			if (player.color == Player.colors.white) {
				this.squares[4][i].setPiece(kingWhite = new King(this, player));
			} else {
				this.squares[4][i].setPiece(kingBlack = new King(this, player));
			}
		}
	}

	/**  method set Pawns in row
	 *  @param i row where to set pawns
	 *  @param player player which is owner of pawns
	 * */
	private void setPawns4NewGame(int i, Player player) {
		if (i != 1 && i != 6) {
			System.out.println("error setting pawns etc.");
			return;
		}
		for (int x = 0; x < 8; x++) {
			this.squares[x][i].setPiece(new PawnDoubleStep(this, player));
		}
	}

	/** method to get reference to square from given x and y integeres
	 * @param x x position on chessboard
	 * @param y y position on chessboard
	 * @return reference to searched square
	 */
	public Square getSquare(int x, int y) {
		if ((x > this.get_height()) || (y > this.get_widht())) // test if click
		// is out of
		// chessboard
		{
			System.out.println("click out of chessboard.");
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
		System.out.println("square_x: " + square_x + " square_y: " + square_y + " \n"); // 4tests
		Square result;
		try {
			result = this.squares[(int) square_x - 1][(int) square_y - 1];
		} catch (java.lang.ArrayIndexOutOfBoundsException exc) {
			System.out.println("!!Array out of bounds when getting Square with Chessboard.getSquare(int,int) : " + exc);
			return null;
		}
		return result;
	}

	/** Method selecting piece in chessboard
	 * @param  sq square to select (when clicked))
	 */
	public void select(Square sq) {
		this.activeSquare = sq;
		this.active_x_square = sq.pozX + 1;
		this.active_y_square = sq.pozY + 1;

		// this.draw();//redraw
		System.out.println("active_x: " + this.active_x_square + " active_y: " + this.active_y_square);// 4tests
		repaint();

	}

	/*--endOf-select--*//** Method set variables active_x_square & active_y_square
											* to 0 values.
											*/
	public void unselect() {
		this.active_x_square = 0;
		this.active_y_square = 0;
		this.activeSquare = null;
		// this.draw();//redraw
		repaint();
	}/*--endOf-unselect--*/

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
			return Chessboard.image.getHeight(null) + upDownLabel.getHeight(null);
		}
		return Chessboard.image.getHeight(null);
	}/*--endOf-get_height--*/

	public int get_square_height() {
		int result = (int) this.square_height;
		return result;
	}

	public boolean redo() {
		return redo(true);
	}

	public boolean redo(boolean refresh) {
		if (this.settings.gameType == Settings.gameTypes.local) // redo only for
		// local game
		{
			Move first = this.moves_history.redo();

			Square from = null;
			Square to = null;

			if (first != null) {
				from = first.getFrom();
				to = first.getTo();

				this.move(this.squares[from.pozX][from.pozY], this.squares[to.pozX][to.pozY], true, false);
				if (first.getPromotedPiece() != null) {
					Pawn pawn = (Pawn) this.squares[to.pozX][to.pozY].piece;
					pawn.square = null;

					this.squares[to.pozX][to.pozY].piece = first.getPromotedPiece();
					Piece promoted = this.squares[to.pozX][to.pozY].piece;
					promoted.square = this.squares[to.pozX][to.pozY];
				}
				return true;
			}

		}
		return false;
	}

	public boolean undo() {
		return undo(true);
	}

	public synchronized boolean undo(boolean refresh) // undo last move
	{
		Move last = this.moves_history.undo();

		if (last != null && last.getFrom() != null) {
			Square begin = last.getFrom();
			Square end = last.getTo();
			try {
				Piece moved = last.getMovedPiece();
				this.squares[begin.pozX][begin.pozY].piece = moved;

				moved.square = this.squares[begin.pozX][begin.pozY];

				Piece taken = last.getTakenPiece();
				if (last.getCastlingMove() != castling.none) {
					Piece rook = null;
					if (last.getCastlingMove() == castling.shortCastling) {
						rook = this.squares[end.pozX - 1][end.pozY].piece;
						this.squares[7][begin.pozY].piece = rook;
						rook.square = this.squares[7][begin.pozY];
						this.squares[end.pozX - 1][end.pozY].piece = null;
					} else {
						rook = this.squares[end.pozX + 1][end.pozY].piece;
						this.squares[0][begin.pozY].piece = rook;
						rook.square = this.squares[0][begin.pozY];
						this.squares[end.pozX + 1][end.pozY].piece = null;
					}
					((King) moved).wasMotion = false;
					((Rook) rook).wasMotion = false;
				} else if (moved.name.equals("Rook")) {
					((Rook) moved).wasMotion = false;
				} else if (moved.name.equals("Pawn") && last.wasEnPassant()) {
					Pawn pawn = (Pawn) last.getTakenPiece();
					this.squares[end.pozX][begin.pozY].piece = pawn;
					pawn.square = this.squares[end.pozX][begin.pozY];

				} else if (moved.name.equals("Pawn") && last.getPromotedPiece() != null) {
					Piece promoted = this.squares[end.pozX][end.pozY].piece;
					promoted.square = null;
					this.squares[end.pozX][end.pozY].piece = null;
				}

				// check one more move back for en passant
				Move oneMoveEarlier = this.moves_history.getLastMoveFromHistory();
				if (oneMoveEarlier != null && oneMoveEarlier.wasPawnTwoFieldsMove()) {
					Piece canBeTakenEnPassant = this.squares[oneMoveEarlier.getTo().pozX][oneMoveEarlier.getTo().pozY].piece;
					if (canBeTakenEnPassant.name.equals("Pawn")) {
						this.twoSquareMovedPawn = (Pawn) canBeTakenEnPassant;
					}
				}

				if (taken != null && !last.wasEnPassant()) {
					this.squares[end.pozX][end.pozY].piece = taken;
					taken.square = this.squares[end.pozX][end.pozY];
				} else {
					this.squares[end.pozX][end.pozY].piece = null;
				}

				if (refresh) {
					this.unselect();// unselect square
					repaint();
				}

			} catch (java.lang.ArrayIndexOutOfBoundsException exc) {
				return false;
			} catch (java.lang.NullPointerException exc) {
				return false;
			}

			return true;
		} else {
			return false;
		}
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
			g2d.drawImage(this.upDownLabel, 0, Chessboard.image.getHeight(null) + topLeftPoint.y, null);
			g2d.drawImage(this.LeftRightLabel, 0, 0, null);
			g2d.drawImage(this.LeftRightLabel, Chessboard.image.getHeight(null) + topLeftPoint.x, 0, null);
		}
		g2d.drawImage(image, topLeftPoint.x, topLeftPoint.y, null);// draw an
		// Image of
		// chessboard
		for (int i = 0; i < 8; i++) // drawPiecesOnSquares
		{
			for (int y = 0; y < 8; y++) {
				if (this.squares[i][y].piece != null) {
					this.squares[i][y].piece.draw(g);// draw image of Piece
				}
			}
		} // --endOf--drawPiecesOnSquares
		if ((this.active_x_square != 0) && (this.active_y_square != 0)) // if
		// some
		// square
		// is
		// active
		{
			g2d.drawImage(sel_square, ((this.active_x_square - 1) * (int) square_height) + topLeftPoint.x,
					((this.active_y_square - 1) * (int) square_height) + topLeftPoint.y, null);// draw
																																											// image
																																											// of
																																											// selected
			// square
			Square tmpSquare = this.squares[(int) (this.active_x_square - 1)][(int) (this.active_y_square - 1)];
			if (tmpSquare.piece != null) {
				this.moves = this.squares[(int) (this.active_x_square - 1)][(int) (this.active_y_square - 1)].piece.allMoves();
			}

			for (Iterator<Square> it = moves.iterator(); moves != null && it.hasNext();) {
				Square sq = (Square) it.next();
				g2d.drawImage(able_square, (sq.pozX * (int) square_height) + topLeftPoint.x, (sq.pozY * (int) square_height) + topLeftPoint.y, null);
			}
		}
	}/*--endOf-paint--*/

	public void resizeChessboard(int height) {
		BufferedImage resized = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics g = resized.createGraphics();
		g.drawImage(Chessboard.orgImage, 0, 0, height, height, null);
		g.dispose();
		Chessboard.image = resized.getScaledInstance(height, height, 0);
		this.square_height = (float) (height / 8);
		if (this.settings.renderLabels) {
			height += 2 * (this.upDownLabel.getHeight(null));
		}
		this.setSize(height, height);

		resized = new BufferedImage((int) square_height, (int) square_height, BufferedImage.TYPE_INT_ARGB_PRE);
		g = resized.createGraphics();
		g.drawImage(Chessboard.org_able_square, 0, 0, (int) square_height, (int) square_height, null);
		g.dispose();
		Chessboard.able_square = resized.getScaledInstance((int) square_height, (int) square_height, 0);

		resized = new BufferedImage((int) square_height, (int) square_height, BufferedImage.TYPE_INT_ARGB_PRE);
		g = resized.createGraphics();
		g.drawImage(Chessboard.org_sel_square, 0, 0, (int) square_height, (int) square_height, null);
		g.dispose();
		Chessboard.sel_square = resized.getScaledInstance((int) square_height, (int) square_height, 0);
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
		uDL2D.setFont(new Font("Arial", Font.BOLD, 12));
		int addX = (square_height / 2);
		if (this.settings.renderLabels) {
			addX += labelHeight;
		}

		String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h" };
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
		uDL2D.setFont(new Font("Arial", Font.BOLD, 12));

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

}
