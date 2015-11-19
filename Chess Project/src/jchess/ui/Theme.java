package jchess.ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import jchess.JChessApp;
import jchess.util.Player;

public class Theme {

	/* Constants */

	private final static String	DEFAULT_THEME	= "default";								//$NON-NLS-1$

	/* Member */

	private static String				activeTheme		= DEFAULT_THEME;
	private static List<String>	themeList			= new ArrayList<String>();

	static {
		themeList.add(DEFAULT_THEME);
		themeList.add("hunter"); //$NON-NLS-1$
		themeList.add("matlak"); //$NON-NLS-1$
	}

	public static Image getImageForPiece(Player.colors color, String pieceSymbol) {
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

	public static Image getImage(String imageName) {
		String imagePath = "resources/theme/" + activeTheme + "/images/" + imageName; //$NON-NLS-1$ //$NON-NLS-2$
		return ImageFactory.getImage(imagePath);
	}

	public static boolean themeIsValid(String name) {
		// TODO check if theme exist
		return true;
	}

	public static void setActiveTheme(String theme) {
		if (activeTheme.equals(theme) == false) {
			activeTheme = theme;
		}
	}

	public static String[] getAvailableThemes() {
		return themeList.toArray(new String[themeList.size()]);
	}

	public static ImageIcon getNoPreviewImage() {
		return new ImageIcon(JChessApp.class.getResource("resources/theme/noPreview.png")); //$NON-NLS-1$
	}

	public static ImageIcon getThemePreviewImage(String theme) {
		String path = GUI.getJarPath() + File.separator + "jchess" + File.separator + "resources" + File.separator + "theme" + File.separator + theme
				+ "/images/Preview.png";
		return ImageFactory.getImageIcon(path);
	}
}
