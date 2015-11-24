package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.ChessboardField;
import jchess.core.board.Square;

public interface IMoveBehavior {

	public ArrayList<Square> allMoves() throws Exception;

	public void setChessboardField(ChessboardField field);

}