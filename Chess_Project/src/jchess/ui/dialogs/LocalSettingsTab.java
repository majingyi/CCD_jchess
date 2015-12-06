package jchess.ui.dialogs;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jchess.core.util.Constants;
import jchess.core.util.Player.PlayerColor;
import jchess.ui.lang.Language;

/**
 * Class responsible for drawing the fold with local game settings
 */
public class LocalSettingsTab extends JPanel {

	private static final long			serialVersionUID	= -3054704162643076714L;

	private static final Integer	TIMES[]						= { 1, 3, 5, 8, 10, 15, 20, 25, 30, 60, 120 };

	private JTextField						firstName					= null;
	private JTextField						secondName				= null;
	private JTextField						thirdName					= null;
	private JCheckBox							timeLimit					= null;
	private JComboBox<Integer>		time4Game					= null;

	public LocalSettingsTab(JDialog parent) {
		super();

		JPanel windowPanel = new JPanel();
		windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.PAGE_AXIS));

		JLabel firstNameLab = new JLabel(Language.getString("first_player_name") + ": "); //$NON-NLS-1$ //$NON-NLS-2$
		windowPanel.add(firstNameLab);

		firstName = new JTextField("Player1", 10);
		windowPanel.add(firstName);

		JLabel secondNameLab = new JLabel(Language.getString("second_player_name") + ": "); //$NON-NLS-1$ //$NON-NLS-2$
		windowPanel.add(secondNameLab);

		secondName = new JTextField("Player2", 10);
		windowPanel.add(secondName);

		JLabel thirdNameLab = new JLabel(Language.getString("third_player_name") + ": "); //$NON-NLS-1$ //$NON-NLS-2$
		windowPanel.add(thirdNameLab);

		thirdName = new JTextField("Player3", 10);
		windowPanel.add(thirdName);

		timeLimit = new JCheckBox(Language.getString("time_game_min")); //$NON-NLS-1$
		windowPanel.add(timeLimit);

		time4Game = new JComboBox<Integer>(TIMES);
		windowPanel.add(time4Game);

		add(windowPanel);
	}

	public String getPlayerName(PlayerColor color) {
		String result = Constants.EMPTY_STRING;
		switch (color) {
			case BLACK:
				result = thirdName.getText();
				break;
			case WHITE:
				result = firstName.getText();
				break;
			case RED:
				result = secondName.getText();
				break;

			default:
				break;
		}

		return result;
	}

	public boolean isTimeLimitSet() {
		return timeLimit.isSelected();
	}

	public int getSelectedTimeLimit() {
		return (Integer) time4Game.getSelectedItem();
	}

	/**
	 * @return true if all players have a name set
	 */
	public boolean areSettingsValid() {
		return (firstName.getText().length() > 0) && (secondName.getText().length() > 0) && (thirdName.getText().length() > 0);
	}
}