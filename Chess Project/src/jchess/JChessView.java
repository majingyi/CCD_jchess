package jchess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.util.Locale;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import jchess.core.Logging;
import jchess.ui.GUI;
import jchess.ui.Game;
import jchess.ui.JChessAboutBox;
import jchess.ui.LanguageChooseWindow;
import jchess.ui.NewGameWindow;
import jchess.ui.PawnPromotionWindow;
import jchess.ui.ThemeChooseWindow;
import jchess.ui.lang.Language;
import jchess.util.Player;

import org.jdesktop.application.FrameView;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.TaskMonitor;

/**
 * The application's main frame.
 */
public class JChessView extends FrameView implements ActionListener, ComponentListener {

	private static final int					MESSAGE_TIMEOUT				= 5000;
	private static final int					BUSY_ANIMATION_RATE		= 30;

	// TODO check member necessary
	private static GUI								gui										= null;
	private javax.swing.JMenu					gameMenu							= null;
	private javax.swing.JTabbedPane		gamesPane							= null;
	private javax.swing.JMenuItem			loadGameItem					= null;
	public javax.swing.JPanel					mainPanel							= null;
	private javax.swing.JMenuBar			menuBar								= null;
	private javax.swing.JMenuItem			moveBackItem					= null;
	private javax.swing.JMenuItem			moveForwardItem				= null;
	private javax.swing.JMenuItem			newGameItem						= null;
	private javax.swing.JMenu					optionsMenu						= null;
	private javax.swing.JMenuItem			aboutMenuItem					= null;
	private javax.swing.JMenuItem			exitMenuItem					= null;
	private javax.swing.JMenu					fileMenu							= null;
	private javax.swing.JMenu					helpMenu							= null;
	private javax.swing.JProgressBar	progressBar						= null;
	private javax.swing.JMenuItem			rewindToBegin					= null;
	private javax.swing.JMenuItem			rewindToEnd						= null;
	private javax.swing.JMenuItem			saveGameItem					= null;
	private javax.swing.JLabel				statusAnimationLabel	= null;
	private javax.swing.JLabel				statusMessageLabel		= null;
	private javax.swing.JPanel				statusPanel						= null;
	private javax.swing.JMenuItem			themeSettingsMenu			= null;
	private javax.swing.JMenuItem			languageSettingsMenu	= null;
	private Timer											messageTimer					= null;
	private Timer											busyIconTimer					= null;
	private Icon											idleIcon							= null;
	private Icon[]										busyIcons							= new Icon[15];
	private int												busyIconIndex					= 0;

	private JDialog										aboutBox							= null;
	private PawnPromotionWindow				promotionBox					= null;
	public JDialog										newGameFrame					= null;

	public Game addNewTab(String title) {
		Game newGUI = new Game();
		this.gamesPane.addTab(title, newGUI);
		return newGUI;
	}

	public void actionPerformed(ActionEvent event) {
		Object target = event.getSource();
		if (target == newGameItem) {
			this.newGameFrame = new NewGameWindow();
			JChessApp.getApplication().show(this.newGameFrame);
		} else if (target == saveGameItem) { // saveGame
			if (this.gamesPane.getTabCount() == 0) {
				JOptionPane.showMessageDialog(null, Language.getString("save_not_called_for_tab")); //$NON-NLS-1$
				return;
			}
			while (true) {// until
				JFileChooser fc = new JFileChooser();
				int retVal = fc.showSaveDialog(this.gamesPane);
				if (retVal == JFileChooser.APPROVE_OPTION) {
					File selFile = fc.getSelectedFile();
					Game tempGUI = (Game) this.gamesPane.getComponentAt(this.gamesPane.getSelectedIndex());
					if (!selFile.exists()) {
						try {
							selFile.createNewFile();
						} catch (java.io.IOException exc) {
							Logging.log(Language.getString("JChessView.1") + exc); //$NON-NLS-1$
						}
					} else if (selFile.exists()) {
						int opt = JOptionPane.showConfirmDialog(tempGUI, Language.getString("file_exists"), Language.getString("file_exists"), JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
						if (opt == JOptionPane.NO_OPTION)// if user choose to
						// now overwrite
						{
							continue; // go back to file choose
						}
					}
					if (selFile.canWrite()) {
						tempGUI.saveGame(selFile);
					}
					Logging.log(fc.getSelectedFile().isFile());
					break;
				} else if (retVal == JFileChooser.CANCEL_OPTION) {
					break;
				}
				// /JChessView.gui.game.saveGame(fc.);
			}
		} else if (target == loadGameItem) { // loadGame
			JFileChooser fc = new JFileChooser();
			int retVal = fc.showOpenDialog(this.gamesPane);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				if (file.exists() && file.canRead()) {
					try {
						Game.loadGame(file);
					} catch (Exception e) {
						Logging.log(e);
					}
				}
			}
		} else if (target == this.themeSettingsMenu) {
			try {
				ThemeChooseWindow choose = new ThemeChooseWindow(this.getFrame());
				JChessApp.getApplication().show(choose);
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(JChessApp.getApplication().getMainFrame(), exc.getMessage());
				Logging.log(Language.getString("JChessView.4"), exc); //$NON-NLS-1$
			}
		} else if (target == this.languageSettingsMenu) {
			try {
				LanguageChooseWindow choose = new LanguageChooseWindow(this.getFrame());
				JChessApp.getApplication().show(choose);
				renameAllVisibleItems();
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(JChessApp.getApplication().getMainFrame(), exc.getMessage());
				Logging.log(Language.getString("JChessView.5"), exc); //$NON-NLS-1$
			}
		}
	}

