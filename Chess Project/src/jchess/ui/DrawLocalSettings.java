package jchess.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.FileNotFoundException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import jchess.JChessApp;
import jchess.core.util.Logging;
import jchess.core.util.Player;
import jchess.core.util.Settings;
import jchess.ui.lang.Language;

/**
 * Class responsible for drawing the fold with local game settings
 */
public class DrawLocalSettings extends JPanel implements ActionListener, TextListener {

	private static final long	serialVersionUID	= -3054704162643076714L;

	JDialog										parent;																																								// needet
																																																										// to
																																																										// close
																																																										// newGame
																																																										// window
	JComboBox<Player.colors>	color;																																									// to
	// choose
	// color
	// of
	// player
	JRadioButton							oponentComp;																																						// choose
																																																										// oponent
	JRadioButton							oponentHuman;																																					// choose
																																																										// oponent
																																																										// (human)
	ButtonGroup								oponentChoos;																																					// group
																																																										// 4
																																																										// radio
																																																										// buttons
	JFrame										localPanel;
	JLabel										compLevLab;
	JSlider										computerLevel;																																					// slider
																																																										// to
																																																										// choose
																																																										// jChess
																																																										// Engine
																																																										// level
	JTextField								firstName;																																							// editable
																																																										// field
																																																										// 4
																																																										// nickname
	JTextField								secondName;																																						// editable
																																																										// field
																																																										// 4
																																																										// nickname
	JLabel										firstNameLab;
	JLabel										secondNameLab;
	JCheckBox									upsideDown;																																						// if
																																																										// true
																																																										// draw
																																																										// chessboard
																																																										// upsideDown(white
																																																										// on
																																																										// top)
	GridBagLayout							gbl;
	GridBagConstraints				gbc;
	Container									cont;
	JSeparator								sep;
	JButton										okButton;
	JCheckBox									timeGame;
	JComboBox<String>					time4Game;
	String										colors[]					= { Language.getString("white"), Language.getString("black") };			//$NON-NLS-1$ //$NON-NLS-2$
	String										times[]						= { "1", "3", "5", "8", "10", "15", "20", "25", "30", "60", "120" };	//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$

																																																										;

	/**
	 * Method witch is checking correction of edit tables
	 * 
	 * @param e
	 *          Object where is saving this what contents edit tables
	 */
	public void textValueChanged(TextEvent e) {
		Object target = e.getSource();
		if (target == this.firstName || target == this.secondName) {
			JTextField temp = new JTextField();
			if (target == this.firstName) {
				temp = this.firstName;
			} else if (target == this.secondName) {
				temp = this.secondName;
			}

			int len = temp.getText().length();
			if (len > 8) {
				try {
					temp.setText(temp.getText(0, 7));
				} catch (BadLocationException exc) {
					Logging.log(Language.getString("DrawLocalSettings.13"), exc); //$NON-NLS-1$
				}
			}
		}
	}

