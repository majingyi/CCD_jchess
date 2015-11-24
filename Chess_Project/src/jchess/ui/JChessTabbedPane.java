package jchess.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.io.FileNotFoundException;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import jchess.JChessApp;
import jchess.core.util.Logging;
import jchess.ui.lang.Language;

public class JChessTabbedPane extends JTabbedPane implements MouseListener, ImageObserver {

	private static final long	serialVersionUID	= -3358955157754197669L;

	private TabbedPaneIcon		closeIcon;
	private Image							addIcon						= null;
	private Image							unclickedAddIcon	= null;
	private Rectangle					addIconRect				= null;

	public JChessTabbedPane() throws FileNotFoundException {
		super();
		this.closeIcon = new TabbedPaneIcon(this.closeIcon);
		this.unclickedAddIcon = Theme.getImage("add-tab-icon.png"); //$NON-NLS-1$
		this.addIcon = this.unclickedAddIcon;
		this.setDoubleBuffered(true);
		super.addMouseListener(this);
	}

	@Override
	public void addTab(String title, Component component) {
		this.addTab(title, component, null);
	}

	public void addTab(String title, Component component, Icon closeIcon) {
		super.addTab(title, new TabbedPaneIcon(closeIcon), component);
		Logging.log(Language.getString("JChessTabbedPane.1") + this.getTabCount()); //$NON-NLS-1$
		this.updateAddIconRect();
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	private void showNewGameWindow() {
		if (JChessApp.jcv.newGameFrame == null) {
			JChessApp.jcv.newGameFrame = new NewGameWindow();
		}
		JChessApp.getApplication().show(JChessApp.jcv.newGameFrame);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Rectangle rect;
		int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());
		if (tabNumber >= 0) {
			rect = ((TabbedPaneIcon) getIconAt(tabNumber)).getBounds();
			if (rect.contains(e.getX(), e.getY())) {
				Logging.log(Language.getString("JChessTabbedPane.2") + tabNumber + Language.getString("JChessTabbedPane.3")); //$NON-NLS-1$ //$NON-NLS-2$
				this.removeTabAt(tabNumber);// remove tab
				this.updateAddIconRect();
			}
			if (this.getTabCount() == 0) {
				this.showNewGameWindow();
			}
		} else if (this.addIconRect != null && this.addIconRect.contains(e.getX(), e.getY())) {
			this.showNewGameWindow();
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	private void updateAddIconRect() {
		if (this.getTabCount() > 0) {
			Rectangle rect = this.getBoundsAt(this.getTabCount() - 1);
			this.addIconRect = new Rectangle(rect.x + rect.width + 5, rect.y, this.addIcon.getWidth(this), this.addIcon.getHeight(this));
		} else {
			this.addIconRect = null;
		}
	}

	private Rectangle getAddIconRect() {
		return this.addIconRect;
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		super.imageUpdate(img, infoflags, x, y, width, height);
		this.updateAddIconRect();
		return true;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Rectangle rect = this.getAddIconRect();
		if (rect != null) {
			g.drawImage(this.addIcon, rect.x, rect.y, null);
		}
	}

	@Override
	public void update(Graphics g) {
		this.repaint();
	}
}

class TabbedPaneIcon implements Icon {

	private int		x_pos;
	private int		y_pos;
	private int		width;
	private int		height;
	private Icon	fileIcon;

	public TabbedPaneIcon(Icon fileIcon) {
		this.fileIcon = fileIcon;
		width = 16;
		height = 16;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		this.x_pos = x;
		this.y_pos = y;

		Color col = g.getColor();

		g.setColor(Color.black);
		int y_p = y + 2;
		g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
		g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
		g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
		g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
		g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
		g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
		g.setColor(col);
		if (fileIcon != null) {
			fileIcon.paintIcon(c, g, x + width, y_p);
		}
	}

	public int getIconWidth() {
		return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
	}

	public int getIconHeight() {
		return height;
	}

	public Rectangle getBounds() {
		return new Rectangle(x_pos, y_pos, width, height);
	}
}