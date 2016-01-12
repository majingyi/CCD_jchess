package jchess.core.util;

import java.io.Serializable;

/**
 * Class representing one player in the game.
 * 
 * @author Maurice
 * 
 */
public class Player implements Serializable {

	private static final long	serialVersionUID	= 8990270306651014243L;

	private String						name							= null;
	private PlayerColor				color							= null;
	private boolean						goDown						= false;

	public enum PlayerColor {
		WHITE, BLACK, RED
	}

	public Player(String name, PlayerColor color) {
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

	public PlayerColor getColor() {
		return color;
	}

	public void setColor(PlayerColor color) {
		this.color = color;
	}

	public boolean isGoDown() {
		return goDown;
	}

	public void setGoDown(boolean goDown) {
		this.goDown = goDown;
	}
}