/*
 * This file is part of Nebula Core library.
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
package tk.freaxsoftware.nebula.server.core.entities;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFields;
import tk.freaxsoftware.extras.faststorage.generic.EntityListReference;
import tk.freaxsoftware.extras.faststorage.reading.EntityReader;
import tk.freaxsoftware.extras.faststorage.writing.EntityWriter;

/**
 * User group entity.
 * @author Stanislav Nepochatov
 */
public class Group implements ECSVAble<String> {
    
    public static final String TYPE = "GROUP";
    
    public static final ECSVDefinition DEFINITION = ECSVDefinition.createNew()
            .addKey(String.class)
            .addPrimitive(ECSVFields.PR_STRING)
            .addPrimitive(ECSVFields.PR_STRING)
            .addReferenceArray(User.class, String.class)
            .addMap(null, null);
    
    /**
     * Group name.
     */
    private String name;
    
    /**
     * Group short description.
     */
    private String description;
    
    /**
     * Image url for group avatar.
     */
    private String imageUrl;
    
    /**
     * Users of group.
     */
    private EntityListReference<User, String> users;
    
    /**
     * Attribute map.
     */
    private Map<String, String> attrs;
    
    public Group() {}

    public Group(String name, String description, String imageUrl, String[] userKeys, Map<String, String> attrs) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.users = new EntityListReference<>(Arrays.asList(userKeys), User.class, false);
        this.attrs = attrs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public EntityListReference<User, String> getUsers() {
        return users;
    }

    public void setUsers(EntityListReference<User, String> users) {
        this.users = users;
    }

    public Map<String, String> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, String> attrs) {
        this.attrs = attrs;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.name);
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
        final Group other = (Group) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.imageUrl, other.imageUrl)) {
            return false;
        }
        if (!Objects.equals(this.attrs, other.attrs)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Group{" + "name=" + name + ", description=" + description + ", imageUrl=" + imageUrl + ", attrs=" + attrs + '}';
    }

    @Override
    public String getEntityType() {
        return TYPE;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public void setKey(String key) {
        throw new IllegalStateException("This entity doesn't support setting key.");
    }

    @Override
    public ECSVDefinition getDefinition() {
        return DEFINITION;
    }

    @Override
    public void readFromECSV(EntityReader<String> reader) {
        this.name = reader.readKey();
        this.description = reader.readString();
        this.imageUrl = reader.readString();
        this.users = reader.readReferenceArray(User.class);
        this.attrs = reader.readMap();
    }

    @Override
    public void writeToECSV(EntityWriter<String> writer) {
        writer.writeKey(this.name);
        writer.writeString(this.description);
        writer.writeString(this.imageUrl);
        writer.writeReferenceArray(users);
        writer.writeMap(attrs);
    }

    @Override
    public void update(ECSVAble<String> updatedEntity) {
        if (updatedEntity instanceof Group) {
            Group other = (Group) updatedEntity;
            this.name = other.name;
            this.description = other.description;
            this.imageUrl = other.imageUrl;
            this.users = other.users;
            this.attrs = other.attrs;
        }
    }
    
}
