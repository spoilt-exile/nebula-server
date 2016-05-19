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

import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.QueryParamsMap;
import static spark.Spark.*;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;
import tk.freaxsoftware.nebula.server.lib.localehandler.LocaleHandler;
import tk.freaxsoftware.nebula.server.standard.SystemMain;
import static tk.freaxsoftware.nebula.server.standard.SystemMain.webTemplateEngine;
import tk.freaxsoftware.nebula.server.core.entities.User;
import tk.freaxsoftware.nebula.server.core.entities.handlers.UserHandler;
import tk.freaxsoftware.nebula.server.standard.utils.JWTTokenService;
import tk.freaxsoftware.nebula.server.core.utils.SHAHash;
import tk.freaxsoftware.nebula.server.standard.utils.UserHolder;

/**
 * Routes and filter for login operation.
 * @author Stanislav Nepochatov
 */
public class LoginRoutes {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRoutes.class);
    
    private static final UserHandler userHandler = (UserHandler) Handlers.getHandlerByClass(User.class);
    
    public static void init() {
        before("/*", (request, response) -> {
            User loginedUser = null;
            if (request.session().attribute("user") != null) {
                loginedUser = userHandler.getUserByLogin(request.session().attribute("user"));
            } else if (request.cookies().containsKey(SystemMain.config.getTokenCookieName())) {
                try {
                    Claims userClaims = JWTTokenService.getInstance().decryptToken(request.cookie(SystemMain.config.getTokenCookieName()));
                    loginedUser = userHandler.getUserByLogin(userClaims.getId());
                } catch (Exception ex) {
                    LOGGER.error("Unable to finish JWT auth", ex);
                }
            }
            if (loginedUser == null) {
                if (request.pathInfo().equals("/login")
                    || request.pathInfo().startsWith("/bower_components")
                    || request.pathInfo().startsWith("/dist")
                    || request.pathInfo().startsWith("/js")
                    || request.pathInfo().startsWith("/less")) {
                    return;
                } else {
                    request.session().removeAttribute("user");
                    response.redirect("/login");
                }
            } else {
                UserHolder.setUser(loginedUser);
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
            User user = userHandler.getUserByLogin(map.value("login"));
            if (user != null && user.getPassword().equals(SHAHash.hashPassword(map.value("password")))) {
                if (map.get("remember").value() != null && map.get("remember").value().equals("rememberTrue")) {
                    LOGGER.debug("Proceed JWT auth: " + user.getLogin());
                    JWTTokenService tokenService = JWTTokenService.getInstance();
                    response.cookie(SystemMain.config.getTokenCookieName(), tokenService.encryptToken(user), SystemMain.config.getTokenValidHours() * 3600);
                } else {
                    LOGGER.debug("Proceed session auth: " + user.getLogin());
                    request.session(true);
                    request.session().attribute("user", user.getLogin());
                }
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
