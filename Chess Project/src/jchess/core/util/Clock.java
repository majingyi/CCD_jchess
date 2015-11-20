package jchess.core.util;

/**
 * Class to represent seperate wall-clock for one player. Full ChessClock is
 * represented by GameClock object (two clock - one for each player)
 */
public class Clock {

	private int			time_left;
	private Player	player;

	public Clock() {
		this.init(time_left);
	}

	Clock(int time) {
		this.init(time);
	}

	/**
	 * Method to init clock with given value
	 * 
	 * @param time
	 *          tell method with how much time init clock
	 */
	public void init(int time) {
		this.time_left = time;
	}

	/**
	 * Method to decrement value of left time
	 * 
	 * @return bool true if time_left > 0, else returns false
	 */
	public boolean decrement() {
		if (this.time_left > 0) {
			this.time_left = this.time_left - 1;
			return true;
		}
		return false;
	}

	public void pause() {
	}

	/**
	 * Method to get left time in seconds
	 * 
	 * @return Player int integer of seconds
	 */
	public int get_left_time() {
		return this.time_left;
	}

	/**
	 * Method to get player (owner of this clock)
	 * 
	 * @param player
	 *          player to set as owner of clock
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Method to get player (owner of this clock)
	 * 
	 * @return Reference to player class object
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Method to prepare time in nice looking String
	 * 
	 * @return String of actual left game time with ':' digits in mm:ss format
	 */
	public String prepareString() {
		String strMin = new String();
		Integer time_min = new Integer(this.get_left_time() / 60);
		Integer time_sec = new Integer(this.get_left_time() % 60);
		if (time_min < 10) {// prepare MINUTES
			strMin = "0" + time_min.toString(); //$NON-NLS-1$
		} else {
			strMin = time_min.toString();
		}
		String result = new String(strMin + ":"); //$NON-NLS-1$
		if (time_sec < 10) {// prepare SECONDS
			result = result + "0" + time_sec.toString(); //$NON-NLS-1$
		} else {
			result = result + time_sec.toString();
		}

		return result;
	}
}