package jchess.core.board;

import jchess.core.util.Constants;

public class ChessBoardUtils {

	public static String getIdentifierLetter(int pos) {
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

	public static int[] getCoordinatesFromID(String id) {

		int[] coordinates = { 0, 0 };

		char letter = id.charAt(0);
		switch (letter) {
			case 'A':
				coordinates[0] = 1;
				break;
			case 'B':
				coordinates[0] = 2;
				break;
			case 'C':
				coordinates[0] = 3;
				break;
			case 'D':
				coordinates[0] = 4;
				break;
			case 'E':
				coordinates[0] = 5;
				break;
			case 'F':
				coordinates[0] = 6;
				break;
			case 'G':
				coordinates[0] = 7;
				break;
			case 'H':
				coordinates[0] = 8;
				break;
			case 'I':
				coordinates[0] = 9;
				break;
			case 'J':
				coordinates[0] = 10;
				break;
			case 'K':
				coordinates[0] = 11;
				break;
			case 'L':
				coordinates[0] = 12;
				break;
			case 'M':
				coordinates[0] = 13;
				break;
			default:
				break;
		}
		char number = id.charAt(1);
		switch (number) {
			case '1':
				coordinates[1] = 1;
				break;
			case '2':
				coordinates[1] = 2;
				break;
			case '3':
				coordinates[1] = 3;
				break;
			case '4':
				coordinates[1] = 4;
				break;
			case '5':
				coordinates[1] = 5;
				break;
			case '6':
				coordinates[1] = 6;
				break;
			case '7':
				coordinates[1] = 7;
				break;
			case '8':
				coordinates[1] = 8;
				break;
			case '9':
				coordinates[1] = 9;
				break;

			default:
				break;
		}
		if (id.length() > 2) {
			char number2 = id.charAt(2);
			switch (number2) {
				case '0':
					coordinates[1] = 10;
					break;
				case '1':
					coordinates[1] = 11;
					break;
				case '2':
					coordinates[1] = 12;
					break;
				case '3':
					coordinates[1] = 13;
					break;
				default:
					break;
			}
		}
		return coordinates;

	}

}
