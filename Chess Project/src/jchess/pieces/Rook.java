/*
 * # This program is free software: you can redistribute it and/or modify # it
 * under the terms of the GNU General Public License as published by # the Free
 * Software Foundation, either version 3 of the License, or # (at your option)
 * any later version. # # This program is distributed in the hope that it will
 * be useful, # but WITHOUT ANY WARRANTY; without even the implied warranty of #
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the # GNU General
 * Public License for more details. # # You should have received a copy of the
 * GNU General Public License # along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

/*
 * Authors: Mateusz Sławomir Lach ( matlak, msl ) Damian Marciniak
 */

package jchess.pieces;

import jchess.board.Chessboard;
import jchess.core.Theme;
import jchess.util.Player;

/**
 * Class to represent a chess pawn rook.
 * 
 * A rook move horizontal and vertical no restrictions on the distance.
 * 
 */
public class Rook extends Piece {

	public static final String	SYMBOL		= "Rook";

	public boolean							wasMotion	= false;

	public Rook(Chessboard chessboard, Player player) {
		super(chessboard, player);
		setSymbol(SYMBOL);
		setImage(Theme.getImageForPiece(player.color, this));
	}

	@Override
	public IMoveBehavior createMoveBehavior() {
		return new RookMoveBehavior(player, chessboard, square);
	}
}