package jchess.core.util;

import jchess.ui.GameClock;

public class Clock {

	private int	currentSeconds	= 0;

	public Clock(int time) {
		currentSeconds = time;
	}

	/**
	 * Method to prepare time in nice looking String
	 * 
	 * @return String of actual left game time with ':' digits in mm:ss format
	 */
	public String toString() {
		String result = Constants.EMPTY_STRING;

		int time_min = currentSeconds / 60;
		int time_sec = currentSeconds % 60;

		if (time_min < 10) { // prepare minutes
			result = Integer.toString(0);
		}
		result += time_min;
		result += Constants.COLON_STRING;

		if (time_sec < 10) {// prepare seconds
			result += Integer.toString(0);
		}
		result += time_sec;

		return result;
	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	public void pause() {
		// TODO Auto-generated method stub

	}

	public void resume() {
		// TODO Auto-generated method stub

	}

	public void addClockListener(GameClock gameClock) {
		// TODO Auto-generated method stub

	}

	public void setTime(int timeForGame) {
		stop();
		currentSeconds = timeForGame;
	}
}