/*
 * This file is part of Nebula plugin loader library.
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
package tk.freaxsoftware.nebula.server.lib.loader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException;
import tk.freaxsoftware.extras.faststorage.ignition.FastStorageIgnition;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;
import tk.freaxsoftware.nebula.server.lib.api.NebulaPlugin;
import tk.freaxsoftware.nebula.server.lib.api.NebulaPluginConflict;
import tk.freaxsoftware.nebula.server.lib.api.Plugable;
import tk.freaxsoftware.nebula.server.lib.loader.storage.ConflictRecordHandler;
import tk.freaxsoftware.nebula.server.lib.loader.storage.PluginRecordHandler;

/**
 * Nebula plugin loader entry class;
 * @author Stanislav Nepochatov
 */
public class PluginLoader {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginLoader.class);
    
    /**
     * Records of plugins which were loaded into system.
     */
    private PluginRecordHandler pluginRecordHandler;
    
    /**
     * Records of conflicts between plugins.
     */
    private ConflictRecordHandler conflictRecordHandler;
    
    /**
     * Path of plugin directory on disk.
     */
    private final String path;
    
    /**
     * Default constructor.
     * @param givenPath path to plugin directory.
     */
    public PluginLoader(String givenPath) {
        path = givenPath;
        try {
            FastStorageIgnition.ignite(getClass().getClassLoader().getResource("Loader.ign").openStream());
        } catch (EntityProcessingException | IOException ex) {
            LOGGER.error("Unable to ignite plugin storages", ex);
            throw new RuntimeException("Unable to proceed without configured plugins.");
        }
        pluginRecordHandler = (PluginRecordHandler) Handlers.getHandlerByClass(PluginRecord.class);
        conflictRecordHandler = (ConflictRecordHandler) Handlers.getHandlerByClass(ConflictRecord.class);
    }
    
    /**
     * Load core plugins of the Nebula system.
     */
    public void loadCore() {
        try {
            pluginRecordHandler.save(tryModuleClass(Class.forName("tk.freaxsoftware.nebula.server.core.sync.SyncPlugin")));
            LOGGER.info("Core sync plugin loaded.");
        } catch (ClassNotFoundException cex) {
            LOGGER.error("unable to load core sync plugin!" , cex);
        }
    }
    
    public void startPlugins() {
        List<PluginRecord> records = pluginRecordHandler.getByStatus(PluginStatus.STARTED);
        for (PluginRecord record: records) {
            LOGGER.info("Starting plugin " + record.getId());
            try {
                record.getInstance().start();
            } catch (Exception ex) {
                LOGGER.error("failed to start plugin " + record.getId(), ex);
                record.setStatus(PluginStatus.START_ERROR);
                pluginRecordHandler.save(record);
            }
        }
    }
    
    /**
     * Loads plugins from plugin directory, fill records and conflicts lists.
     */
    public void load() {
        File[] modulesRaw = new File(path).listFiles();
        if (modulesRaw != null) {
            for (File moduleFile : modulesRaw) {
                processModule(moduleFile.getName(), path);
            }
        }
    }
    
    /**
     * Process single file as plugin.
     * @param fileName name of file;
     * @param restOfPath rest of path to this file;
     * @param classes lists of records to fill;
     */
    private void processModule(String fileName, String restOfPath) {
        try {
            JarFile jarFile;
            java.net.URLClassLoader loader;
            loader = java.net.URLClassLoader.newInstance(new URL[] {new URL("jar:file:" + restOfPath + "/" + fileName + "!/")});
            jarFile = new JarFile(restOfPath + "/" + fileName);
            Enumeration<JarEntry> entries = jarFile.entries();
            while(entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                String className = null;
                if(entryName.endsWith(".class")) {
                    className = entry.getName().substring(0,entry.getName().length()-6).replace('/', '.');
                }
                if (className != null) {
                    try {
                        Class loadedClass = loader.loadClass(className);
                        PluginRecord module = tryModuleClass(loadedClass);
                        if (module != null) {
                            LOGGER.info("Loaded module " + module.getId() + " - " + className);
                            pluginRecordHandler.save(module);
                        }
                    } catch (ClassNotFoundException ex) {
                        LOGGER.error("Can't load class " + className, ex);
                    }
                }
            }
        } catch (java.net.MalformedURLException ex) {
            LOGGER.error("Incorrect URL for file " + fileName + "!", ex);
        } catch (IOException ex) {
            LOGGER.error("Can't read file " + fileName + "!", ex);
        }
    }
    
    /**
     * Process checking of single class inside jar file. If 
     * class passes check then it will be instanced and add to records.
     * @param givenClass loaded class;
     * @return plugin record with instance;
     */
    private PluginRecord tryModuleClass(Class givenClass) {
        NebulaPlugin plugAnnotation = null;
        try {
            plugAnnotation = (NebulaPlugin) givenClass.getAnnotation(NebulaPlugin.class);
            if (plugAnnotation != null && Plugable.class.isAssignableFrom(givenClass)) {
                Plugable pluginInstance = (Plugable) givenClass.newInstance();
                NebulaPluginConflict[] conflicts = (NebulaPluginConflict[]) givenClass.getAnnotationsByType(NebulaPluginConflict.class);
                if (conflicts != null) {
                    for (NebulaPluginConflict conflictEntry: conflicts) {
                        processModuleConflicts(plugAnnotation.id(), conflictEntry);
                    }
                }
                return new PluginRecord(plugAnnotation, pluginInstance);
            } else {
                return null;
            }
        } catch (IllegalAccessException ex) {
            LOGGER.error("Class " + givenClass.getName() + " doesn't have public empty constructor.", ex);
            return plugAnnotation != null ? new PluginRecord(plugAnnotation, null) : null;
        } catch (InstantiationException ex) {
            LOGGER.error("Class " + givenClass.getName() + " can't be instanced as object.", ex);
            return plugAnnotation != null ? new PluginRecord(plugAnnotation, null) : null;
        }
    }
    
    /**
     * Process conflict annotation on plugin class.
     * @param pluginId plugin id which marked by conflict;
     * @param conflict annotation extracted from class;
     */
    private void processModuleConflicts(String pluginId, NebulaPluginConflict conflict) {
        LOGGER.warn("add conflicvt record for plugin " + pluginId + " with conflict id " + conflict.conflictId());
        conflictRecordHandler.save(new ConflictRecord(pluginId, conflict));
    }
    
    /**
     * Get all plugin records.
     * @return list of records;
     */
    public List<PluginRecord> getRecords() {
        return pluginRecordHandler.getAll();
    }
    
    /**
     * Get all conflict records.
     * @return list of conflicts;
     */
    public List<ConflictRecord> getConflicts() {
        return conflictRecordHandler.getAll();
    }
    
    /**
     * Installs plugin and call it to start. Updates plugin status 
     * corresponding to all successfull steps.
     * @param pluginId plugin to install and run;
     */
    public void installAndStart(String pluginId) {
        PluginRecord record = pluginRecordHandler.getRecordById(pluginId);
        if (record != null) {
            LOGGER.warn("Installing plugin with id " + pluginId);
            if (record.getStatus() != PluginStatus.INIT_ERROR) {
                
                //Install step
                try {
                    record.getInstance().install();
                    record.setStatus(PluginStatus.INSTALLED);
                } catch (Exception ex) {
                    LOGGER.error("failed to install plugin " + pluginId, ex);
                    record.setStatus(PluginStatus.INSTALL_ERROR);
                }
                
                //Start step
                try {
                    record.getInstance().start();
                    record.setStatus(PluginStatus.STARTED);
                } catch (Exception ex) {
                    LOGGER.error("failed to start plugin " + pluginId, ex);
                    record.setStatus(PluginStatus.START_ERROR);
                }
                
                pluginRecordHandler.save(record);
            } else {
                LOGGER.error("Pluggin unable to install or start cause initial error " + pluginId + " " + record.getStatus());
            }
        } else {
            LOGGER.error("Can't install plugin with id " + pluginId + "; plugin doesn't exsist!");
        }
    }
}
