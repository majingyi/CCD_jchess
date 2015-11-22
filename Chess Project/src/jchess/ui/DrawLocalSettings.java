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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import jchess.JChessApp;
import jchess.core.util.Logging;
import jchess.core.util.Settings;
import jchess.ui.lang.Language;

/**
 * Class responsible for drawing the fold with local game settings
 */
public class DrawLocalSettings extends JPanel implements ActionListener, TextListener {

	private static final long		serialVersionUID	= -3054704162643076714L;

	private JDialog							parent;
	private JFrame							localPanel;
	private JTextField					firstName;
	private JTextField					secondName;
	private JLabel							firstNameLab;
	private JLabel							secondNameLab;

	private GridBagLayout				gbl;
	private GridBagConstraints	gbc;
	private Container						cont;
	private JSeparator					sep;
	private JButton							okButton;
	private JCheckBox						timeGame;
	private JComboBox<String>		time4Game;
	private String							colors[]					= { Language.getString("white"), Language.getString("black") };			//$NON-NLS-1$ //$NON-NLS-2$
	private String							times[]						= { "1", "3", "5", "8", "10", "15", "20", "25", "30", "60", "120" };	//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$

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

		if (target == this.okButton) // if clicked OK button (on finish)
		{
			if (this.firstName.getText().length() > 9) {// make names short to 10
																									// digits
				this.firstName.setText(this.trimString(firstName, 9));
			}
			if (this.secondName.getText().length() > 9) {// make names short to 10
																										// digits
				this.secondName.setText(this.trimString(secondName, 9));
			}
			if ((this.firstName.getText().length() == 0 || this.secondName.getText().length() == 0)) {
				JOptionPane.showMessageDialog(this, Language.getString("fill_names")); //$NON-NLS-1$
				return;
			}

			try {
				Settings.setWhitePlayersName(firstName.getText());
				Settings.setBlackPlayersName(secondName.getText());
			} catch (Exception exc) {
				// TODO show to user
				Logging.log(exc);
			}

			GameTab newGUI = null;
			try {
				newGUI = JChessApp.jcv.addNewTab(this.firstName.getText() + Language.getString("DrawLocalSettings.16") + this.secondName.getText());
			} catch (Exception e2) {
				Logging.log(e2);
			}

			if (this.timeGame.isSelected()) // if timeGame is checked
			{
				String value = this.times[this.time4Game.getSelectedIndex()];// set time
																																			// for
																																			// game
				Integer val = new Integer(value);
				Settings.setTimeLimetSet(true);
				try {
					Settings.setTimeForGame((int) val * 60);
				} catch (Exception e1) {
					Logging.log(e1);// Should never happen, because user can only chose
													// between correct values. IGNORE Exception
				}
			}
			Logging.log(this.time4Game.getActionCommand());
			// this.time4Game.getComponent(this.time4Game.getSelectedIndex());
			Logging
					.log(Language.getString("DrawLocalSettings.18") + Settings.getWhitePlayersName() + Language.getString("DrawLocalSettings.19") + Settings.getBlackPlayersName() + Language.getString("DrawLocalSettings.20") + Settings.getTimeForGame() + Language.getString("DrawLocalSettings.21") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
							+ Settings.getTimeForGame() + Language.getString("DrawLocalSettings.23"));// 4test //$NON-NLS-1$ //$NON-NLS-2$
			try {
				newGUI.newGame();
			} catch (Exception e1) {
				Logging.log(e1);
			}// start new Game
			this.parent.setVisible(false);// hide parent
			newGUI.getChessboardUI().repaint();
			// newGUI.getChessboardUI().draw();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	DrawLocalSettings(JDialog parent) {
		super();

		this.parent = parent;
		this.gbl = new GridBagLayout();
		this.gbc = new GridBagConstraints();
		this.sep = new JSeparator();
		this.okButton = new JButton(Language.getString(Language.getString("DrawLocalSettings.24"))); //$NON-NLS-1$

		this.firstName = new JTextField(Language.getString("DrawLocalSettings.26"), 10); //$NON-NLS-1$
		this.firstName.setSize(new Dimension(200, 50));
		this.secondName = new JTextField(Language.getString("DrawLocalSettings.27"), 10); //$NON-NLS-1$
		this.secondName.setSize(new Dimension(200, 50));
		this.firstNameLab = new JLabel(Language.getString("first_player_name") + ": "); //$NON-NLS-1$ //$NON-NLS-2$
		this.secondNameLab = new JLabel(Language.getString("second_player_name") + ": "); //$NON-NLS-1$ //$NON-NLS-2$

		this.timeGame = new JCheckBox(Language.getString("time_game_min")); //$NON-NLS-1$
		this.time4Game = new JComboBox(times);

		this.setLayout(gbl);
		this.okButton.addActionListener(this);

		this.secondName.addActionListener(this);

		this.gbc.gridx = 0;
		this.gbc.gridy = 0;
		this.gbc.insets = new Insets(3, 3, 3, 3);
		this.gbc.gridx = 0;
		this.gbc.gridy = 1;
		this.gbl.setConstraints(firstNameLab, gbc);
		this.add(firstNameLab);
		this.gbc.gridx = 0;
		this.gbc.gridy = 2;
		this.gbl.setConstraints(firstName, gbc);
		this.add(firstName);
		this.gbc.gridx = 0;
		this.gbc.gridy = 3;
		this.gbl.setConstraints(secondNameLab, gbc);
		this.add(secondNameLab);
		this.gbc.gridy = 4;
		this.gbl.setConstraints(secondName, gbc);
		this.add(secondName);
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