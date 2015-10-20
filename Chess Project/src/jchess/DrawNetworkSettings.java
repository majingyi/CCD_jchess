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
 */
package jchess;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import jchess.server.Server;

/**
 * Class responible for drawing Network Settings, when player want to start
 * a game on a network
 * @param parent Where are saved default settings
 */
public class DrawNetworkSettings extends JPanel implements ActionListener
{

    private JDialog parent;
    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    private ButtonGroup serverORclient;
    private JRadioButton radioServer;
    private JRadioButton radioClient;
    private JLabel labelNick;
    private JLabel labelPassword;
    private JLabel labelGameID;
    private JLabel labelOptions;
    private JPanel panelOptions;
    private JTextField textNick;
    private JPasswordField textPassword;
    private JTextField textGameID;
    private JButton buttonStart;
    private ServOptionsPanel servOptions;
    private ClientOptionsPanel clientOptions;

    DrawNetworkSettings(JDialog parent)
    {
        super();

        //components
        this.parent = parent;

        this.radioServer = new JRadioButton(Settings.lang("create_server"), true);
        this.radioClient = new JRadioButton(Settings.lang("connect_2_server"), false);
        this.serverORclient = new ButtonGroup();
        this.serverORclient.add(this.radioServer);
        this.serverORclient.add(this.radioClient);
        this.radioServer.addActionListener(this);
        this.radioClient.addActionListener(this);

        this.labelNick = new JLabel(Settings.lang("nickname"));
        this.labelPassword = new JLabel(Settings.lang("password"));
        this.labelGameID = new JLabel(Settings.lang("game_id"));
        this.labelOptions = new JLabel(Settings.lang("server_options"));

        this.textNick = new JTextField();
        this.textPassword = new JPasswordField();
        this.textGameID = new JTextField();

        this.panelOptions = new JPanel();
        this.clientOptions = new ClientOptionsPanel();
        this.servOptions = new ServOptionsPanel();

        this.buttonStart = new JButton(Settings.lang("start"));
        this.buttonStart.addActionListener(this);

        //add components
        this.gbl = new GridBagLayout();
        this.gbc = new GridBagConstraints();
        this.gbc.fill = GridBagConstraints.BOTH;
        this.setLayout(gbl);

        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbl.setConstraints(radioServer, gbc);
        this.add(radioServer);

        this.gbc.gridx = 1;
        this.gbc.gridy = 0;
        this.gbl.setConstraints(radioClient, gbc);
        this.add(radioClient);

        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(labelGameID, gbc);
        this.add(labelGameID);

        this.gbc.gridx = 0;
        this.gbc.gridy = 2;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(textGameID, gbc);
        this.add(textGameID);

        this.gbc.gridx = 0;
        this.gbc.gridy = 3;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(labelNick, gbc);
        this.add(labelNick);

        this.gbc.gridx = 0;
        this.gbc.gridy = 4;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(textNick, gbc);
        this.add(textNick);

        this.gbc.gridx = 0;
        this.gbc.gridy = 5;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(labelPassword, gbc);
        this.add(labelPassword);

        this.gbc.gridx = 0;
        this.gbc.gridy = 6;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(textPassword, gbc);
        this.add(textPassword);

        this.gbc.gridx = 0;
        this.gbc.gridy = 7;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(labelOptions, gbc);
        this.add(labelOptions);

        this.gbc.gridx = 0;
        this.gbc.gridy = 8;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(panelOptions, gbc);
        this.add(panelOptions);

        this.gbc.gridx = 0;
        this.gbc.gridy = 9;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(buttonStart, gbc);
        this.add(buttonStart);

        this.panelOptions.add(servOptions);
    }

