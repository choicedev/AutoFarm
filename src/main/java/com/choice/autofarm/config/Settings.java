package com.choice.autofarm.config;

import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.SimpleSettings;

import static com.choice.autofarm.util.SettingsConstants.*;

public class Settings extends SimpleSettings {

    @Override
    protected int getConfigVersion() {
        return 1;
    }

    public static class AutoFarmSettings {


        private static void init() {
            setPathPrefix(FARM_SETTINGS);
        }

        public static class StoneSettings {
            public static String STONE_NAME;
            public static Integer STONE_DISTANCE;
            public static String STONE_SKIN;
            public static String STONE_ARMOR_NBT;
            public static String STONE_HAND_ITEM_NBT;
            public static Boolean STONE_ALLOW_VERTICAL;

            private static void init() {
                setPathPrefix(FARM_SETTINGS+"."+FARM_STONE);
                STONE_NAME = getString(FARM_NAME);
                STONE_DISTANCE = getInteger(DISTANCE);
                STONE_SKIN = getString(HEAD_SKIN);
                STONE_ARMOR_NBT = getString(ARMOR_NBT);
                STONE_HAND_ITEM_NBT = getString(HAND_ITEM_NBT);
                STONE_ALLOW_VERTICAL = getBoolean(ALLOW_VERTICAL);
            }
        }

        public static class WheatSettings {
            public static String  WHEAT_NAME;
            public static Integer WHEAT_DISTANCE;
            public static String  WHEAT_SKIN;
            public static Boolean WHEAT_ALLOW_VERTICAL;
            public static String WHEAT_ARMOR_NBT;

            public static String WHEAT_HAND_ITEM_NBT;
            private static void init() {
                setPathPrefix(FARM_SETTINGS+"."+FARM_WHEAT);
                WHEAT_NAME = getString(FARM_NAME);
                WHEAT_DISTANCE = getInteger(DISTANCE);
                WHEAT_SKIN = getString(HEAD_SKIN);
                WHEAT_ARMOR_NBT = getString(ARMOR_NBT);
                WHEAT_HAND_ITEM_NBT = getString(HAND_ITEM_NBT);
                WHEAT_ALLOW_VERTICAL = getBoolean(ALLOW_VERTICAL);
            }
        }

    }


    public static class DatabaseSettings {

        public static String USERNAME;
        public static String PASSWORD;
        public static String URL;

        private static void init() {
            setPathPrefix(DATABASE_SETTINGS);
            USERNAME = getString(DB_USERNAME);
            PASSWORD = getString(DB_PASSWORD);
            URL = getString(DB_URL);
        }

    }

    private static void init() {
        setPathPrefix(null);
    }
}
