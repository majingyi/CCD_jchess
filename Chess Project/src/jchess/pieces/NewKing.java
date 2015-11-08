package jchess.pieces;

import jchess.board.Chessboard;
import jchess.util.Player;

/**
 * Class to represent a chess pawn NewKing. NewKing is a chess figure based on
 * the King, who is only allowed to to travel in horizontal and vertical
 * directions.
 */

public class NewKing extends King {

	NewKing(Chessboard chessboard, Player player) {
		super(chessboard, player);
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new NewKingMoveBehavior(player, chessboard, square, this);
	}

}