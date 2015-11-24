package jchess.core.pieces;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.board.Square;
import jchess.core.util.Player;

public abstract class MoveBehavior implements IMoveBehavior {

	protected Player			m_Player			= null;
	protected Chessboard	m_Chessboard	= null;
	protected Square			m_Field				= null;

	public MoveBehavior(Player player, Chessboard chessboard, ChessboardField field) {
		this.m_Player = player;
		this.m_Chessboard = chessboard;
		this.m_Field = (Square) field;
	}

	/**
	 * @param x
	 *          y position on chessboard
	 * @param y
	 *          y position on chessboard
	 * @return true if can move, false otherwise
	 * */
	protected boolean checkPiece(int x, int y) {
		if (m_Chessboard.getFields()[x][y].getPiece() != null && m_Chessboard.getFields()[x][y].getPiece().getSymbol() == King.SYMBOL) {
			return false;
		}
		Piece piece = m_Chessboard.getFields()[x][y].getPiece();
		if (piece == null || // if this sqhuare is empty
				piece.getPlayer() != this.m_Player) // or piece is another player
		{
			return true;
		}
		return false;
	}

	/**
	 * Method check if piece has other owner than calling piece
	 * 
	 * @param x
	 *          x position on chessboard
	 * @param y
	 *          y position on chessboard
	 * @return true if owner(player) is different
	 * */
	protected boolean otherOwner(int x, int y) {
		ChessboardField sq = m_Chessboard.getFields()[x][y];
		if (sq.getPiece() == null) {
			return false;
		}
		if (this.m_Player != sq.getPiece().getPlayer()) {
			return true;
		}
		return false;
	}

	public void setChessboardField(ChessboardField square) {
		this.m_Field = (Square) square;
	}
}