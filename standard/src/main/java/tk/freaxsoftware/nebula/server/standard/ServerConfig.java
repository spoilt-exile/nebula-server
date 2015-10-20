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

import java.util.Properties;

/**
 * Server configuration class.
 * @author Stanislav Nepochatov
 */
public class ServerConfig {
    
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
    
    public ServerConfig() {
        
    }
}
