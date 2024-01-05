package com.choice.autofarm.config.remote.sql;

import com.choice.autofarm.config.Settings;
import com.choice.autofarm.config.Settings.DatabaseSettings;
import org.mineacademy.fo.database.SimpleDatabase;


public class MinionSQL extends SimpleDatabase {

    @Override
    protected void onConnected() {
        update("CREATE TABLE IF NOT EXISTS {table} (\n" +
                "    minion_uuid VARCHAR(36) NOT NULL,\n" +
                "    owner_uuid VARCHAR(36) NOT NULL,\n" +
                "    display_name VARCHAR(255),\n" +
                "    minion_type VARCHAR(255),\n" +
                "    amount INT,\n" +
                "    loc_x DOUBLE,\n" +
                "    loc_y DOUBLE,\n" +
                "    loc_z DOUBLE,\n" +
                "    PRIMARY KEY (minion_uuid)\n" +
                ");");
    }

    public MinionSQL(){
        if(!isConnected()) connect(
                DatabaseSettings.URL,
                DatabaseSettings.USERNAME,
                DatabaseSettings.PASSWORD,
                "minions_db"
        );
    }

    public void save(){

    }

}
