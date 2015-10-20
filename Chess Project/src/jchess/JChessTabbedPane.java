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
#    along with this program.  If not, see <http://www.gnu.org/licenses/>..
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package jchess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;

public class JChessTabbedPane extends JTabbedPane implements MouseListener, ImageObserver
{

    private TabbedPaneIcon closeIcon;
    private Image addIcon = null;
    private Image clickedAddIcon = null;
    private Image unclickedAddIcon = null;
    private Rectangle addIconRect = null;

    JChessTabbedPane()
    {
        super();
        this.closeIcon = new TabbedPaneIcon(this.closeIcon);
        this.unclickedAddIcon = GUI.loadImage("add-tab-icon.png");
        this.clickedAddIcon = GUI.loadImage("clicked-add-tab-icon.png");
        this.addIcon = this.unclickedAddIcon;
        this.setDoubleBuffered(true);
        super.addMouseListener(this);
    }

    @Override
    public void addTab(String title, Component component)
    {
        this.addTab(title, component, null);
    }

    public void addTab(String title, Component component, Icon closeIcon)
    {
        super.addTab(title, new TabbedPaneIcon(closeIcon), component);
        System.out.println("Present number of tabs: " + this.getTabCount());
        this.updateAddIconRect();
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
    }

    private void showNewGameWindow()
    {
        if (JChessApp.jcv.newGameFrame == null)
        {
            JChessApp.jcv.newGameFrame = new NewGameWindow();
        }
        JChessApp.getApplication().show(JChessApp.jcv.newGameFrame);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        Rectangle rect; 
        int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());
        if (tabNumber >= 0)
        {
            rect = ((TabbedPaneIcon) getIconAt(tabNumber)).getBounds();
            if (rect.contains(e.getX(), e.getY()))
            {
                System.out.println("Removing tab with " + tabNumber + " number!...");
                this.removeTabAt(tabNumber);//remove tab
                this.updateAddIconRect();
            }
            if(this.getTabCount() == 0)
            {
                this.showNewGameWindow();
            }
        }
        else if (this.addIconRect != null && this.addIconRect.contains(e.getX(), e.getY()))
        {
            System.out.println("newGame by + button");
            this.showNewGameWindow();
        }
        //System.out.println("x:" +e.getX()+" y: "+e.getY()+" x:"+this.addIconRect.x+" y::"+this.addIconRect.y+" width:"+this.addIconRect.width+" height: "+this.addIconRect.height);
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    private void updateAddIconRect()
    {
        if (this.getTabCount() > 0)
        {
            Rectangle rect = this.getBoundsAt(this.getTabCount() - 1);
            this.addIconRect = new Rectangle(rect.x + rect.width + 5, rect.y, this.addIcon.getWidth(this), this.addIcon.getHeight(this));
        }
        else
        {
            this.addIconRect = null;
        }
    }

    private Rectangle getAddIconRect()
    {
        return this.addIconRect;
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
    {
        super.imageUpdate(img, infoflags, x, y, width, height);
        this.updateAddIconRect();
        return true;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Rectangle rect = this.getAddIconRect();
        if (rect != null)
        {
            g.drawImage(this.addIcon, rect.x, rect.y, null);
        }
    }

    @Override
    public void update(Graphics g)
    {
        this.repaint();
    }
}

class TabbedPaneIcon implements Icon
{

    private int x_pos;
    private int y_pos;
    private int width;
    private int height;
    private Icon fileIcon;

    public TabbedPaneIcon(Icon fileIcon)
    {
        this.fileIcon = fileIcon;
        width = 16;
        height = 16;
    }//--endOf-TabbedPaneIcon--

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y)
    {
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
        if (fileIcon != null)
        {
            fileIcon.paintIcon(c, g, x + width, y_p);
        }
    }//--endOf-PaintIcon--

    public int getIconWidth()
    {
        return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
    }//--endOf-getIconWidth--

    public int getIconHeight()
    {
        return height;
    }//--endOf-getIconHeight()--

    public Rectangle getBounds()
    {
        return new Rectangle(x_pos, y_pos, width, height);
    }
}