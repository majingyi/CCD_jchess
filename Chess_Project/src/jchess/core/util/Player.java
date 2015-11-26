package jchess.core.util;

import java.io.Serializable;

/**
 * Class representing the player in the game
 * 
 */
public class Player implements Serializable {

	private static final long	serialVersionUID	= 8990270306651014243L;

	private String						name							= null;
	private colors						color							= null;
	private boolean						goDown						= false;

	public enum colors {
		white, black, red
	}

	public Player() {
	}

	public Player(String name, colors color) {
		this.name = name;
		this.setColor(color);
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

	public colors getColor() {
		return color;
	}

	public void setColor(colors color) {
		this.color = color;
	}

	public boolean isGoDown() {
		return goDown;
	}

	public void setGoDown(boolean goDown) {
		this.goDown = goDown;
	}
}