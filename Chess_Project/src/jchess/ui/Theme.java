package jchess.ui;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import jchess.core.util.Constants;
import jchess.core.util.Player;
import jchess.core.util.Settings;
import jchess.core.util.Utils;

/**
 *  
 * @author Jingyi Ma
 */
public class Theme {

	public final static String	DEFAULT_THEME	= "default";							//$NON-NLS-1$

	private static List<String>	themeList			= new ArrayList<String>();
	private static String[]			themeImages		= null;

	static {
		themeList.add(DEFAULT_THEME);
		themeList.add("hunter"); //$NON-NLS-1$
		themeList.add("matlak"); //$NON-NLS-1$

		themeImages = new String[] { "able_hexagon.png", "add-tab-icon.png", "chessboard.jpg", "clicked-add-tab-icon.png", "Preview.png", "sel_hexagon.png",
			"Bishop-B.png", "Bishop-W.png", "Bishop-R.png", "King-B.png", "King-W.png", "King-R.png", "Knight-B.png", "Knight-W.png", "Knight-R.png", "Pawn-B.png",
			"Pawn-W.png", "Pawn-R.png", "Queen-B.png", "Queen-W.png", "Queen-R.png", "Rook-B.png", "Rook-W.png", "Rook-R.png" };
	}

	public static Image getImageForPiece(Player.PlayerColor color, String pieceSymbol) throws FileNotFoundException {
		String imageName = pieceSymbol;

		if (color == Player.PlayerColor.WHITE) {
			imageName = imageName + "-W.png"; //$NON-NLS-1$
		} else if (color == Player.PlayerColor.BLACK) {
			imageName = imageName + "-B.png"; //$NON-NLS-1$
		} else if (color == Player.PlayerColor.RED) {
			imageName = imageName + "-R.png"; //$NON-NLS-1$
		}
		return getImage(imageName);
	}

	public static Image getImage(String imageName) throws FileNotFoundException {
		String imagePath = "resources/theme/" + Settings.getActiveTheme() + "/images/" + imageName; //$NON-NLS-1$ //$NON-NLS-2$
		return ImageFactory.getImage(imagePath);
	}

	public static boolean themeIsValid(String name) {
		boolean result = (name != null && name.length() > 0);

		if (result) {
			String basePath = Utils.getJarPath() + Constants.SLASH_STRING + "jchess" + Constants.SLASH_STRING;
			String themePath = "resources/theme/" + name + Constants.SLASH_STRING;
			File folder = new File(basePath + themePath);
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
			if (Settings.getActiveTheme().equals(theme) == false) {
				Settings.setActiveTheme(theme);
			}
		} else {
			throw new Exception("The theme '" + theme + "' is invalid.");
		}
	}

	public static String[] getAvailableThemes() {
		return themeList.toArray(new String[themeList.size()]);
	}

	public static ImageIcon getNoPreviewImage() throws FileNotFoundException {
		String basePath = Utils.getJarPath() + Constants.SLASH_STRING + "jchess" + Constants.SLASH_STRING;
		return ImageFactory.getImageIcon(basePath + "resources/theme/noPreview.png"); //$NON-NLS-1$
	}

	public static ImageIcon getThemePreviewImage(String theme) throws FileNotFoundException {
		String path = Utils.getJarPath() + File.separator + "jchess" + File.separator + "resources" + File.separator + "theme" + File.separator + theme
				+ "/images/Preview.png";
		return ImageFactory.getImageIcon(path);
	}

	public static String getActiveTheme() {
		return Settings.getActiveTheme();
	}
}