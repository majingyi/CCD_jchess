/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package jchess.ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import jchess.Game;

/**
 * Class representing the game interface which is seen by a player and where are
 * located available for player options, current games and where can he start a
 * new game (load it or save it)
 */
public class GUI {

	public Game											game;
	static final public Properties	configFile	= GUI.getConfigFile();

	public GUI() {
		this.game = new Game();

		// this.drawGUI();
	}/*--endOf-GUI--*/

	/*
	 * Method load image by a given name with extension
	 * 
	 * @name : string of image to load for ex. "chessboard.jpg"
	 * 
	 * @returns : image or null if cannot load
	 */

	public static Image loadImage(String name) {
		if (configFile == null) {
			return null;
		}
		Image img = null;
		URL url = null;
		Toolkit tk = Toolkit.getDefaultToolkit();
		try {
			String imageLink = "resources/theme/" + configFile.getProperty("THEME", "default") + "/images/" + name;
			System.out.println(configFile.getProperty("THEME"));
			url = JChessApp.class.getResource(imageLink);
			img = tk.getImage(url);

		} catch (Exception e) {
			System.out.println("some error loading image!");
			e.printStackTrace();
		}
		return img;
	}/*--endOf-loadImage--*/

	public static boolean themeIsValid(String name) {
		return true;
	}

	public static String getJarPath() {
		String path = GUI.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		path = path.replaceAll("[a-zA-Z0-9%!@#$%^&*\\(\\)\\[\\]\\{\\}\\.\\,\\s]+\\.jar", "");
		int lastSlash = path.lastIndexOf(File.separator);
		if (path.length() - 1 == lastSlash) {
			path = path.substring(0, lastSlash);
		}
		path = path.replace("%20", " ");
		return path;
	}

	public static Properties getConfigFile() {
		Properties confFile = new Properties();
		File configFile = new File(GUI.getJarPath() + File.separator + "config.txt");

		if (configFile.exists()) {
			try {
				confFile.load(new FileInputStream(configFile));
			} catch (java.io.IOException exc) {
				System.out.println("some error loading properties file. Cause: " + exc);
				exc.printStackTrace();
			}
		}

		return confFile;
	}

	public static void storeConfigFile() {
		try {
			File outFile = new File(GUI.getJarPath() + File.separator + "config.txt");
			configFile.store(new FileOutputStream(outFile), null);
		} catch (FileNotFoundException e) {
			System.out.println("some error storing properties file. Cause: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("some error storing properties file. Cause: " + e);
			e.printStackTrace();
		}
	}
}
