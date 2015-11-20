package jchess.core.board;

import jchess.core.pieces.Piece;

/**
 * Class to represent a chessboard square
 */
public class Square {

	public int		pozX;				// 0-7, becouse 8 squares for row/column
	public int		pozY;				// 0-7, becouse 8 squares for row/column
	public Piece	piece	= null; // object Piece on square (and extending Piecie)

	public Square(int pozX, int pozY, Piece piece) {
		this.pozX = pozX;
		this.pozY = pozY;
		this.piece = piece;
	}

	public Square(Square square) {
		this.pozX = square.pozX;
		this.pozY = square.pozY;
		this.piece = square.piece;
	}

	public Square clone(Square square) {
		return new Square(square);
	}

	public void setPiece(Piece piece) throws Exception {
		this.piece = piece;
		this.piece.setSquare(this);
	}
}