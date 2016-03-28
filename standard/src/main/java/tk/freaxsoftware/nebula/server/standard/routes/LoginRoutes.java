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
package tk.freaxsoftware.nebula.server.standard.routes;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.QueryParamsMap;
import static spark.Spark.*;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;
import tk.freaxsoftware.nebula.server.lib.localehandler.LocaleHandler;
import static tk.freaxsoftware.nebula.server.standard.SystemMain.webTemplateEngine;
import tk.freaxsoftware.nebula.server.standard.entities.User;
import tk.freaxsoftware.nebula.server.standard.entities.handlers.UserHandler;
import tk.freaxsoftware.nebula.server.standard.utils.SHAHash;

/**
 * Routes and filter for login operation.
 * @author Stanislav Nepochatov
 */
public class LoginRoutes {
    
    public static void init() {
        before("/*", (request, response) -> {
            if (request.session().attribute("user") == null && !request.pathInfo().equals("/login")
                    && !request.pathInfo().startsWith("/bower_components")
                    && !request.pathInfo().startsWith("/dist")
                    && !request.pathInfo().startsWith("/js")
                    && !request.pathInfo().startsWith("/less")) {
                response.redirect("/login");
            }
        });
        
        get("/login", (request, response) -> {
            LocaleHandler.Accesser lc = LocaleHandler
                    .getAccessByHeader(request.headers("Accept-Language"));
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("lc", lc);
            if (request.session().attribute("error") != null) {
                attributes.put("error", request.session().attribute("error"));
                request.session().removeAttribute("error");
            }
            return new ModelAndView(attributes, "login.html");
        }, webTemplateEngine);
        
        post("/login", (request, response) -> {
            QueryParamsMap map = request.queryMap();
            UserHandler userHandler = (UserHandler) Handlers.getHandlerByClass(User.class);
            User user = userHandler.getUserByLogin(map.value("login"));
            if (user != null && user.getPassword().equals(SHAHash.hashPassword(map.value("password")))) {
                request.session(true);
                request.session().attribute("user", user.getLogin());
                response.redirect("/");
            } else {
                request.session(true);
                request.session().attribute("error", "server_login_error_message");
                response.redirect("/login");
            }
            return null;
        });
    }
    
}
