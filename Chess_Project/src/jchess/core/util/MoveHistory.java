package jchess.core.util;

import java.util.ArrayList;
import java.util.Stack;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.pieces.Piece;
import jchess.ui.GameTab;

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

	public String addMove(ChessboardField begin, ChessboardField end, boolean registerInHistory, castling castlingMove, boolean wasEnPassant,
			Piece promotedPiece, GameTab gameTab) throws Exception {
		String locMove = new String(begin.getPiece().getSymbolForMoveHistory());

		locMove += begin.getIdentifier();

		if (end.getPiece() != null) {
			locMove += "x";// take down opponent piece //$NON-NLS-1$
		} else {
			locMove += "-";// normal move //$NON-NLS-1$
		}

		locMove += end.getIdentifier();

		// TODO enpassant recognition
		// if (begin.getPiece().getSymbol() == Pawn.SYMBOL && begin.pozX - end.pozX
		// != 0 && end.getPiece() == null) {
		//			locMove += "(e.p)";// pawn take down opponent en passant //$NON-NLS-1$
		// wasEnPassant = true;
		// }

		if (((this.enterBlack == false) && gameTab.getChessboard().getKingForColor(Player.colors.black).isChecked())
				|| (this.enterBlack && gameTab.getChessboard().getKingForColor(Player.colors.white).isChecked())) {// if
			// checked

			if ((!this.enterBlack && gameTab.getChessboard().getKingForColor(Player.colors.black).isCheckmatedOrStalemated() == 1)
					|| (this.enterBlack && gameTab.getChessboard().getKingForColor(Player.colors.white).isCheckmatedOrStalemated() == 1)) {// check
				// if
				// checkmated
				locMove += "#";// check mate //$NON-NLS-1$
			} else {
				locMove += "+";// check //$NON-NLS-1$
			}
		}

		if (registerInHistory) {
			this.moveBackStack.add(new Move(begin.copy(), end.copy(), begin.getPiece(), end.getPiece(), castlingMove, wasEnPassant, promotedPiece));
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
		// TODO
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
