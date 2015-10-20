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

import freemarker.template.Configuration;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;
import tk.freaxsoftware.nebula.server.lib.localehandler.LocaleHandler;

/**
 * Nebula server main class.
 * @author Stanislav Nepochatov
 */
public class SystemMain {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(SystemMain.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        LOGGER.info("Starting Nebula server instance...");
        initLocalization();
        Spark.externalStaticFileLocation("web");
        
        FreeMarkerEngine freeExternalEngine = new FreeMarkerEngine();
        Configuration freeExternalConfig = new Configuration();
        freeExternalConfig.setDirectoryForTemplateLoading(new File("web"));
        freeExternalEngine.setConfiguration(freeExternalConfig);
        
        Spark.get("/", (req, res) -> {
            String localeKey = req.headers("Accept-Language").split("\\,")[0];
            LocaleHandler.Accesser lc = LocaleHandler.getAccessFor(localeKey);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("lc", lc);
            return new ModelAndView(attributes, "locale.ftl");
        }, freeExternalEngine);
    }
    
    /**
     * Init server localization files.
     */
    private static void initLocalization() {
        try {
            LocaleHandler.loadLocale("en", SystemMain.class.getClassLoader().getResourceAsStream("server_en_US.properties"));
            LocaleHandler.loadLocale("ru", SystemMain.class.getClassLoader().getResourceAsStream("server_ru_RU.properties"));
            LocaleHandler.loadLocale("uk", SystemMain.class.getClassLoader().getResourceAsStream("server_uk_UA.properties"));
        } catch (IOException ex) {
            LOGGER.error("unable to load locales", ex);
        }
    }
}
