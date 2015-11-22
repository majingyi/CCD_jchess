package jchess.ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jchess.JChessApp;
import jchess.core.util.Constants;
import jchess.core.util.Logging;
import jchess.ui.lang.Language;

public class ImageFactory {

	private static Map<String, Image>			imageCache	= new HashMap<String, Image>();
	private static Map<String, ImageIcon>	iconCache		= new HashMap<String, ImageIcon>();

	/**
	 * Create Icon with given key. The key is looked up in the language properties
	 * file.
	 * 
	 * @param key
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Icon getIcon(String key) throws FileNotFoundException {
		String basePath = GUI.getJarPath() + Constants.SLASH_STRING + "jchess";
		String imageName = Language.getString(key);

		String imageLink = basePath + Constants.SLASH_STRING + "resources/" + imageName; //$NON-NLS-1$
		return getImageIcon(imageLink);
	}

	/**
	 * Loads image from given image path.
	 * 
	 * @param imagePath
	 * @return
	 * @throws FileNotFoundException
	 * 
	 */
	public static Image getImage(String imagePath) throws FileNotFoundException {
		Image img = null;
		String basePath = GUI.getJarPath() + Constants.SLASH_STRING + "jchess";
		File file = new File(basePath, imagePath);
		if (file.exists()) {
			img = imageCache.get(imagePath);

			if (img == null) {
				URL url = null;
				Toolkit tk = Toolkit.getDefaultToolkit();
				try {
					url = JChessApp.class.getResource(imagePath);
					img = tk.getImage(url);
					imageCache.put(imagePath, img);
				} catch (Exception e) {
					Logging.log(Language.getString("Theme.8"), e); //$NON-NLS-1$
				}
			}
		} else {
			throw new FileNotFoundException(imagePath);
		}

		return img;
	}

	/**
	 * Creates ImageIcon from given path.
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	public static ImageIcon getImageIcon(String path) throws FileNotFoundException {
		ImageIcon icon = null;

		File file = new File(path);
		if (file.exists()) {
			icon = iconCache.get(path);
			if (icon == null) {
				icon = new ImageIcon(path);
				iconCache.put(path, icon);
			}
		} else {
			throw new FileNotFoundException(path);
		}
		return icon;
	}
}