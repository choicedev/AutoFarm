package com.choice.autofarm.config.remote.sql;


import com.choice.autofarm.config.Settings.DatabaseSettings;
import com.choice.autofarm.config.remote.sql.script.FarmScript;
import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.util.IResult;
import com.choice.autofarm.util.throwable.MinionException;
import lombok.NonNull;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.database.SimpleDatabase;
import org.mineacademy.fo.debug.LagCatcher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static com.choice.autofarm.config.remote.sql.table.ConstantsSQL.*;
import static com.choice.autofarm.factory.MinionFactory.createEntityMinion;
import static com.choice.autofarm.util.ExceptionUtils.runCatching;


public class MinionSQL extends SimpleDatabase {

    private boolean isQuerying = false;

    @Override
    protected void onConnected() {
        update("CREATE TABLE IF NOT EXISTS {table} (\n" +
                MINION_ID + " int NOT NULL AUTO_INCREMENT,\n" +
                MINION_UUID + "     VARCHAR(36) NOT NULL,\n" +
                OWNER_UUID + "     VARCHAR(36) NOT NULL,\n" +
                DISPLAY_NAME + "     VARCHAR(255),\n" +
                MINION_TYPE + "     VARCHAR(255),\n" +
                AMOUNT + "     INT,\n" +
                LOC_X + "     DOUBLE,\n" +
                LOC_Y + "     DOUBLE,\n" +
                LOC_Z + "     DOUBLE,\n" +
                "    PRIMARY KEY (" + MINION_UUID + "," + OWNER_UUID + ")\n" +
                ");");
    }


    public void createNewMinion(EntityMinion entityMinion, IResult<Object> response) {
        if (this.isLoaded() && !this.isQuerying) {
            this.isQuerying = true;
           Common.runAsync(() -> {
               runCatching(
                       () -> {
                           this.update(FarmScript.insertNewMinion(
                                   entityMinion.getUUID().toString(),
                                   entityMinion.getOwner().toString(),
                                   entityMinion.getDisplayName(),
                                   entityMinion.getMinionType().name(),
                                   entityMinion.getAmount(),
                                   0.0,
                                   0.0,
                                   0.0
                           ));
                           response.onSuccess(true);
                       }, response::onFailure,
                       () -> {
                           this.isQuerying = false;
                       }
               );
            });
        }
    }

    public void loadMinion(UUID uuid, IResult<EntityMinion> response) {
        if (this.isLoaded() && !this.isQuerying) {

            LagCatcher.start("mysql");
            Common.runAsync(() -> {
                runCatching(
                        () -> {
                            ResultSet result = this.query(FarmScript.getMinion(uuid));
                            if (result == null) {
                                throw new MinionException(uuid+" owner not found");
                            }
                            if (result.next()) {
                                UUID ownerUUID = UUID.fromString(result.getString(OWNER_UUID));
                                UUID entityUUID = UUID.fromString(result.getString(MINION_UUID));
                                MinionType entityType = MinionType.valueOf(result.getString(MINION_TYPE));

                                response.onSuccess(createEntityMinion(ownerUUID, entityUUID, entityType));
                            }

                        }, response::onFailure,
                        () -> {
                            this.isQuerying = false;
                        }
                );
            });
        }
    }

    private boolean existsUUID(@NonNull UUID uuid) throws SQLException {
        if (uuid == null) return false;
        ResultSet result = this.query("SELECT * FROM {from} WHERE " + OWNER_UUID + "= '" + uuid.toString() + "'");
        if (result == null) return false;
        if (result.next()) return result.getString(OWNER_UUID) != null;
        return false;
    }


    public MinionSQL() {
        if (!isConnected()) connect(
                DatabaseSettings.URL,
                DatabaseSettings.USERNAME,
                DatabaseSettings.PASSWORD,
                "minions_db"
        );
    }

    public void save() {

    }

}
