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

package jchess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jchess.Player.playerTypes;

/**
 * Class responsible for server references: For running the server,
 * settings of the players, for clients references on the server
 * and references of observators
 */

public class Server implements Runnable
{
    public static boolean isPrintEnable = true; //print all messages (print function)

    private static Map<Integer, Table> tables;
    public static int port=4449;
    private static ServerSocket ss;
    private static boolean isRunning=false;

    public static enum connection_info
    {
        all_is_ok(0),
        err_bad_table_ID(1),
        err_table_is_full(2),
        err_game_without_observer(3),
        err_bad_password(4);

        private int value;

        connection_info(int value)
        {
            this.value = value;
        }

        public static connection_info get(int id)
        {
            switch(id)
            {
                case 0:
                    return connection_info.all_is_ok;
                case 1:
                    return connection_info.err_bad_table_ID;
                case 2:
                    return connection_info.err_table_is_full;
                case 3:
                    return connection_info.err_game_without_observer;
                case 4:
                    return connection_info.err_bad_password;
                default:
                    return null;
            }
        }

        public int getValue()
        {
            return value;
        }
    }

    public Server()
    {
        if(!Server.isRunning) //run server if isn't running previous
        {
            runServer();

            Thread thread = new Thread(this);
            thread.start();

            Server.isRunning = true;
        }
    }

    /*
     * Method with is checking is the server is running
     * @return bool true if server is running, else false
     */
    public static boolean isRunning() //is server running?
    {
        return isRunning;
    }

    /*
     * Method to starting a new server
     * It's running a new game server
     */

