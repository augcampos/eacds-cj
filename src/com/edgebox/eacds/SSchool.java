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

import com.edgebox.eacds.data.CDSchool;
import com.edgebox.eacds.net.CDConnection;
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
public class SSchool extends SBaseModule {

    SSchool(String serverJavaScriptInterface) {
        super(serverJavaScriptInterface);
    }

    /**
     * List all Schools from the Content Server
     *
     * @param groupId - Group to list schools from
     * @param recursive - recursive in sub Groups
     * @param offset- start offset
     * @param limit - limit returned records
     * @return Collection<CDSchool>
     * @see CDSchool
     */
    public Collection<CDSchool> list(int groupId, boolean recursive, int offset, int limit) {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("method", "SSchools.listSchools");
            params.put("param1", "" + groupId);
            params.put("param2", "" + recursive);
            params.put("param3", "" + offset);
            params.put("param4", "" + limit);

            String tt = CDConnection.Post(this.ServerJavaScriptInterface, params);

            CDSchool[] ar = gson.fromJson(tt, CDSchool[].class);
            return new ArrayList<>(Arrays.asList(ar));

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
