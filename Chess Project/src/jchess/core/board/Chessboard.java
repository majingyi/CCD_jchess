package jchess.core.board;

import java.util.HashMap;
import java.util.Map;

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
import jchess.core.util.MoveHistory;
import jchess.core.util.MoveHistory.castling;
import jchess.core.util.Player;
import jchess.ui.GameTab;
import jchess.ui.MoveHistoryUI;
import jchess.ui.lang.Language;

/**
 * Class to represent chessboard. Chessboard is made from squares. It is setting
 * the squers of chessboard and sets the pieces(pawns) witch the owner is
 * current player on it.
 */

public class Chessboard {

	public static final int						top									= 0;
	public static final int						bottom							= 7;

	private GameTab										gameUI							= null;

	// squares of chessboard
	private ChessboardField						fields[][]					= null;
	private ChessboardField						activeField					= null;

	private int												active_x_square			= -1;
	private int												active_y_square			= -1;

	private Pawn											twoSquareMovedPawn	= null;

	private MoveHistoryUI							moves_history				= null;
	private Map<Player.colors, King>	m_KingsMap					= new HashMap<Player.colors, King>();

	public Chessboard(GameTab ui, MoveHistoryUI movesHistory) throws Exception {
		gameUI = ui;
		moves_history = movesHistory;

		initChessBoard();
	}

