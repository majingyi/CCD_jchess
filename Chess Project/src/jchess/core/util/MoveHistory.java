package jchess.core.util;

import java.util.ArrayList;
import java.util.Stack;

import jchess.core.board.Chessboard;
import jchess.core.board.Square;
import jchess.core.pieces.Pawn;
import jchess.core.pieces.Piece;
import jchess.ui.ChessboardUI;
import jchess.ui.GameTab;
import jchess.ui.lang.Language;

/**
 * Class representing the players moves, it's also checking that the moves taken
 * by player are correct. All moves which was taken by current player are saving
 * as List of Strings The history of moves is printing in a table
 * 
 */
public class MoveHistory {

	private ArrayList<String>	moves							= new ArrayList<String>();
	private boolean						enterBlack				= false;
	protected Stack<Move>			moveBackStack			= new Stack<Move>();
	protected Stack<Move>			moveForwardStack	= new Stack<Move>();

	public enum castling {
		none, shortCastling, longCastling
	}

	public void addCastling(String move) {
		this.moves.remove(this.moves.size() - 1);// remove last element (move of
																							// Rook)

		this.moves.add(move);// add new move (O-O or O-O-O)
	}

	public String addMove(Square begin, Square end, boolean registerInHistory, castling castlingMove, boolean wasEnPassant, Piece promotedPiece, GameTab gameTab)
			throws Exception {
		String locMove = new String(begin.getPiece().getSymbolForMoveHistory());

		locMove += Character.toString((char) (begin.pozX + 97));// add letter of
																														// Square from
																														// which move was
																														// made
		locMove += Integer.toString(8 - begin.pozY);// add number of Square from
																								// which move was made

		if (end.getPiece() != null) {
			locMove += "x";// take down opponent piece //$NON-NLS-1$
		} else {
			locMove += "-";// normal move //$NON-NLS-1$
		}

		locMove += Character.toString((char) (end.pozX + 97));// add letter of
																													// Square to which
																													// move was made
		locMove += Integer.toString(8 - end.pozY);// add number of Square to which
																							// move was made

		if (begin.getPiece().getSymbol() == Pawn.SYMBOL && begin.pozX - end.pozX != 0 && end.getPiece() == null) {
			locMove += "(e.p)";// pawn take down opponent en passant //$NON-NLS-1$
			wasEnPassant = true;
		}

		if (((this.enterBlack == false) && gameTab.getChessboard().getBlackKing().isChecked())
				|| (this.enterBlack && gameTab.getChessboard().getWhiteKing().isChecked())) {// if
			// checked

			if ((!this.enterBlack && gameTab.getChessboard().getBlackKing().isCheckmatedOrStalemated() == 1)
					|| (this.enterBlack && gameTab.getChessboard().getWhiteKing().isCheckmatedOrStalemated() == 1)) {// check
				// if
				// checkmated
				locMove += "#";// check mate //$NON-NLS-1$
			} else {
				locMove += "+";// check //$NON-NLS-1$
			}
		}

		if (registerInHistory) {
			this.moveBackStack.add(new Move(new Square(begin), new Square(end), begin.getPiece(), end.getPiece(), castlingMove, wasEnPassant, promotedPiece));
		}

		return locMove;
	}

	public void clearMoveForwardStack() {
		this.moveForwardStack.clear();
	}

	public ArrayList<String> getMoves() {
		return this.moves;
	}

	public synchronized Move getLastMoveFromHistory() {
		try {
			Move last = this.moveBackStack.get(this.moveBackStack.size() - 1);
			return last;
		} catch (java.lang.ArrayIndexOutOfBoundsException exc) {
			return null;
		}
	}

	public synchronized Move getNextMoveFromHistory() {
		try {
			Move next = this.moveForwardStack.get(this.moveForwardStack.size() - 1);
			return next;
		} catch (java.lang.ArrayIndexOutOfBoundsException exc) {
			return null;
		}

	}