	// /--endOf- don't delete, becouse they're interfaces for MouseEvent

	private void renameAllVisibleItems() {
		fileMenu.setText(Language.getString("fileMenu.text")); // NOI18N //$NON-NLS-1$
		newGameItem.setText(Language.getString("newGameItem.text")); // NOI18N //$NON-NLS-1$
		loadGameItem.setText(Language.getString("loadGameItem.text")); // NOI18N //$NON-NLS-1$
		saveGameItem.setText(Language.getString("saveGameItem.text")); // NOI18N //$NON-NLS-1$
		gameMenu.setText(Language.getString("gameMenu.text")); // NOI18N //$NON-NLS-1$

		moveBackItem.setText(Language.getString("moveBackItem.text")); // NOI18N //$NON-NLS-1$
		moveForwardItem.setText(Language.getString("moveForwardItem.text")); // NOI18N //$NON-NLS-1$

		rewindToBegin.setText(Language.getString("rewindToBegin.text")); // NOI18N //$NON-NLS-1$
		rewindToEnd.setText(Language.getString("rewindToEnd.text")); // NOI18N //$NON-NLS-1$

		optionsMenu.setText(Language.getString("optionsMenu.text")); // NOI18N //$NON-NLS-1$
		themeSettingsMenu.setText(Language.getString("themeSettingsMenu.text")); // NOI18N //$NON-NLS-1$

		languageSettingsMenu.setText(Language.getString("languageSettingMenu.text"));// NOI18N //$NON-NLS-1$

		helpMenu.setText(Language.getString("helpMenu.text")); // NOI18N //$NON-NLS-1$

	}

