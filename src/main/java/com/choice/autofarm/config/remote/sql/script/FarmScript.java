package com.choice.autofarm.config.remote.sql.script;

import java.util.UUID;

import static com.choice.autofarm.config.remote.sql.table.ConstantsSQL.*;

public class FarmScript {

    public static String insertNewMinion(
            String minion_uuid,
            String owner_uuid,
            String display_name,
            String minion_type,
            Integer amount,
            Double loc_x,
            Double loc_y,
            Double loc_z
    ) {
        return "INSERT INTO {table} (" +
                MINION_UUID + ", " +
                OWNER_UUID + ", " +
                DISPLAY_NAME + ", " +
                MINION_TYPE + ", " +
                AMOUNT + ", " +
                LOC_X + ", " +
                LOC_Y + ", " +
                LOC_Z+")\n" +
                "VALUES (" +
                "'"+minion_uuid+"'," +
                "'"+owner_uuid+"'," +
                "'"+display_name+"'," +
                "'"+minion_type+"'," +
                "'"+amount+"'," +
                "'"+loc_x+"'," +
                "'"+loc_y+"'," +
                "'"+loc_z+"'" +
                ");";
    }


    public static String getMinion(UUID uuid){
        return "SELECT * FROM {table} \n" +
                "WHERE "+OWNER_UUID+"= '"+uuid+"'";

    }
}