	public synchronized Move redo() {
		Move result = null;
		try {
			Move first = this.moveForwardStack.pop();
			this.moveBackStack.push(first);
			result = first;
		} catch (java.util.EmptyStackException exc) {
			Logging.log(exc);
			result = null;
		}
		return result;
	}

	/**
	 * Method with is checking is the move is correct
	 * 
	 * @param move
	 *          String which in is capt player move
	 * @return boolean 1 if the move is correct, else 0
	 */
	static public boolean isMoveCorrect(String move) {// TODO not correct, Symbols
																										// where changed
		if (move.equals("O-O") || move.equals("O-O-O")) { //$NON-NLS-1$ //$NON-NLS-2$
			return true;
		}
		try {
			int from = 0;
			int sign = move.charAt(from);// get First
			switch (sign) // if sign of piece, get next
			{
				case 66: // B like Bishop
				case 75: // K like King
				case 78: // N like Knight
				case 81: // Q like Queen
				case 82:
					from = 1;
					break; // R like Rook
			}
			sign = move.charAt(from);
			Logging.log(sign);
			if (sign < 97 || sign > 104) // if lower than 'a' or higher than 'h'
			{
				return false;
			}
			sign = move.charAt(from + 1);
			if (sign < 49 || sign > 56) // if lower than '1' or higher than '8'
			{
				return false;
			}
			if (move.length() > 3) // if is equal to 3 or lower, than it's in short
															// notation, no more checking needed
			{
				sign = move.charAt(from + 2);
				if (sign != 45 && sign != 120) // if isn't '-' and 'x'
				{
					return false;
				}
				sign = move.charAt(from + 3);
				if (sign < 97 || sign > 104) // if lower than 'a' or higher than 'h'
				{
					return false;
				}
				sign = move.charAt(from + 4);
				if (sign < 49 || sign > 56) // if lower than '1' or higher than '8'
				{
					return false;
				}
			}
		} catch (java.lang.StringIndexOutOfBoundsException exc) {
			return false;
		}

		return true;
	}

	public void addMoves(ArrayList<String> list) {
		for (String singleMove : list) {
			if (isMoveCorrect(singleMove)) {
				this.addMove(singleMove);
			}
		}
	}

	/**
	 * Method of getting the moves in string
	 * 
	 * @return str String which in is capt player move
	 */
	public String getMovesInString() {
		int n = 1;
		int i = 0;
		String str = new String();
		for (String locMove : this.getMoves()) {
			if (i % 2 == 0) {
				str += n + ". "; //$NON-NLS-1$
				n += 1;
			}
			str += locMove + " "; //$NON-NLS-1$
			i += 1;
		}
		return str;
	}

