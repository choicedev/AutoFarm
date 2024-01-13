package com.choice.autofarm.util.hologram;

import com.choice.autofarm.entity.minion.EntityMinion;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.Settings;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MinionHologram {

    private final String HOLOGRAM_ID;
    private Hologram hologram;

    public MinionHologram(String id){
        HOLOGRAM_ID = id;
    }

    public void createHologramName(Location location, List<String> minionName){
        hologram = DHAPI.createHologram(HOLOGRAM_ID, location.add(0, 2, 0), minionName);
    }

    public void updateName(int editLine, String content, Location location){
        DHAPI.setHologramLine(hologram, editLine, content);
        DHAPI.moveHologram(hologram, location.add(0, 2, 0));
    }

    public void deleteName(UUID entityUuid){
        DHAPI.removeHologram(entityUuid.toString());
    }

    /*public List<String> formartList(List<String> hologram_names, EntityMinion minion){
        holo.replace("{name}", minion.getDisplayName())
                .replace("{amount}", ""+minion.getAmount())
                .replace("{status}", minion.getStatus());
    }*/

}
