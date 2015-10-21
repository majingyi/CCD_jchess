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
package jchess;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;

/**
 * Class representings game settings available for the current player
 */
public class Settings implements Serializable {

	private static Locale	locale	= Locale.US;
	public int						timeForGame;
	public boolean				runningChat;
	public boolean				runningGameClock;
	public boolean				timeLimitSet;				// tel us if player choose time 4
																							// game or it's
	// infinity
	public boolean				upsideDown;

	public enum gameModes {

		newGame, loadGame
	}

	public gameModes	gameMode;
	public Player			playerWhite;
	public Player			playerBlack;

	public enum gameTypes {

		local, network
	}

	public gameTypes	gameType;
	public boolean		renderLabels	= true;

	public Settings() {
		// temporally
		this.playerWhite = new Player("", "white");
		this.playerBlack = new Player("", "black");
		this.timeLimitSet = false;

		gameMode = gameModes.newGame;
	}

	/**
	 * Method to get game time set by player
	 * 
	 * @return timeFofGame int with how long the game will leasts
	 */
	public int getTimeForGame() {
		return this.timeForGame;
	}

	public static void setLocale(Locale localization) {
		locale = localization;
	}

	public static String lang(String key) {
		String result = "";

		Locale.setDefault(locale);
		ResourceBundle.clearCache();
		ResourceBundle bundle = ResourceBundle.getBundle("jchess.resources.i18n.main");

		try {
			result = bundle.getString(key);
		} catch (java.util.MissingResourceException exc) {
			// ignore
		}

		if (result.equals("")) {
			bundle = ResourceBundle.getBundle("jchess.resources.JChessView");
		}

		try {
			result = bundle.getString(key);
		} catch (java.util.MissingResourceException exc) {
			result = key;
		}

		if (result.equals("")) {
			bundle = ResourceBundle.getBundle("jchess.resources.JChessAboutBox");
		}

		try {
			result = bundle.getString(key);
		} catch (java.util.MissingResourceException exc) {
			result = key;
		}

		return result;
	}

	public static Locale getLocale() {
		return locale;
	}

	public static Icon getIcon(String key) {
		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(jchess.JChessApp.class).getContext()
				.getResourceMap(JChessAboutBox.class);
		return resourceMap.getIcon(key);
	}
}
