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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jchess.MD5;

public class Console
{

    public static void main(String[] args)
    {
        System.out.println("JChess Server Start!");

        Server server = new Server(); //create server
        server.isPrintEnable = false;

        boolean isOK = true;
        while (isOK)
        {
            System.out.println("--------------------");
            System.out.println("[1] Nowy stół");
            System.out.println("[2] Lista aktywnych stołów");
            System.out.println("[3] Włącz/wyłącz komunikaty serwera");
            System.out.println("[4] Wyłącz serwer");
            System.out.print("-> ");
            String str = readString();

            if (str.equals("1")) //new table
            {
                System.out.print("ID gry: ");
                int gameID = Integer.parseInt(readString());

                System.out.print("Hasło: ");
                String pass = MD5.encrypt(readString());

                String observer;
                do
                {
                    System.out.print("Gra z obserwatorami[t/n]: ");
                    observer = readString();
                }
                while (!observer.equalsIgnoreCase("t") && !observer.equalsIgnoreCase("n"));

                boolean canObserver = observer.equalsIgnoreCase("t");

                server.newTable(gameID, pass, canObserver, true); //create new table
            }
            else if (str.equals("2")) //list of tables
            {
                for (Map.Entry<Integer, Table> entry : server.tables.entrySet())
                {
                    Integer id = entry.getKey();
                    Table table = entry.getValue();

                    String p1, p2;

                    if (table.clientPlayer1 == null || table.clientPlayer1.nick == null)
                    {
                        p1 = "empty";
                    }
                    else
                    {
                        p1 = table.clientPlayer1.nick;
                    }

                    if (table.clientPlayer2 == null || table.clientPlayer2.nick == null)
                    {
                        p2 = "empty";
                    }
                    else
                    {
                        p2 = table.clientPlayer2.nick;
                    }

                    System.out.println("\t" + id + ": " + p1 + " vs " + p2);
                }
            }
            else if (str.equals("3")) //on/off server's communicats
            {
                if (server.isPrintEnable == false)
                {
                    server.isPrintEnable = true;
                    System.out.println("Komunikaty serwera zostały włączone");
                }
                else
                {
                    server.isPrintEnable = false;
                    System.out.println("Komunikaty serwera zostały wyłączone");
                }
            }
            else if (str.equals("4")) //exit
            {
                isOK = false;
            }
            else //bad commant
            {
                System.out.println("Nierozpoznane polecenie");
            }
        }
        System.exit(0);
    }

    public static String readString() //read string from console
    {
        int ch;
        StringBuffer sb = new StringBuffer();
        try
        {
            while ((ch = System.in.read()) != 10)
            {
                sb.append((char) ch);
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sb.toString();
    }
}
