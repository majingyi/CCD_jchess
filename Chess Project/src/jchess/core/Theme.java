package jchess.core;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import jchess.JChessApp;
import jchess.resources.i18n.Language;
import jchess.ui.GUI;
import jchess.util.Player;

public class Theme {

	/* Constants */

	private final static String				DEFAULT_THEME	= "default"; //$NON-NLS-1$

	/* Member */

	private static String							activeTheme		= DEFAULT_THEME;
	private static Map<String, Image>	imageCache		= new HashMap<String, Image>();
	private static List<String>				themeList			= new ArrayList<String>();

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

	/**
	 * Loads image from resources folder
	 * 
	 * @param imageName
	 * @return
	 */
	public static Image getImage(String imageName) {
		Image img = imageCache.get(imageName);

		if (img == null) {
			URL url = null;
			Toolkit tk = Toolkit.getDefaultToolkit();
			try {
				String imageLink = "resources/theme/" + activeTheme + "/images/" + imageName; //$NON-NLS-1$ //$NON-NLS-2$
				url = JChessApp.class.getResource(imageLink);
				img = tk.getImage(url);
				imageCache.put(imageName, img);
			} catch (Exception e) {
				Logging.log(Language.getString("Theme.8"), e); //$NON-NLS-1$
			}
		}

		return img;
	}

	public static boolean themeIsValid(String name) {
		return true;
	}

	public static void setActiveTheme(String theme) {
		if (activeTheme.equals(theme) == false) {
			activeTheme = theme;
			imageCache.clear();
		}
	}

	public static String[] getAvailableThemes() {
		return themeList.toArray(new String[themeList.size()]);
	}

	public static ImageIcon getNoPreviewImage() {
		return new ImageIcon(JChessApp.class.getResource("resources/theme/noPreview.png")); //$NON-NLS-1$
	}

	public static ImageIcon getThemePreviewImage(String theme) {
		String path = GUI.getJarPath() + File.separator + "jchess" + File.separator + "resources" + File.separator + "theme" + File.separator; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Logging.log(path + theme + "/images/Preview.png"); //$NON-NLS-1$
		return new ImageIcon(path + theme + "/images/Preview.png"); //$NON-NLS-1$
	}
}
