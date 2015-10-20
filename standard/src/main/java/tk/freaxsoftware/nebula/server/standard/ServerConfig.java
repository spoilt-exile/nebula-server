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
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
         * Default language locale for web ui.
         */
        NEBULA_UI_DEFAULT_LOCALE("nebula_ui_default_locale", "en");
        
        /**
         * Option property key.
         */
        private final String propertyKey;
        
        /**
         * Default option value.
         */
        private final Object defaultValue;
        
        /**
         * Default constructor.
         * @param givenKey option key;
         * @param givenValue default value;
         */
        Options(String givenKey, Object givenValue) {
            propertyKey = givenKey;
            defaultValue = givenValue;
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
                        option.defaultValue.toString());
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
     * Get default locale for user interface.
     * @return locale string e.g. 'en' or 'ru';
     */
    public String getDefaultLocale() {
        return optionsProperties.containsKey(Options.NEBULA_UI_DEFAULT_LOCALE.propertyKey)
                ? optionsProperties.getProperty(Options.NEBULA_UI_DEFAULT_LOCALE.propertyKey)
                : Options.NEBULA_UI_DEFAULT_LOCALE.defaultValue.toString();
    }
}
