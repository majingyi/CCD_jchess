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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;

/**
 * Class representing the game chat
 * Players are in touch and can write a messages to each other
 */
public class Chat extends JPanel implements ActionListener
{

    public Client client;
    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    private JScrollPane scrollPane;
    private JTextArea textOutput;
    private JTextField textInput;
    private JButton buttonSend;

    Chat()
    {
        super();

        this.textOutput = new JTextArea();
        this.textOutput.setEditable(false);
        this.scrollPane = new JScrollPane();
        this.scrollPane.setViewportView(this.textOutput);
        this.textInput = new JTextField();
        this.textInput.addActionListener(this);
        this.buttonSend = new JButton("^");
        this.buttonSend.addActionListener(this);

        //add components
        this.gbl = new GridBagLayout();
        this.gbc = new GridBagConstraints();
        this.gbc.fill = GridBagConstraints.BOTH;
        this.setLayout(gbl);

        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbc.gridwidth = 2;
        this.gbc.gridheight = 1;
        this.gbc.weighty = 1.0;
        this.gbc.weightx = 0;
        this.gbl.setConstraints(scrollPane, gbc);
        this.add(scrollPane);

        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.gridwidth = 1;
        this.gbc.gridheight = 1;
        this.gbc.weighty = 0;
        this.gbc.weightx = 1.0;
        this.gbl.setConstraints(textInput, gbc);
        this.add(textInput);

        this.gbc.gridx = 1;
        this.gbc.gridy = 1;
        this.gbc.gridwidth = 1;
        this.gbc.gridheight = 1;
        this.gbc.weighty = 0;
        this.gbc.weightx = 0;
        this.gbl.setConstraints(buttonSend, gbc);
        this.add(buttonSend);
    }

    /** Method of adding message to the list
     */
    public void addMessage(String str) //added message to list
    {
        textOutput.append(str + "\n");
        textOutput.setCaretPosition(textOutput.getDocument().getLength());
    }

    /** Sending message method
     */
    public void actionPerformed(ActionEvent arg0) //sending message
    {
        this.client.sendMassage(textInput.getText());
        textInput.setText("");
    }
}
