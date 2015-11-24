package jchess.core.util;

import java.util.ArrayList;
import java.util.List;

public class Clock {

	private int										currentSeconds	= 0;
	private List<IClockListener>	listeners				= new ArrayList<IClockListener>();
	private ClockThread						clockThread			= null;

	public Clock(int time) throws Exception {
		if (time < 0) {
			throw new Exception("Time needs to be greater or equal than 0.");
		}
		currentSeconds = time;
	}

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

	public void start() throws Exception {
		if (clockThread == null) {
			clockThread = new ClockThread(currentSeconds, listeners, this);
			clockThread.start();
		} else {
			throw new Exception("Clock Thread is already running.");
		}
	}

	public void stop() {
		if (clockThread != null) {
			clockThread.stopClock();
			clockThread = null;
		}
	}

	public void pause() {
		if (clockThread != null) {
			clockThread.pauseClock();
		}
	}

	public void resume() {
		if (clockThread != null) {
			clockThread.resumeClock();
		}
	}

	public void addClockListener(IClockListener listener) {
		listeners.add(listener);
	}

	public void setTime(int timeForGame) {
		stop();
		currentSeconds = timeForGame;
	}

	public int getRemainingTime() {
		int result = currentSeconds;
		if (clockThread != null) {
			result = clockThread.getRemainingTime();
		}
		return result;
	}

	public boolean isClockRunning() {
		boolean result = (clockThread != null) && (clockThread.isRunning());
		return result;
	}
}