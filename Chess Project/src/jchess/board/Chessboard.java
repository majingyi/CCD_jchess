package jchess.board;

import jchess.JChessApp;
import jchess.Settings;
import jchess.pieces.Bishop;
import jchess.pieces.King;
import jchess.pieces.Knight;
import jchess.pieces.Pawn;
import jchess.pieces.PawnDoubleStep;
import jchess.pieces.Piece;
import jchess.pieces.Queen;
import jchess.pieces.Rook;
import jchess.ui.ChessboardUI;
import jchess.util.Move;
import jchess.util.Moves;
import jchess.util.Moves.castling;
import jchess.util.Player;
import jchess.util.Square;

/** Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squers of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */

public class Chessboard {

	public static final int	top									= 0;
	public static final int	bottom							= 7;

	private ChessboardUI		uiChessboard				= null;

	// squares of chessboard
	public Square						squares[][];

	public Square						activeSquare;

	private int							active_x_square;
	private int							active_y_square;

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

	public Chessboard(ChessboardUI ui) {
		uiChessboard = ui;
	}

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

	/** Method to move piece over chessboard
	 * @param xFrom from which x move piece
	 * @param yFrom from which y move piece
	 * @param xTo to which x move piece
	 * @param yTo to which y move piece
	 */
	public void move(int xFrom, int yFrom, int xTo, int yTo) {
		Square fromSQ = null;
		Square toSQ = null;
		try {
			fromSQ = this.squares[xFrom][yFrom];
			toSQ = this.squares[xTo][yTo];
		} catch (java.lang.IndexOutOfBoundsException exc) {
			System.out.println("error moving piece: " + exc);
			return;
		}
		this.move(fromSQ, toSQ, true);
	}

	public void move(Square begin, Square end, boolean refresh) {
		this.move(begin, end, refresh, true);
	}

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

	/** Method selecting piece in chessboard
	 * @param  sq square to select (when clicked))
	 */
	public void select(Square sq) {
		this.activeSquare = sq;
		this.active_x_square = sq.pozX + 1;
		this.active_y_square = sq.pozY + 1;

		// this.draw();//redraw
		System.out.println("active_x: " + this.active_x_square + " active_y: " + this.active_y_square);// 4tests
		uiChessboard.repaint();

	}

	/*--endOf-select--*//** Method set variables active_x_square & active_y_square
																																																			* to 0 values.
																																																			*/
	public void unselect() {
		this.active_x_square = 0;
		this.active_y_square = 0;
		this.activeSquare = null;
		// this.draw();//redraw

		uiChessboard.repaint();
	}/*--endOf-unselect--*/

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

	public void move(Square begin, Square end) {
		move(begin, end, true);
	}

	/** Method move piece from square to square
	 * @param begin square from which move piece
	 * @param end square where we want to move piece         *
	 * @param refresh chessboard, default: true
	 * */
	public void move(Square begin, Square end, boolean refresh, boolean clearForwardHistory) {

		castling wasCastling = Moves.castling.none;
		Piece promotedPiece = null;
		boolean wasEnPassant = false;
		if (end.piece != null) {
			end.piece.square = null;
		}

		Square tempBegin = new Square(begin);// 4 moves history
		Square tempEnd = new Square(end); // 4 moves history

		twoSquareMovedPawn2 = twoSquareMovedPawn;

		begin.piece.square = end;// set square of piece to ending
		end.piece = begin.piece;// for ending square set piece from beginin
		// square
		begin.piece = null;// make null piece for begining square

		if (end.piece.name.equals("King")) {
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
		} else if (end.piece.name.equals("Rook")) {
			if (((Rook) end.piece).wasMotion == false) {
				((Rook) end.piece).wasMotion = true;
			}
		} else if (end.piece.name.equals("Pawn")) {
			if (twoSquareMovedPawn != null && squares[end.pozX][begin.pozY] == twoSquareMovedPawn.square) // en
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

			if (end.piece.square.pozY == 0 || end.piece.square.pozY == 7) // promote
			// Pawn
			{
				if (clearForwardHistory) {
					String color;
					if (end.piece.player.color == Player.colors.white) {
						color = "W"; // promotionWindow was show with pieces in
						// this color
					} else {
						color = "B";
					}

					String newPiece = JChessApp.jcv.showPawnPromotionBox(color); // return
					// name
					// of
					// new
					// piece

					if (newPiece.equals("Queen")) // transform pawn to queen
					{
						Queen queen = new Queen(this, end.piece.player);
						queen.chessboard = end.piece.chessboard;
						queen.player = end.piece.player;
						queen.square = end.piece.square;
						end.piece = queen;
					} else if (newPiece.equals("Rook")) // transform pawn to
					// rook
					{
						Rook rook = new Rook(this, end.piece.player);
						rook.chessboard = end.piece.chessboard;
						rook.player = end.piece.player;
						rook.square = end.piece.square;
						end.piece = rook;
					} else if (newPiece.equals("Bishop")) // transform pawn to
					// bishop
					{
						Bishop bishop = new Bishop(this, end.piece.player);
						bishop.chessboard = end.piece.chessboard;
						bishop.player = end.piece.player;
						bishop.square = end.piece.square;
						end.piece = bishop;
					} else // transform pawn to knight
					{
						Knight knight = new Knight(this, end.piece.player);
						knight.chessboard = end.piece.chessboard;
						knight.player = end.piece.player;
						knight.square = end.piece.square;
						end.piece = knight;
					}
					promotedPiece = end.piece;
				}
			}
		} else if (!end.piece.name.equals("Pawn")) {
			twoSquareMovedPawn = null; // erase last saved move (for En passant)
		}
		// }

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
}
