/*
 *
 * Copyright (c) Critical Links S.A., All Rights Reserved.
 * (www.critical-links.com)
 *
 * This software is the proprietary information of Critical Links S.A. Use is
 * subject to license terms.
 *
 */
package com.edgebox.eacds;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

/**
 *
 * @author Augusto Campos <a-campos at critical-links.com>
 */
abstract class SBaseModule {

    protected final String ServerJavaScriptInterface;
    protected final JsonParser jsonParser = new JsonParser();
    protected final Gson gson = new Gson();

    SBaseModule(String serverJavaScriptInterface) {
        this.ServerJavaScriptInterface = serverJavaScriptInterface;
    }
}
