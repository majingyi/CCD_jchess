package jchess.core.board;

import jchess.JChessApp;
import jchess.core.pieces.Bishop;
import jchess.core.pieces.King;
import jchess.core.pieces.Knight;
import jchess.core.pieces.Pawn;
import jchess.core.pieces.Piece;
import jchess.core.pieces.Queen;
import jchess.core.pieces.Rook;
import jchess.core.util.Constants;
import jchess.core.util.Logging;
import jchess.core.util.Move;
import jchess.core.util.Moves;
import jchess.core.util.Player;
import jchess.core.util.Settings;
import jchess.core.util.Moves.castling;
import jchess.ui.ChessboardUI;
import jchess.ui.lang.Language;

/**
 * Class to represent chessboard. Chessboard is made from squares. It is setting
 * the squers of chessboard and sets the pieces(pawns) witch the owner is
 * current player on it.
 */

public class Chessboard {

	public static final int	top									= 0;
	public static final int	bottom							= 7;

	private ChessboardUI		uiChessboard				= null;

	// squares of chessboard
	public Square						squares[][];

	public Square						activeSquare;

	public int							active_x_square;
	public int							active_y_square;

	public static final int	img_x								= 5;
	public static final int	img_y								= img_x;
	public static final int	img_widht						= 480;
	public static final int	img_height					= img_widht;

	private Settings				settings;
	public King							kingWhite;
	public King							kingBlack;

	public Pawn							twoSquareMovedPawn	= null;
	public Pawn							twoSquareMovedPawn2	= null;
	private Moves						moves_history;

	public Chessboard(ChessboardUI ui, Settings settings, Moves movesHistory) {
		uiChessboard = ui;
		moves_history = movesHistory;

		initChessBoard();

		this.settings = settings;
	}

	private void initChessBoard() {
		squares = new Square[8][8];

		for (int i = 0; i < 8; i++) {// create object for each square
			for (int y = 0; y < 8; y++) {
				squares[i][y] = new Square(i, y, null);
			}
		}// --endOf--create object for each square
	}

	/**
	 * Method setPieces on begin of new game or loaded game
	 * 
	 * @param places
	 *          string with pieces to set on chessboard
	 * @param plWhite
	 *          reference to white player
	 * @param plBlack
	 *          reference to black player
	 * @throws Exception
	 */

	public void setPieces(String places, Player plWhite, Player plBlack) throws Exception {

		if ((plWhite.color == plBlack.color) == false) {

			if (places.equals(Constants.EMPTY_STRING)) // if newGame
			{
				if (this.settings.isUpsideDown()) {
					this.setPieces4NewGame(true, plWhite, plBlack);
				} else {
					this.setPieces4NewGame(false, plWhite, plBlack);
				}
			}
		} else {
			throw new Exception(Language.getString("Chessboard.0")); //$NON-NLS-1$
		}
	}

	/**
	 * Method to move piece over chessboard
	 * 
	 * @param xFrom
	 *          from which x move piece
	 * @param yFrom
	 *          from which y move piece
	 * @param xTo
	 *          to which x move piece
	 * @param yTo
	 *          to which y move piece
	 * @throws Exception
	 */
	public void move(int xFrom, int yFrom, int xTo, int yTo) throws Exception {
		Square fromSQ = null;
		Square toSQ = null;
		try {
			fromSQ = this.squares[xFrom][yFrom];
			toSQ = this.squares[xTo][yTo];
		} catch (java.lang.IndexOutOfBoundsException exc) {
			Logging.log(Language.getString("Chessboard.1"), exc); //$NON-NLS-1$
			return;
		}
		this.move(fromSQ, toSQ, true);
	}

	public void move(Square begin, Square end, boolean refresh) throws Exception {
		this.move(begin, end, refresh, true);
	}

