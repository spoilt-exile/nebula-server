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
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFields;
import tk.freaxsoftware.extras.faststorage.generic.EntityListReference;
import tk.freaxsoftware.extras.faststorage.reading.EntityReader;
import tk.freaxsoftware.extras.faststorage.writing.EntityWriter;

/**
 * User entity.
 * @author Stanislav Nepochatov
 */
public class User implements ECSVAble<String> {
    
    public static final String TYPE = "USER";
    
    public static final ECSVDefinition DEFINITION = ECSVDefinition.createNew()
            .addKey(String.class)
            .addPrimitive(ECSVFields.PR_STRING)
            .addPrimitive(ECSVFields.PR_STRING)
            .addPrimitive(ECSVFields.PR_STRING)
            .addPrimitive(ECSVFields.PR_STRING)
            .addPrimitive(ECSVFields.PR_WORD)
            .addPrimitive(ECSVFields.PR_BOOLEAN)
            .addDate("yyyyMMddHHmmssz")
            .addReferenceArray(Group.class, String.class)
            .addMap(null, null);
    
    /**
     * User login.
     */
    private String login;
    
    /**
     * User name.
     */
    private String username;
    
    /**
     * Basic user description.
     */
    private String description;
    
    /**
     * Image url for avatar.
     */
    private String imageUrl;
    
    /**
     * User mail address.
     */
    private String email;
    
    /**
     * Hashed password.
     */
    private String password;
    
    /**
     * Active user or not.
     */
    private Boolean active;
    
    /**
     * Date of user login expiration.
     */
    private Date expireDate;
    
    /**
     * Groups of user.
     */
    private EntityListReference<Group, String> groups;
    
    /**
     * Maps of additional attributes.
     */
    private Map<String, String> attrs;

    public User() {
    }

    public User(String login, String username, String description, String imageUrl, String email, String password, Boolean active, Date expireDate, String[] groupKeys, Map<String, String> attrs) {
        this.login = login;
        this.username = username;
        this.description = description;
        this.imageUrl = imageUrl;
        this.email = email;
        this.password = password;
        this.active = active;
        this.expireDate = expireDate;
        this.groups = new EntityListReference<>(Arrays.asList(groupKeys), Group.class, false);
        this.attrs = attrs;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public EntityListReference<Group, String> getGroups() {
        return groups;
    }

    public void setGroups(EntityListReference<Group, String> groups) {
        this.groups = groups;
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
        hash = 73 * hash + Objects.hashCode(this.login);
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
        final User other = (User) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.imageUrl, other.imageUrl)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.active, other.active)) {
            return false;
        }
        if (!Objects.equals(this.expireDate, other.expireDate)) {
            return false;
        }
        if (!Objects.equals(this.attrs, other.attrs)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "login=" + login + ", username=" + username + ", description=" + description + ", imageUrl=" + imageUrl + ", email=" + email + ", password=" + password + ", active=" + active + ", expireDate=" + expireDate + ", attrs=" + attrs + '}';
    }

    @Override
    public String getEntityType() {
        return TYPE;
    }

    @Override
    public String getKey() {
        return login;
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
        this.login = reader.readKey();
        this.username = reader.readString();
        this.description = reader.readString();
        this.imageUrl = reader.readString();
        this.email = reader.readString();
        this.password = reader.readWord();
        this.active = reader.readBoolean();
        this.expireDate = reader.readDate();
        this.groups = reader.readReferenceArray(Group.class);
        this.attrs = reader.readMap();
    }

    @Override
    public void writeToECSV(EntityWriter<String> writer) {
        writer.writeKey(login);
        writer.writeString(username);
        writer.writeString(description);
        writer.writeString(imageUrl);
        writer.writeString(email);
        writer.writeWord(password);
        writer.writeBoolean(active);
        writer.writeDate(expireDate);
        writer.writeReferenceArray(groups);
        writer.writeMap(attrs);
    }

    @Override
    public void update(ECSVAble<String> updatedEntity) {
        if (updatedEntity instanceof User) {
            User other = (User) updatedEntity;
            this.login = other.login;
            this.username = other.username;
            this.description = other.description;
            this.imageUrl = other.imageUrl;
            this.email = other.email;
            this.password = other.password;
            this.active = other.active;
            this.expireDate = other.expireDate;
            this.groups = other.groups;
            this.attrs = other.attrs;
        }
    }
    
}
