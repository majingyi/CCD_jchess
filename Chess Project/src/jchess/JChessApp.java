package jchess;

import java.io.FileNotFoundException;

import jchess.core.util.Logging;
import jchess.core.util.Settings;
import jchess.ui.JChessView;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class JChessApp extends SingleFrameApplication {
	public static JChessView	jcv;

	/**
	 * At startup create and show the main frame of the application.
	 */
	@Override
	protected void startup() {

		// initialize logging
		Logging.initLogger();

		try {
			jcv = new JChessView(this);
			show(jcv);
		} catch (FileNotFoundException e) {
			Logging.log(e);
		}
	}

	@Override
	protected void configureWindow(java.awt.Window root) {
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