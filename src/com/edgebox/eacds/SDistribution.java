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
     * @throws java.lang.Exception
     */
    public Collection<CDJob> listJobs() throws Exception {

        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SDistribution.listJobs");

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        Collection<CDJob> lrt = new ArrayList<>();
        CDJob[] ar = gson.fromJson(rt, CDJob[].class);
        lrt.addAll(Arrays.asList(ar));

        return lrt;
    }
}
