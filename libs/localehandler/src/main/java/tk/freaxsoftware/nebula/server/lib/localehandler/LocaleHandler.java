/*
 * This file is part of LocaleHandler library.
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
package tk.freaxsoftware.nebula.server.lib.localehandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Locale handler main class.
 * @author Stanislav Nepochatov
 */
public final class LocaleHandler {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LocaleHandler.class);
    
    /**
     * Accessor's map.
     */
    private static final Map<String, Accesser> accessMap = new HashMap<>();
    
    /**
     * Internal map to store localization strings.
     */
    private static final Map<String, Map<String, String>> localeMap = new HashMap<>();
    
    /**
     * Default locale id for fallback.
     */
    private static String defaultLocaleKey = "en";
    
    /**
     * Set default locale for handler. Used for fallback.
     * @param locale localization id, e.g. 'en_US' or 'uk_UA'
     */
    public static void setDefaultLocale(String locale) {
        LOGGER.info("changing default locale " + defaultLocaleKey + " => " + locale);
        defaultLocaleKey = locale;
    }
    
    /**
     * Get access to localization information by raw {@code Accept-Language} 
     * HTTP request header.
     * @param headerString raw header string;
     * @return accesser instance for access;
     * @see Accesser
     */
    public static Accesser getAccessByHeader(String headerString) {
        String localeKey = headerString.split("\\,")[0];
        return getAccessFor(localeKey);
    }
    
    /**
     * Get access to localization information.
     * @param locale locale supplied by peer;
     * @return accesser instance for access;
     * @see Accesser
     */
    public static Accesser getAccessFor(String locale) {
        if (accessMap.containsKey(locale)) {
            LOGGER.info("returning accesser for " + locale);
            return accessMap.get(locale);
        } else {
            LOGGER.info("building accesser for " + locale);
            Accesser newAccess = new Accesser(locale);
            accessMap.put(locale, newAccess);
            return newAccess;
        }
    }
    
    /**
     * Load supplied resourece to localization map.
     * @param locale locale id;
     * @param inputStream resource stream;
     */
    public static void loadLocale(String locale, InputStream inputStream) throws IOException {
        if (inputStream != null) {
            LOGGER.info("loading resource for " + locale);
            Properties localeProperties = new Properties();
            localeProperties.load(inputStream);
            Map<String, String> currentLocaleMap;
            if (localeMap.containsKey(locale)) {
                currentLocaleMap = localeMap.get(locale);
            } else {
                currentLocaleMap = new HashMap<>();
                localeMap.put(locale, currentLocaleMap);
            }
            for (Entry<Object, Object> entry: localeProperties.entrySet()) {
                currentLocaleMap.put(entry.getKey().toString(), entry.getValue().toString());
            }
            LOGGER.info("loaiding complete. " + localeProperties.size() + " entries loaded.");
        } else {
            LOGGER.error("supplied stream for " + locale + " is null!");
        }

    }
    
    /**
     * Localization access utility class.
     */
    public static class Accesser {
        
        /**
         * Localization key for handling keys.
         */
        private final String localeKey;
        
        /**
         * Default class constructor.
         * @param locale locale for access to.
         */
        public Accesser(String locale) {
            localeKey = locale;
        }
        
        /**
         * Get localization string by it's key.
         * @param key key to search;
         * @return string from localization specified during accesser creation or fallback string;
         */
        public String get(String key) {
            if (localeMap.containsKey(localeKey) && localeMap.get(localeKey).containsKey(key)) {
                return localeMap.get(localeKey).get(key);
            } else {
                return localeMap.get(defaultLocaleKey).get(key);
            }
        }
    }
}
