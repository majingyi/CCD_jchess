package jchess.core.util;

import java.io.File;

public class Utils {
	public static String getJarPath() {
		String path = Utils.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		path = path.replaceAll("[a-zA-Z0-9%!@#$%^&*\\(\\)\\[\\]\\{\\}\\.\\,\\s]+\\.jar", ""); //$NON-NLS-1$ //$NON-NLS-2$
		int lastSlash = path.lastIndexOf(File.separator);
		if (path.length() - 1 == lastSlash) {
			path = path.substring(0, lastSlash);
		}
		path = path.replace("%20", " "); //$NON-NLS-1$ //$NON-NLS-2$
		return path;
	}
}
