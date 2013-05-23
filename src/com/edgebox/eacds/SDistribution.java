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
import com.edgebox.eacds.data.CDJob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Augusto Campos <a-campos at critical-links.com>
 */
public class SDistribution extends SBaseModule {

    SDistribution(String serverJavaScriptInterface) {
        super(serverJavaScriptInterface);
    }

    /**
     * List all running sync jobs in de Content Server
     *
     * @return Collection<CDJob>
     */
    public Collection<CDJob> listJobs() {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("method", "SDistribution.listJobs");

            String tt = CDConnection.Post(this.ServerJavaScriptInterface, params);

            CDJob[] ar = gson.fromJson(tt, CDJob[].class);
            return new ArrayList<>(Arrays.asList(ar));

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
