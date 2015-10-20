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
package jchess.server;

import java.io.IOException;
import java.util.ArrayList;
import jchess.Player;
import jchess.Settings;

public class Table
{//Table: {two player, one chessboard and x observers}

    public SClient clientPlayer1;
    public SClient clientPlayer2;
    public ArrayList<SClient> clientObservers;
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

        if (canObserversJoin)
        {
            clientObservers = new ArrayList<SClient>();
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
        player1Set.playerWhite.setType(Player.playerTypes.localUser);
        player1Set.playerBlack.setType(Player.playerTypes.networkUser);
        player1Set.gameType = Settings.gameTypes.network;
        player1Set.upsideDown = true;

        player2Set.gameMode = Settings.gameModes.newGame;
        player2Set.playerWhite.setName(clientPlayer1.nick);
        player2Set.playerBlack.setName(clientPlayer2.nick);
        player2Set.playerWhite.setType(Player.playerTypes.networkUser);
        player2Set.playerBlack.setType(Player.playerTypes.localUser);
        player2Set.gameType = Settings.gameTypes.network;
        player2Set.upsideDown = false;

        if (canObserversJoin())
        {
            observerSettings = new Settings();

            observerSettings.gameMode = Settings.gameModes.newGame;
            observerSettings.playerWhite.setName(clientPlayer1.nick);
            observerSettings.playerBlack.setName(clientPlayer2.nick);
            observerSettings.playerWhite.setType(Player.playerTypes.networkUser);
            observerSettings.playerBlack.setType(Player.playerTypes.networkUser);
            observerSettings.gameType = Settings.gameTypes.network;
            observerSettings.upsideDown = true;
        }
    }

    public void sendSettingsToAll() throws IOException //send generated settings to all clients on this table
    {

        Server.print("running function: sendSettingsToAll()");

        clientPlayer1.output.writeUTF("#settings");
        clientPlayer1.output.writeObject(player1Set);
        clientPlayer1.output.flush();

        clientPlayer2.output.writeUTF("#settings");
        clientPlayer2.output.writeObject(player2Set);
        clientPlayer2.output.flush();

        if (canObserversJoin())
        {
            for (SClient observer : clientObservers)
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
        SClient observer = clientObservers.get(clientObservers.size() - 1);

        observer.output.writeUTF("#settings");
        observer.output.writeObject(observerSettings);
        observer.output.flush();

        for (Move m : movesList)
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
    public void sendMoveToOther(SClient sender, int beginX, int beginY, int endX, int endY) throws IOException
    {
        Server.print("running function: sendMoveToOther(" + sender.nick + ", " + beginX + ", " + beginY + ", " + endX + ", " + endY + ")");

        if (sender == clientPlayer1 || sender == clientPlayer2) //only player1 and player2 can move
        {
            SClient receiver = (clientPlayer1 == sender) ? clientPlayer2 : clientPlayer1;
            receiver.output.writeUTF("#move");
            receiver.output.writeInt(beginX);
            receiver.output.writeInt(beginY);
            receiver.output.writeInt(endX);
            receiver.output.writeInt(endY);
            receiver.output.flush();
            
            if (canObserversJoin())
            {
                for (SClient observer : clientObservers)
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
    
    public void sendUndoToAll( SClient sender, String msg ) throws IOException
    {
        if( sender == clientPlayer1 || sender == clientPlayer2 )
        {
            this.sendToAll(sender, msg);
            try
            {
                this.movesList.remove( this.movesList.size()-1 );
            }
            catch(ArrayIndexOutOfBoundsException exc)
            {
                return;
            }
        }
    }

    
    public void sendToAll( SClient sender, String msg ) throws IOException
    {
        if( sender == clientPlayer1 || sender == clientPlayer2 )
        {
            SClient receiver = (clientPlayer1 == sender) ? clientPlayer2 : clientPlayer1;
            receiver.output.writeUTF( msg );
            receiver.output.flush();
            
            if (canObserversJoin())
            {
                for (SClient observer : clientObservers)
                {
                    observer.output.writeUTF( msg );
                    observer.output.flush();
                }
            }            
        }
    }
    
    
    public void sendToOtherPlayer( SClient sender, String msg ) throws IOException
    {
        if( sender == clientPlayer1 || sender == clientPlayer2 )
        {
            SClient receiver = (clientPlayer1 == sender) ? clientPlayer2 : clientPlayer1;
            receiver.output.writeUTF( msg );
            receiver.output.flush();     
        }
    }

    //send message about error with connection to other client
    //send only if sender is player (not observer)
    public void sendErrorConnectionToOther(SClient sender) throws IOException
    {
        Server.print("running function: sendErrorConnectionToOther(" + sender.nick + ")");

        if (sender == clientPlayer1 || sender == clientPlayer2) //only player1 and player2 can move
        {
            if (clientPlayer1 != sender)
            {
                clientPlayer1.output.writeUTF("#errorConnection");
                clientPlayer1.output.flush();
            }
            if (clientPlayer2 != sender)
            {
                clientPlayer2.output.writeUTF("#errorConnection");
                clientPlayer2.output.flush();
            }

            if (canObserversJoin())
            {
                for (SClient observer : clientObservers)
                {
                    observer.output.writeUTF("#errorConnection");
                    observer.output.flush();
                }
            }
        }
    }

    public void sendMessageToAll(String str) throws IOException
    {
        Server.print("running function: sendMessageToAll(" + str + ")");

        if (clientPlayer1 != null)
        {
            clientPlayer1.output.writeUTF("#message");
            clientPlayer1.output.writeUTF(str);
            clientPlayer1.output.flush();
        }

        if (clientPlayer2 != null)
        {
            clientPlayer2.output.writeUTF("#message");
            clientPlayer2.output.writeUTF(str);
            clientPlayer2.output.flush();
        }

        if (canObserversJoin())
        {
            for (SClient observer : clientObservers)
            {
                observer.output.writeUTF("#message");
                observer.output.writeUTF(str);
                observer.output.flush();
            }
        }
    }

    public boolean isAllPlayers()
    {//is it all playing players?

        if (clientPlayer1 == null || clientPlayer2 == null)
        {
            return false;
        }
        return true;
    }

    public boolean isObservers()
    {//is it any observer?

        return !clientObservers.isEmpty();
    }

    public boolean canObserversJoin()
    {//can wathing game?

        return this.canObserversJoin;
    }

    public void addPlayer(SClient client)
    {//join player to game

        if (clientPlayer1 == null)
        {
            clientPlayer1 = client;
            Server.print("Player1 connected");
        }
        else if (clientPlayer2 == null)
        {
            clientPlayer2 = client;
            Server.print("Player2 connected");
        }
    }

    public void addObserver(SClient client)
    {//join observer to game

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