	private void setPieces4NewGame(boolean upsideDown, Player plWhite, Player plBlack) throws Exception {

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

	/**
	 * method set Figures in row (and set Queen and King to right position)
	 * 
	 * @param i
	 *          row where to set figures (Rook, Knight etc.)
	 * @param player
	 *          which is owner of pawns
	 * @param upsideDown
	 *          if true white pieces will be on top of chessboard
	 * @throws Exception
	 * */
	private void setFigures4NewGame(int i, Player player, boolean upsideDown) throws Exception {

		if (i != 0 && i != 7) {
			Logging.logError(Language.getString("Chessboard.2")); //$NON-NLS-1$
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

	/**
	 * method set Pawns in row
	 * 
	 * @param i
	 *          row where to set pawns
	 * @param player
	 *          player which is owner of pawns
	 * @throws Exception
	 * */
	private void setPawns4NewGame(int i, Player player) throws Exception {
		if (i != 1 && i != 6) {
			Logging.logError(Language.getString("Chessboard.3")); //$NON-NLS-1$
			return;
		}
		for (int x = 0; x < 8; x++) {
			this.squares[x][i].setPiece(new Pawn(this, player));
		}
	}

	/**
	 * Method selecting piece in chessboard
	 * 
	 * @param sq
	 *          square to select (when clicked))
	 */
	public void select(Square sq) {
		this.activeSquare = sq;
		this.active_x_square = sq.pozX + 1;
		this.active_y_square = sq.pozY + 1;

		Logging.log("active_x: " + this.active_x_square + " active_y: " + this.active_y_square);// 4tests //$NON-NLS-1$ //$NON-NLS-2$
		uiChessboard.repaint();
	}

	/*--endOf-select--*//**
	 * Method set variables active_x_square &
	 * active_y_square to 0 values.
	 */
	public void unselect() {
		this.active_x_square = 0;
		this.active_y_square = 0;
		this.activeSquare = null;
		// this.draw();//redraw

		uiChessboard.repaint();
	}/*--endOf-unselect--*/

	public boolean redo() throws Exception {
		return redo(true);
	}

	public boolean redo(boolean refresh) throws Exception {
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
					pawn.setSquare(null);

					this.squares[to.pozX][to.pozY].piece = first.getPromotedPiece();
					Piece promoted = this.squares[to.pozX][to.pozY].piece;
					promoted.setSquare(this.squares[to.pozX][to.pozY]);
				}
				return true;
			}

		}
		return false;
	}

	public void move(Square begin, Square end) throws Exception {
		move(begin, end, true);
	}

	/**
	 * Method move piece from square to square
	 * 
	 * @param begin
	 *          square from which move piece
	 * @param end
	 *          square where we want to move piece *
	 * @param refresh
	 *          chessboard, default: true
	 * @throws Exception
	 * */
	public void move(Square begin, Square end, boolean refresh, boolean clearForwardHistory) throws Exception {

		castling wasCastling = Moves.castling.none;
		Piece promotedPiece = null;
		boolean wasEnPassant = false;
		if (end.piece != null) {
			end.piece.setSquare(null);
		}

		Square tempBegin = new Square(begin);// 4 moves history
		Square tempEnd = new Square(end); // 4 moves history

		twoSquareMovedPawn2 = twoSquareMovedPawn;

		begin.piece.setSquare(end);// set square of piece to ending
		end.piece = begin.piece;// for ending square set piece from beginin
		// square
		begin.piece = null;// make null piece for begining square

		if (end.piece.getSymbol().equals("King")) { //$NON-NLS-1$
			if (((King) end.piece).wasMotion == false) {
				((King) end.piece).wasMotion = true;
			}

			// Castling
			if (begin.pozX + 2 == end.pozX) {
				move(squares[7][begin.pozY], squares[end.pozX - 1][begin.pozY], false, false);
				wasCastling = Moves.castling.shortCastling;
				// this.moves_history.addMove(tempBegin, tempEnd,
				// clearForwardHistory, wasCastling, wasEnPassant);
				// return;
			} else if (begin.pozX - 2 == end.pozX) {
				move(squares[0][begin.pozY], squares[end.pozX + 1][begin.pozY], false, false);
				wasCastling = Moves.castling.longCastling;
				// this.moves_history.addMove(tempBegin, tempEnd,
				// clearForwardHistory, wasCastling, wasEnPassant);
				// return;
			}
			// endOf Castling
		} else if (end.piece.getSymbol() == Rook.SYMBOL) {
			if (((Rook) end.piece).wasMotion == false) {
				((Rook) end.piece).wasMotion = true;
			}
		} else if (end.piece.getSymbol() == Pawn.SYMBOL) {
			if (twoSquareMovedPawn != null && squares[end.pozX][begin.pozY] == twoSquareMovedPawn.getSquare()) // en
			// passant
			{

				tempEnd.piece = squares[end.pozX][begin.pozY].piece; // ugly
				// hack - put taken pawn in en passant plasty do end square

				squares[end.pozX][begin.pozY].piece = null;
				wasEnPassant = true;
			}

			if (begin.pozY - end.pozY == 2 || end.pozY - begin.pozY == 2) // moved
			// two
			// square
			{
				twoSquareMovedPawn = (Pawn) end.piece;
			} else {
				twoSquareMovedPawn = null; // erase last saved move (for En
				// passant)
			}

			if (end.piece.getSquare().pozY == 0 || end.piece.getSquare().pozY == 7) // promote
			// Pawn
			{
				if (clearForwardHistory) {
					String newPiece = JChessApp.jcv.showPawnPromotionBox(end.piece.player.color);
					Pawn pawn = (Pawn) end.piece;
					pawn.promote(newPiece);
					promotedPiece = end.piece;
				}
			}
		} else if (end.piece.getSymbol() == Pawn.SYMBOL == false) {
			twoSquareMovedPawn = null; // erase last saved move (for En passant)
		}

		if (refresh) {
			this.unselect();// unselect square
			uiChessboard.repaint();
		}

		if (clearForwardHistory) {
			this.moves_history.clearMoveForwardStack();
			this.moves_history.addMove(tempBegin, tempEnd, true, wasCastling, wasEnPassant, promotedPiece);
		} else {
			this.moves_history.addMove(tempBegin, tempEnd, false, wasCastling, wasEnPassant, promotedPiece);
		}
	}/* endOf-move()- */

