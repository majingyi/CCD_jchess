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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import jchess.board.Chessboard;
import jchess.util.Player;
import jchess.util.Square;

/**
 * Class to represent a piece (any kind) - this class should be extended to
 * represent pawn, bishop etc.
 */
public abstract class Piece {

	public Chessboard				chessboard;
	protected Square				square;
	public Player						player;
	public String						name;
	public String						symbol;

	protected static Image	imageBlack		= null;
	protected static Image	imageWhite		= null;

	public Image						orgImage;
	public Image						image;

	protected IMoveBehavior	moveBehavior	= null;

	public Piece(Chessboard chessboard, Player player) {
		this.chessboard = chessboard;
		this.player = player;
		if (player.color == Player.colors.black) {
			image = imageBlack;
		} else {
			image = imageWhite;
		}
		this.name = this.getClass().getSimpleName();

		moveBehavior = createMoveBehavior();

	}

	public abstract IMoveBehavior createMoveBehavior();

	/*
	 * Method to draw piece on chessboard
	 * 
	 * @graph : where to draw
	 */

	public final void draw(Graphics g) {
		try {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Point topLeft = this.chessboard.getChessboardUI().getTopLeftPoint();
			int height = this.chessboard.getChessboardUI().get_square_height();
			int x = (this.square.pozX * height) + topLeft.x;
			int y = (this.square.pozY * height) + topLeft.y;

			if (image != null && g != null) {
				Image tempImage = orgImage;
				BufferedImage resized = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB_PRE);
				Graphics2D imageGr = (Graphics2D) resized.createGraphics();
				imageGr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				imageGr.drawImage(tempImage, 0, 0, height, height, null);
				imageGr.dispose();
				image = resized.getScaledInstance(height, height, 0);
				g2d.drawImage(image, x, y, null);
			} else {
				System.out.println("image is null!");
			}

		} catch (java.lang.NullPointerException exc) {
			System.out.println("Something wrong when painting piece: " + exc.getMessage());
		}
	}

	protected void clean() {
	}

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

	void setImage() {
		if (this.player.color == Player.colors.black) {
			image = imageBlack;
		} else {
			image = imageWhite;
		}
	}

	public ArrayList<Square> allMoves() {
		return moveBehavior.allMoves();
	}

	public String getSymbol() {
		return this.symbol;
	}

	public Square getSquare() {
		return square;
	}

	public void setSquare(Square square) {
		this.square = square;
		moveBehavior.setSquare(this.square);
	}
}
