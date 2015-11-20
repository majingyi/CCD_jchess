package jchess.core.pieces;

import java.util.ArrayList;

import jchess.core.board.Square;

public interface IMoveBehavior {

	public ArrayList<Square> allMoves();

	public void setSquare(Square square);

}