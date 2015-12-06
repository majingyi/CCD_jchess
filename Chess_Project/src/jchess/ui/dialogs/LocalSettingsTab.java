package jchess.ui.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jchess.ui.lang.Language;

/**
 * Class responsible for drawing the fold with local game settings
 */
public class LocalSettingsTab extends JPanel {

	private static final long			serialVersionUID	= -3054704162643076714L;

	private static final Integer	TIMES[]						= { 1, 3, 5, 8, 10, 15, 20, 25, 30, 60, 120 };

	public LocalSettingsTab(JDialog parent) {
		super();

		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3, 3, 3, 3);
		gbc.gridx = 0;
		gbc.gridy = 1;

		JLabel firstNameLab = new JLabel(Language.getString("first_player_name") + ": "); //$NON-NLS-1$ //$NON-NLS-2$
		gbl.setConstraints(firstNameLab, gbc);
		add(firstNameLab);

		gbc.gridx = 0;
		gbc.gridy = 2;
		JTextField firstName = new JTextField(Language.getString("DrawLocalSettings.26"), 10); //$NON-NLS-1$
		gbl.setConstraints(firstName, gbc);
		add(firstName);

		gbc.gridx = 0;
		gbc.gridy = 3;
		JLabel secondNameLab = new JLabel(Language.getString("second_player_name") + ": "); //$NON-NLS-1$ //$NON-NLS-2$
		gbl.setConstraints(secondNameLab, gbc);
		add(secondNameLab);

		gbc.gridy = 4;
		JTextField secondName = new JTextField(Language.getString("DrawLocalSettings.27"), 10); //$NON-NLS-1$
		gbl.setConstraints(secondName, gbc);
		add(secondName);

		gbc.gridy = 8;
		gbc.gridwidth = 1;
		JCheckBox timeGame = new JCheckBox(Language.getString("time_game_min")); //$NON-NLS-1$
		gbl.setConstraints(timeGame, gbc);
		add(timeGame);

		gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.gridwidth = 1;
		JComboBox<Integer> time4Game = new JComboBox<Integer>(TIMES);
		gbl.setConstraints(time4Game, gbc);
		add(time4Game);
	}
}