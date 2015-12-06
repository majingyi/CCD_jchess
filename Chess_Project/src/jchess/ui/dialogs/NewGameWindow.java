package jchess.ui.dialogs;

import javax.swing.JDialog;
import javax.swing.JFrame;

import jchess.ui.lang.Language;

public class NewGameWindow extends JDialog {

	private static final long				serialVersionUID	= -8079964841143543672L;

	private javax.swing.JTabbedPane	jTabbedPane			= null;

	public NewGameWindow() {
		initComponents();

		this.setTitle(Language.getString("NewGameWindow.0")); //$NON-NLS-1$
		this.setSize(400, 700);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.jTabbedPane.addTab(Language.getString("local_game"), new LocalSettingsTab(this)); //$NON-NLS-1$
	}

	private void initComponents() {
		jTabbedPane = new javax.swing.JTabbedPane();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setName("Form"); //$NON-NLS-1$

		jTabbedPane.setName("jTabbedPane1"); //$NON-NLS-1$

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addGap(20, 20, 20).addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
						.addContainerGap()));

		pack();
	}
}