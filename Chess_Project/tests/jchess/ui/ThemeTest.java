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

/**
 * 
 * @author Maurice
 *
 */
public class ThemeTest {

	@Test
	public void testGetImageForPiece() throws Exception {
		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.BLACK, Pawn.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.WHITE, Pawn.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.RED, Pawn.SYMBOL));

		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.BLACK, Rook.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.WHITE, Rook.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.RED, Rook.SYMBOL));

		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.BLACK, Bishop.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.WHITE, Bishop.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.RED, Bishop.SYMBOL));

		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.BLACK, Knight.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.WHITE, Knight.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.RED, Knight.SYMBOL));

		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.BLACK, King.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.WHITE, King.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.RED, King.SYMBOL));

		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.BLACK, Queen.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.WHITE, Queen.SYMBOL));
		Assert.assertNotNull(Theme.getImageForPiece(Player.PlayerColor.RED, Queen.SYMBOL));
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
			Assert.assertTrue(e.getMessage(), e.getMessage().endsWith("/images/Preview.png"));
			exception = true;
		}
		Assert.assertTrue(exception);

		exception = false;
		try {
			prev = Theme.getThemePreviewImage("hdwf");
		} catch (FileNotFoundException e) {
			Assert.assertTrue(e.getMessage(), e.getMessage().endsWith("hdwf/images/Preview.png"));
			exception = true;
		}
		Assert.assertTrue(exception);
	}
}