	public boolean undo() throws Exception {
		return undo(true);
	}

	public synchronized boolean undo(boolean refresh) throws Exception // undo
																																			// last
																																			// move
	{
		Move last = this.moves_history.undo();

		if (last != null && last.getFrom() != null) {
			Square begin = last.getFrom();
			Square end = last.getTo();
			try {
				Piece moved = last.getMovedPiece();
				this.squares[begin.pozX][begin.pozY].piece = moved;

				moved.setSquare(this.squares[begin.pozX][begin.pozY]);

				Piece taken = last.getTakenPiece();
				if (last.getCastlingMove() != castling.none) {
					Piece rook = null;
					if (last.getCastlingMove() == castling.shortCastling) {
						rook = this.squares[end.pozX - 1][end.pozY].piece;
						this.squares[7][begin.pozY].piece = rook;
						rook.setSquare(this.squares[7][begin.pozY]);
						this.squares[end.pozX - 1][end.pozY].piece = null;
					} else {
						rook = this.squares[end.pozX + 1][end.pozY].piece;
						this.squares[0][begin.pozY].piece = rook;
						rook.setSquare(this.squares[0][begin.pozY]);
						this.squares[end.pozX + 1][end.pozY].piece = null;
					}
					((King) moved).wasMotion = false;
					((Rook) rook).wasMotion = false;
				} else if (moved.getSymbol() == Rook.SYMBOL) {
					((Rook) moved).wasMotion = false;
				} else if (moved.getSymbol() == Pawn.SYMBOL && last.wasEnPassant()) {
					Pawn pawn = (Pawn) last.getTakenPiece();
					this.squares[end.pozX][begin.pozY].piece = pawn;
					pawn.setSquare(this.squares[end.pozX][begin.pozY]);

				} else if (moved.getSymbol() == Pawn.SYMBOL && last.getPromotedPiece() != null) {
					Piece promoted = this.squares[end.pozX][end.pozY].piece;
					promoted.setSquare(null);
					this.squares[end.pozX][end.pozY].piece = null;
				}

				// check one more move back for en passant
				Move oneMoveEarlier = this.moves_history.getLastMoveFromHistory();
				if (oneMoveEarlier != null && oneMoveEarlier.wasPawnTwoFieldsMove()) {
					Piece canBeTakenEnPassant = this.squares[oneMoveEarlier.getTo().pozX][oneMoveEarlier.getTo().pozY].piece;
					if (canBeTakenEnPassant.getSymbol() == Pawn.SYMBOL) {
						this.twoSquareMovedPawn = (Pawn) canBeTakenEnPassant;
					}
				}

				if (taken != null && !last.wasEnPassant()) {
					this.squares[end.pozX][end.pozY].piece = taken;
					taken.setSquare(this.squares[end.pozX][end.pozY]);
				} else {
					this.squares[end.pozX][end.pozY].piece = null;
				}

				if (refresh) {
					this.unselect();// unselect square
					uiChessboard.repaint();
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

	public ChessboardUI getChessboardUI() {
		return uiChessboard;
	}

	/**
	 * Method is useful for out of bounds protection
	 * 
	 * @param x
	 *          x position on chessboard
	 * @param y
	 *          y position on chessboard
	 * @return true if parameters are out of bounds (array)
	 * */
	public static boolean isout(int x, int y) {
		if (x < 0 || x > 7 || y < 0 || y > 7) {
			return true;
		}
		return false;
	}

	/**
	 * Checks is a given square is on the board, or outside the board borders.
	 * 
	 * @param sq
	 * @return
	 */
	public static boolean isValidSquare(Square sq) {
		return isout(sq.pozX, sq.pozY) == false;
	}
}