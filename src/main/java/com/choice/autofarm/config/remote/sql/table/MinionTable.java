package com.choice.autofarm.config.remote.sql.table;

import lombok.Data;

public @Data class MinionTable {

    private String minion_uuid;
    private String owner_uuid;
    private String display_name;
    private String minion_type;
    private int amount;
    private double loc_x;
    private double loc_y;
    private double loc_z;


}