    private static void runServer() //run server
    {
        try
        {
            ss = new ServerSocket(port);
            print("running");
        }
        catch (IOException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        tables = new HashMap<Integer, Table>();
    }

    public void run() //listening
    {
        print("listening port: "+port);
        while(true)
        {
            Socket s;
            ObjectInputStream input;
            ObjectOutputStream output;

            try
            {
                s = ss.accept();
                input = new ObjectInputStream(s.getInputStream());
                output = new ObjectOutputStream(s.getOutputStream());

                print("new connection");

                //readed all data
                int tableID = input.readInt();
                print("readed table ID: "+tableID);
                boolean joinAsPlayer = input.readBoolean();
                print("readed joinAsPlayer: "+joinAsPlayer);
                String nick = input.readUTF();
                print("readed nick: "+nick);
                String password = input.readUTF();
                print("readed password: "+password);
                //---------------

                if(!tables.containsKey(tableID))
                {
                    print("bad table ID");
                    output.writeInt(connection_info.err_bad_table_ID.getValue());
                    output.flush();
                    continue;
                }
                Table table = tables.get(tableID);

                if(!table.password.equals(password))
                {
                    print("bad password");
                    output.writeInt(connection_info.err_bad_password.getValue());
                    output.flush();
                    continue;
                }
                 
                if(joinAsPlayer)
                {
                    print("join as player");
                    if(table.isAllPlayers())
                    {
                        print("error: was all players at this table");
                        output.writeInt(connection_info.err_table_is_full.getValue());
                        output.flush();
                        continue;
                    }
                    else
                    {
                        print("wasn't all players at this table");

                        output.writeInt(connection_info.all_is_ok.getValue());
                        output.flush();

                        table.addPlayer(new Client(s, input, output, nick, table));
                        table.sendMessageToAll("** Gracz "+nick+" dołączył do gry **");

                        if(table.isAllPlayers())
                        {
                            table.generateSettings();

                            print("Send settings to all");
                            table.sendSettingsToAll();

                            table.sendMessageToAll("** Nowa gra, zaczna: "+table.clientPlayer1.nick+"**");
                        }
                        else
                        {
                            table.sendMessageToAll("** Oczekiwanie na drugiego gracza **");
                        }
                    }
                }
                else//join as observer
                {
                    print("join as observer");
                    if(!table.canObserversJoin())
                    {
                        print("Observers can't join");
                        output.writeInt(connection_info.err_game_without_observer.getValue());
                        output.flush();
                        continue;
                    }
                    else
                    {
                        output.writeInt(connection_info.all_is_ok.getValue());
                        output.flush();

                        table.addObserver(new Client(s, input, output, nick, table));

                        if(table.clientPlayer2 != null) //all players is playing
                        {
                            table.sendSettingsAndMovesToNewObserver();
                        }

                        table.sendMessageToAll("** Obserwator "+nick+" dołączył do gry **");
                    }
                }
            }
            catch (IOException ex)
            {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                continue;
            }
        }
    }

    /*
     * Method with is printing the servers message
     *
     */
    private static void print(String str)
    {
        if(isPrintEnable)
            System.out.println("Server: "+str);
    }

    /*
     * Method with is creating a new table
     * @param idTable int witch number of the table
     * @param password String with password
     * @param withObserver bool true if observers available, else false
     * @param enableChat bool true if chat enable, else false
     */
    public void newTable(int idTable, String password, boolean withObserver, boolean enableChat) //create new table
    {
        print("create new table - id: "+idTable);
        tables.put(idTable, new Table(password, withObserver, enableChat));
    }

    /*
     * Method with sets a players settings and pawns
     */

    private class Table //Table: {two player, one chessboard and x observers}
    {
        public Client clientPlayer1;
        public Client clientPlayer2;
        public ArrayList<Client> clientObservers;
        public Settings player1Set;
        public Settings player2Set;
        public Settings observerSettings;
        public String password;
        private boolean canObserversJoin;
        private boolean enableChat;
        private ArrayList<Move> movesList;

        Table(String password, boolean canObserversJoin, boolean enableChat) 
        {
            this.password = password;
            this.enableChat = enableChat;
            this.canObserversJoin = canObserversJoin;

            if(canObserversJoin)
            {
                clientObservers = new ArrayList<Client>();
            }
            
            movesList = new ArrayList<Move>();
        }

        public void generateSettings() //generate settings for both players and observers
        {
            player1Set = new Settings();
            player2Set = new Settings();

            player1Set.gameMode = Settings.gameModes.newGame;
            player1Set.playerWhite.setName(clientPlayer1.nick);
            player1Set.playerBlack.setName(clientPlayer2.nick);
            player1Set.playerWhite.setType(playerTypes.localUser);
            player1Set.playerBlack.setType(playerTypes.networkUser);
            player1Set.gameType = Settings.gameTypes.network;
            player1Set.upsideDown = true;

            player2Set.gameMode = Settings.gameModes.newGame;
            player2Set.playerWhite.setName(clientPlayer1.nick);
            player2Set.playerBlack.setName(clientPlayer2.nick);
            player2Set.playerWhite.setType(playerTypes.networkUser);
            player2Set.playerBlack.setType(playerTypes.localUser);
            player2Set.gameType = Settings.gameTypes.network;
            player2Set.upsideDown = false;

            if(canObserversJoin())
            {
                observerSettings = new Settings();

                observerSettings.gameMode = Settings.gameModes.newGame;
                observerSettings.playerWhite.setName(clientPlayer1.nick);
                observerSettings.playerBlack.setName(clientPlayer2.nick);
                observerSettings.playerWhite.setType(playerTypes.networkUser);
                observerSettings.playerBlack.setType(playerTypes.networkUser);
                observerSettings.gameType = Settings.gameTypes.network;
                observerSettings.upsideDown = true;
            }
        }

        public void sendSettingsToAll() throws IOException //send generated settings to all clients on this table
        {
            print("running function: sendSettingsToAll()");

            clientPlayer1.output.writeUTF("#settings");
            clientPlayer1.output.writeObject(player1Set);
            clientPlayer1.output.flush();

            clientPlayer2.output.writeUTF("#settings");
            clientPlayer2.output.writeObject(player2Set);
            clientPlayer2.output.flush();

            if(canObserversJoin())
            {
                for(Client observer: clientObservers)
                {
                    observer.output.writeUTF("#settings");
                    observer.output.writeObject(observerSettings);
                    observer.output.flush();
                }
            }
        }
        
        //send all settings and moves to new observer
        //warning: used only if game started
        public void sendSettingsAndMovesToNewObserver() throws IOException
        {
            Client observer = clientObservers.get(clientObservers.size()-1);
            
            observer.output.writeUTF("#settings");
            observer.output.writeObject(observerSettings);
            observer.output.flush();
            
            for(Move m: movesList)
            {
                observer.output.writeUTF("#move");
                observer.output.writeInt(m.bX);
                observer.output.writeInt(m.bY);
                observer.output.writeInt(m.eX);
                observer.output.writeInt(m.eY);
                
            }
            observer.output.flush();
        }

        //send new move to other clients without himself
        public void sendMoveToOther(Client sender, int beginX, int beginY, int endX, int endY) throws IOException
        {
            print("running function: sendMoveToOther("+sender.nick+", "+beginX+", "+beginY+", "+endX+", "+endY+")");

            if(sender == clientPlayer1 || sender == clientPlayer2) //only player1 and player2 can move
            {
                if(clientPlayer1 != sender)
                {
                    clientPlayer1.output.writeUTF("#move");
                    clientPlayer1.output.writeInt(beginX);
                    clientPlayer1.output.writeInt(beginY);
                    clientPlayer1.output.writeInt(endX);
                    clientPlayer1.output.writeInt(endY);
                    clientPlayer1.output.flush();
                }
                if(clientPlayer2 != sender)
                {
                    clientPlayer2.output.writeUTF("#move");
                    clientPlayer2.output.writeInt(beginX);
                    clientPlayer2.output.writeInt(beginY);
                    clientPlayer2.output.writeInt(endX);
                    clientPlayer2.output.writeInt(endY);
                    clientPlayer2.output.flush();
                }

                if(canObserversJoin())
                {
                    for(Client observer: clientObservers)
                    {
                        observer.output.writeUTF("#move");
                        observer.output.writeInt(beginX);
                        observer.output.writeInt(beginY);
                        observer.output.writeInt(endX);
                        observer.output.writeInt(endY);
                        observer.output.flush();
                    }
                }
                
                this.movesList.add(new Move(beginX, beginY, endX, endY));
            }
        }

        public void sendMessageToAll(String str) throws IOException
        {
            print("running function: sendMessageToAll("+str+")");

            if(clientPlayer1 != null)
            {
                clientPlayer1.output.writeUTF("#message");
                clientPlayer1.output.writeUTF(str);
                clientPlayer1.output.flush();
            }
            
            if(clientPlayer2 != null)
            {
                clientPlayer2.output.writeUTF("#message");
                clientPlayer2.output.writeUTF(str);
                clientPlayer2.output.flush();
            }

            if(canObserversJoin())
            {
                for(Client observer: clientObservers)
                {
                    observer.output.writeUTF("#message");
                    observer.output.writeUTF(str);
                    observer.output.flush();
                }
             }
        }

        public boolean isAllPlayers() //is it all playing players?
        {
            if(clientPlayer1==null || clientPlayer2==null)
                return false;
            return true;
        }

        public boolean isObservers() //is it any observer?
        {
            return !clientObservers.isEmpty();
        }

        public boolean canObserversJoin() //can wathing game?
        {
            return this.canObserversJoin;
        }

        public void addPlayer(Client client) //join player to game
        {
            if(clientPlayer1 == null)
            {
                clientPlayer1 = client;
                print("Player1 connected");
            }
            else if(clientPlayer2 == null)
            {
                clientPlayer2 = client;
                print("Player2 connected");
            }
        }

        public void addObserver(Client client) //join observer to game
        {
            clientObservers.add(client);
        }

        private class Move
        {
            int bX;
            int bY;
            int eX;
            int eY;

            Move(int bX, int bY, int eX, int eY) //beginX, beginY, endX, endY
            {
                this.bX = bX;
                this.bY = bY;
                this.eX = eX;
                this.eY = eY;
            }
        }
    }

    private class Client implements Runnable //connecting client
    {
        private Socket s;
        public ObjectInputStream input;
        public ObjectOutputStream output;
        public String nick;
        private Table table;

        Client(Socket s, ObjectInputStream input, ObjectOutputStream output, String nick, Table table)
        {
            this.s = s;
            this.input = input;
            this.output = output;
            this.nick = nick;
            this.table = table;

            Thread thread = new Thread(this);
            thread.start();
        }

        public void run() //listening
        {
            print("running function: run()");
            while(true)
            {
                try
                {
                    String in = input.readUTF();

                    if(in.equals("#move"))//new move
                    {
                        int bX = input.readInt();
                        int bY = input.readInt();
                        int eX = input.readInt();
                        int eY = input.readInt();

                        table.sendMoveToOther(this, bX, bY, eX, eY);
                    }
                    if(in.equals("#message"))//new message
                    {
                        String str = input.readUTF();

                        table.sendMessageToAll(nick + ": " + str);
                    }
                }
                catch (IOException ex)
                {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
