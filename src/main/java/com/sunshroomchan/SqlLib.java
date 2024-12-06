package com.sunshroomchan;

import com.sunshroomchan.database.MariaDBSQL;
import com.sunshroomchan.database.MySQL;

public class SqlLib {

    public static MySQL mySQL;
    public static MariaDBSQL mariaDB;

    public static MariaDBSQL getMariaDB(){
        return mariaDB;
    }

    public static MySQL getMySQL(){
        return mySQL;
    }

}