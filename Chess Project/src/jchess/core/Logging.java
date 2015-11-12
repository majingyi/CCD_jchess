package jchess.core;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class Logging {

	private static Logger	logger	= Logger.getRootLogger();

	public static void initLogger() {
		try {
			SimpleLayout layout = new SimpleLayout();
			ConsoleAppender consoleAppender = new ConsoleAppender(layout);
			logger.addAppender(consoleAppender);

			FileAppender fileAppender = new FileAppender(layout, "log.log", false);
			logger.addAppender(fileAppender);

			logger.setLevel(Level.INFO);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void log(String message) {
		logger.info(message);
	}

	public static void logError(String message) {
		logger.error(message);
	}

	public static void logException(Exception e) {
		logException("Exception occurred", e);// TODO externalize Strings
	}

	public static void logException(String message, Exception e) {
		logger.error(message, e);
	}
}