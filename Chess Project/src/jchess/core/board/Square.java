package jchess.core.board;

import jchess.core.pieces.Piece;

/**
 * Class to represent a chessboard square
 */
public class Square extends ChessboardField {

	public int	pozX; // 0-7, becouse 8 squares for row/column
	public int	pozY; // 0-7, becouse 8 squares for row/column

	public Square(int pozX, int pozY, Piece piece) {
		super(piece);
		this.pozX = pozX;
		this.pozY = pozY;
	}

	public Square(Square square) {
		super(square.getPiece());
		this.pozX = square.pozX;
		this.pozY = square.pozY;
	}
}