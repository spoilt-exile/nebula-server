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

import java.util.Objects;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import static tk.freaxsoftware.extras.faststorage.generic.ECSVFields.*;
import tk.freaxsoftware.extras.faststorage.reading.EntityReader;
import tk.freaxsoftware.extras.faststorage.writing.EntityWriter;
import tk.freaxsoftware.nebula.server.lib.api.NebulaPluginConflict;

/**
 * Conflict record for plugin.
 * @author spoilt
 */
public class ConflictRecord implements ECSVAble<String> {
    
    private final static String TYPE = "CONFLICT";
    
    /**
     * ECSV entity definition.
     */
    public static final ECSVDefinition DEFINITION = ECSVDefinition.createNew()
            .addKey(String.class)
            .addPrimitive(PR_WORD)
            .addPrimitive(PR_STRING);
    
    /**
     * Id of plugin which adds this record to the system.
     */
    private String pluginId;
    
    /**
     * Id of plugin which incompatible to current plugin.
     */
    private String conflictId;

    /**
     * Short description about incompatibility cause.
     */
    private String conflictDescription;

    /**
     * Default constructor.
     * @param callerId id of plugin which class marked with conflict annotation;
     * @param conflictAnnotation conflict annotation extracted from class;
     */
    public ConflictRecord(String callerId, NebulaPluginConflict conflictAnnotation) {
        this.pluginId = callerId;
        this.conflictId = conflictAnnotation.conflictId();
        this.conflictDescription = conflictAnnotation.conflictDescription();
    }
    
    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public String getConflictId() {
        return conflictId;
    }

    public void setConflictId(String conflictId) {
        this.conflictId = conflictId;
    }

    public String getConflictDescription() {
        return conflictDescription;
    }

    public void setConflictDescription(String conflictDescription) {
        this.conflictDescription = conflictDescription;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.pluginId);
        hash = 43 * hash + Objects.hashCode(this.conflictId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConflictRecord other = (ConflictRecord) obj;
        if (!Objects.equals(this.pluginId, other.pluginId)) {
            return false;
        }
        if (!Objects.equals(this.conflictId, other.conflictId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ConflictRecord{" + "pluginId=" + pluginId + ", conflictId=" + conflictId + '}';
    }

    @Override
    public String getKey() {
        return pluginId;
    }

    @Override
    public void setKey(String key) {
        //Do nothing...
    }

    @Override
    public ECSVDefinition getDefinition() {
        return DEFINITION;
    }

    @Override
    public void readFromECSV(EntityReader<String> reader) {
        this.pluginId = reader.readKey();
        this.conflictId = reader.readWord();
        this.conflictDescription = reader.readString();
    }

    @Override
    public void writeToECSV(EntityWriter<String> writer) {
        writer.writeKey(pluginId);
        writer.writeWord(conflictId);
        writer.writeString(conflictDescription);
    }

    @Override
    public void update(ECSVAble<String> updatedEntity) {
        //Do nothing...
    }
}
