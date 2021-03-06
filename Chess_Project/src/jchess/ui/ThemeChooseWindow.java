
package jchess.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jchess.core.util.Logging;
import jchess.ui.lang.Language;

/**
 * 
 * @author Jingyi Ma
 */
public class ThemeChooseWindow extends JDialog implements ActionListener, ListSelectionListener {

	private static final long	serialVersionUID		= 4833195962704657449L;

	// TODO check ,if we need member here
	private JList<String>			themesList					= null;
	private ImageIcon					themePreview				= null;
	// private GridBagLayout gbl = null;
	// private GridBagConstraints gbc = null;
	private JButton						themePreviewButton	= null;
	private JButton						okButton						= null;

	public ThemeChooseWindow(Frame parent) throws Exception {
		super(parent);

		// String[] themes = Theme.getAvailableThemes();
		this.setTitle(Language.getString("choose_theme_window_title")); //$NON-NLS-1$
		Dimension winDim = new Dimension(420, 420);
		this.setMinimumSize(winDim);
		this.setMaximumSize(winDim);
		this.setSize(winDim);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.themesList = new JList<String>(Theme.getAvailableThemes());
		this.themesList.setLocation(new Point(10, 10));
		this.themesList.setSize(new Dimension(100, 420));
		this.add(this.themesList);
		this.themesList.setSelectionMode(0);
		this.themesList.addListSelectionListener(this);

		// this.gbl = new GridBagLayout();
		// this.gbc = new GridBagConstraints();
		try {
			this.themePreview = new ImageIcon(Theme.getImage("Preview.png")); //$NON-NLS-1$
		} catch (java.lang.NullPointerException exc) {
			Logging.log(Language.getString("ThemeChooseWindow.2"), exc); //$NON-NLS-1$
			this.themePreview = Theme.getNoPreviewImage();
			return;
		}

		this.themePreviewButton = new JButton(this.themePreview);
		this.themePreviewButton.setLocation(new Point(110, 10));
		this.themePreviewButton.setSize(new Dimension(280, 280));
		this.add(this.themePreviewButton);
		this.okButton = new JButton(Language.getString("ThemeChooseWindow.3")); //$NON-NLS-1$
		this.okButton.setLocation(new Point(150, 300));
		this.okButton.setSize(new Dimension(200, 50));
		this.add(this.okButton);
		this.okButton.addActionListener(this);
		this.setModal(true);
	}

	@Override
	// TODO check if event gives selected theme
	public void valueChanged(ListSelectionEvent event) {
		try {
			String theme = this.themesList.getModel().getElementAt(this.themesList.getSelectedIndex()).toString();
			this.themePreview = Theme.getThemePreviewImage(theme);
			this.themePreviewButton.setIcon(this.themePreview);
		} catch (FileNotFoundException e) {
			Logging.log(e);
		}
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == this.okButton) {
			int element = this.themesList.getSelectedIndex();
			String theme = this.themesList.getModel().getElementAt(element).toString();
			if (Theme.themeIsValid(theme)) {
				try {
					Theme.setActiveTheme(theme);
					JOptionPane.showMessageDialog(this, Language.getString("changes_visible_after_restart")); //$NON-NLS-1$
					this.setVisible(true);
				} catch (Exception e) {
					Logging.log(e);
				}
			} else {
				JOptionPane.showMessageDialog(this, Language.getString("invalid_Theme")); //$NON-NLS-1$
				this.setVisible(true);
			}
		}
	}
}