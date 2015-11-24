package jchess.core.board;

import jchess.core.pieces.Piece;
import jchess.core.util.Constants;

/**
 * Class to represent a chessboard square
 * 
 * @Deprecated Will be removed, as soon as all Pieces are reimplemmented.
 */
public class Square extends ChessboardField {

	public int			pozX;																								// 0-7,
																																				// becouse
																																				// 8
																																				// squares
																																				// for
																																				// row/column
	public int			pozY;																								// 0-7,
																																				// becouse
																																				// 8
																																				// squares
																																				// for
																																				// row/column

	static String[]	letters	= { "A", "B", "C", "D", "E", "F", "G", "H" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$

	public Square(int pozX, int pozY, Piece piece, Chessboard chessboard) throws Exception {
		super(((pozX >= letters.length) || pozX < 0) ? Constants.EMPTY_STRING : letters[pozX] + pozY, chessboard);
		this.pozX = pozX;
		this.pozY = pozY;
		setPiece(piece);
	}

	public Square(Square square) throws Exception {
		super(square.getIdentifier(), square.getChessBoard());
		this.pozX = square.pozX;
		this.pozY = square.pozY;
		setPiece(square.getPiece());
	}

}