    /*Method for showing settings which the player is intrested with
     */
    public void actionPerformed(ActionEvent arg0)
    {
        if (arg0.getSource() == this.radioServer) //show options for server
        {
            this.panelOptions.removeAll();
            this.panelOptions.add(servOptions);
            this.panelOptions.revalidate();
            this.panelOptions.requestFocus();
            this.panelOptions.repaint();
        }
        else if (arg0.getSource() == this.radioClient) //show options for client
        {
            this.panelOptions.removeAll();
            this.panelOptions.add(clientOptions);
            this.panelOptions.revalidate();
            this.panelOptions.requestFocus();
            this.panelOptions.repaint();
        }
        else if (arg0.getSource() == this.buttonStart) //click start button
        {
            String error = "";
            if (this.textGameID.getText().isEmpty())
            {
                error = Settings.lang("fill_game_id") + "\n";
            }
            if (this.textNick.getText().length() == 0)
            {
                error += Settings.lang("fill_name") + "\n";
            }
            if (this.textPassword.getText().length() <= 4)
            {
                error += Settings.lang("fill_pass_with_more_than_4_signs") + "\n";
            }
            if (this.radioClient.isSelected() && this.clientOptions.textServIP.getText().length() == 0)
            {
                error += Settings.lang("please_fill_field") + " IP \n";
            }
            else if (this.radioClient.isSelected())
            {
                Pattern ipPattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
                if (!ipPattern.matcher(this.clientOptions.textServIP.getText()).matches())
                {
                    error += Settings.lang("bad_ip_format") + "\n";
                }
            }
            if (error.length() > 0)
            {
                JOptionPane.showMessageDialog(this, error);
                return;
            }
            String pass = this.textPassword.getText().toString();
            if (this.radioServer.isSelected())
            {
                Server server = new Server(); //create server
                server.newTable(Integer.parseInt(textGameID.getText()), pass, !servOptions.checkWitchoutObserver.isSelected(), !servOptions.checkDisableChat.isSelected()); //create new table
                //set client options
                clientOptions.textServIP.setText("127.0.0.1");

                try
                {
                    Thread.sleep(100); //wait 100 ms
                }
                catch (InterruptedException ex)
                {
                    Logger.getLogger(DrawNetworkSettings.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Client client;
            try
            {
                client = new Client(clientOptions.textServIP.getText(), Server.port);//create client
                boolean isJoining = client.join(Integer.parseInt(textGameID.getText()), !clientOptions.checkOnlyWatch.isSelected(), textNick.getText(), MD5.encrypt(textPassword.getText()));//join and wait for all players

                if (isJoining) //Client connection: succesful
                {
                    System.out.println("Client connection: succesful");
                    //create new game and draw chessboard
                    Game newGUI = JChessApp.jcv.addNewTab("Network game, table: " + textGameID.getText()/*client.sett.playerWhite.getName()+" vs "+client.sett.playerBlack.getName()*/);
                    client.game = newGUI;
                    newGUI.add(newGUI.chat);
                    newGUI.chessboard.draw();

                    Thread thread = new Thread(client);
                    thread.start(); //client listening

                    this.parent.setVisible(false);//hide parent
                }
                else
                {
                    JOptionPane.showMessageDialog(this, Settings.lang("error_connecting_to_server"));
                }

            }
            catch (Error err)
            {
                System.out.println("Client connection: failure");
                JOptionPane.showMessageDialog(this, err);
            }
        }
    }

    /* Method witch is saving the latest network settings
     */
    private class ServOptionsPanel extends JPanel //options for server
    {

        private GridBagLayout gbl;
        private GridBagConstraints gbc;
        private JLabel labelGameTime;
        public JTextField textGameTime;
        public JCheckBox checkWitchoutObserver;
        public JCheckBox checkDisableChat;

        ServOptionsPanel()
        {
            super();

            labelGameTime = new JLabel(Settings.lang("time_game_min"));
            textGameTime = new JTextField();
            checkWitchoutObserver = new JCheckBox(Settings.lang("without_observers"));
            checkDisableChat = new JCheckBox(Settings.lang("without_chat"));

            //temporary disabled options
            this.labelGameTime.setEnabled(false);
            this.textGameTime.setEnabled(false);
            this.checkDisableChat.setEnabled(false);
            //------------------------

            this.gbl = new GridBagLayout();
            this.gbc = new GridBagConstraints();
            this.gbc.fill = GridBagConstraints.BOTH;
            this.setLayout(gbl);

            this.gbc.gridx = 0;
            this.gbc.gridy = 0;
            this.gbc.gridwidth = 2;
            this.gbl.setConstraints(labelGameTime, gbc);
            this.add(labelGameTime);

            this.gbc.gridx = 0;
            this.gbc.gridy = 1;
            this.gbc.gridwidth = 2;
            this.gbl.setConstraints(textGameTime, gbc);
            this.add(textGameTime);

            this.gbc.gridx = 0;
            this.gbc.gridy = 2;
            this.gbc.gridwidth = 1;
            this.gbl.setConstraints(checkWitchoutObserver, gbc);
            this.add(checkWitchoutObserver);

            this.gbc.gridx = 1;
            this.gbc.gridy = 2;
            this.gbc.gridwidth = 1;
            this.gbl.setConstraints(checkDisableChat, gbc);
            this.add(checkDisableChat);
        }
    }

    /* Method responible for drawing clients panel
     */
    private class ClientOptionsPanel extends JPanel //options for client
    {

        private GridBagLayout gbl;
        private GridBagConstraints gbc;
        private JLabel labelServIP;
        public JTextField textServIP;
        public JCheckBox checkOnlyWatch;

        ClientOptionsPanel()
        {
            super();

            this.labelServIP = new JLabel(Settings.lang("server_ip"));
            this.textServIP = new JTextField();
            this.checkOnlyWatch = new JCheckBox(Settings.lang("only_observe"));

            this.gbl = new GridBagLayout();
            this.gbc = new GridBagConstraints();
            this.gbc.fill = GridBagConstraints.BOTH;
            this.setLayout(gbl);

            this.gbc.gridx = 0;
            this.gbc.gridy = 0;
            this.gbl.setConstraints(labelServIP, gbc);
            this.add(labelServIP);

            this.gbc.gridx = 0;
            this.gbc.gridy = 1;
            this.gbl.setConstraints(textServIP, gbc);
            this.add(textServIP);

            this.gbc.gridx = 0;
            this.gbc.gridy = 2;
            this.gbl.setConstraints(checkOnlyWatch, gbc);
            this.add(checkOnlyWatch);
        }
    }
}
