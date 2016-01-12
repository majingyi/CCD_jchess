package jchess.core.util;

import java.util.Locale;

import jchess.core.util.Player.PlayerColor;
import jchess.ui.lang.Language;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Maurice
 *
 */
public class SettingsTest {

	@Test
	public void testGetTimeForGame() throws Exception {
		try {
			Assert.assertEquals(0, Settings.getTimeForGame());

			Settings.setTimeForGame(10);
			Assert.assertEquals(10, Settings.getTimeForGame());

			Settings.setTimeForGame((int) 10.0);
			Assert.assertEquals(10, Settings.getTimeForGame());

			boolean exception = false;
			try {
				Settings.setTimeForGame(-1);
			} catch (Exception e) {
				exception = true;
				Assert.assertEquals("Time limit needs to be greater or equal than 0.", e.getMessage());
			}
			Assert.assertEquals(true, exception);
		} finally {
			Settings.setTimeForGame(0);
		}
	}

	@Test
	public void testSetLocale() throws Exception {
		try {
			Assert.assertEquals("error moving piece: ", Language.getString("Chessboard.1"));
			Settings.setLocale(Locale.GERMANY);
			Assert.assertEquals("Fehler beim Setzen der Figur: ", Language.getString("Chessboard.1"));

			boolean exception = false;
			try {
				Settings.setLocale(Locale.CANADA);
			} catch (Exception e) {
				exception = true;
				Assert.assertEquals("Locale en_CA not supported.", e.getMessage());
			}
			Assert.assertEquals(true, exception);
		} finally {
			Settings.setLocale(Locale.US);
		}
	}

	@Test
	public void testGetLocale() throws Exception {
		try {
			Assert.assertEquals(Locale.US, Settings.getLocale());
			Settings.setLocale(Locale.GERMANY);
			Assert.assertEquals(Locale.GERMANY, Settings.getLocale());
		} finally {
			Settings.setLocale(Locale.US);
		}
	}

	@Test
	public void testSetTimeLimitSet() throws Exception {
		try {
			Assert.assertEquals(false, Settings.isTimeLimitSet());
			Settings.setTimeLimitSet(true);
			Assert.assertEquals(true, Settings.isTimeLimitSet());
		} finally {
			Settings.setTimeLimitSet(false);
		}
	}

	@Test
	public void testSetTimeForGame() throws Exception {
		try {
			Assert.assertEquals(0, Settings.getTimeForGame());

			Settings.setTimeForGame(10);
			Assert.assertEquals(10, Settings.getTimeForGame());

			Settings.setTimeForGame((int) 10.0);
			Assert.assertEquals(10, Settings.getTimeForGame());

			boolean exception = false;
			try {
				Settings.setTimeForGame(-1);
			} catch (Exception e) {
				exception = true;
				Assert.assertEquals("Time limit needs to be greater or equal than 0.", e.getMessage());
			}
			Assert.assertEquals(true, exception);
		} finally {
			Settings.setTimeForGame(0);
		}
	}

	@Test
	public void testIsTimeLimitSet() throws Exception {
		try {
			Assert.assertEquals(false, Settings.isTimeLimitSet());
			Settings.setTimeLimitSet(true);
			Assert.assertEquals(true, Settings.isTimeLimitSet());
		} finally {
			Settings.setTimeLimitSet(false);
		}
	}

	@Test
	public void testAddPlayersName() throws Exception {
		Settings.addPlayerName("Hans Wurst", PlayerColor.WHITE);
		Assert.assertEquals("Hans Wurst", Settings.getPlayerNameForColor(PlayerColor.WHITE));

		Settings.addPlayerName("trallala", PlayerColor.WHITE);
		Assert.assertEquals("trallala", Settings.getPlayerNameForColor(PlayerColor.WHITE));

		boolean exception = false;
		try {
			Settings.addPlayerName(null, PlayerColor.WHITE);
		} catch (Exception e) {
			exception = true;
			Assert.assertEquals("Players name is not allowed to be empty string.", e.getMessage());
		}
		Assert.assertEquals(true, exception);

		exception = false;
		try {
			Settings.addPlayerName(Constants.EMPTY_STRING, PlayerColor.WHITE);
		} catch (Exception e) {
			exception = true;
			Assert.assertEquals("Players name is not allowed to be empty string.", e.getMessage());
		}
		Assert.assertEquals(true, exception);
	}

	@Test
	public void testAddPlayersNameDifferentColors() throws Exception {
		Assert.assertEquals(null, Settings.getPlayerNameForColor(PlayerColor.BLACK));
		Assert.assertEquals(null, Settings.getPlayerNameForColor(PlayerColor.RED));

		Settings.addPlayerName("Hans Wurst", PlayerColor.WHITE);
		Assert.assertEquals("Hans Wurst", Settings.getPlayerNameForColor(PlayerColor.WHITE));

		Settings.addPlayerName("HDWF", PlayerColor.BLACK);
		Assert.assertEquals("HDWF", Settings.getPlayerNameForColor(PlayerColor.BLACK));

		Settings.addPlayerName("HGIDL", PlayerColor.RED);
		Assert.assertEquals("HGIDL", Settings.getPlayerNameForColor(PlayerColor.RED));
	}
}