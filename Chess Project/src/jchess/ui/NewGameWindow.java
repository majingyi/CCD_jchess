package jchess.ui;

import javax.swing.JDialog;
import javax.swing.JFrame;

import jchess.core.Language;

public class NewGameWindow extends JDialog {

	private static final long				serialVersionUID	= -8079964841143543672L;

	private javax.swing.JTabbedPane	jTabbedPane1			= null;

	/** Creates new form NewGameWindow */
	public NewGameWindow() {
		initComponents();

		this.setTitle("New Game");
		this.setSize(400, 700);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.jTabbedPane1.addTab(Language.getString("local_game"), new DrawLocalSettings(this));
	}

	private void initComponents() {
		jTabbedPane1 = new javax.swing.JTabbedPane();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setName("Form"); // NOI18N

		jTabbedPane1.setName("jTabbedPane1"); // NOI18N

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addGap(20, 20, 20).addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
						.addContainerGap()));

		pack();
	}
}