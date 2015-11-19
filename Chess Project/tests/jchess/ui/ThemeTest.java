package jchess.ui;

import java.io.FileNotFoundException;

import javax.swing.ImageIcon;

import jchess.core.pieces.Bishop;
import jchess.core.pieces.King;
import jchess.core.pieces.Knight;
import jchess.core.pieces.Pawn;
import jchess.core.pieces.Queen;
import jchess.core.pieces.Rook;
import jchess.core.util.Constants;
import jchess.core.util.Player;

import org.junit.Assert;
import org.junit.Test;

public class ThemeTest {

	@Test
	public void testGetImageForPiece() throws Exception {
		Assert.assertNotNull(Theme.getImageForPiece(Player.colors.black, Pawn.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.colors.white, Pawn.SYMBOL));

		boolean exception = false;
		try {
			Assert.assertNotNull(Theme.getImageForPiece(Player.colors.gray, Pawn.SYMBOL));
		} catch (FileNotFoundException e) {
			exception = true;
		}
		Assert.assertTrue(exception);

		Assert.assertNotNull(Theme.getImageForPiece(Player.colors.black, Rook.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.colors.white, Rook.SYMBOL));

		exception = false;
		try {
			Assert.assertNotNull(Theme.getImageForPiece(Player.colors.gray, Rook.SYMBOL));
		} catch (FileNotFoundException e) {
			exception = true;
		}
		Assert.assertTrue(exception);

		Assert.assertNotNull(Theme.getImageForPiece(Player.colors.black, Bishop.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.colors.white, Bishop.SYMBOL));

		exception = false;
		try {
			Assert.assertNotNull(Theme.getImageForPiece(Player.colors.gray, Bishop.SYMBOL));
		} catch (FileNotFoundException e) {
			exception = true;
		}
		Assert.assertTrue(exception);

		Assert.assertNotNull(Theme.getImageForPiece(Player.colors.black, Knight.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.colors.white, Knight.SYMBOL));

		exception = false;
		try {
			Assert.assertNotNull(Theme.getImageForPiece(Player.colors.gray, Knight.SYMBOL));
		} catch (FileNotFoundException e) {
			exception = true;
		}
		Assert.assertTrue(exception);

		Assert.assertNotNull(Theme.getImageForPiece(Player.colors.black, King.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.colors.white, King.SYMBOL));

		exception = false;
		try {
			Assert.assertNotNull(Theme.getImageForPiece(Player.colors.gray, King.SYMBOL));
		} catch (FileNotFoundException e) {
			exception = true;
		}
		Assert.assertTrue(exception);

		Assert.assertNotNull(Theme.getImageForPiece(Player.colors.black, Queen.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.colors.white, Queen.SYMBOL));

		exception = false;
		try {
			Assert.assertNotNull(Theme.getImageForPiece(Player.colors.gray, Queen.SYMBOL));
		} catch (FileNotFoundException e) {
			exception = true;
		}
		Assert.assertTrue(exception);
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
		Assert.assertEquals("default", Theme.getActiveTheme());
		Theme.setActiveTheme("matlak");
		Assert.assertEquals("matlak", Theme.getActiveTheme());
		Theme.setActiveTheme("hunter");
		Assert.assertEquals("hunter", Theme.getActiveTheme());

		boolean exception = false;
		try {
			Theme.setActiveTheme("");
		} catch (Exception e) {
			Assert.assertEquals("The theme '' is invalid.", e.getMessage());
			exception = true;
		}
		Assert.assertTrue(exception);

		exception = false;
		try {
			Theme.setActiveTheme(null);
		} catch (Exception e) {
			Assert.assertEquals("The theme 'null' is invalid.", e.getMessage());
			exception = true;
		}
		Assert.assertTrue(exception);

		exception = false;
		try {
			Theme.setActiveTheme("hdwf");
		} catch (Exception e) {
			Assert.assertEquals("The theme 'hdwf' is invalid.", e.getMessage());
			exception = true;
		}
		Assert.assertTrue(exception);

		Theme.setActiveTheme("default");
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
		ImageIcon prev = Theme.getThemePreviewImage("default");
		Assert.assertNotNull(prev);
		Assert.assertTrue(prev.getDescription().endsWith("default/images/Preview.png"));

		prev = Theme.getThemePreviewImage("hunter");
		Assert.assertNotNull(prev);
		Assert.assertTrue(prev.getDescription().endsWith("hunter/images/Preview.png"));

		prev = Theme.getThemePreviewImage("matlak");
		Assert.assertNotNull(prev);
		Assert.assertTrue(prev.getDescription().endsWith("matlak/images/Preview.png"));

		boolean exception = false;
		try {
			prev = Theme.getThemePreviewImage(Constants.EMPTY_STRING);
		} catch (FileNotFoundException e) {
			Assert.assertTrue(e.getMessage(), e.getMessage().endsWith("resources/theme//images/Preview.png"));
			exception = true;
		}
		Assert.assertTrue(exception);

		exception = false;
		try {
			prev = Theme.getThemePreviewImage("hdwf");
		} catch (FileNotFoundException e) {
			Assert.assertTrue(e.getMessage(), e.getMessage().endsWith("resources/theme/hdwf/images/Preview.png"));
			exception = true;
		}
		Assert.assertTrue(exception);
	}
}