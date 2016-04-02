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
import tk.freaxsoftware.extras.faststorage.generic.ECSVFields;
import tk.freaxsoftware.extras.faststorage.reading.EntityReader;
import tk.freaxsoftware.extras.faststorage.writing.EntityWriter;
import tk.freaxsoftware.nebula.server.lib.api.NebulaPlugin;
import tk.freaxsoftware.nebula.server.lib.api.Plugable;
import tk.freaxsoftware.nebula.server.lib.api.PluginTypes;

/**
 * System record of plugin which present in the system.
 * @author Stanislav Nepochatov
 */
public class PluginRecord implements ECSVAble<String> {
    
    public final static String TYPE = "PLUGIN";
    
    /**
     * ECSV entity definition.
     */
    public final static ECSVDefinition DEFINITION = ECSVDefinition.createNew()
            .addKey(String.class)
            .addPrimitive(ECSVFields.PR_WORD)
            .addPrimitive(ECSVFields.PR_WORD)
            .addPrimitive(ECSVFields.PR_STRING)
            .addPrimitive(ECSVFields.PR_STRING)
            .addPrimitive(ECSVFields.PR_STRING)
            .addPrimitive(ECSVFields.PR_INT)
            .addPrimitive(ECSVFields.PR_STRING)
            .addPrimitive(ECSVFields.PR_BOOLEAN)
            .addPrimitive(ECSVFields.PR_WORD)
            .addPrimitive(ECSVFields.PR_WORD);
    
    /**
     * Internal id of plugin in the system.
     */
    private String id;
    
    /**
     * Name of plugin which user can see.
     */
    private String name;
    
    /**
     * Type of plugin.
     * @see PluginTypes
     */
    private PluginTypes type;
    
    /**
     * Short description of plugin.
     */
    private String description;
    
    /**
     * Url to plugin homepage.
     */
    private String homepage;
    
    /**
     * Path to plugin icon in resource folder.
     */
    private String icon;
    
    /**
     * Version iteration counter for correct upgrade.
     */
    private int versionCode;
    
    /**
     * Version string name for display.
     */
    private String versionName;
    
    /**
     * Flag which launces tranlation loading from plugin.
     */
    private Boolean initLocalization;
    
    /**
     * Entry class name which marked by NebulaPlugin annotation.
     * @see NebulaPlugin
     */
    private String classEntryName;
    
    /**
     * Plugin current status.
     * @see PluginStatus
     */
    private PluginStatus status;
    
    /**
     * Initiated plugin instance.
     */
    private transient Plugable instance;
    
    public PluginRecord() {}

    /**
     * Default constructor.
     * @param pluginAnnotation annotation extracted from plugin class.
     * @param instance plugin inited instance.
     */
    public PluginRecord(NebulaPlugin pluginAnnotation, Plugable instance) {
        this.id = pluginAnnotation.id();
        this.name = pluginAnnotation.name();
        this.type = pluginAnnotation.type();
        this.description = pluginAnnotation.description();
        this.homepage = pluginAnnotation.homepage();
        this.icon = pluginAnnotation.icon();
        this.versionCode = pluginAnnotation.versionCode();
        this.versionName = pluginAnnotation.versionName();
        this.initLocalization = pluginAnnotation.initLocalization();
        this.classEntryName = instance.getClass().getName();
        this.instance = instance;
        if (this.instance == null) {
            this.status = PluginStatus.INIT_ERROR;
        } else {
            this.status = PluginStatus.PRESENT;
        }
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PluginTypes getType() {
        return type;
    }

    public void setType(PluginTypes type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Boolean isInitLocalization() {
        return initLocalization;
    }

    public void setInitLocalization(Boolean initLocalization) {
        this.initLocalization = initLocalization;
    }

    public String getClassEntryName() {
        return classEntryName;
    }

    public void setClassEntryName(String classEntryName) {
        this.classEntryName = classEntryName;
    }

    public Plugable getInstance() {
        return instance;
    }

    public void setInstance(Plugable instance) {
        this.instance = instance;
    }

    public PluginStatus getStatus() {
        return status;
    }

    public void setStatus(PluginStatus status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + this.versionCode;
        hash = 29 * hash + Objects.hashCode(this.versionName);
        hash = 29 * hash + Objects.hashCode(this.classEntryName);
        hash = 29 * hash + Objects.hashCode(this.status);
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
        final PluginRecord other = (PluginRecord) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (this.versionCode != other.versionCode) {
            return false;
        }
        if (!Objects.equals(this.versionName, other.versionName)) {
            return false;
        }
        if (!Objects.equals(this.classEntryName, other.classEntryName)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PluginRecord{" + "id=" + id + ", name=" + name + ", type=" + type + ", status=" + status + ", versionCode=" + versionCode + ", versionName=" + versionName + '}';
    }

    @Override
    public String getKey() {
        return getId();
    }

    @Override
    public ECSVDefinition getDefinition() {
        return DEFINITION;
    }

    @Override
    public void readFromECSV(EntityReader<String> reader) {
        this.id = reader.readKey();
        this.name = reader.readWord();
        this.type = PluginTypes.valueOf(reader.readWord());
        this.description = reader.readString();
        this.homepage = reader.readString();
        this.icon = reader.readString();
        this.versionCode = reader.readInteger();
        this.versionName = reader.readString();
        this.initLocalization = reader.readBoolean();
        this.classEntryName = reader.readWord();
        this.status = PluginStatus.valueOf(reader.readWord());
    }

    @Override
    public void writeToECSV(EntityWriter<String> writer) {
        writer.writeKey(id);
        writer.writeWord(name);
        writer.writeWord(type.name());
        writer.writeString(description);
        writer.writeString(homepage);
        writer.writeString(icon);
        writer.writeInteger(versionCode);
        writer.writeString(versionName);
        writer.writeBoolean(initLocalization);
        writer.writeWord(classEntryName);
        writer.writeWord(status.name());
    }

    @Override
    public void setKey(String key) {
        //Do nothing
    }

    @Override
    public void update(ECSVAble<String> updatedEntity) {
        if (updatedEntity instanceof PluginRecord) {
            PluginRecord updatedRecord = (PluginRecord) updatedEntity;
            this.setId(updatedRecord.getId());
            this.setName(updatedRecord.getName());
            this.setType(updatedRecord.getType());
            this.setDescription(updatedRecord.getDescription());
            this.setHomepage(updatedRecord.getHomepage());
            this.setIcon(updatedRecord.getIcon());
            this.setVersionCode(updatedRecord.getVersionCode());
            this.setVersionName(updatedRecord.getVersionName());
            this.setInitLocalization(updatedRecord.isInitLocalization());
            this.setClassEntryName(updatedRecord.getClassEntryName());
            this.setStatus(this.getStatus() != PluginStatus.PRESENT && updatedRecord.getStatus() == PluginStatus.PRESENT ? this.getStatus() : updatedRecord.getStatus());
            this.setInstance(updatedRecord.getInstance());
        }
    }

    @Override
    public String getEntityType() {
        return TYPE;
    }
}
