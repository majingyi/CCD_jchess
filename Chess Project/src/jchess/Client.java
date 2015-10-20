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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jchess.server.Connection_info;

/**
 * Class responsible for clients references:
 * for running game, for joing the game, adding moves
 */
public class Client implements Runnable
{

    public static boolean isPrintEnable = true; //print all messages (print function)
    Socket s;
    ObjectOutputStream output;
    ObjectInputStream input;
    String ip;
    int port;
    Game game;
    Settings sett;
    boolean wait4undoAnswer = false;
    boolean isObserver = false;

    Client(String ip, int port)
    {
        print("running");

        this.ip = ip;
        this.port = port;
    }

    /* Method responsible for joining to the server on 
     * witch the game was created
     */
    boolean join(int tableID, boolean asPlayer, String nick, String password) throws Error //join to server
    {
        print("running function: join(" + tableID + ", " + asPlayer + ", " + nick + ")");
        try
        {
            print("join to server: ip:" + ip + " port:" + port);
            this.isObserver = !asPlayer;
            try
            {
                s = new Socket(ip, port);
                output = new ObjectOutputStream(s.getOutputStream());
                input = new ObjectInputStream(s.getInputStream());
                //send data to server
                print("send to server: table ID");
                output.writeInt(tableID);
                print("send to server: player or observer");
                output.writeBoolean(asPlayer);
                print("send to server: player nick");
                output.writeUTF(nick);
                print("send to server: password");
                output.writeUTF(password);
                output.flush();

                int servCode = input.readInt(); //server returning code
                print("connection info: " + Connection_info.get(servCode).name());
                if (Connection_info.get(servCode).name().startsWith("err_"))
                {
                    throw new Error(Connection_info.get(servCode).name());
                }
                if (servCode == Connection_info.all_is_ok.getValue())
                {
                    return true;
                }
                else //is any bug
                {
                    return false;
                }
            }
            catch (Error err)
            {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, err);
                return false;
            }
            catch (ConnectException ex)
            {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

        }
        catch (UnknownHostException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        catch (IOException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /* Method responsible for running of the game
     */
    public void run()
    {
        print("running function: run()");
        boolean isOK = true;
        while (isOK)
        {
            try
            {
                String in = input.readUTF();
                print("input code: " + in);

                if (in.equals("#move")) //getting new move from server
                {
                    int beginX = input.readInt();
                    int beginY = input.readInt();
                    int endX = input.readInt();
                    int endY = input.readInt();

                    game.simulateMove(beginX, beginY, endX, endY);
                }
                else if (in.equals("#message")) //getting message from server
                {
                    String str = input.readUTF();

                    game.chat.addMessage(str);
                }
                else if (in.equals("#settings")) //getting settings from server
                {
                    try
                    {
                        this.sett = (Settings) input.readObject();
                    }
                    catch (ClassNotFoundException ex)
                    {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    game.settings = this.sett;
                    game.client = this;
                    game.chat.client = this;
                    game.newGame();//start new Game
                    game.chessboard.draw();
                }
                else if (in.equals("#errorConnection"))
                {
                    game.chat.addMessage("** "+Settings.lang("error_connecting_one_of_player")+" **");
                }
                else if(in.equals("#undoAsk") && !this.isObserver)
                {
                    int result = JOptionPane.showConfirmDialog(
                        null, 
                        Settings.lang("your_oponent_plase_to_undo_move_do_you_agree"), 
                        Settings.lang("confirm_undo_move"), 
                        JOptionPane.YES_NO_OPTION
                    );
                    
                    if( result == JOptionPane.YES_OPTION )
                    {
                        game.chessboard.undo();
                        game.switchActive();
                        this.sendUndoAnswerPositive();
                    }
                    else 
                    {
                        this.sendUndoAnswerNegative();
                    }
                }
                else if(in.equals("#undoAnswerPositive") && ( this.wait4undoAnswer || this.isObserver ) )
                {
                    this.wait4undoAnswer = false;
                    String lastMove = game.moves.getMoves().get( game.moves.getMoves().size() -1 );
                    game.chat.addMessage("** "+Settings.lang("permision_ok_4_undo_move")+": "+lastMove+"**");
                    game.chessboard.undo();
                }
                else if(in.equals("#undoAnswerNegative") && this.wait4undoAnswer)
                {
                    game.chat.addMessage( Settings.lang("no_permision_4_undo_move") );
                }
            }
            catch (IOException ex)
            {
                isOK = false;
                game.chat.addMessage("** "+Settings.lang("error_connecting_to_server")+" **");
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /* Method responsible for printing on screen client informations
     */
    public static void print(String str)
    {
        if (isPrintEnable)
        {
            System.out.println("Client: " + str);
        }
    }
    
    /* Method responsible for sending the move witch was taken by a player
     */
    public void sendMove(int beginX, int beginY, int endX, int endY) //sending new move to server
    {
        print("running function: sendMove(" + beginX + ", " + beginY + ", " + endX + ", " + endY + ")");
        try
        {
            output.writeUTF("#move");
            output.writeInt(beginX);
            output.writeInt(beginY);
            output.writeInt(endX);
            output.writeInt(endY);
            output.flush();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendUndoAsk()
    {
        print("sendUndoAsk");
        try
        {
            this.wait4undoAnswer = true;
            output.writeUTF("#undoAsk");
            output.flush();
        }
        catch(IOException exc)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, exc);
        }
    }
    
    public void sendUndoAnswerPositive()
    {
        try
        {
            output.writeUTF("#undoAnswerPositive");
            output.flush();
        }
        catch(IOException exc)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, exc);
        }        
    }
    
    public void sendUndoAnswerNegative()
    {
        try
        {
            output.writeUTF("#undoAnswerNegative");
            output.flush();
        }
        catch(IOException exc)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, exc);
        }        
    }    
    
    /* Method responsible for sending to the server informations about
     * moves of a player
     */
    public void sendMassage(String str) //sending new move to server
    {
        print("running function: sendMessage(" + str + ")");
        try
        {
            output.writeUTF("#message");
            output.writeUTF(str);
            output.flush();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