	public void setMoves(String moves, Player activePlayer, Chessboard chessboard) throws Exception {
		int from = 0;
		int to = 0;
		int n = 1;
		ArrayList<String> tempArray = new ArrayList<String>();
		int tempStrSize = moves.length() - 1;
		while (true) {
			from = moves.indexOf(Constants.WHITE_SPACE_STRING, from); //$NON-NLS-1$
			to = moves.indexOf(Constants.WHITE_SPACE_STRING, from + 1); //$NON-NLS-1$
			// Logging.log(from+">"+to);
			try {
				tempArray.add(moves.substring(from + 1, to).trim());
			} catch (java.lang.StringIndexOutOfBoundsException exc) {
				Logging.log(Language.getString("Moves.0"), exc); //$NON-NLS-1$
				break;
			}
			if (n % 2 == 0) {
				from = moves.indexOf(Constants.DOT_STRING, to); //$NON-NLS-1$
				if (from < to) {
					break;
				}
			} else {
				from = to;
			}
			n += 1;
			if (from > tempStrSize || to > tempStrSize) {
				break;
			}
		}
		for (String locMove : tempArray) // test if moves are written correctly
		{
			if (MoveHistory.isMoveCorrect(locMove.trim()) == false) // if not
			{
				throw new Exception(Language.getString("invalid_file_to_load") + moves); //$NON-NLS-1$
			}
		}
		boolean canMove = false;
		for (String locMove : tempArray) {
			if (locMove.equals("O-O-O") || locMove.equals("O-O")) // if castling //$NON-NLS-1$ //$NON-NLS-2$
			{
				int[] values = new int[4];
				if (locMove.equals("O-O-O")) { //$NON-NLS-1$
					if (activePlayer.getColor() == Player.colors.black) // if
					// black
					// turn
					{
						values = new int[] { 4, 0, 2, 0 };// move value for castling (King
						// move)
					} else {
						values = new int[] { 4, 7, 2, 7 };// move value for castling (King
						// move)
					}
				} else if (locMove.equals("O-O")) // if short castling //$NON-NLS-1$
				{
					if (activePlayer.getColor() == Player.colors.black) // if
					// black
					// turn
					{
						values = new int[] { 4, 0, 6, 0 };// move value for castling (King
						// move)
					} else {
						values = new int[] { 4, 7, 6, 7 };// move value for castling (King
						// move)
					}
				}
				canMove = chessboard.tryMove(values[0], values[1], values[2], values[3]);

				if (canMove == false) // if move is illegal
				{
					throw new Exception(Language.getString("illegal_move_on") + locMove); //$NON-NLS-1$
				}
				continue;
			}
			from = 0;
			int num = locMove.charAt(from);
			if (num <= 90 && num >= 65) {
				from = 1;
			}
			int xFrom = 9; // set to higher value than chessboard has fields, to cause
			// error if piece won't be found
			int yFrom = 9;
			int xTo = 9;
			int yTo = 9;
			boolean pieceFound = false;
			if (locMove.length() <= 3) {
				Square[][] squares = chessboard.getFields();
				xTo = locMove.charAt(from) - 97;// from ASCII
				yTo = ChessboardUI.bottom - (locMove.charAt(from + 1) - 49);// from
				// ASCII
				for (int i = 0; i < squares.length && (pieceFound == false); i++) {
					for (int j = 0; j < squares[i].length && (pieceFound == false); j++) {
						if (squares[i][j].getPiece() == null || activePlayer.getColor() != squares[i][j].getPiece().player.getColor()) {
							continue;
						}
						ArrayList<Square> pieceMoves = squares[i][j].getPiece().allMoves();
						for (Object square : pieceMoves) {
							Square currSquare = (Square) square;
							if (currSquare.pozX == xTo && currSquare.pozY == yTo) {
								Square sq = (Square) squares[i][j].getPiece().getField();
								xFrom = sq.pozX;
								yFrom = sq.pozY;
								pieceFound = true;
							}
						}
					}
				}
			} else {
				xFrom = locMove.charAt(from) - 97;// from ASCII
				yFrom = ChessboardUI.bottom - (locMove.charAt(from + 1) - 49);// from
				// ASCII
				xTo = locMove.charAt(from + 3) - 97;// from ASCII
				yTo = ChessboardUI.bottom - (locMove.charAt(from + 4) - 49);// from
				// ASCII
			}
			canMove = chessboard.tryMove(xFrom, yFrom, xTo, yTo);
			if (canMove == false) // if move is illegal
			{
				chessboard.setActiveField(null);
				throw new Exception(Language.getString("illegal_move_on") + locMove); //$NON-NLS-1$
			}
		}
	}

	public void addMove(String move) {
		moves.add(move);
		this.moveForwardStack.clear();
	}

	public boolean isEnterBlack() {
		return enterBlack;
	}

	public void setEnterBlack(boolean enterBlack) {
		this.enterBlack = enterBlack;
	}

	public String getMoveAt(int position) {
		return moves.get(position);
	}

	public void removeLastMove() {
		this.moves.remove(this.moves.size() - 1);
	}

	public Move popBackwardStack() {
		return moveBackStack.pop();
	}

	public void pushForwardStack(Move move) {
		moveForwardStack.push(move);
	}
}
