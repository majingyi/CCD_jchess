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

public enum Connection_info
{

    all_is_ok(0),
    err_bad_table_ID(1),
    err_table_is_full(2),
    err_game_without_observer(3),
    err_bad_password(4);
    private int value;

    Connection_info(int value)
    {
        this.value = value;
    }

    public static Connection_info get(int id)
    {
        switch (id)
        {
            case 0:
                return Connection_info.all_is_ok;
            case 1:
                return Connection_info.err_bad_table_ID;
            case 2:
                return Connection_info.err_table_is_full;
            case 3:
                return Connection_info.err_game_without_observer;
            case 4:
                return Connection_info.err_bad_password;
            default:
                return null;
        }
    }

    public int getValue()
    {
        return value;
    }
}
