package com.choice.autofarm.config;

import com.choice.autofarm.util.SettingsContants;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.SimpleSettings;

import static com.choice.autofarm.util.SettingsContants.FARM_SETTINGS;

public class Settings extends SimpleSettings {


    public static class AutoFarmSettings {

        public static Integer DISTANCE_FARM;
        public static Boolean ALLOW_VERTICAL;

        private static void init() {
            SimpleSettings.LOCALE_PREFIX = "pt_BR";
            PLUGIN_PREFIX = "<gold>" + SimplePlugin.getNamed() + "</gold>";
            setPathPrefix(FARM_SETTINGS);
            DISTANCE_FARM = getInteger(SettingsContants.DISTANCE_FARM);
            ALLOW_VERTICAL = getBoolean(SettingsContants.ALLOW_VERTICAL);
        }

    }

    private static void init() {
        setPathPrefix(null);
    }
}
