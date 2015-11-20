package jchess.core.pieces;

import java.util.ArrayList;
import java.util.Iterator;

import jchess.core.board.Chessboard;
import jchess.core.board.Square;
import jchess.core.util.Constants;
import jchess.core.util.Player;
import jchess.ui.lang.Language;

/**
 * Class to represent a piece (any kind) - this class should be extended to
 * represent pawn, bishop etc.
 */
public abstract class Piece {

	// TODO Piece class is not allowed to have image, this needs to be moved to
	// scheme

	// TODO this class shall not draw itself, checkBoardUI is responsible for this

	public Chessboard				chessboard		= null;
	protected Square				square				= null;
	public Player						player				= null;
	private String					symbol				= Constants.EMPTY_STRING;

	protected IMoveBehavior	moveBehavior	= null;

	public Piece(Chessboard chessboard, Player player, String symbol) throws Exception {
		this.chessboard = chessboard;
		this.player = player;
		setSymbol(symbol);
		this.moveBehavior = createMoveBehavior();
	}

	public abstract IMoveBehavior createMoveBehavior();

	/**
	 * method check if Piece can move to given square
	 * 
	 * @param square
	 *          square where piece want to move (Square object)
	 * @param allmoves
	 *          all moves which can piece do
	 * */
	protected boolean canMove(Square square, ArrayList<Square> allmoves) {
		// throw new UnsupportedOperationException("Not supported yet.");
		ArrayList<Square> moves = allmoves;
		for (Iterator<Square> it = moves.iterator(); it.hasNext();) {
			Square sq = (Square) it.next();// get next from iterator
			if (sq == square) {// if adress is the same
				return true; // piece canMove
			}
		}
		return false;// if not, piece cannot move
	}

	public ArrayList<Square> allMoves() {
		return moveBehavior.allMoves();
	}

	public String getSymbol() {
		return this.symbol;
	}

	public void setSymbol(String symbol) throws Exception {
		if (symbol != null && symbol.length() > 0) {
			this.symbol = symbol;
		} else
			throw new Exception(Language.getString("Piece.0")); //$NON-NLS-1$
	}

	public Square getSquare() {
		return square;
	}

	public void setSquare(Square square) throws Exception {
		if (Chessboard.isValidSquare(square)) {
			this.square = square;
			moveBehavior.setSquare(this.square);
		} else {
			throw new Exception(Language.getString("Piece.1")); //$NON-NLS-1$
		}
	}

	protected void setMoveBehavior(IMoveBehavior moveBehavior) {
		this.moveBehavior = moveBehavior;
	}

	/**
	 * Used for testing purposes.
	 * 
	 * @return
	 */
	public Class<? extends IMoveBehavior> getMoveBehaviorClass() {
		return moveBehavior.getClass();
	}

	public String getSymbolForMoveHistory() {
		String result = null;
		if (symbol != null && symbol.length() > 0) {
			if (symbol.startsWith("K") && symbol.length() > 1) { //$NON-NLS-1$
				result = symbol.substring(0, 2);
			} else {
				result = symbol.substring(0, 1);
			}
		}
		return result;
	}
}