package jchess.core.util;

import java.util.List;

public class ClockThread extends Thread {

	private long									time			= 0;
	private boolean								pause			= false;
	private boolean								stop			= false;
	private List<IClockListener>	listener	= null;
	private Clock									clock			= null;
	private boolean								isRunning	= false;

	public ClockThread(int time, List<IClockListener> listeners, Clock clock) {
		this.time = time * 1000;
		listener = listeners;
		this.clock = clock;
	}

	@Override
	public void run() {
		isRunning = true;
		while ((time > 0) && (stop == false)) {
			long startTime = System.currentTimeMillis();
			try {
				Thread.sleep(250);
				if (pause == false) {
					long diff = System.currentTimeMillis() - startTime;
					time -= diff;
				}
			} catch (InterruptedException e) {
				Logging.log(e);
			}
		}

		isRunning = false;

		for (IClockListener listener : this.listener) {
			listener.timeOver(clock);
		}
	}

	public int getRemainingTime() {
		int result = (int) (time / 1000);
		return result;
	}

	public void pauseClock() {
		pause = true;
	}

	public void resumeClock() {
		pause = false;
	}

	public void stopClock() {
		stop = true;
	}

	/**
	 * Thread is considered running, if it is started, alive and not paused.
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return isRunning && isAlive() && (pause == false);
	}
}