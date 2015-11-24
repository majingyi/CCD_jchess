package jchess.ui;

import java.io.FileNotFoundException;

import jchess.ui.lang.Language;

import org.jdesktop.application.Action;

public class JChessAboutBox extends javax.swing.JDialog {

	private javax.swing.JButton	closeButton				= null;

	private static final long		serialVersionUID	= 1604980974366922289L;

	public JChessAboutBox(java.awt.Frame parent) throws FileNotFoundException {
		super(parent);
		initComponents();
		getRootPane().setDefaultButton(closeButton);
	}

	@Action
	public void closeAboutBox() {
		dispose();
	}

	private void initComponents() throws FileNotFoundException {

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
		setTitle(Language.getString("title")); // NOI18N //$NON-NLS-1$
		setModal(true);
		setName("aboutBox"); // NOI18N //$NON-NLS-1$
		setResizable(false);

		javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(jchess.JChessApp.class).getContext()
				.getActionMap(JChessAboutBox.class, this);
		closeButton.setAction(actionMap.get("closeAboutBox")); // NOI18N //$NON-NLS-1$
		closeButton.setName("closeButton"); // NOI18N //$NON-NLS-1$

		appTitleLabel.setFont(appTitleLabel.getFont().deriveFont(appTitleLabel.getFont().getStyle() | java.awt.Font.BOLD, appTitleLabel.getFont().getSize() + 4));
		appTitleLabel.setText(Language.getString("Application.title")); // NOI18N //$NON-NLS-1$
		appTitleLabel.setName("appTitleLabel"); // NOI18N //$NON-NLS-1$

		versionLabel.setFont(versionLabel.getFont().deriveFont(versionLabel.getFont().getStyle() | java.awt.Font.BOLD));
		versionLabel.setText(Language.getString("versionLabel.text")); // NOI18N //$NON-NLS-1$
		versionLabel.setName("versionLabel"); // NOI18N //$NON-NLS-1$

		appVersionLabel.setText(Language.getString("Application.version")); // NOI18N //$NON-NLS-1$
		appVersionLabel.setName("appVersionLabel"); // NOI18N //$NON-NLS-1$

		homepageLabel.setFont(homepageLabel.getFont().deriveFont(homepageLabel.getFont().getStyle() | java.awt.Font.BOLD));
		homepageLabel.setText(Language.getString("homepageLabel.text")); // NOI18N //$NON-NLS-1$
		homepageLabel.setName("homepageLabel"); // NOI18N //$NON-NLS-1$

		appHomepageLabel.setText(Language.getString("Application.homepage")); // NOI18N //$NON-NLS-1$
		appHomepageLabel.setName("appHomepageLabel"); // NOI18N //$NON-NLS-1$

		appDescLabel.setText(Language.getString("appDescLabel.text")); // NOI18N //$NON-NLS-1$
		appDescLabel.setName("appDescLabel"); // NOI18N //$NON-NLS-1$

		imageLabel.setIcon(ImageFactory.getIcon("imageLabel.icon")); // NOI18N //$NON-NLS-1$
		imageLabel.setName("imageLabel"); // NOI18N //$NON-NLS-1$

		vendorLabel1.setFont(vendorLabel1.getFont().deriveFont(vendorLabel1.getFont().getStyle() | java.awt.Font.BOLD));
		vendorLabel1.setText(Language.getString("vendorLabel1.text")); // NOI18N //$NON-NLS-1$
		vendorLabel1.setName("vendorLabel1"); // NOI18N //$NON-NLS-1$

		appVendorLabel1.setName("appVendorLabel1"); // NOI18N //$NON-NLS-1$

		appHomepageLabel1.setText(Language.getString("appHomepageLabel1.text")); // NOI18N //$NON-NLS-1$
		appHomepageLabel1.setName("appHomepageLabel1"); // NOI18N //$NON-NLS-1$

		appHomepageLabel2.setText(Language.getString("appHomepageLabel2.text")); // NOI18N //$NON-NLS-1$
		appHomepageLabel2.setName("appHomepageLabel2"); // NOI18N //$NON-NLS-1$

		vendorLabel2.setFont(vendorLabel2.getFont().deriveFont(vendorLabel2.getFont().getStyle() | java.awt.Font.BOLD));
		vendorLabel2.setText(Language.getString("vendorLabel2.text")); // NOI18N //$NON-NLS-1$
		vendorLabel2.setName("vendorLabel2"); // NOI18N //$NON-NLS-1$

		appHomepageLabel3.setText(Language.getString("appHomepageLabel3.text")); // NOI18N //$NON-NLS-1$
		appHomepageLabel3.setName("appHomepageLabel3"); // NOI18N //$NON-NLS-1$

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
	}

}