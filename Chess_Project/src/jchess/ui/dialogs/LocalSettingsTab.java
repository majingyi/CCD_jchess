package jchess.ui.dialogs;

import javax.swing.BoxLayout;
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

		JPanel windowPanel = new JPanel();
		windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.PAGE_AXIS));

		JLabel firstNameLab = new JLabel(Language.getString("first_player_name") + ": "); //$NON-NLS-1$ //$NON-NLS-2$
		windowPanel.add(firstNameLab);

		JTextField firstName = new JTextField(Language.getString("DrawLocalSettings.26"), 10); //$NON-NLS-1$
		windowPanel.add(firstName);

		JLabel secondNameLab = new JLabel(Language.getString("second_player_name") + ": "); //$NON-NLS-1$ //$NON-NLS-2$
		windowPanel.add(secondNameLab);

		JTextField secondName = new JTextField(Language.getString("DrawLocalSettings.27"), 10); //$NON-NLS-1$
		windowPanel.add(secondName);

		JCheckBox timeGame = new JCheckBox(Language.getString("time_game_min")); //$NON-NLS-1$
		windowPanel.add(timeGame);

		JComboBox<Integer> time4Game = new JComboBox<Integer>(TIMES);
		windowPanel.add(time4Game);

		add(windowPanel);
	}
}