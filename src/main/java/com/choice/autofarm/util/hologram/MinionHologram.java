package com.choice.autofarm.util.hologram;

import com.choice.autofarm.entity.minion.domain.MinionType;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.List;

public class MinionHologram {

    private String HOLOGRAM_NAME;
    private String minionName;
    private int blockAmount;
    private int NAME_LINE = 0;
    private int BLOCK_COUNT_LINE = 1;

    private Hologram hologram;

    public MinionHologram(String id, String name){
        HOLOGRAM_NAME = id;
        this.minionName = name;
    }

    public void createHologramName(Location location){
        List<String> lines = Arrays.asList(minionName, "0");
        hologram = DHAPI.createHologram(HOLOGRAM_NAME, location.add(0, 2, 0), lines);
    }

    public void updateName(String blockAmount, Location location){
        List<String> lines = Arrays.asList(minionName, blockAmount);
        DHAPI.setHologramLines(hologram, lines);
        DHAPI.moveHologram(hologram, location.add(0, 2, 0));
    }

    public void deleteName(String entityUuid){
        DHAPI.removeHologram(entityUuid);
    }

}
