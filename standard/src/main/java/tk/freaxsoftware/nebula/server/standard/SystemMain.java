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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;
import tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException;
import tk.freaxsoftware.extras.faststorage.ignition.FastStorageIgnition;
import tk.freaxsoftware.nebula.server.lib.loader.PluginLoader;
import tk.freaxsoftware.nebula.server.lib.localehandler.LocaleHandler;
import tk.freaxsoftware.nebula.server.standard.routes.LoginRoutes;
import tk.freaxsoftware.nebula.server.standard.routes.MainRoutes;

/**
 * Nebula server main class.
 * @author Stanislav Nepochatov
 */
public class SystemMain {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(SystemMain.class);
    
    public static ServerConfig config;
    
    public static PluginLoader loader;
    
    public static FreeMarkerEngine webTemplateEngine;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        LOGGER.info("Starting Nebula server instance...");
        
        config = new ServerConfig();
        Spark.port(config.getSparkPort());
        Spark.threadPool(config.getSparkThreadPoolMax(), 
                config.getSparkThreadPoolMin(), 30000);
        LocaleHandler.setDefaultLocale(config.getDefaultLocale());
        
        try {
            FastStorageIgnition.ignite(SystemMain.class.getClassLoader().getResourceAsStream("Entities.ign"));
        } catch (EntityProcessingException ex) {
            LOGGER.error("Error during loading of users or groups", ex);
            throw new RuntimeException("Unable to proceed");
        }

        File pluginFolder = new File("plugins/");
        loader = new PluginLoader(pluginFolder.getAbsolutePath());
        LOGGER.info("loading core plugins.");
        loader.loadCore();
        
        if (config.isPluginsEnabled()) {
            LOGGER.info("loading extra plugins.");
            loader.load();
        }
        
        initLocalization();
        Spark.externalStaticFileLocation("web");
        
        webTemplateEngine = new FreeMarkerEngine();
        Configuration freeExternalConfig = new Configuration();
        freeExternalConfig.setDirectoryForTemplateLoading(new File("web"));
        webTemplateEngine.setConfiguration(freeExternalConfig);
        
        //Init routes
        LoginRoutes.init();
        MainRoutes.init();
        
        //Plugin intt
        loader.startPlugins();
    }
    
    /**
     * Init server localization files.
     */
    private static void initLocalization() {
        try {
            LocaleHandler.loadLocale("en", SystemMain.class.getClassLoader()
                    .getResourceAsStream("server_en_US.properties"));
            LocaleHandler.loadLocale("ru", SystemMain.class.getClassLoader()
                    .getResourceAsStream("server_ru_RU.properties"));
            LocaleHandler.loadLocale("uk", SystemMain.class.getClassLoader()
                    .getResourceAsStream("server_uk_UA.properties"));
        } catch (IOException ex) {
            LOGGER.error("unable to load locales", ex);
        }
    }
}
