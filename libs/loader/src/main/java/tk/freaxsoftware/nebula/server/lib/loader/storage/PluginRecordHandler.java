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
package tk.freaxsoftware.nebula.server.lib.loader.storage;

import java.util.ArrayList;
import java.util.List;
import tk.freaxsoftware.extras.faststorage.storage.AbstractEntityHandler;
import tk.freaxsoftware.nebula.server.lib.api.Features;
import tk.freaxsoftware.nebula.server.lib.loader.PluginRecord;
import tk.freaxsoftware.nebula.server.lib.loader.PluginStatus;

/**
 * Plugin record entity handler.
 * @author Stanislav Nepochatov
 */
public class PluginRecordHandler extends AbstractEntityHandler<PluginRecord, String> {

    public PluginRecordHandler(String filePath) {
        super(PluginRecord.class, PluginRecord.DEFINITION, filePath);
    }

    @Override
    public String getNewKey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PluginRecord> find(String query) {
        List<PluginRecord> list = new ArrayList<>();
        synchronized (entitiesLock) {
            for (PluginRecord record: entitiesStore) {
                if (record.getId().equalsIgnoreCase(query) || record.getId().contains(query)) {
                    list.add(record);
                }
            }
        }
        return list;
    }

    @Override
    public PluginRecord getNewEntity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getType() {
        return PluginRecord.TYPE;
    }

    /**
     * Get plugin record with specified id.
     * @param pluginId id to search;
     * @return record or null if not found;
     */
    public PluginRecord getRecordById(String pluginId) {
        synchronized (entitiesLock) {
            for (PluginRecord record: entitiesStore) {
                if (record.getId().equals(pluginId)) {
                    return record;
                }
            }
        }
        return null;
    }
    
    public List<PluginRecord> getRecordsByFeature(Features feature) {
        List<PluginRecord> result = new ArrayList<>();
        synchronized (entitiesLock) {
            for (PluginRecord record: entitiesStore) {
                if (record.getFeatures().containsKey(feature)) {
                    result.add(record);
                }
            }
        }
        return result;
    }

    @Override
    public void onStorageCreation() {
        //Do nothing
    }
    
    /**
     * Get plugin records list by status.
     * @param status status to search;
     * @return list of records with specified status;
     */
    public List<PluginRecord> getByStatus(PluginStatus status) {
        List<PluginRecord> result = new ArrayList<>();
        synchronized(this.entitiesLock) {
            for (PluginRecord record: entitiesStore) {
                if (record.getStatus() == status) {
                    result.add(record);
                }
            }
        }
        return result;
    }
}