	/**
	 * Method responsible for changing the options which can make a player when he
	 * want to start new local game
	 * 
	 * @param e
	 *          where is saving data of performed action
	 */
	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();
		if (target == this.oponentComp) // toggle enabled of controls depends of
																		// oponent (if computer)
		{
			this.computerLevel.setEnabled(true);// enable level of computer abilities
			this.secondName.setEnabled(false);// disable field with name of player2
		} else if (target == this.oponentHuman) // else if oponent will be HUMAN
		{
			this.computerLevel.setEnabled(false);// disable level of computer
																						// abilities
			this.secondName.setEnabled(true);// enable field with name of player2
		} else if (target == this.okButton) // if clicked OK button (on finish)
		{
			if (this.firstName.getText().length() > 9) {// make names short to 10
																									// digits
				this.firstName.setText(this.trimString(firstName, 9));
			}
			if (this.secondName.getText().length() > 9) {// make names short to 10
																										// digits
				this.secondName.setText(this.trimString(secondName, 9));
			}
			if (!this.oponentComp.isSelected() && (this.firstName.getText().length() == 0 || this.secondName.getText().length() == 0)) {
				JOptionPane.showMessageDialog(this, Language.getString("fill_names")); //$NON-NLS-1$
				return;
			}
			if ((this.oponentComp.isSelected() && this.firstName.getText().length() == 0)) {
				JOptionPane.showMessageDialog(this, Language.getString("fill_name")); //$NON-NLS-1$
				return;
			}
			Game newGUI = null;
			try {
				newGUI = JChessApp.jcv.addNewTab(this.firstName.getText() + Language.getString("DrawLocalSettings.16") + this.secondName.getText());
			} catch (FileNotFoundException e2) {
				Logging.log(e2);
			}
			Settings sett = newGUI.settings;// sett local settings variable
			Player pl1 = sett.playerWhite;// set local player variable
			Player pl2 = sett.playerBlack;// set local player variable
			sett.gameMode = Settings.gameModes.newGame;
			// if(this.firstName.getText().length() >9 )
			// this.firstName.setText(this.firstName.getText(0,8));
			if (this.color.getActionCommand().equals("biały")) // if first player is //$NON-NLS-1$
																													// white
			{
				pl1.setName(this.firstName.getText());// set name of player
				pl2.setName(this.secondName.getText());// set name of player
			} else // else change names
			{
				pl2.setName(this.firstName.getText());// set name of player
				pl1.setName(this.secondName.getText());// set name of player
			}
			pl1.setType(Player.playerTypes.localUser);// set type of player
			pl2.setType(Player.playerTypes.localUser);// set type of player
			sett.gameType = Settings.gameTypes.local;
			if (this.oponentComp.isSelected()) // if computer oponent is checked
			{
				pl2.setType(Player.playerTypes.computer);
			}
			if (this.upsideDown.isSelected()) // if upsideDown is checked
			{
				sett.setUpsideDown(true);
			} else {
				sett.setUpsideDown(false);
			}
			if (this.timeGame.isSelected()) // if timeGame is checked
			{
				String value = this.times[this.time4Game.getSelectedIndex()];// set time
																																			// for
																																			// game
				Integer val = new Integer(value);
				sett.setTimeLimetSet(true);
				sett.setTimeForGame((int) val * 60);// set time for game and mult it to
				// seconds
				newGUI.gameClock.setTime(sett.getTimeForGame());
				newGUI.gameClock.start();
			}
			Logging.log(this.time4Game.getActionCommand());
			// this.time4Game.getComponent(this.time4Game.getSelectedIndex());
			Logging
					.log(Language.getString("DrawLocalSettings.18") + pl1.name + Language.getString("DrawLocalSettings.19") + pl2.name + Language.getString("DrawLocalSettings.20") + sett.getTimeForGame() + Language.getString("DrawLocalSettings.21") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
							+ sett.getTimeForGame() + Language.getString("DrawLocalSettings.22") + sett.isUpsideDown() + Language.getString("DrawLocalSettings.23"));// 4test //$NON-NLS-1$ //$NON-NLS-2$
			try {
				newGUI.newGame();
			} catch (Exception e1) {
				Logging.log(e1);
			}// start new Game
			this.parent.setVisible(false);// hide parent
			newGUI.chessboard.repaint();
			newGUI.chessboard.draw();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	DrawLocalSettings(JDialog parent) {
		super();

		this.parent = parent;
		this.color = new JComboBox(colors);
		this.gbl = new GridBagLayout();
		this.gbc = new GridBagConstraints();
		this.sep = new JSeparator();
		this.okButton = new JButton(Language.getString(Language.getString("DrawLocalSettings.24"))); //$NON-NLS-1$
		this.compLevLab = new JLabel(Language.getString(Language.getString("DrawLocalSettings.25"))); //$NON-NLS-1$

		this.firstName = new JTextField(Language.getString("DrawLocalSettings.26"), 10); //$NON-NLS-1$
		this.firstName.setSize(new Dimension(200, 50));
		this.secondName = new JTextField(Language.getString("DrawLocalSettings.27"), 10); //$NON-NLS-1$
		this.secondName.setSize(new Dimension(200, 50));
		this.firstNameLab = new JLabel(Language.getString("first_player_name") + ": "); //$NON-NLS-1$ //$NON-NLS-2$
		this.secondNameLab = new JLabel(Language.getString("second_player_name") + ": "); //$NON-NLS-1$ //$NON-NLS-2$
		this.oponentChoos = new ButtonGroup();
		this.computerLevel = new JSlider();
		this.upsideDown = new JCheckBox(Language.getString("upside_down")); //$NON-NLS-1$
		this.timeGame = new JCheckBox(Language.getString("time_game_min")); //$NON-NLS-1$
		this.time4Game = new JComboBox(times);

		this.oponentComp = new JRadioButton(Language.getString("against_computer"), false); //$NON-NLS-1$
		this.oponentHuman = new JRadioButton(Language.getString("against_other_human"), true); //$NON-NLS-1$

		this.setLayout(gbl);
		this.oponentComp.addActionListener(this);
		this.oponentHuman.addActionListener(this);
		this.okButton.addActionListener(this);

		this.secondName.addActionListener(this);

		this.oponentChoos.add(oponentComp);
		this.oponentChoos.add(oponentHuman);
		this.computerLevel.setEnabled(false);
		this.computerLevel.setMaximum(3);
		this.computerLevel.setMinimum(1);

		this.gbc.gridx = 0;
		this.gbc.gridy = 0;
		this.gbc.insets = new Insets(3, 3, 3, 3);
		this.gbl.setConstraints(oponentComp, gbc);
		this.add(oponentComp);
		this.gbc.gridx = 1;
		this.gbl.setConstraints(oponentHuman, gbc);
		this.add(oponentHuman);
		this.gbc.gridx = 0;
		this.gbc.gridy = 1;
		this.gbl.setConstraints(firstNameLab, gbc);
		this.add(firstNameLab);
		this.gbc.gridx = 0;
		this.gbc.gridy = 2;
		this.gbl.setConstraints(firstName, gbc);
		this.add(firstName);
		this.gbc.gridx = 1;
		this.gbc.gridy = 2;
		this.gbl.setConstraints(color, gbc);
		this.add(color);
		this.gbc.gridx = 0;
		this.gbc.gridy = 3;
		this.gbl.setConstraints(secondNameLab, gbc);
		this.add(secondNameLab);
		this.gbc.gridy = 4;
		this.gbl.setConstraints(secondName, gbc);
		this.add(secondName);
		this.gbc.gridy = 5;
		this.gbc.insets = new Insets(0, 0, 0, 0);
		this.gbl.setConstraints(compLevLab, gbc);
		this.add(compLevLab);
		this.gbc.gridy = 6;
		this.gbl.setConstraints(computerLevel, gbc);
		this.add(computerLevel);
		this.gbc.gridy = 7;
		this.gbl.setConstraints(upsideDown, gbc);
		this.add(upsideDown);
		this.gbc.gridy = 8;
		this.gbc.gridwidth = 1;
		this.gbl.setConstraints(timeGame, gbc);
		this.add(timeGame);
		this.gbc.gridx = 1;
		this.gbc.gridy = 8;
		this.gbc.gridwidth = 1;
		this.gbl.setConstraints(time4Game, gbc);
		this.add(time4Game);
		this.gbc.gridx = 1;
		this.gbc.gridy = 9;
		this.gbc.gridwidth = 0;
		this.gbl.setConstraints(okButton, gbc);
		this.add(okButton);
		this.oponentComp.setEnabled(false);// for now, becouse not implemented!

	}

	/**
	 * Method responsible for triming white symbols from strings
	 * 
	 * @param txt
	 *          Where is capt value to equal
	 * @param length
	 *          How long is the string
	 * @return result trimmed String
	 */
	public String trimString(JTextField txt, int length) {
		String result = new String();
		try {
			result = txt.getText(0, length);
		} catch (BadLocationException exc) {
			Logging.log(Language.getString("DrawLocalSettings.36"), exc); //$NON-NLS-1$
		}
		return result;
	}
}