package jchess.core.pieces;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.util.Player;

public abstract class MoveBehavior implements IMoveBehavior {

	protected Player			m_Player		= null;
	protected Chessboard		m_Chessboard	= null;
	protected ChessboardField	m_Field			= null;

	public MoveBehavior(Player player, Chessboard chessboard, ChessboardField field) {
		this.m_Player = player;
		this.m_Chessboard = chessboard;
		this.m_Field = field;
	}

	public void setChessboardField(ChessboardField field) {
		this.m_Field = field;
	}
}