/**NewRook: Create a new chess figure based on the rook, 
 * who is only allowed to travel up to 5 fields in a horizontal or 
 * vertical direction*/

package jchess;//////

import java.awt.Image;
//testtesttesttest
import java.util.ArrayList;

/**
 * Class to represent a chess pawn rook
 * Rook can move:
 *       |_|_|_|X|_|_|_|_|7
         |_|_|_|X|_|_|_|_|6
         |_|_|_|X|_|_|_|_|5
         |_|_|_|X|_|_|_|_|4
         |X|X|X|B|X|X|X|X|3
         |_|_|_|X|_|_|_|_|2
         |_|_|_|X|_|_|_|_|1
         |_|_|_|X|_|_|_|_|0
          0 1 2 3 4 5 6 7
 *
 */
public class NewRook extends Rook {

	boolean												wasMotion		= false;
	protected static final Image	imageWhite	= GUI.loadImage("NewRook-W.png");
	protected static final Image	imageBlack	= GUI.loadImage("NewRook-B.png");
	public static short						value				= 5;

	NewRook(Chessboard chessboard, Player player) {
		super(chessboard, player);// call initializer of super type: Piece
		// this.setImages("NewRook-W.png", "NewRook-B.png");
		this.symbol = "R";
		this.setImage();
	}

	@Override
	void setImage() {
		if (this.player.color == this.player.color.black) {
			image = imageBlack;
		} else {
			image = imageWhite;
		}
		orgImage = image;
	}

	/**
	 *  Annotation to superclass Piece changing pawns location
	 * @return  ArrayList with new position of piece
	 */
	@Override
	public ArrayList allMoves() {

		ArrayList list = new ArrayList();

		for (int i = this.square.pozY + 1; i <= 7; ++i) {// up
			for (int step = 5; step > 0; --step) {

				if (this.checkPiece(this.square.pozX, i)) {// if on this square isn't
																										// piece

					if (this.player.color == Player.colors.white) {// white

						if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i])) {
							list.add(chessboard.squares[this.square.pozX][i]);
						}
					} else {// or black

						if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i])) {
							list.add(chessboard.squares[this.square.pozX][i]);
						}
					}

					if (this.otherOwner(this.square.pozX, i)) {
						break;
					}
				} else {
					break;// we've to break because we cannot go beside other piece!!
				}

			}
		}

		for (int i = this.square.pozY - 1; i >= 0; --i) {// down
			for (int step = 5; step > 0; --step) {
				if (this.checkPiece(this.square.pozX, i)) {// if on this square isn't
																										// piece

					if (this.player.color == Player.colors.white) {// white

						if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i])) {
							list.add(chessboard.squares[this.square.pozX][i]);
						}
					} else {// or black

						if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i])) {
							list.add(chessboard.squares[this.square.pozX][i]);
						}
					}

					if (this.otherOwner(this.square.pozX, i)) {
						break;
					}
				} else {
					break;// we've to break because we cannot go beside other piece!!
				}
			}
		}

		for (int i = this.square.pozX - 1; i >= 0; --i) {// left
			for (int step = 5; step > 0; --step) {
				if (this.checkPiece(i, this.square.pozY)) {// if on this square isn't
																										// piece

					if (this.player.color == Player.colors.white) {// white

						if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY])) {
							list.add(chessboard.squares[i][this.square.pozY]);
						}
					} else {// or black

						if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY])) {
							list.add(chessboard.squares[i][this.square.pozY]);
						}
					}

					if (this.otherOwner(i, this.square.pozY)) {
						break;
					}
				} else {
					break;// we've to break becouse we cannot go beside other piece!!
				}
			}
		}

		for (int i = this.square.pozX + 1; i <= 7; ++i) {// right
			for (int step = 5; step > 0; --step) {
				if (this.checkPiece(i, this.square.pozY)) {// if on this square isn't
																										// piece

					if (this.player.color == Player.colors.white) {// white

						if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY])) {
							list.add(chessboard.squares[i][this.square.pozY]);
						}
					} else {// or black

						if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY])) {
							list.add(chessboard.squares[i][this.square.pozY]);
						}
					}

					if (this.otherOwner(i, this.square.pozY)) {
						break;
					}
				} else {
					break;// we've to break because we cannot go beside other piece!!
				}
			}

			return list;
		}
	}
}