	private void initChessBoard() throws Exception {
		fields = new Square[8][8];

		for (int i = 0; i < 8; i++) {// create object for each square
			for (int y = 0; y < 8; y++) {
				getFields()[i][y] = new Square(i, y, null, this);
			}
		}
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

		if ((plWhite.getColor() == plBlack.getColor()) == false) {
			if (places.equals(Constants.EMPTY_STRING)) // if newGame
			{
				this.setPieces4NewGame(plWhite, plBlack);
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
	 * 
	 */
	public void move(int xFrom, int yFrom, int xTo, int yTo) throws Exception {
		ChessboardField fromSQ = null;
		ChessboardField toSQ = null;
		try {
			fromSQ = this.getFields()[xFrom][yFrom];
			toSQ = this.getFields()[xTo][yTo];
		} catch (java.lang.IndexOutOfBoundsException exc) {
			Logging.log(Language.getString("Chessboard.1"), exc); //$NON-NLS-1$
			return;
		}
		this.move(fromSQ, toSQ, true);
	}

	private void setPieces4NewGame(Player plWhite, Player plBlack) throws Exception {
		m_KingsMap.clear();

		Player player = plWhite;
		Player player1 = plBlack;
		this.setFigures4NewGame(0, player);
		this.setPawns4NewGame(1, player);
		this.setFigures4NewGame(7, player1);
		this.setPawns4NewGame(6, player1);
	}

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
	private void setFigures4NewGame(int i, Player player) throws Exception {

		if (i != 0 && i != 7) {
			Logging.logError(Language.getString("Chessboard.2")); //$NON-NLS-1$
			return;
		} else if (i == 0) {
			player.setGoDown(true);
		}

		this.getFields()[0][i].setPiece(new Rook(this, player, this.getFields()[0][i]));
		this.getFields()[7][i].setPiece(new Rook(this, player, this.getFields()[7][i]));
		this.getFields()[1][i].setPiece(new Knight(this, player, this.getFields()[1][i]));
		this.getFields()[6][i].setPiece(new Knight(this, player, this.getFields()[6][i]));
		this.getFields()[2][i].setPiece(new Bishop(this, player, this.getFields()[2][i]));
		this.getFields()[5][i].setPiece(new Bishop(this, player, this.getFields()[5][i]));

		if (player.getColor() == Player.colors.white) {
			King kingWhite = new King(this, player, this.getFields()[3][i]);
			m_KingsMap.put(Player.colors.white, kingWhite);
			this.getFields()[3][i].setPiece(kingWhite);
			this.getFields()[4][i].setPiece(new Queen(this, player, this.getFields()[4][i]));
		} else {
			King kingBlack = new King(this, player, this.getFields()[3][i]);
			m_KingsMap.put(Player.colors.black, kingBlack);
			this.getFields()[3][i].setPiece(kingBlack);
			this.getFields()[4][i].setPiece(new Queen(this, player, this.getFields()[4][i]));
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
			this.getFields()[x][i].setPiece(new Pawn(this, player, this.getFields()[x][i]));
		}
	}

	/**
	 * Method selecting piece in chessboard
	 * 
	 * @param sq
	 *          square to select (when clicked))
	 */
	public void select(ChessboardField field) {
		Square sq = (Square) field;
		this.activeField = (Square) sq;
		this.setActive_x_square(sq.pozX + 1);
		this.setActive_y_square(sq.pozY + 1);

		Logging.log("active_x: " + this.getActive_x_square() + " active_y: " + this.getActive_y_square());// 4tests //$NON-NLS-1$ //$NON-NLS-2$
		gameUI.getChessboardUI().repaint();
	}

	/**
	 * Method set variables active_x_square & active_y_square to 0 values.
	 */
	public void unselect() {
		this.setActive_x_square(0);
		this.setActive_y_square(0);
		this.activeField = null;

		gameUI.getChessboardUI().repaint();
	}

	public boolean redo() throws Exception {
		return redo(true);
	}

	public boolean redo(boolean refresh) throws Exception {
		boolean result = false;
		Move first = this.moves_history.redo();

		Square from = null;
		Square to = null;

		if (first != null) {
			from = first.getFrom();
			to = first.getTo();

			this.move(this.getFields()[from.pozX][from.pozY], this.getFields()[to.pozX][to.pozY], false);
			if (first.getPromotedPiece() != null) {
				Pawn pawn = (Pawn) this.getFields()[to.pozX][to.pozY].getPiece();
				pawn.setField(null);

				this.getFields()[to.pozX][to.pozY].setPiece(first.getPromotedPiece());
				Piece promoted = this.getFields()[to.pozX][to.pozY].getPiece();
				promoted.setField(this.getFields()[to.pozX][to.pozY]);
			}
			result = true;
		}
		return result;
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
	private void move(ChessboardField start, ChessboardField target, boolean clearForwardHistory) throws Exception {
		Square begin = (Square) start;
		Square end = (Square) target;
		castling wasCastling = MoveHistory.castling.none;
		Piece promotedPiece = null;
		boolean wasEnPassant = false;
		if (end.getPiece() != null) {
			end.getPiece().setField(null);
		}

		Square tempBegin = new Square((Square) begin);// 4 moves history
		Square tempEnd = new Square((Square) end); // 4 moves history

		begin.getPiece().setField(end);// set square of piece to ending
		end.setPiece(begin.getPiece());// for ending square set piece from beginin
		// square
		begin.setPiece(null);// make null piece for begining square

		if (end.getPiece().getSymbol().equals("King")) { //$NON-NLS-1$
			if (((King) end.getPiece()).wasMotion == false) {
				((King) end.getPiece()).wasMotion = true;
			}

			if (begin.pozX + 2 == end.pozX) {
				move(getFields()[7][begin.pozY], getFields()[end.pozX - 1][begin.pozY], false);
				wasCastling = MoveHistory.castling.shortCastling;
			} else if (begin.pozX - 2 == end.pozX) {
				move(getFields()[0][begin.pozY], getFields()[end.pozX + 1][begin.pozY], false);
				wasCastling = MoveHistory.castling.longCastling;
			}
		} else if (end.getPiece().getSymbol() == Rook.SYMBOL) {
			if (((Rook) end.getPiece()).wasMotion == false) {
				((Rook) end.getPiece()).wasMotion = true;
			}
		} else if (end.getPiece().getSymbol() == Pawn.SYMBOL) {
			if (getTwoSquareMovedPawn() != null && getFields()[end.pozX][begin.pozY] == getTwoSquareMovedPawn().getField()) // en
			// passant
			{
				tempEnd.setPiece(getFields()[end.pozX][begin.pozY].getPiece()); // ugly
				getFields()[end.pozX][begin.pozY].setPiece(null);
				wasEnPassant = true;
			}

			if (begin.pozY - end.pozY == 2 || end.pozY - begin.pozY == 2) // moved
			// two
			// square
			{
				setTwoSquareMovedPawn((Pawn) end.getPiece());
			} else {
				setTwoSquareMovedPawn(null); // erase last saved move (for En
				// passant)
			}
			ChessboardField field = end.getPiece().getField();
			if (((Square) field).pozY == 0 || ((Square) field).pozY == 7) // promote
			// Pawn
			{
				if (clearForwardHistory) {
					String newPiece = JChessApp.jcv.showPawnPromotionBox(end.getPiece().getPlayer().getColor());
					Pawn pawn = (Pawn) end.getPiece();
					pawn.promote(newPiece);
					promotedPiece = end.getPiece();
				}
			}
		} else if (end.getPiece().getSymbol() == Pawn.SYMBOL == false) {
			setTwoSquareMovedPawn(null); // erase last saved move (for En passant)
		}

		this.unselect();// unselect square
		gameUI.getChessboardUI().repaint();

		if (clearForwardHistory) {
			this.moves_history.clearMoveForwardStack();
			this.moves_history.addMove(tempBegin, tempEnd, true, wasCastling, wasEnPassant, promotedPiece);
		} else {
			this.moves_history.addMove(tempBegin, tempEnd, false, wasCastling, wasEnPassant, promotedPiece);
		}
	}

	public synchronized boolean undo() throws Exception // undo
																											// last
																											// move
	{
		Move last = this.moves_history.undo();

		if (last != null && last.getFrom() != null) {
			Square begin = last.getFrom();
			Square end = last.getTo();
			try {
				Piece moved = last.getMovedPiece();
				this.getFields()[begin.pozX][begin.pozY].setPiece(moved);

				moved.setField(this.getFields()[begin.pozX][begin.pozY]);

				Piece taken = last.getTakenPiece();
				if (last.getCastlingMove() != castling.none) {
					Piece rook = null;
					if (last.getCastlingMove() == castling.shortCastling) {
						rook = this.getFields()[end.pozX - 1][end.pozY].getPiece();
						this.getFields()[7][begin.pozY].setPiece(rook);
						rook.setField(this.getFields()[7][begin.pozY]);
						this.getFields()[end.pozX - 1][end.pozY].setPiece(null);
					} else {
						rook = this.getFields()[end.pozX + 1][end.pozY].getPiece();
						this.getFields()[0][begin.pozY].setPiece(rook);
						rook.setField(this.getFields()[0][begin.pozY]);
						this.getFields()[end.pozX + 1][end.pozY].setPiece(null);
					}
					((King) moved).wasMotion = false;
					((Rook) rook).wasMotion = false;
				} else if (moved.getSymbol() == Rook.SYMBOL) {
					((Rook) moved).wasMotion = false;
				} else if (moved.getSymbol() == Pawn.SYMBOL && last.wasEnPassant()) {
					Pawn pawn = (Pawn) last.getTakenPiece();
					this.getFields()[end.pozX][begin.pozY].setPiece(pawn);
					pawn.setField(this.getFields()[end.pozX][begin.pozY]);

				} else if (moved.getSymbol() == Pawn.SYMBOL && last.getPromotedPiece() != null) {
					Piece promoted = this.getFields()[end.pozX][end.pozY].getPiece();
					promoted.setField(null);
					this.getFields()[end.pozX][end.pozY].setPiece(null);
				}

				// check one more move back for en passant
				Move oneMoveEarlier = this.moves_history.getLastMoveFromHistory();
				if (oneMoveEarlier != null && oneMoveEarlier.wasPawnTwoFieldsMove()) {
					Piece canBeTakenEnPassant = this.getFields()[oneMoveEarlier.getTo().pozX][oneMoveEarlier.getTo().pozY].getPiece();
					if (canBeTakenEnPassant.getSymbol() == Pawn.SYMBOL) {
						this.setTwoSquareMovedPawn((Pawn) canBeTakenEnPassant);
					}
				}

				if (taken != null && !last.wasEnPassant()) {
					this.getFields()[end.pozX][end.pozY].setPiece(taken);
					taken.setField(this.getFields()[end.pozX][end.pozY]);
				} else {
					this.getFields()[end.pozX][end.pozY].setPiece(null);
				}

				this.unselect();// unselect square
				gameUI.getChessboardUI().repaint();

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
	 * Method is useful for out of bounds protection
	 * 
	 * @param x
	 *          x position on chessboard
	 * @param y
	 *          y position on chessboard
	 * @return true if parameters are out of bounds (array)
	 * @deprecated will not be need due to graph implementation
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
	 * @deprecated will not be need due to graph implementation
	 */
	public static boolean isValidSquare(ChessboardField field) {
		boolean result = false;
		if (field instanceof Square) {
			result = isout(((Square) field).pozX, ((Square) field).pozY) == false;
		}
		return result;
	}

	public King getKingForColor(Player.colors color) {
		return m_KingsMap.get(color);
	}

	@Deprecated
	public int getActive_x_square() {
		return active_x_square;
	}

	@Deprecated
	public void setActive_x_square(int active_x_square) {
		this.active_x_square = active_x_square;
	}

	@Deprecated
	public int getActive_y_square() {
		return active_y_square;
	}

	@Deprecated
	public void setActive_y_square(int active_y_square) {
		this.active_y_square = active_y_square;
	}

	/**
	 * This method tries to perform a move. If it passes, the move is executed. IF not this method does not fail, but return false.
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
	public boolean tryMove(int beginX, int beginY, int endX, int endY) throws Exception {
		try {
			select(getFields()[beginX][beginY]);
			if (activeField.getPiece().allMoves().indexOf(getFields()[endX][endY]) != -1) // move
			{
				move(getFields()[beginX][beginY], getFields()[endX][endY], true);
			} else {
				Logging.log(Language.getString("Game.29")); //$NON-NLS-1$
				return false;
			}
			unselect();
			gameUI.nextMove();

			return true;

		} catch (StringIndexOutOfBoundsException exc) {
			return false;
		} catch (ArrayIndexOutOfBoundsException exc) {
			return false;
		} catch (NullPointerException exc) {
			return false;
		}
	}

	public void setActiveField(ChessboardField field) {
		activeField = field;
	}

	public ChessboardField getActiveField() {
		return activeField;
	}

	public ChessboardField[][] getFields() {
		return fields;
	}

	public Pawn getTwoSquareMovedPawn() {
		return twoSquareMovedPawn;
	}

	public void setTwoSquareMovedPawn(Pawn twoSquareMovedPawn) {
		this.twoSquareMovedPawn = twoSquareMovedPawn;
	}

	public void move(ChessboardField begin, ChessboardField end) throws Exception {
		move(begin, end, true);
	}
}