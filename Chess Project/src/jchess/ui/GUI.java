package jchess.ui;


/**
 * Class representing the game interface which is seen by a player and where are
 * located available for player options, current games and where can he start a
 * new game (load it or save it)
 */
public class GUI {

	public Game	game	= null;

	public GUI() throws Exception {
		this.game = new Game();
	}

}
