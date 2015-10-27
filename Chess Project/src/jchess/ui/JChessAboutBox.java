/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jchess.ui;

import jchess.Settings;

import org.jdesktop.application.Action;

public class JChessAboutBox extends javax.swing.JDialog {

	private static final long	serialVersionUID	= 1604980974366922289L;

	public JChessAboutBox(java.awt.Frame parent) {
		super(parent);
		initComponents();
		getRootPane().setDefaultButton(closeButton);
	}

	@Action
	public void closeAboutBox() {
		dispose();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		closeButton = new javax.swing.JButton();
		javax.swing.JLabel appTitleLabel = new javax.swing.JLabel();
		javax.swing.JLabel versionLabel = new javax.swing.JLabel();
		javax.swing.JLabel appVersionLabel = new javax.swing.JLabel();
		javax.swing.JLabel homepageLabel = new javax.swing.JLabel();
		javax.swing.JLabel appHomepageLabel = new javax.swing.JLabel();
		javax.swing.JLabel appDescLabel = new javax.swing.JLabel();
		javax.swing.JLabel imageLabel = new javax.swing.JLabel();
		javax.swing.JLabel vendorLabel1 = new javax.swing.JLabel();
		javax.swing.JLabel appVendorLabel1 = new javax.swing.JLabel();
		javax.swing.JLabel appHomepageLabel1 = new javax.swing.JLabel();
		javax.swing.JLabel appHomepageLabel2 = new javax.swing.JLabel();
		javax.swing.JLabel vendorLabel2 = new javax.swing.JLabel();
		javax.swing.JLabel appHomepageLabel3 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(Settings.lang("title")); // NOI18N
		setModal(true);
		setName("aboutBox"); // NOI18N
		setResizable(false);

		javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(jchess.JChessApp.class).getContext()
				.getActionMap(JChessAboutBox.class, this);
		closeButton.setAction(actionMap.get("closeAboutBox")); // NOI18N
		closeButton.setName("closeButton"); // NOI18N

		appTitleLabel.setFont(appTitleLabel.getFont().deriveFont(appTitleLabel.getFont().getStyle() | java.awt.Font.BOLD, appTitleLabel.getFont().getSize() + 4));
		appTitleLabel.setText(Settings.lang("Application.title")); // NOI18N
		appTitleLabel.setName("appTitleLabel"); // NOI18N

		versionLabel.setFont(versionLabel.getFont().deriveFont(versionLabel.getFont().getStyle() | java.awt.Font.BOLD));
		versionLabel.setText(Settings.lang("versionLabel.text")); // NOI18N
		versionLabel.setName("versionLabel"); // NOI18N

		appVersionLabel.setText(Settings.lang("Application.version")); // NOI18N
		appVersionLabel.setName("appVersionLabel"); // NOI18N

		homepageLabel.setFont(homepageLabel.getFont().deriveFont(homepageLabel.getFont().getStyle() | java.awt.Font.BOLD));
		homepageLabel.setText(Settings.lang("homepageLabel.text")); // NOI18N
		homepageLabel.setName("homepageLabel"); // NOI18N

		appHomepageLabel.setText(Settings.lang("Application.homepage")); // NOI18N
		appHomepageLabel.setName("appHomepageLabel"); // NOI18N

		appDescLabel.setText(Settings.lang("appDescLabel.text")); // NOI18N
		appDescLabel.setName("appDescLabel"); // NOI18N

		imageLabel.setIcon(Settings.getIcon("imageLabel.icon")); // NOI18N
		imageLabel.setName("imageLabel"); // NOI18N

		vendorLabel1.setFont(vendorLabel1.getFont().deriveFont(vendorLabel1.getFont().getStyle() | java.awt.Font.BOLD));
		vendorLabel1.setText(Settings.lang("vendorLabel1.text")); // NOI18N
		vendorLabel1.setName("vendorLabel1"); // NOI18N

		appVendorLabel1.setName("appVendorLabel1"); // NOI18N

		appHomepageLabel1.setText(Settings.lang("appHomepageLabel1.text")); // NOI18N
		appHomepageLabel1.setName("appHomepageLabel1"); // NOI18N

		appHomepageLabel2.setText(Settings.lang("appHomepageLabel2.text")); // NOI18N
		appHomepageLabel2.setName("appHomepageLabel2"); // NOI18N

		vendorLabel2.setFont(vendorLabel2.getFont().deriveFont(vendorLabel2.getFont().getStyle() | java.awt.Font.BOLD));
		vendorLabel2.setText(Settings.lang("vendorLabel2.text")); // NOI18N
		vendorLabel2.setName("vendorLabel2"); // NOI18N

		appHomepageLabel3.setText(Settings.lang("appHomepageLabel3.text")); // NOI18N
		appHomepageLabel3.setName("appHomepageLabel3"); // NOI18N

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout
						.createSequentialGroup()
						.addComponent(imageLabel)
						.addGap(18, 18, 18)
						.addGroup(
								layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(appTitleLabel, javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(appDescLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
										.addGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												layout
														.createSequentialGroup()
														.addGroup(
																layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(versionLabel).addComponent(homepageLabel)
																		.addComponent(vendorLabel1).addComponent(vendorLabel2))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(
																layout
																		.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addGroup(
																				layout
																						.createSequentialGroup()
																						.addGroup(
																								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(appHomepageLabel1)
																										.addComponent(appHomepageLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE))
																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(closeButton))
																		.addComponent(appVersionLabel)
																		.addGroup(
																				layout
																						.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
																						.addComponent(appVendorLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																						.addComponent(appHomepageLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																						.addComponent(appHomepageLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE))))).addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 194, Short.MAX_VALUE)
				.addGroup(
						layout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(appTitleLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(appDescLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(versionLabel).addComponent(appVersionLabel))
								.addGap(27, 27, 27)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(homepageLabel).addComponent(appHomepageLabel))
								.addGroup(
										layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
																.addComponent(closeButton).addContainerGap())
												.addGroup(
														layout
																.createSequentialGroup()
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(vendorLabel1)
																				.addComponent(appVendorLabel1).addComponent(appHomepageLabel2))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout
																				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(appHomepageLabel1)
																				.addGroup(
																						layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(vendorLabel2)
																								.addComponent(appHomepageLabel3))).addGap(36, 36, 36)))));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton	closeButton;
	// End of variables declaration//GEN-END:variables

}