	public JChessView(SingleFrameApplication app) {
		super(app);

		initComponents();
		// status bar initialization - message timeout, idle icon and busy
		// animation, etc
		int messageTimeout = MESSAGE_TIMEOUT;
		messageTimer = new Timer(messageTimeout, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statusMessageLabel.setText(""); //$NON-NLS-1$
			}
		});
		messageTimer.setRepeats(false);
		int busyAnimationRate = BUSY_ANIMATION_RATE;
		for (int i = 0; i < busyIcons.length; i++) {
			busyIcons[i] = Settings.getIcon("StatusBar.busyIcons[" + i + "]"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
				statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
			}
		});
		idleIcon = Settings.getIcon("StatusBar.idleIcon"); //$NON-NLS-1$
		statusAnimationLabel.setIcon(idleIcon);
		progressBar.setVisible(false);

		// connecting action tasks to status bar via TaskMonitor
		TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
		taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
				String propertyName = evt.getPropertyName();
				if ("started".equals(propertyName)) { //$NON-NLS-1$
					if (busyIconTimer.isRunning() == false) {
						statusAnimationLabel.setIcon(busyIcons[0]);
						busyIconIndex = 0;
						busyIconTimer.start();
					}
					progressBar.setVisible(true);
					progressBar.setIndeterminate(true);
				} else if ("done".equals(propertyName)) { //$NON-NLS-1$
					busyIconTimer.stop();
					statusAnimationLabel.setIcon(idleIcon);
					progressBar.setVisible(false);
					progressBar.setValue(0);
				} else if ("message".equals(propertyName)) { //$NON-NLS-1$
					String text = (String) (evt.getNewValue());
					statusMessageLabel.setText((text == null) ? "" : text); //$NON-NLS-1$
					messageTimer.restart();
				} else if ("progress".equals(propertyName)) { //$NON-NLS-1$
					int value = (Integer) (evt.getNewValue());
					progressBar.setVisible(true);
					progressBar.setIndeterminate(false);
					progressBar.setValue(value);
				}
			}
		});
	}

	public void showAboutBox() {
		if (aboutBox == null) {
			JFrame mainFrame = JChessApp.getApplication().getMainFrame();
			aboutBox = new JChessAboutBox(mainFrame);
			aboutBox.setLocationRelativeTo(mainFrame);
		}
		JChessApp.getApplication().show(aboutBox);
	}

	public String showPawnPromotionBox(Player.colors color) {
		if (promotionBox == null) {
			JFrame mainFrame = JChessApp.getApplication().getMainFrame();
			promotionBox = new PawnPromotionWindow(mainFrame, color);
			promotionBox.setLocationRelativeTo(mainFrame);
			promotionBox.setModal(true);

		}
		promotionBox.setColor(color);
		JChessApp.getApplication().show(promotionBox);

		return promotionBox.getResult();
	}

	public String showSaveWindow() {

		return ""; //$NON-NLS-1$
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		Locale.setDefault(Locale.US);

		mainPanel = new javax.swing.JPanel();
		gamesPane = new jchess.ui.JChessTabbedPane();
		menuBar = new javax.swing.JMenuBar();
		fileMenu = new javax.swing.JMenu();
		newGameItem = new javax.swing.JMenuItem();
		loadGameItem = new javax.swing.JMenuItem();
		saveGameItem = new javax.swing.JMenuItem();
		exitMenuItem = new javax.swing.JMenuItem();
		gameMenu = new javax.swing.JMenu();
		moveBackItem = new javax.swing.JMenuItem();
		moveForwardItem = new javax.swing.JMenuItem();
		rewindToBegin = new javax.swing.JMenuItem();
		rewindToEnd = new javax.swing.JMenuItem();
		optionsMenu = new javax.swing.JMenu();
		themeSettingsMenu = new javax.swing.JMenuItem();
		languageSettingsMenu = new javax.swing.JMenuItem();
		helpMenu = new javax.swing.JMenu();
		aboutMenuItem = new javax.swing.JMenuItem();
		statusPanel = new javax.swing.JPanel();
		javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
		statusMessageLabel = new javax.swing.JLabel();
		statusAnimationLabel = new javax.swing.JLabel();
		progressBar = new javax.swing.JProgressBar();

		mainPanel.setMaximumSize(new java.awt.Dimension(800, 600));
		mainPanel.setMinimumSize(new java.awt.Dimension(800, 600));
		mainPanel.setName("mainPanel"); // NOI18N //$NON-NLS-1$
		mainPanel.setPreferredSize(new java.awt.Dimension(800, 600));

		gamesPane.setName("gamesPane"); // NOI18N //$NON-NLS-1$

		javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				mainPanelLayout.createSequentialGroup().addContainerGap().addComponent(gamesPane, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
						.addContainerGap()));
		mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				mainPanelLayout.createSequentialGroup().addContainerGap().addComponent(gamesPane, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)));

		menuBar.setName("menuBar"); // NOI18N //$NON-NLS-1$

		fileMenu.setText(Language.getString("fileMenu.text")); // NOI18N //$NON-NLS-1$
		fileMenu.setName("fileMenu"); // NOI18N //$NON-NLS-1$

		newGameItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
		newGameItem.setText(Language.getString("newGameItem.text")); // NOI18N //$NON-NLS-1$
		newGameItem.setName("newGameItem"); // NOI18N //$NON-NLS-1$
		fileMenu.add(newGameItem);
		newGameItem.addActionListener(this);

		loadGameItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
		loadGameItem.setText(Language.getString("loadGameItem.text")); // NOI18N //$NON-NLS-1$
		loadGameItem.setName("loadGameItem"); // NOI18N //$NON-NLS-1$
		fileMenu.add(loadGameItem);
		loadGameItem.addActionListener(this);

		saveGameItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
		saveGameItem.setText(Language.getString("saveGameItem.text")); // NOI18N //$NON-NLS-1$
		saveGameItem.setName("saveGameItem"); // NOI18N //$NON-NLS-1$
		fileMenu.add(saveGameItem);
		saveGameItem.addActionListener(this);

		javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(jchess.JChessApp.class).getContext()
				.getActionMap(JChessView.class, this);
		exitMenuItem.setAction(actionMap.get("quit")); // NOI18N //$NON-NLS-1$
		exitMenuItem.setName("exitMenuItem"); // NOI18N //$NON-NLS-1$
		fileMenu.add(exitMenuItem);

		menuBar.add(fileMenu);

		gameMenu.setText(Language.getString("gameMenu.text")); // NOI18N //$NON-NLS-1$
		gameMenu.setName("gameMenu"); // NOI18N //$NON-NLS-1$

		moveBackItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
		moveBackItem.setText(Language.getString("moveBackItem.text")); // NOI18N //$NON-NLS-1$
		moveBackItem.setName("moveBackItem"); // NOI18N //$NON-NLS-1$
		moveBackItem.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				moveBackItemMouseClicked(evt);
			}
		});
		moveBackItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moveBackItemActionPerformed(evt);
			}
		});
		gameMenu.add(moveBackItem);

		moveForwardItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
		moveForwardItem.setText(Language.getString("moveForwardItem.text")); // NOI18N //$NON-NLS-1$
		moveForwardItem.setName("moveForwardItem"); // NOI18N //$NON-NLS-1$
		moveForwardItem.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				moveForwardItemMouseClicked(evt);
			}
		});
		moveForwardItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					moveForwardItemActionPerformed(evt);
				} catch (Exception e) {
					// TODO Inform User
					Logging.log(e);
				}
			}
		});
		gameMenu.add(moveForwardItem);

		rewindToBegin.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.SHIFT_MASK
				| java.awt.event.InputEvent.CTRL_MASK));
		rewindToBegin.setText(Language.getString("rewindToBegin.text")); // NOI18N //$NON-NLS-1$
		rewindToBegin.setName("rewindToBegin"); // NOI18N //$NON-NLS-1$
		rewindToBegin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rewindToBeginActionPerformed(evt);
			}
		});
		gameMenu.add(rewindToBegin);

		rewindToEnd.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.SHIFT_MASK
				| java.awt.event.InputEvent.CTRL_MASK));
		rewindToEnd.setText(Language.getString("rewindToEnd.text")); // NOI18N //$NON-NLS-1$
		rewindToEnd.setName("rewindToEnd"); // NOI18N //$NON-NLS-1$
		rewindToEnd.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					rewindToEndActionPerformed(evt);
				} catch (Exception e) {
					// TODO Inform User
					Logging.log(e);
				}
			}
		});
		gameMenu.add(rewindToEnd);

		menuBar.add(gameMenu);

		optionsMenu.setText(Language.getString("optionsMenu.text")); // NOI18N //$NON-NLS-1$
		optionsMenu.setName("optionsMenu"); // NOI18N //$NON-NLS-1$

		themeSettingsMenu.setText(Language.getString("themeSettingsMenu.text")); // NOI18N //$NON-NLS-1$
		themeSettingsMenu.setName("themeSettingsMenu"); // NOI18N //$NON-NLS-1$
		optionsMenu.add(themeSettingsMenu);
		themeSettingsMenu.addActionListener(this);

		languageSettingsMenu.setText(Language.getString("languageSettingMenu.text"));// NOI18N //$NON-NLS-1$
		languageSettingsMenu.setName("languageSettingsMenu");// NOI18N //$NON-NLS-1$
		optionsMenu.add(languageSettingsMenu);
		languageSettingsMenu.addActionListener(this);

		menuBar.add(optionsMenu);

		helpMenu.setText(Language.getString("helpMenu.text")); // NOI18N //$NON-NLS-1$
		helpMenu.setName("helpMenu"); // NOI18N //$NON-NLS-1$

		aboutMenuItem.setText(Language.getString("JChessView.60")); //$NON-NLS-1$
		aboutMenuItem.setName("aboutMenuItem"); // NOI18N //$NON-NLS-1$
		helpMenu.add(aboutMenuItem);
		aboutMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showAboutBox();
			}
		});

		menuBar.add(helpMenu);

		statusPanel.setName("statusPanel"); // NOI18N //$NON-NLS-1$

		statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N //$NON-NLS-1$

		statusMessageLabel.setName("statusMessageLabel"); // NOI18N //$NON-NLS-1$

		statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N //$NON-NLS-1$

		progressBar.setName("progressBar"); // NOI18N //$NON-NLS-1$

		javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
		statusPanel.setLayout(statusPanelLayout);
		statusPanelLayout
				.setHorizontalGroup(statusPanelLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
						.addGroup(
								statusPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(statusMessageLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 616, Short.MAX_VALUE)
										.addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(statusAnimationLabel).addContainerGap()));
		statusPanelLayout.setVerticalGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				statusPanelLayout
						.createSequentialGroup()
						.addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(
								statusPanelLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(statusMessageLabel)
										.addComponent(statusAnimationLabel)
										.addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(3, 3, 3)));

		setComponent(mainPanel);
		setMenuBar(menuBar);
		setStatusBar(statusPanel);
	}// </editor-fold>//GEN-END:initComponents

	private void moveBackItemActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_moveBackItemActionPerformed
	{
		try {
			if (gui != null && gui.game != null) {
				gui.game.undo();
			} else {
				Game activeGame = this.getActiveTabGame();
				if (activeGame.undo() == false) {
					JOptionPane.showMessageDialog(null, Language.getString("JChessView.67")); //$NON-NLS-1$
				}
			}
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(null, exc.getMessage());
		}
	}

	private void moveBackItemMouseClicked(java.awt.event.MouseEvent evt)// GEN-FIRST:event_moveBackItemMouseClicked
	{// GEN-HEADEREND:event_moveBackItemMouseClicked
		// TODO add your handling code here:

	}// GEN-LAST:event_moveBackItemMouseClicked

	private void moveForwardItemMouseClicked(java.awt.event.MouseEvent evt)// GEN-FIRST:event_moveForwardItemMouseClicked
	{// GEN-HEADEREND:event_moveForwardItemMouseClicked
		// TODO add your handling code here:

	}// GEN-LAST:event_moveForwardItemMouseClicked

	private void moveForwardItemActionPerformed(java.awt.event.ActionEvent evt) throws Exception// GEN-FIRST:event_moveForwardItemActionPerformed
	{// GEN-HEADEREND:event_moveForwardItemActionPerformed
		// TODO add your handling code here:
		if (gui != null && gui.game != null) {
			gui.game.redo();
		} else {
			try {
				Game activeGame = this.getActiveTabGame();
				if (!activeGame.redo()) {
					JOptionPane.showMessageDialog(null, "W pamieci brak ruchow do przodu!"); //$NON-NLS-1$
				}
			} catch (java.lang.ArrayIndexOutOfBoundsException exc) {
				JOptionPane.showMessageDialog(null, "Brak aktywnej karty!"); //$NON-NLS-1$
			} catch (UnsupportedOperationException exc) {
				JOptionPane.showMessageDialog(null, exc.getMessage());
			}
		}
	}// GEN-LAST:event_moveForwardItemActionPerformed

	private void rewindToBeginActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_rewindToBeginActionPerformed
	{
		try {
			Game activeGame = this.getActiveTabGame();
			if (activeGame.rewindToBegin() == false) {
				JOptionPane.showMessageDialog(null, "Undo to game start failed."); //$NON-NLS-1$
			}
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(null, exc.getMessage());
		}
	}

	private void rewindToEndActionPerformed(java.awt.event.ActionEvent evt) throws Exception// GEN-FIRST:event_rewindToEndActionPerformed
	{// GEN-HEADEREND:event_rewindToEndActionPerformed
		try {
			Game activeGame = this.getActiveTabGame();
			if (!activeGame.rewindToEnd()) {
				JOptionPane.showMessageDialog(null, "W pamieci brak ruchow wstecz!"); //$NON-NLS-1$
			}
		} catch (ArrayIndexOutOfBoundsException exc) {
			JOptionPane.showMessageDialog(null, "Brak aktywnej karty!"); //$NON-NLS-1$
		} catch (UnsupportedOperationException exc) {
			JOptionPane.showMessageDialog(null, exc.getMessage());
		}
	}// GEN-LAST:event_rewindToEndActionPerformed

	public void componentResized(ComponentEvent e) {
		Logging.log("jchessView resized!!;"); //$NON-NLS-1$
		throw new UnsupportedOperationException("Not supported yet."); //$NON-NLS-1$
	}

	public Game getActiveTabGame() throws ArrayIndexOutOfBoundsException {
		Game activeGame = (Game) this.gamesPane.getComponentAt(this.gamesPane.getSelectedIndex());
		return activeGame;
	}

	public int getNumberOfOpenedTabs() {
		return this.gamesPane.getTabCount();
	}

	public void componentMoved(ComponentEvent e) {
		throw new UnsupportedOperationException("Not supported yet."); //$NON-NLS-1$
	}

	public void componentShown(ComponentEvent e) {
		throw new UnsupportedOperationException("Not supported yet."); //$NON-NLS-1$
	}

	public void componentHidden(ComponentEvent e) {
		throw new UnsupportedOperationException("Not supported yet."); //$NON-NLS-1$
	}

}
