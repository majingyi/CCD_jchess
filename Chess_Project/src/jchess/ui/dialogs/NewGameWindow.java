package jchess.ui.dialogs;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jchess.ui.lang.Language;

public class NewGameWindow extends JDialog {

	private static final long				serialVersionUID				= -8079964841143543672L;

	private javax.swing.JTabbedPane	localSettingsTabbedPane	= null;

	public NewGameWindow() {
		initComponents();

		setName("NewGameDialog"); //$NON-NLS-1$
		setTitle(Language.getString("NewGameWindow.0")); //$NON-NLS-1$
		setSize(400, 700);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setAlwaysOnTop(true);

		this.localSettingsTabbedPane.addTab(Language.getString("local_game"), new LocalSettingsTab(this)); //$NON-NLS-1$
	}

	private void initComponents() {

		JPanel windowPanel = new JPanel();
		windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.PAGE_AXIS));

		localSettingsTabbedPane = new javax.swing.JTabbedPane();
		localSettingsTabbedPane.setName("localSettingsTab"); //$NON-NLS-1$
		windowPanel.add(localSettingsTabbedPane);

		JButton okButton = new JButton("OK");
		windowPanel.add(okButton);

		add(windowPanel);

		pack();
	}
	// private void okButtonPressed() {
	// if ((this.firstName.getText().length() == 0 ||
	// this.secondName.getText().length() == 0)) {
	//			JOptionPane.showMessageDialog(this, Language.getString("fill_names")); //$NON-NLS-1$
	// return;
	// }
	//
	// try {
	// Settings.setWhitePlayersName(firstName.getText());
	// Settings.setBlackPlayersName(secondName.getText());
	// } catch (Exception exc) {
	// // TODO show to user
	// Logging.log(exc);
	// }
	//
	// GameTab newGUI = null;
	// try {
	// newGUI = JChessView.getInstance().addNewTab(this.firstName.getText() +
	// Language.getString("DrawLocalSettings.16") + this.secondName.getText());
	// } catch (Exception e2) {
	// Logging.log(e2);
	// }
	//
	// if (this.timeGame.isSelected()) // if timeGame is checked
	// {
	// String value = this.times[this.time4Game.getSelectedIndex()];// set time
	// // for
	// // game
	// Integer val = new Integer(value);
	// Settings.setTimeLimetSet(true);
	// try {
	// Settings.setTimeForGame((int) val * 60);
	// } catch (Exception e1) {
	// Logging.log(e1);// Should never happen, because user can only chose
	// // between correct values. IGNORE Exception
	// }
	// }
	// Logging.log(this.time4Game.getActionCommand());
	// // this.time4Game.getComponent(this.time4Game.getSelectedIndex());
	// Logging
	//				.log(Language.getString("DrawLocalSettings.18") + Settings.getWhitePlayersName() + Language.getString("DrawLocalSettings.19") + Settings.getBlackPlayersName() + Language.getString("DrawLocalSettings.20") + Settings.getTimeForGame() + Language.getString("DrawLocalSettings.21") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	//						+ Settings.getTimeForGame() + Language.getString("DrawLocalSettings.23"));// 4test //$NON-NLS-1$ //$NON-NLS-2$
	// try {
	// newGUI.newGame();
	// } catch (Exception e1) {
	// Logging.log(e1);
	// }// start new Game
	//
	// setVisible(false);// hide parent
	// newGUI.getChessboardUI().repaint();
	// }
}