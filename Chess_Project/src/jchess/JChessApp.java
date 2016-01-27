package jchess;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import jchess.core.util.Logging;
import jchess.core.util.Settings;
import jchess.ui.JChessView;

/**
 * The main class of the application.
 * @author Jingyi Ma
 */
public class JChessApp extends SingleFrameApplication {

	/**
	 * At startup create and show the main frame of the application.
	 */
	@Override
	protected void startup() {

		// initialize logging
		Logging.initLogger();

		// open view
		show(JChessView.getInstance());
	}

	/**
	 * A convenient static getter for the application instance.
	 * 
	 * @return the instance of JChessApp
	 */
	public static JChessApp getApplication() {
		return Application.getInstance(JChessApp.class);
	}

	public static void main(String[] args) {
		launch(JChessApp.class, args);
	}

	@Override
	protected void shutdown() {
		Settings.storeConfigFile();
		super.shutdown();
	}
}