package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.ChessboardField;

public interface IMoveBehavior {

	public ArrayList<ChessboardField> allMoves() throws Exception;

	public void setChessboardField(ChessboardField field);

}