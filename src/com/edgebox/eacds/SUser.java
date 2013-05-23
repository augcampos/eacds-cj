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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * @return json string user with all fields filled OR Error message
     * <p><b>Note: </b> On success new fields are filled in the user parameter
     * @see CDUser
     */
    public boolean create(CDUser user) {
        try {
            if (user == null) {
                throw new Exception("Invalid user information!");
            }

            Map<String, String> params = new LinkedHashMap<>();
            params.put("method", "SUsers.createUser");
            params.put("param1", gson.toJson(user));

            String tt = CDConnection.Post(this.ServerJavaScriptInterface, params);

            CDPostResponse pr = gson.fromJson(tt, CDPostResponse.class);
            if (pr.success) {
                // new fields add to User clas
                CDUser nu = gson.fromJson(pr.data, CDUser.class);
                user.cloneData(nu);
                return true;
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, pr.log());
            }

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /**
     * Get user info
     *
     * @param userId
     * @return <CDUser>
     */
    public CDUser get(int userId) {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("method", "SUsers.getUser");
            params.put("param1", "" + userId);

            String tt = CDConnection.Post(this.ServerJavaScriptInterface, params);
            return gson.fromJson(tt, CDUser.class);

        } catch (CDConnectionException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ex.log());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * List all register users in de Content Server
     *
     * @param offset - initial offset of the list
     * @param limit - limit of users(rows) returned
     * @return a Collection<CDUser>
     */
    public Collection<CDUser> list(int offset, int limit) {
        Collection<CDUser> rt = new ArrayList<>();
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("method", "SUsers.listUsers");
            params.put("param1", "" + offset);
            params.put("param2", "" + limit);

            String tt = CDConnection.Post(this.ServerJavaScriptInterface, params);
            CDUser[] ar = gson.fromJson(tt, CDUser[].class);
            rt.addAll(Arrays.asList(ar));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return rt;
    }
}
