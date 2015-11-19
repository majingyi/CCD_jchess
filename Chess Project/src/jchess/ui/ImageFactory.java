package jchess.ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jchess.JChessApp;
import jchess.core.Logging;
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
	 */
	public static Icon getIcon(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("jchess.ui.lang.main"); //$NON-NLS-1$
		String imageName = bundle.getString(key);

		String imageLink = "resources/" + imageName; //$NON-NLS-1$
		URL url = JChessApp.class.getResource(imageLink);
		return getImageIcon(url.getFile());
	}

	/**
	 * Loads image from given image path.
	 * 
	 * @param imagePath
	 * @return
	 * 
	 */
	public static Image getImage(String imagePath) {
		Image img = imageCache.get(imagePath);

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

		return img;
	}

	/**
	 * Creates ImageIcon from given path.
	 * 
	 * @param path
	 * @return
	 */
	public static ImageIcon getImageIcon(String path) {
		ImageIcon icon = iconCache.get(path);
		if (icon == null) {
			icon = new ImageIcon(path);
			iconCache.put(path, icon);
		}
		return icon;
	}
}