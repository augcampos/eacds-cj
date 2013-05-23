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

import com.edgebox.eacds.data.CDUser;
import com.edgebox.eacds.net.CDConnection;
import com.edgebox.eacds.net.CDConnectionException;
import com.edgebox.eacds.net.CDPostResponse;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

/**
 * Module to handle User related actions
 *
 * @author Augusto Campos <a-campos at critical-links.com>
 */
public class SUser extends SBaseModule {

    SUser(String serverJavaScriptInterface) {
        super(serverJavaScriptInterface);
    }

    /**
     * Create a new user
     *
     * @param user user into to create
     * <p><b>Note: </b> On success new fields are filled in the user parameter
     * @throws Exception
     * @see CDUser
     */
    public void create(CDUser user) throws Exception {
        if (user == null) {
            throw new Exception("Invalid user information!");
        }

        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SUsers.createUser");
        params.put("param1", gson.toJson(user));

        String tt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(tt, CDPostResponse.class);
        if (pr.success) {
            // new fields add to User class
            String user_jason = pr.data.toString();
            CDUser nu = gson.fromJson(user_jason, CDUser.class);
            user.cloneData(nu);
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * Update user Info
     *
     * @param user new Info about the user
     * <p><b>Note: </b> On success new fields are filled in the user parameter
     * @throws Exception
     */
    public void update(CDUser user) throws Exception {
        if (user == null) {
            throw new Exception("Invalid user information!");
        }

        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SUsers.updateUser");
        params.put("param1", gson.toJson(user));

        String tt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(tt, CDPostResponse.class);
        if (pr.success) {
            // new fields add to User class
            String user_jason = pr.data.toString();
            CDUser nu = gson.fromJson(user_jason, CDUser.class);
            user.cloneData(nu);
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * Removes a existing user.
     *
     * @param userId id of user to remove
     * @throws Exception
     */
    public void remove(int userId) throws Exception {

        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SUsers.removeUser");
        params.put("param1", "" + userId);

        String tt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(tt, CDPostResponse.class);

        if (!pr.success) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * Get user info
     *
     * @param userId id of user to get
     * @return <CDUser>
     * @throws java.lang.Exception
     */
    public CDUser get(int userId) throws Exception {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("method", "SUsers.getUser");
            params.put("param1", "" + userId);

            String tt = CDConnection.Post(this.ServerJavaScriptInterface, params);
            return gson.fromJson(tt, CDUser.class);

        } catch (CDConnectionException ex) {
            if (ex.getStatusCode() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                return null; // NOTE recive a 400 error on a not invalid id user
            }
            throw ex;
        }
    }

    /**
     * List all register users in de Content Server
     *
     * @param offset - initial offset of the list
     * @param limit - limit of users(rows) returned
     * @return a Collection<CDUser>
     * @throws Exception
     */
    public Collection<CDUser> list(int offset, int limit) throws Exception {
        Collection<CDUser> rt = new ArrayList<>();
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SUsers.listUsers");
        params.put("param1", "" + offset);
        params.put("param2", "" + limit);

        String tt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        CDUser[] ar = gson.fromJson(tt, CDUser[].class);
        rt.addAll(Arrays.asList(ar));

        return rt;
    }
}
