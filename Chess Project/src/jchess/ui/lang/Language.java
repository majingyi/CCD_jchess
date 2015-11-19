package jchess.ui.lang;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import jchess.Settings;
import jchess.util.Constants;

public class Language {
	private static final String	BUNDLE_NAME	= "jchess.ui.lang.main";	//$NON-NLS-1$

	private Language() {
	}

	public static String getString(String key) {
		try {
			String lang = Settings.getLocale().toString();
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME + Constants.UNDER_SCORE + lang);
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
