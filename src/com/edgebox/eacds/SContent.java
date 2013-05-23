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
import com.edgebox.eacds.data.CDPackage;
import com.edgebox.eacds.data.CDPackageContent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Module to handle Content related actions
 *
 * @author Augusto Campos <a-campos at critical-links.com>
 */
public class SContent extends SBaseModule {

    SContent(String serverJavaScriptInterface) {
        super(serverJavaScriptInterface);
    }

    /**
     * List all packages subscribed from a school
     *
     * @param schoolId
     * @return
     */
    public Collection<CDPackage> listSchoolPackagesSubscribed(int schoolId) {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("method", "SContents.getPackagesSubscribe");
            params.put("param1", "" + schoolId);

            String tt = CDConnection.Post(this.ServerJavaScriptInterface, params);

            CDPackage[] ar = gson.fromJson(tt, CDPackage[].class);
            return new ArrayList<>(Arrays.asList(ar));

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * List all Packages from the Content Server
     *
     * @param text - ?? Always empty
     * @param offset- start offset
     * @param limit - limit returned records
     * @return Collection<CDPackage>
     * @see CDPackage
     */
    public Collection<CDPackage> listPackage(String text, int offset, int limit) {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("method", "SContents.listPackages");
            params.put("param1", "\"" + text + "\"");
            params.put("param2", "" + offset);
            params.put("param3", "" + limit);

            String tt = CDConnection.Post(this.ServerJavaScriptInterface, params);

            CDPackage[] ar = gson.fromJson(tt, CDPackage[].class);
            return new ArrayList<>(Arrays.asList(ar));

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * List Package Content (files and directories)
     *
     * @param pakageId - Package id to list content
     * @param offset- start offset
     * @param limit - limit returned records
     * @return Collection<CDPackageContent>
     * @see* CDPackageContent
     */
    public Collection<CDPackageContent> listPackageContent(int pakageId, int offset, int limit) {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("method", "SContents.listPackage");
            params.put("param1", "" + pakageId);
            params.put("param2", "" + offset);
            params.put("param3", "" + limit);

            String tt = CDConnection.Post(this.ServerJavaScriptInterface, params);

            CDPackageContent[] ar = gson.fromJson(tt, CDPackageContent[].class);
            return new ArrayList<>(Arrays.asList(ar));

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
