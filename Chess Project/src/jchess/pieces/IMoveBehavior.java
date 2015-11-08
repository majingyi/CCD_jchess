package jchess.pieces;

import java.util.ArrayList;

import jchess.util.Square;

public interface IMoveBehavior {

	public ArrayList<Square> allMoves();

	public void setSquare(Square square);

}
