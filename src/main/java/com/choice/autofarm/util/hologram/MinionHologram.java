package com.choice.autofarm.util.hologram;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MinionHologram {

    private String HOLOGRAM_NAME;
    private String minionName;
    public static  int NAME_LINE = 0;
    public static int BLOCK_COUNT_LINE = 1;

    private Hologram hologram;

    public MinionHologram(String id, String name){
        HOLOGRAM_NAME = id;
        this.minionName = name;
    }

    public void createHologramName(Location location, int amount){
        List<String> lines = Arrays.asList(minionName, ""+amount);
        hologram = DHAPI.createHologram(HOLOGRAM_NAME, location.add(0, 2, 0), lines);
    }

    public void updateName(int editLine, String content, Location location){
        DHAPI.setHologramLine(hologram, editLine, content);
        DHAPI.moveHologram(hologram, location.add(0, 2, 0));
    }

    public void deleteName(UUID entityUuid){
        DHAPI.removeHologram(entityUuid.toString());
    }

}
