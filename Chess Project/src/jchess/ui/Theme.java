package jchess.ui;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import jchess.core.util.Constants;
import jchess.core.util.Player;

public class Theme {

	private final static String	DEFAULT_THEME	= "default";								//$NON-NLS-1$

	private static String				activeTheme		= DEFAULT_THEME;
	private static List<String>	themeList			= new ArrayList<String>();
	private static String[]			themeImages		= null;

	static {
		themeList.add(DEFAULT_THEME);
		themeList.add("hunter"); //$NON-NLS-1$
		themeList.add("matlak"); //$NON-NLS-1$

		themeImages = new String[] { "able_square.png", "add-tab-icon.png", "Bishop-B.png", "Bishop-W.png", "chessboard.png", "clicked-add-tab-icon.png",
			"King-B.png", "King-W.png", "Knight-B.png", "Knight-W.png", "Pawn-B.png", "Pawn-W.png", "Preview.png", "Queen-B.png", "Queen-W.png", "Rook-B.png",
			"Rook-W.png", "sel_square.png" };
	}

	public static Image getImageForPiece(Player.colors color, String pieceSymbol) throws FileNotFoundException {
		String imageName = pieceSymbol;

		if (color == Player.colors.white) {
			imageName = imageName + "-W.png"; //$NON-NLS-1$
		} else if (color == Player.colors.black) {
			imageName = imageName + "-B.png"; //$NON-NLS-1$
		} else if (color == Player.colors.gray) {
			imageName = imageName + "-G.png"; //$NON-NLS-1$
		}
		return getImage(imageName);
	}

	public static Image getImage(String imageName) throws FileNotFoundException {
		String imagePath = "resources/theme/" + activeTheme + "/images/" + imageName; //$NON-NLS-1$ //$NON-NLS-2$
		return ImageFactory.getImage(imagePath);
	}

	public static boolean themeIsValid(String name) {
		boolean result = name != null && name.length() > 0;

		if (result) {
			String basePath = GUI.getJarPath() + Constants.SLASH_STRING + "jchess" + Constants.SLASH_STRING;
			String themePath = "resources/theme/" + name + Constants.SLASH_STRING;
			File folder = new File(basePath + Constants.SLASH_STRING + themePath);
			result &= folder.exists();

			for (String themeImage : themeImages) {
				File image = new File(folder, "images" + Constants.SLASH_STRING + themeImage);
				result &= image.exists();
			}
		}
		return result;
	}

	public static void setActiveTheme(String theme) throws Exception {
		if (themeIsValid(theme)) {
			if (activeTheme.equals(theme) == false) {
				activeTheme = theme;
			}
		} else {
			throw new Exception("The theme '" + theme + "' is invalid.");
		}
	}

	public static String[] getAvailableThemes() {
		return themeList.toArray(new String[themeList.size()]);
	}

	public static ImageIcon getNoPreviewImage() throws FileNotFoundException {
		String basePath = GUI.getJarPath() + Constants.SLASH_STRING + "jchess" + Constants.SLASH_STRING;
		return ImageFactory.getImageIcon(basePath + "resources/theme/noPreview.png"); //$NON-NLS-1$
	}

	public static ImageIcon getThemePreviewImage(String theme) throws FileNotFoundException {
		String path = GUI.getJarPath() + File.separator + "jchess" + File.separator + "resources" + File.separator + "theme" + File.separator + theme
				+ "/images/Preview.png";
		return ImageFactory.getImageIcon(path);
	}

	public static String getActiveTheme() {
		return activeTheme;
	}
}