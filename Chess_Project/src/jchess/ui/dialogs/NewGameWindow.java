package jchess.ui.dialogs;

import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import jchess.ui.lang.Language;

public class NewGameWindow extends JDialog {

	private static final long				serialVersionUID				= -8079964841143543672L;

	private javax.swing.JTabbedPane	localSettingsTabbedPane	= null;

	public NewGameWindow() {
		initComponents();

		this.setTitle(Language.getString("NewGameWindow.0")); //$NON-NLS-1$
		this.setSize(400, 700);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.localSettingsTabbedPane.addTab(Language.getString("local_game"), new LocalSettingsTab(this)); //$NON-NLS-1$
	}

	private void initComponents() {
		localSettingsTabbedPane = new javax.swing.JTabbedPane();
		JButton okButton = new JButton("OK");

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setName("NewGameDialog"); //$NON-NLS-1$

		localSettingsTabbedPane.setName("jTabbedPane1"); //$NON-NLS-1$

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		ParallelGroup parallel = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		SequentialGroup seq = layout.createSequentialGroup();
		seq.addContainerGap();
		seq.addComponent(localSettingsTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE);
		seq.addContainerGap();
		parallel.addGroup(seq);
		parallel.addComponent(okButton, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE);
		layout.setHorizontalGroup(parallel);

		parallel = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		seq = layout.createSequentialGroup();
		seq.addGap(20, 20, 20);
		seq.addComponent(localSettingsTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE);
		seq.addContainerGap();
		seq.addComponent(okButton, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE);
		parallel.addGroup(seq);
		layout.setVerticalGroup(parallel);

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