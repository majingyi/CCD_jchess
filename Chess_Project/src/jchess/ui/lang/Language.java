package jchess.ui.lang;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import jchess.core.util.Constants;
import jchess.core.util.Settings;

public class Language {
	private static final String	BUNDLE_NAME	= "jchess.ui.lang.main";	//$NON-NLS-1$

	private Language() {
	}

	public static String getString(String key) {
		try {
			String lang = Settings.getLocale().toString();
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME + Constants.UNDER_SCORE_STRING + lang);
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}