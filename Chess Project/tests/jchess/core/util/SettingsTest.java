package jchess.core.util;

import java.util.Locale;

import jchess.ui.lang.Language;

import org.junit.Assert;
import org.junit.Test;

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
	public void testSetTimeLimetSet() throws Exception {
		try {
			Assert.assertEquals(false, Settings.isTimeLimitSet());
			Settings.setTimeLimetSet(true);
			Assert.assertEquals(true, Settings.isTimeLimitSet());
		} finally {
			Settings.setTimeLimetSet(false);
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
			Settings.setTimeLimetSet(true);
			Assert.assertEquals(true, Settings.isTimeLimitSet());
		} finally {
			Settings.setTimeLimetSet(false);
		}
	}

	@Test
	public void testSetWhitePlayersName() throws Exception {
		Assert.assertEquals(null, Settings.getWhitePlayersName());

		Settings.setWhitePlayersName("Hans Wurst");
		Assert.assertEquals("Hans Wurst", Settings.getWhitePlayersName());

		Settings.setWhitePlayersName("trallala");
		Assert.assertEquals("trallala", Settings.getWhitePlayersName());

		boolean exception = false;
		try {
			Settings.setWhitePlayersName(null);
		} catch (Exception e) {
			exception = true;
			Assert.assertEquals("Players name is not allowed to be empty string.", e.getMessage());
		}
		Assert.assertEquals(true, exception);

		exception = false;
		try {
			Settings.setWhitePlayersName("");
		} catch (Exception e) {
			exception = true;
			Assert.assertEquals("Players name is not allowed to be empty string.", e.getMessage());
		}
		Assert.assertEquals(true, exception);
	}

	@Test
	public void testSetBlackPlayersName() throws Exception {
		Assert.assertEquals(null, Settings.getBlackPlayersName());

		Settings.setBlackPlayersName("Hans Wurst");
		Assert.assertEquals("Hans Wurst", Settings.getBlackPlayersName());

		Settings.setBlackPlayersName("trallala");
		Assert.assertEquals("trallala", Settings.getBlackPlayersName());

		boolean exception = false;
		try {
			Settings.setBlackPlayersName(null);
		} catch (Exception e) {
			exception = true;
			Assert.assertEquals("Players name is not allowed to be empty string.", e.getMessage());
		}
		Assert.assertEquals(true, exception);

		exception = false;
		try {
			Settings.setBlackPlayersName("");
		} catch (Exception e) {
			exception = true;
			Assert.assertEquals("Players name is not allowed to be empty string.", e.getMessage());
		}
		Assert.assertEquals(true, exception);
	}

	// @Test
	// public void testGetBlackPlayersName() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testGetWhitePlayersName() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testStoreConfigFile() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testSetProperty() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testGetActiveTheme() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testSetActiveTheme() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }

}
