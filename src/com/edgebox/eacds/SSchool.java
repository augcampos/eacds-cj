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
import com.edgebox.eacds.net.CDPostResponse;
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
     * @throws java.lang.Exception
     * @see CDSchool
     */
    public Collection<CDSchool> listSchools(int groupId, boolean recursive, int offset, int limit) throws Exception {

        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SSchools.listSchools");
        params.put("param1", "" + groupId);
        params.put("param2", "" + recursive);
        params.put("param3", "" + offset);
        params.put("param4", "" + limit);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        Collection<CDSchool> lrt = new ArrayList<>();
        CDSchool[] ar = gson.fromJson(rt, CDSchool[].class);
        lrt.addAll(Arrays.asList(ar));

        return lrt;
    }

    /**
     * Get School info
     *
     * @param schoolId
     * @return CDSchool
     * @throws Exception
     */
    public CDSchool getSchool(int schoolId) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SSchools.getSchool");
        params.put("param1", "" + schoolId);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        return gson.fromJson(rt, CDSchool.class);

    }

    /**
     * Create new School info
     *
     * @param school CDSchool
     * @throws Exception
     */
    public void createSchool(CDSchool school) throws Exception {
        this.setSchool("SSchools.createSchool", school);
    }

    /**
     * Update School Info
     *
     * @param school
     * @throws Exception
     */
    public void updateSchool(CDSchool school) throws Exception {
        this.setSchool("SSchools.updateSchool", school);

    }

    private void setSchool(String action, CDSchool school) throws Exception {
        if (school == null) {
            throw new Exception("Invalid school information!");
        }
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", action);
        params.put("param1", gson.toJson(school));

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (pr.success) {
            // new fields add to class
            String user_jason = pr.data.toString();
            CDSchool nu = gson.fromJson(user_jason, CDSchool.class);
            school.cloneData(nu);
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * Remove an existing schools
     *
     * @param schoolId
     * @throws Exception
     */
    public void removeSchool(int schoolId) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SSchools.removeSchool");
        params.put("param1", "" + schoolId);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (!pr.success) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    public void moveSchool() throws Exception {
        throw new Exception("NOT IMPLEMENTED...");
    }

    public void moveGroupToGroup() throws Exception {
        throw new Exception("NOT IMPLEMENTED...");
    }

    public void getGroup() throws Exception {
        throw new Exception("NOT IMPLEMENTED...");
    }

    public void getSubGroups() throws Exception {
        throw new Exception("NOT IMPLEMENTED...");
    }

    public void createGroup() throws Exception {
        throw new Exception("NOT IMPLEMENTED...");
    }

    public void updateGroup() throws Exception {
        throw new Exception("NOT IMPLEMENTED...");
    }

    public void removeGroup() throws Exception {
        throw new Exception("NOT IMPLEMENTED...");
    }

}
