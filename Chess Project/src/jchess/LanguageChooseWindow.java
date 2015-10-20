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

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package jchess;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDialog;

public class LanguageChooseWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1167231690870626220L;

	private JButton okButton = null;

	LanguageChooseWindow(Frame parent) throws Exception {
		super(parent);

		this.setTitle(Settings.lang("choose_language_window_title"));
		Dimension winDim = new Dimension(550, 230);
		this.setMinimumSize(winDim);
		this.setMaximumSize(winDim);
		this.setSize(winDim);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.okButton = new JButton("OK");
		this.okButton.setLocation(new Point(175, 140));
		this.okButton.setSize(new Dimension(200, 50));
		this.add(this.okButton);
		this.okButton.addActionListener(this);
		this.setModal(true);
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == this.okButton) {
			// TODO find selected Button and change Locale
			Settings.setLocale(Locale.US);
			this.setVisible(false);
		}
	}
}
