package jchess.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jchess.JChessApp;
import jchess.Settings;
import jchess.core.Logging;

public class ThemeChooseWindow extends JDialog implements ActionListener, ListSelectionListener {

	private static final long	serialVersionUID	= 4833195962704657449L;

	JList<String>							themesList;
	ImageIcon									themePreview;
	GridBagLayout							gbl;
	public String							result;
	GridBagConstraints				gbc;
	JButton										themePreviewButton;
	JButton										okButton;

	public ThemeChooseWindow(Frame parent) throws Exception {
		super(parent);

		File dir = new File(GUI.getJarPath() + File.separator + "jchess" + File.separator + "resources" + File.separator + "theme" + File.separator);

		Logging.log("Theme path: " + dir.getPath());

		File[] files = dir.listFiles();
		if (files != null && dir.exists()) {
			this.setTitle(Settings.lang("choose_theme_window_title"));
			Dimension winDim = new Dimension(550, 230);
			this.setMinimumSize(winDim);
			this.setMaximumSize(winDim);
			this.setSize(winDim);
			this.setResizable(false);
			this.setLayout(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			String[] dirNames = new String[files.length];
			for (int i = 0; i < files.length; i++) {
				dirNames[i] = files[i].getName();
			}
			this.themesList = new JList<String>(dirNames);
			this.themesList.setLocation(new Point(10, 10));
			this.themesList.setSize(new Dimension(100, 120));
			this.add(this.themesList);
			this.themesList.setSelectionMode(0);
			this.themesList.addListSelectionListener(this);

			this.gbl = new GridBagLayout();
			this.gbc = new GridBagConstraints();
			try {
				this.themePreview = new ImageIcon(GUI.loadImage("Preview.png"));
			} catch (java.lang.NullPointerException exc) {
				Logging.log("Cannot find preview image: ", exc);
				this.themePreview = new ImageIcon(JChessApp.class.getResource("resources/theme/noPreview.png"));
				return;
			}
			this.result = "";
			this.themePreviewButton = new JButton(this.themePreview);
			this.themePreviewButton.setLocation(new Point(110, 10));
			this.themePreviewButton.setSize(new Dimension(420, 120));
			this.add(this.themePreviewButton);
			this.okButton = new JButton("OK");
			this.okButton.setLocation(new Point(175, 140));
			this.okButton.setSize(new Dimension(200, 50));
			this.add(this.okButton);
			this.okButton.addActionListener(this);
			this.setModal(true);
		} else {
			throw new Exception(Settings.lang("error_when_creating_theme_config_window"));
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		String element = this.themesList.getModel().getElementAt(this.themesList.getSelectedIndex()).toString();
		String path = GUI.getJarPath() + File.separator + "jchess" + File.separator + "resources" + File.separator + "theme" + File.separator;
		Logging.log(path + element + "/images/Preview.png");
		this.themePreview = new ImageIcon(path + element + "/images/Preview.png");
		this.themePreviewButton.setIcon(this.themePreview);
	}

	/**
	 * Method which is changing a pawn into queen, rook, bishop or knight
	 * 
	 * @param evt
	 *          Capt information about performed action
	 */
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == this.okButton) {
			Properties prp = GUI.getConfigFile();
			int element = this.themesList.getSelectedIndex();
			String name = this.themesList.getModel().getElementAt(element).toString();
			if (GUI.themeIsValid(name)) {
				prp.setProperty("THEME", name);
				try {
					// FileOutputStream fOutStr = new
					// FileOutputStream(ThemeChooseWindow.class.getResource("config.txt").getFile());
					FileOutputStream fOutStr = new FileOutputStream("config.txt");
					prp.store(fOutStr, null);
					fOutStr.flush();
					fOutStr.close();
				} catch (java.io.IOException exc) {
				}
				JOptionPane.showMessageDialog(this, Settings.lang("changes_visible_after_restart"));
				this.setVisible(false);

			}
			Logging.log(prp.getProperty("THEME"));
		}
	}
}
