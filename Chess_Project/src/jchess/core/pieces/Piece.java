package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Constants;
import jchess.core.util.Player;
import jchess.ui.lang.Language;

/**
 * Class to represent a piece (any kind) - this class should be extended to
 * represent pawn, bishop etc.
 */
public abstract class Piece {

	private Chessboard			m_Chessboard	= null;
	private ChessboardField	m_Field				= null;
	private Player					m_Player			= null;
	private String					m_Symbol			= Constants.EMPTY_STRING;

	protected IMoveBehavior	moveBehavior	= null;

	public Piece(Chessboard chessboard, Player player, String symbol, ChessboardField field) throws Exception {
		this.m_Chessboard = chessboard;
		this.setPlayer(player);
		setSymbol(symbol);
		m_Field = field;
		this.moveBehavior = createMoveBehavior();
	}

	public abstract IMoveBehavior createMoveBehavior();

	/**
	 * method check if Piece can move to given square
	 * 
	 * @param field
	 *          field where piece want to move (Square object)
	 * @param allmoves
	 *          all moves which can piece do
	 * */
	protected boolean canMove(ChessboardField field, ArrayList<ChessboardField> allmoves) {
		return allmoves.contains(field);
	}

	public ArrayList<ChessboardField> allMoves() throws Exception {
		return moveBehavior.allMoves();
	}

	public String getSymbol() {
		return this.m_Symbol;
	}

	public void setSymbol(String symbol) throws Exception {
		if (symbol != null && symbol.length() > 0) {
			this.m_Symbol = symbol;
		} else
			throw new Exception(Language.getString("Piece.0")); //$NON-NLS-1$
	}

	public ChessboardField getField() {
		return m_Field;
	}

	public void setField(ChessboardField field, Chessboard board) throws Exception {
		if (field == null || Chessboard.isValidField(board, field)) {
			this.m_Field = field;
			moveBehavior.setChessboardField(this.m_Field);
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
		if (m_Symbol != null && m_Symbol.length() > 0) {
			if (m_Symbol.startsWith("K") && m_Symbol.length() > 1) { //$NON-NLS-1$
				result = m_Symbol.substring(0, 2);
			} else {
				result = m_Symbol.substring(0, 1);
			}
		}
		return result;
	}

	public Player getPlayer() {
		return m_Player;
	}

	public void setPlayer(Player m_Player) {
		this.m_Player = m_Player;
	}

	public Chessboard getChessboard() {
		return m_Chessboard;
	}
}