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
import tk.freaxsoftware.nebula.server.lib.localehandler.LocaleHandler;
import tk.freaxsoftware.nebula.server.standard.SystemMain;
import static tk.freaxsoftware.nebula.server.standard.SystemMain.webTemplateEngine;

/**
 * Routes for main page.
 * @author Stanislav Nepochatov
 */
public class MainRoutes {
    
    public static void init() {
        get("/", (req, res) -> {
            LocaleHandler.Accesser lc = LocaleHandler
                    .getAccessByHeader(req.headers("Accept-Language"));
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("lc", lc);
            attributes.put("records", SystemMain.loader.getRecords());
            return new ModelAndView(attributes, "plugin.html");
        }, webTemplateEngine);
        
        post("/install", (req, res) -> {
            QueryParamsMap map = req.queryMap();
            SystemMain.loader.installAndStart(map.value("recordId"));
            res.redirect("/");
            return null;
        });
    }
    
}
