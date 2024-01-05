package com.choice.autofarm.config;

import com.choice.autofarm.util.SettingsContants;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.SimpleSettings;

import static com.choice.autofarm.util.SettingsContants.*;

public class Settings extends SimpleSettings {


    public static class AutoFarmSettings {

        public static Integer DISTANCE;
        public static Boolean ALLOW_VERTICAL;

        private static void init() {
            setPathPrefix(FARM_SETTINGS);
            DISTANCE = getInteger(DISTANCE_FARM);
            ALLOW_VERTICAL = getBoolean(SettingsContants.ALLOW_VERTICAL);
        }

    }


    public static class DatabaseSettings {

        public static String USERNAME;
        public static String PASSWORD;
        public static String URL;

        private static void init(){
            setPathPrefix(DATABASE_SETTINGS);
            USERNAME = getString(DB_USERNAME);
            PASSWORD = getString(DB_PASSWORD);
            URL = getString(DB_URL);
        }

    }

    private static void init() {
        setPathPrefix(null);
        PLUGIN_PREFIX = "<gold>" + SimplePlugin.getNamed() + "</gold>";
    }
}
