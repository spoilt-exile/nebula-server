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
import tk.freaxsoftware.nebula.server.lib.loader.ConflictRecord;

/**
 * Conflict record handler.
 * @author Stanislav Nepochatov
 */
public class ConflictRecordHandler extends AbstractEntityHandler<ConflictRecord, String> {

    public ConflictRecordHandler(String filePath) {
        super(ConflictRecord.class, ConflictRecord.DEFINITION, filePath);
    }

    @Override
    public String getNewKey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ConflictRecord> find(String query) {
        List<ConflictRecord> list = new ArrayList<>();
        synchronized (entitiesLock) {
            for (ConflictRecord record: entitiesStore) {
                if (record.getPluginId().equalsIgnoreCase(query) || record.getPluginId().contains(query)
                        || record.getConflictId().equalsIgnoreCase(query) || record.getConflictId().contains(query)) {
                    list.add(record);
                }
            }
        }
        return list;
    }

    @Override
    public ConflictRecord getNewEntity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getType() {
        return ConflictRecord.TYPE;
    }
    
}
