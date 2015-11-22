package jchess.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import jchess.core.util.Logging;
import jchess.core.util.Utils;
import jchess.ui.lang.Language;

/**
 * Class representing the game interface which is seen by a player and where are
 * located available for player options, current games and where can he start a
 * new game (load it or save it)
 */
public class GUI {

	public Game											game				= null;
	static final public Properties	configFile	= GUI.getConfigFile();

	public GUI() throws Exception {
		this.game = new Game();
	}

	// TODO move to Settings
	public static Properties getConfigFile() {
		Properties confFile = new Properties();
		File configFile = new File(Utils.getJarPath() + File.separator + "config.txt"); //$NON-NLS-1$

		if (configFile.exists()) {
			try {
				confFile.load(new FileInputStream(configFile));
			} catch (java.io.IOException exc) {
				Logging.log(Language.getString("GUI.5"), exc); //$NON-NLS-1$
				exc.printStackTrace();
			}
		}

		return confFile;
	}

	// TODO move to settings
	public static void storeConfigFile() {
		try {
			File outFile = new File(Utils.getJarPath() + File.separator + "config.txt"); //$NON-NLS-1$
			configFile.store(new FileOutputStream(outFile), null);
		} catch (FileNotFoundException e) {
			Logging.log(Language.getString("GUI.7"), e); //$NON-NLS-1$
			e.printStackTrace();
		} catch (IOException e) {
			Logging.log(Language.getString("GUI.8"), e); //$NON-NLS-1$
			e.printStackTrace();
		}
	}
}
