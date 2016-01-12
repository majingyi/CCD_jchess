package jchess.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import jchess.core.util.Logging;
import jchess.core.util.Player.PlayerColor;
import jchess.core.util.Settings;
import jchess.ui.GameTab;
import jchess.ui.JChessView;
import jchess.ui.lang.Language;

public class NewGameWindow extends JDialog {

	private static final long	serialVersionUID	= -8079964841143543672L;
	private LocalSettingsTab	localSettingsTab	= null;

	public NewGameWindow() {
		initComponents();
		setSize(400, 700);

		setName("NewGameDialog"); //$NON-NLS-1$
		setTitle(Language.getString("NewGameWindow.0")); //$NON-NLS-1$
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setAlwaysOnTop(true);
		setResizable(false);
	}

	private void initComponents() {

		JPanel windowPanel = new JPanel();
		windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.PAGE_AXIS));

		JTabbedPane localSettingsTabbedPane = new javax.swing.JTabbedPane();
		localSettingsTabbedPane.setName("localSettingsTab"); //$NON-NLS-1$

		JPanel tabs = new JPanel();
		tabs.setLayout(new BoxLayout(tabs, BoxLayout.LINE_AXIS));

		windowPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		windowPanel.add(tabs);

		tabs.add(Box.createRigidArea(new Dimension(5, 0)));
		tabs.add(localSettingsTabbedPane);
		tabs.add(Box.createRigidArea(new Dimension(5, 0)));

		localSettingsTab = new LocalSettingsTab(this);
		localSettingsTab.setLayout(new BoxLayout(localSettingsTab, BoxLayout.PAGE_AXIS));

		localSettingsTabbedPane.addTab(Language.getString("local_game"), localSettingsTab); //$NON-NLS-1$

		createButtonArea(windowPanel);

		add(windowPanel);
		pack();
	}

	private void createButtonArea(JPanel windowPanel) {

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				okButtonPressed();
			}
		});
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPanel.add(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				// DO nothing, just close the dialog
				setVisible(false);
			}
		});

		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPanel.add(cancelButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		windowPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		windowPanel.add(buttonPanel, BorderLayout.LINE_END);
		windowPanel.add(Box.createRigidArea(new Dimension(0, 5)));
	}

	private void okButtonPressed() {
		if (localSettingsTab.areSettingsValid() == false) {
			JOptionPane.showMessageDialog(this, Language.getString("fill_names")); //$NON-NLS-1$
			return;
		}

		try {
			Settings.addPlayerName(localSettingsTab.getPlayerName(PlayerColor.WHITE), PlayerColor.WHITE);
			Settings.addPlayerName(localSettingsTab.getPlayerName(PlayerColor.BLACK), PlayerColor.BLACK);
			Settings.addPlayerName(localSettingsTab.getPlayerName(PlayerColor.RED), PlayerColor.RED);

			GameTab newGUI = JChessView.getInstance().addNewTab(
					MessageFormat.format("{0} vs {1} vs {2}", localSettingsTab.getPlayerName(PlayerColor.WHITE), localSettingsTab.getPlayerName(PlayerColor.RED),
							localSettingsTab.getPlayerName(PlayerColor.BLACK)));

			if (localSettingsTab.isTimeLimitSet()) // if timeGame is checked
			{
				Settings.setTimeLimitSet(true);
				try {
					Settings.setTimeForGame((int) localSettingsTab.getSelectedTimeLimit() * 60);
				} catch (Exception e) {
					Logging.log(e);
					/*
					 * Should never happen, because user can only chose between correct
					 * values. IGNORE Exception
					 */
				}
			}

			newGUI.newGame();

			/*
			 * Hide dialog, if everything worked as expected, leave dialog open
			 * otherwise.
			 */
			setVisible(false);
			newGUI.getChessboardUI().repaint();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error occurred:\n" + exc.getMessage()); //$NON-NLS-1$
			Logging.log(exc);
		}
	}
}