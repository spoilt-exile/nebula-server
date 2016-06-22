/*
 * This file is part of Nebula Server application.
 * 
 * Copyright (C) 2015 Freax Software
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

package tk.freaxsoftware.nebula.server.standard;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.freaxsoftware.nebula.server.lib.api.Features;

/**
 * Server configuration class.
 * @author Stanislav Nepochatov
 */
public class ServerConfig {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConfig.class);

    private static final String CONFIG_FILENAME = "nebula.properties";
    
    /**
     * Server configuration properties loaded from file.
     */
    private Properties optionsProperties;
    
    /**
     * Server configuration options enumeration with default values.
     */
    private enum Options {
        
        /**
         * Main address of this nebula instance. Used for generation permanent links.
         */
        NEBULA_MAIN_URL("nebula_main_url", "http://localhost:4444"),
        
        /**
         * Start web server on specified port.
         */
        NEBULA_SPARK_PORT("nebula_spark_port", 4444),
        
        /**
         * Web server maximum thread pool size.
         */
        NEBULA_SPARK_TPOOL_MAX("nebula_spark_tpool_max", 16),
        
        /**
         * Web server minimum thead pool size.
         */
        NEBULA_SPARK_TPOOL_MIN("nebula_spark_tpool_min", 2),
        
        /**
         * Enable system to load external plugins (except core plugins).
         */
        NEBULA_PLUGIN_LOADER_ENABLED("nebula_plugin_loader_enabled", true),
        
        /**
         * Required features which will run automatically.
         */
        NEBULA_PLUGIN_REQUIRED_FEATURES("nebula_plugin_required_features", new Features[] {Features.LOGIN_PROVIDER}, "LOGIN_PROVIDER"),
        
        /**
         * Default language locale for web ui.
         */
        NEBULA_UI_DEFAULT_LOCALE("nebula_ui_default_locale", "en"),
        
        /**
         * Auth cookie name.
         */
        NEBULA_TOKEN_COOKIE_NAME("nebula_token_cookie_name", "nebula_system"),
        
        /**
         * Secret for JWT token.
         */
        NEBULA_TOKEN_SECRET("nebula_token_secret", "secret"),
        
        /**
         * JWT token max age in hours.
         */
        NEBULA_TOKEN_VALID_HOURS("nebula_token_valid_hours", 24 * 30);
        
        /**
         * Option property key.
         */
        private final String propertyKey;
        
        /**
         * Default option value.
         */
        private final Object defaultValue;
        
        /**
         * Default properties value.
         */
        private final String propertyStrValue;
        
        /**
         * Default constructor.
         * @param givenKey option key;
         * @param givenValue default value;
         */
        Options(String givenKey, Object givenValue) {
            propertyKey = givenKey;
            defaultValue = givenValue;
            propertyStrValue = null;
        }

        private Options(String givenKey, Object givenValue, String giveStrValue) {
            this.propertyKey = givenKey;
            this.defaultValue = givenValue;
            this.propertyStrValue = giveStrValue;
        }
    }
    
    /**
     * Default constructor. Loads exists file or try to create new 
     * one if config absent.
     */
    public ServerConfig() {
        File configFile = new File(CONFIG_FILENAME);
        if (!configFile.exists()) {
            LOGGER.info("creating new config file " + CONFIG_FILENAME);
            Properties newProperties = new Properties();
            for (Options option: Options.values()) {
                newProperties.setProperty(option.propertyKey, 
                        option.propertyStrValue == null ? option.defaultValue.toString() : option.propertyStrValue);
            }
            try {
                newProperties.store(new FileWriter(CONFIG_FILENAME), null);
            } catch (IOException ex) {
                LOGGER.error("unable to create new config file", ex);
            }
        }
        optionsProperties = new Properties();
        try {
            optionsProperties.load(new FileReader(configFile));
        } catch (IOException ex) {
            LOGGER.error("unable to load config file", ex);
        }
        LOGGER.info("config loading complete");
        printConfig();
    }
    
    /**
     * Print current config into log.
     */
    private void printConfig() {
        LOGGER.info("===========================================================");
        LOGGER.info("Default url: " + getDefaultUrl());
        LOGGER.info("Spark port: " + getSparkPort());
        LOGGER.info("Spark max threads: " + getSparkThreadPoolMax());
        LOGGER.info("Spark min threads: " + getSparkThreadPoolMin());
        LOGGER.info("Plugins enabled: " + isPluginsEnabled());
        LOGGER.info("Required features: " + Arrays.toString(getRequiredFeatures()));
        LOGGER.info("Default locale: " + getDefaultLocale());
        LOGGER.info("Auth cookie name:" + getTokenCookieName());
        LOGGER.info("Auth token secret:" + getTokenSecret());
        LOGGER.info("Auth token max age:" + getTokenValidHours());
        LOGGER.info("===========================================================");
    }
    
    /**
     * Get Nebula instance default url.
     * @return url of this instance root;
     */
    public String getDefaultUrl() {
        return optionsProperties.containsKey(Options.NEBULA_MAIN_URL.propertyKey)
                ? optionsProperties.getProperty(Options.NEBULA_MAIN_URL.propertyKey)
                : Options.NEBULA_MAIN_URL.defaultValue.toString();
    }
    
    /**
     * Get port number for web server.
     * @return port number;
     */
    public Integer getSparkPort() {
        return optionsProperties.containsKey(Options.NEBULA_SPARK_PORT.propertyKey) 
                ? Integer.valueOf(optionsProperties.getProperty(Options.NEBULA_SPARK_PORT.propertyKey))
                : Integer.valueOf(Options.NEBULA_SPARK_PORT.defaultValue.toString());
    }
    
    /**
     * Get maximum count of threads for web server.
     * @return integer count;
     */
    public Integer getSparkThreadPoolMax() {
        return optionsProperties.containsKey(Options.NEBULA_SPARK_TPOOL_MAX.propertyKey) 
                ? Integer.valueOf(optionsProperties.getProperty(Options.NEBULA_SPARK_TPOOL_MAX.propertyKey))
                : Integer.valueOf(Options.NEBULA_SPARK_TPOOL_MAX.defaultValue.toString());
    }
    
    /**
     * Get minimum count of threads for web server.
     * @return integer count;
     */
    public Integer getSparkThreadPoolMin() {
        return optionsProperties.containsKey(Options.NEBULA_SPARK_TPOOL_MIN.propertyKey) 
                ? Integer.valueOf(optionsProperties.getProperty(Options.NEBULA_SPARK_TPOOL_MIN.propertyKey))
                : Integer.valueOf(Options.NEBULA_SPARK_TPOOL_MIN.defaultValue.toString());
    }
    
    /**
     * Get flag which enables load of non-core plugins.
     * @return boolean flag;
     */
    public Boolean isPluginsEnabled() {
        return optionsProperties.containsKey(Options.NEBULA_PLUGIN_LOADER_ENABLED.propertyKey)
                ? Boolean.valueOf(optionsProperties.getProperty(Options.NEBULA_PLUGIN_LOADER_ENABLED.propertyKey))
                : Boolean.valueOf(Options.NEBULA_PLUGIN_LOADER_ENABLED.defaultValue.toString());
    }
    
    /**
     * Get system required features.
     * @return array of features;
     */
    public Features[] getRequiredFeatures() {
        if (optionsProperties.containsKey(Options.NEBULA_PLUGIN_REQUIRED_FEATURES.propertyKey)) {
            String[] rawFeatures = optionsProperties.get(Options.NEBULA_PLUGIN_REQUIRED_FEATURES.propertyKey).toString().split(",");
            Features[] features = new Features[rawFeatures.length];
            for (int i = 0; i < rawFeatures.length; i++) {
                features[i] = Features.valueOf(rawFeatures[i]);
            }
            return features;
        } else {
            return (Features[]) Options.NEBULA_PLUGIN_REQUIRED_FEATURES.defaultValue;
        }
    }
    
    /**
     * Get default locale for user interface.
     * @return locale string e.g. 'en' or 'ru';
     */
    public String getDefaultLocale() {
        return optionsProperties.containsKey(Options.NEBULA_UI_DEFAULT_LOCALE.propertyKey)
                ? optionsProperties.getProperty(Options.NEBULA_UI_DEFAULT_LOCALE.propertyKey)
                : Options.NEBULA_UI_DEFAULT_LOCALE.defaultValue.toString();
    }
    
    /**
     * Get JWT token secret.
     * @return jwt secret string;
     */
    public String getTokenCookieName() {
        return optionsProperties.containsKey(Options.NEBULA_TOKEN_COOKIE_NAME.propertyKey)
                ? optionsProperties.getProperty(Options.NEBULA_TOKEN_COOKIE_NAME.propertyKey)
                : Options.NEBULA_TOKEN_COOKIE_NAME.defaultValue.toString();

    }
    
    /**
     * Get JWT token secret.
     * @return jwt secret string;
     */
    public String getTokenSecret() {
        return optionsProperties.containsKey(Options.NEBULA_TOKEN_SECRET.propertyKey)
                ? optionsProperties.getProperty(Options.NEBULA_TOKEN_SECRET.propertyKey)
                : Options.NEBULA_TOKEN_SECRET.defaultValue.toString();
    }
    
    /**
     * Get JWT token max age in hours.
     * @return amount of hours (integer);
     */
    public Integer getTokenValidHours() {
        return optionsProperties.containsKey(Options.NEBULA_TOKEN_VALID_HOURS.propertyKey) 
                ? Integer.valueOf(optionsProperties.getProperty(Options.NEBULA_TOKEN_VALID_HOURS.propertyKey))
                : Integer.valueOf(Options.NEBULA_TOKEN_VALID_HOURS.defaultValue.toString());
    }
}
