package jchess.core.util;

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

	// @Test
	// public void testSetLocale() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testGetLocale() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testSetTimeLimetSet() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testSetTimeForGame() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testIsTimeLimitSet() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testGetRenderLabels() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testSetGameMode() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testGetGameMode() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testSetWhitePlayersName() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
	// @Test
	// public void testSetBlackPlayersName() throws Exception {
	// throw new RuntimeException("not yet implemented");
	// }
	//
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
