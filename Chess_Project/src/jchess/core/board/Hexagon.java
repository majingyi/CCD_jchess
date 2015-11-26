package jchess.core.board;

public class Hexagon extends ChessboardField {

	public Hexagon(String identifier, Chessboard chessboard) {
		super(identifier, chessboard);
	}

	@Override
	public ChessboardField copy() {
		Hexagon copy = new Hexagon(getIdentifier(), getChessBoard());
		copy.setPiece(getPiece());
		return copy;
	}
}
