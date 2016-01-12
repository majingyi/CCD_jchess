package jchess.core.util;

import java.io.File;

/**
 * This class is basically a collection of convenience methods.
 * 
 * @author Maurice
 *
 */
public class Utils {

	/**
	 * 
	 * @return The path the executed jchess jar is located.
	 */
	public static String getJarPath() {
		String path = Utils.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		path = path.replaceAll("[a-zA-Z0-9%!@#$%^&*\\(\\)\\[\\]\\{\\}\\.\\,\\s]+\\.jar", Constants.EMPTY_STRING); //$NON-NLS-1$ 
		int lastSlash = path.lastIndexOf(File.separator);
		if (path.length() - 1 == lastSlash) {
			path = path.substring(0, lastSlash);
		}
		path = path.replace("%20", Constants.WHITE_SPACE_STRING); //$NON-NLS-1$ 
		return path;
	}
}
