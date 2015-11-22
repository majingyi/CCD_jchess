package jchess.core.util;

import java.io.Serializable;

/**
 * Class representing the player in the game
 * 
 * TODO drop up and dwon, not applicable for three player chess
 */
public class Player implements Serializable {

	private static final long	serialVersionUID	= 8990270306651014243L;

	public String							name							= null;

	public enum colors {
		white, black, gray
	}

	public colors	color;

	// TODO drop ki
	public enum playerTypes {
		localUser, computer
	}

	public playerTypes	playerType;
	public boolean			goDown;

	public Player() {
	}

	public Player(String name, colors color) {
		this.name = name;
		this.color = color;
		this.goDown = false;
	}

	/**
	 * Method setting the players name
	 * 
	 * @param name
	 *          name of player
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method getting the players name
	 * 
	 * @return name of player
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Method setting the players type
	 * 
	 * @param type
	 *          type of player - enumerate
	 */
	public void setType(playerTypes type) {
		this.playerType = type;
	}
}
