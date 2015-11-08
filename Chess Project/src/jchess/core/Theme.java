package jchess.core;

import java.awt.Image;

import jchess.pieces.Bishop;
import jchess.pieces.King;
import jchess.pieces.Knight;
import jchess.pieces.Pawn;
import jchess.pieces.Piece;
import jchess.pieces.Queen;
import jchess.pieces.Rook;
import jchess.ui.GUI;
import jchess.util.Player;

public class Theme {

	private static String	IMAGE_WHITE_BISHOP	= "Bishop-W.png";
	private static String	IMAGE_BLACK_BISHOP	= "Bishop-B.png";

	private static String	IMAGE_WHITE_KING		= "King-W.png";
	private static String	IMAGE_BLACK_KING		= "King-B.png";

	private static String	IMAGE_WHITE_KNIGHT	= "Knight-W.png";
	private static String	IMAGE_BLACK_KNIGHT	= "Knight-B.png";

	private static String	IMAGE_WHITE_PAWN		= "Pawn-W.png";
	private static String	IMAGE_BLACK_PAWN		= "Pawn-B.png";

	private static String	IMAGE_WHITE_QUEEN		= "Queen-W.png";
	private static String	IMAGE_BLACK_QUEEN		= "Queen-B.png";

	private static String	IMAGE_WHITE_ROOK		= "Rook-W.png";
	private static String	IMAGE_BLACK_ROOK		= "Rook-B.png";

	public static Image getImageForPiece(Player.colors color, Piece piece) {
		return getImageForPiece(color, piece.symbol);
	}

	public static Image getImageForPiece(Player.colors color, String pieceSymbol) {
		Image result = null;

		if (pieceSymbol == King.SYMBOL) {
			if (color == Player.colors.black) {
				result = GUI.loadImage(IMAGE_BLACK_KING);
			} else {
				result = GUI.loadImage(IMAGE_WHITE_KING);
			}
		} else if (pieceSymbol == Bishop.SYMBOL) {
			if (color == Player.colors.black) {
				result = GUI.loadImage(IMAGE_BLACK_BISHOP);
			} else {
				result = GUI.loadImage(IMAGE_WHITE_BISHOP);
			}
		} else if (pieceSymbol == Knight.SYMBOL) {
			if (color == Player.colors.black) {
				result = GUI.loadImage(IMAGE_BLACK_KNIGHT);
			} else {
				result = GUI.loadImage(IMAGE_WHITE_KNIGHT);
			}
		} else if (pieceSymbol == Pawn.SYMBOL) {
			if (color == Player.colors.black) {
				result = GUI.loadImage(IMAGE_BLACK_PAWN);
			} else {
				result = GUI.loadImage(IMAGE_WHITE_PAWN);
			}
		} else if (pieceSymbol == Queen.SYMBOL) {
			if (color == Player.colors.black) {
				result = GUI.loadImage(IMAGE_BLACK_QUEEN);
			} else {
				result = GUI.loadImage(IMAGE_WHITE_QUEEN);
			}
		} else if (pieceSymbol == Rook.SYMBOL) {
			if (color == Player.colors.black) {
				result = GUI.loadImage(IMAGE_BLACK_ROOK);
			} else {
				result = GUI.loadImage(IMAGE_WHITE_ROOK);
			}
		}

		return result;
	}

}
