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

			FileAppender fileAppender = new FileAppender(layout, "log.log", false); //$NON-NLS-1$
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

	public static void log(Throwable e) {
		log(Language.getString("Logging.1"), e);// TODO externalize Strings //$NON-NLS-1$
	}

	public static void log(String message, Throwable e) {
		logger.error(message, e);
	}

	public static void log(int number) {
		logger.info(number);
	}

	public static void log(boolean bool) {
		logger.info(bool);
	}
}