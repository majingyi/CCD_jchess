package jchess.core.board.graph;

import jchess.core.board.Chessboard;
import jchess.core.board.ChessboardField;
import jchess.core.board.Hexagon;
import jchess.core.pieces.Rook;
import jchess.core.util.Constants;
import jchess.core.util.Player;

public class HexagonChessFieldGraphInitializer {

	public static void initialise(Chessboard chessboard, Player white, Player black, Player red) throws Exception {

		ChessboardField[][] fields = new ChessboardField[13][13];

		/*
		 * First row has 8 fields, second 9,... until number of fields = 13. Next
		 * field has 12 , 11, .. until end.
		 * 
		 * There are 13 rows.
		 */
		int numberofFields = 8;
		int start = 0;
		for (int row = 0; row < 13;) {
			for (int column = start; column < numberofFields + start; column++) {
				String identifier = getIdentifierLetter(row + 1) + (column + 1);
				ChessboardField field = new Hexagon(identifier, chessboard);
				chessboard.addNode(field);
				fields[row][column] = field;
			}
			row++;
			if (row < 6) {
				numberofFields++;
			} else {
				numberofFields--;
				start++;
			}
		}

		/*
		 * Add straight edges
		 */
		for (int i = 0; i < 13; i++) {
			connectAllStraightHorizontal(fields[i]);
			connectAllStraightVertical(i, fields);
		}

		connectDiagonalStraightNeighbors(fields);

		/*
		 * Add diagonal edges
		 */
		connectDiagonalEdges(fields);

		/*
		 * Add pieces for the player
		 */

		// white on top
		ChessboardField a1 = (ChessboardField) chessboard.getNode("A1");
		a1.setPiece(new Rook(chessboard, white, a1));

		// black to the left

		// red to the right

	}

	private static void connectDiagonalEdges(ChessboardField[][] fields) {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				ChessboardField field = fields[i][j];
				if (field != null) {
					// left below
					int newI = i + 1;
					int newJ = j - 1;
					if ((newI < 13) && (newJ > -1)) {
						ChessboardField field2 = fields[newI][newJ];
						if (field2 != null) {
							DiagonalEdge edge = new DiagonalEdge(field, field2);
							field.addEdge(edge);
							field2.addEdge(edge);
						}
					}

					// down
					newI = i + 2;
					newJ = j + 1;
					if ((newI < 13) && (newJ < 13)) {
						ChessboardField field2 = fields[newI][newJ];
						if (field2 != null) {
							DiagonalEdge edge = new DiagonalEdge(field, field2);
							field.addEdge(edge);
							field2.addEdge(edge);
						}
					}

					// right below
					newI = i + 1;
					newJ = j + 2;
					if ((newI < 13) && (newJ < 13)) {
						ChessboardField field2 = fields[newI][newJ];
						if (field2 != null) {
							DiagonalEdge edge = new DiagonalEdge(field, field2);
							field.addEdge(edge);
							field2.addEdge(edge);
						}
					}
				}
			}
		}
	}

	private static void connectDiagonalStraightNeighbors(ChessboardField[][] fields) {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				ChessboardField field = fields[i][j];
				if ((field != null) && ((i + 1) < 13) && ((j + 1) < 13)) {
					ChessboardField field2 = fields[i + 1][j + 1];
					if (field2 != null) {
						StraightEdge edge = new StraightEdge(field, field2);
						field.addEdge(edge);
						field2.addEdge(edge);
					}
				}
			}
		}
	}

	private static void connectAllStraightHorizontal(ChessboardField[] fields) {
		for (int i = 0; i < 13; i++) {
			ChessboardField field = fields[i];
			if ((field != null) && ((i + 1) < 13)) {
				ChessboardField field2 = fields[i + 1];
				if (field2 != null) {
					StraightEdge edge = new StraightEdge(field, field2);
					field.addEdge(edge);
					field2.addEdge(edge);
				}
			}
		}
	}

	private static void connectAllStraightVertical(int column, ChessboardField[][] fields) {
		for (int i = 0; i < 13; i++) {
			ChessboardField field = fields[i][column];
			if ((field != null) && ((i + 1) < 13)) {
				ChessboardField field2 = fields[i + 1][column];
				if (field2 != null) {
					StraightEdge edge = new StraightEdge(field, field2);
					field.addEdge(edge);
					field2.addEdge(edge);
				}
			}
		}
	}

	private static String getIdentifierLetter(int pos) {
		String result = Constants.EMPTY_STRING;
		switch (pos) {
			case 1:
				result = "A";
				break;
			case 2:
				result = "B";
				break;
			case 3:
				result = "C";
				break;
			case 4:
				result = "D";
				break;
			case 5:
				result = "E";
				break;
			case 6:
				result = "F";
				break;
			case 7:
				result = "G";
				break;
			case 8:
				result = "H";
				break;
			case 9:
				result = "I";
				break;
			case 10:
				result = "J";
				break;
			case 11:
				result = "K";
				break;
			case 12:
				result = "L";
				break;
			case 13:
				result = "M";
				break;

			default:
				break;
		}

		return result;
	}
}