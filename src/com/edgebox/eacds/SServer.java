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

import com.edgebox.eacds.net.CDConnection;
import com.edgebox.eacds.data.CDServerVersion;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Module to handle server actions
 *
 * @author Augusto Campos <a-campos at critical-links.com>
 */
public class SServer extends SBaseModule {

    SServer(String serverJavaScriptInterface) {
        super(serverJavaScriptInterface);
    }

    /**
     * Get the Server version
     *
     * @return ServerVersion with server version information
     * @see ServerVersion
     */
    public CDServerVersion getVersion() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("method", "SServer.getVersion");

            String tt = CDConnection.Post(this.ServerJavaScriptInterface, params);

            CDServerVersion sv = gson.fromJson(tt, CDServerVersion.class);

            return sv;

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
