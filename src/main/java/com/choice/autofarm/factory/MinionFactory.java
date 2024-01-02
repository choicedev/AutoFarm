package com.choice.autofarm.factory;

import com.choice.autofarm.entity.minion.EntityWheatMinion;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.entity.minion.EntityStoneMinion;

import java.util.UUID;

public class MinionFactory {

    public static EntityMinion createEntityMinion(UUID uuid, MinionType minionType){
        switch (minionType){
            case STONE -> {
                return new EntityStoneMinion(uuid);
            }
            case WHEAT -> {
                return new EntityWheatMinion(uuid);
            }
        }

        return null;
    }

}
