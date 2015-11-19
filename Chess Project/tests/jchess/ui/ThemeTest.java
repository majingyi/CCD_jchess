package jchess.ui;

import javax.swing.ImageIcon;

import org.junit.Assert;
import org.junit.Test;

public class ThemeTest {

	@Test
	public void testGetImageForPiece() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testGetImage() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testThemeIsValid() throws Exception {
		Assert.assertTrue(Theme.themeIsValid("default"));
		Assert.assertTrue(Theme.themeIsValid("hunter"));
		Assert.assertTrue(Theme.themeIsValid("matlak"));

		Assert.assertFalse(Theme.themeIsValid(null));
		Assert.assertFalse(Theme.themeIsValid(""));
		Assert.assertFalse(Theme.themeIsValid("unknown"));

	}

	@Test
	public void testSetActiveTheme() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testGetAvailableThemes() throws Exception {
		String[] themes = Theme.getAvailableThemes();
		Assert.assertEquals(3, themes.length);

		// check for correct Themes
		Assert.assertEquals("default", themes[0]);
		Assert.assertEquals("hunter", themes[1]);
		Assert.assertEquals("matlak", themes[2]);
	}

	@Test
	public void testGetNoPreviewImage() throws Exception {
		ImageIcon noPrev = Theme.getNoPreviewImage();
		Assert.assertNotNull(noPrev);
	}

	@Test
	public void testGetThemePreviewImage() throws Exception {
		throw new RuntimeException("not yet implemented");
	}
}