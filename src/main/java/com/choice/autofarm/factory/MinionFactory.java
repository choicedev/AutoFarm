package com.choice.autofarm.factory;

import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.entity.minion.EntityStoneMinion;
import com.choice.autofarm.entity.minion.EntityWheatMinion;
import com.choice.autofarm.entity.minion.domain.MinionType;

import java.util.UUID;

public class MinionFactory {

    public static EntityMinion createEntityMinion(UUID uuid, UUID entityUUID, MinionType minionType){
        switch (minionType){
            case STONE -> {
                return new EntityStoneMinion(uuid, entityUUID);
            }
            case WHEAT -> {
                return new EntityWheatMinion(uuid, entityUUID);
            }
        }

        return null;
    }

    public static EntityMinion getEntityMinion(String uuid, String entityUUID, MinionType minionType, double level, int amount){
        switch (minionType){
            case STONE -> {

                return new EntityStoneMinion(UUID.fromString(uuid), UUID.fromString(entityUUID), minionType, level, amount);
            }
            case WHEAT -> {
                return new EntityWheatMinion(UUID.fromString(uuid), UUID.fromString(entityUUID), minionType, level, amount);
            }
        }

        return null;
    }

}
