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
        this.setUser("SUsers.createUser", user);
    }

    /**
     * Update user Info
     *
     * @param user new Info about the user
     * <p><b>Note: </b> On success new fields are filled in the user parameter
     * @throws Exception
     */
    public void update(CDUser user) throws Exception {
        this.setUser("SUsers.updateUser", user);
    }

    private void setUser(String action, CDUser user) throws Exception {
        if (user == null) {
            throw new Exception("Invalid user information!");
        }

        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", action);
        params.put("param1", gson.toJson(user));

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
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
     * Change User password for the current logged user
     *
     * @param currentPassword current user password
     * @param newPassword new user password
     * @throws Exception
     */
    public void changeUserPassword(String currentPassword, String newPassword) throws Exception {
        this.changeUserPassword(-1, currentPassword, newPassword);
    }

    /**
     * Change Password for a user
     *
     * @param userId User Id or -1 to current user
     * @param currentPassword current user password
     * @param newPassword new user password
     * @throws Exception
     */
    public void changeUserPassword(int userId, String currentPassword, String newPassword) throws Exception {
        if (currentPassword == null) {
            throw new Exception("Invalid CurrentPassword!");
        }
        if (newPassword == null) {
            throw new Exception("Invalid BewPassword!");
        }

        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SUsers.changePassword");
        if (userId == -1) {
            params.put("param1", "\"" + currentPassword + "\"");
            params.put("param2", "\"" + newPassword + "\"");
        } else {
            params.put("param1", "" + userId);
            params.put("param2", "\"" + currentPassword + "\"");
            params.put("param3", "\"" + newPassword + "\"");
        }
        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (!pr.success) {
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

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);

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

            String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
            return gson.fromJson(rt, CDUser.class);

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
        Collection<CDUser> lrt = new ArrayList<>();
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SUsers.listUsers");
        params.put("param1", "" + offset);
        params.put("param2", "" + limit);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        CDUser[] ar = gson.fromJson(rt, CDUser[].class);
        lrt.addAll(Arrays.asList(ar));

        return lrt;
    }

    /**
     * List All SchoolAdministrator users for a school
     *
     * @param schoolId School ID
     * @return Collections of CDUser
     * @throws Exception
     */
    public Collection<CDUser> getSchoolUserAdmins(int schoolId) throws Exception {
        Collection<CDUser> lrt = new ArrayList<>();

        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SUsers.getSchoolUserAdmins");
        params.put("param1", "" + schoolId);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        CDUser[] ar = gson.fromJson(rt, CDUser[].class);
        lrt.addAll(Arrays.asList(ar));
        return lrt;
    }

    /**
     * Get Current logged user info
     *
     * @return CDUser
     * @throws Exception
     */
    public CDUser getSessionUser() throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SUsers.getSessionUser");

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        return gson.fromJson(rt, CDUser.class);

    }

    /**
     * List Schools where the user is School Administrator
     *
     * @param userId User ID
     * @return Collection<CDSchool>
     * @throws Exception
     */
    public Collection<CDSchool> getUserAdminSchools(int userId) throws Exception {
        Collection<CDSchool> lrt = new ArrayList<>();
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SUsers.getUserAdminSchools");
        params.put("param1", "" + userId);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        CDSchool[] ar = gson.fromJson(rt, CDSchool[].class);
        lrt.addAll(Arrays.asList(ar));
        return lrt;
    }

    /**
     * Add a user as School administrator for the school
     *
     * @param schoolId School ID
     * @param userId User ID
     * @throws Exception
     */
    public void addSchoolUserAdmin(int schoolId, int userId) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SUsers.addSchoolUserAdmin");
        params.put("param1", "" + schoolId);
        params.put("param2", "" + userId);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);

        if (!pr.success) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * Remove a user as School administrator for the school
     *
     * @param schoolId School ID
     * @param userId User ID
     * @throws Exception
     */
    public void removeSchoolUserAdmin(int schoolId, int userId) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SUsers.removeSchoolUserAdmin");
        params.put("param1", "" + schoolId);
        params.put("param2", "" + userId);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);

        if (!pr.success) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * Set N Users as School administrators for the school
     *
     * @param schoolId School ID
     * @param userIds Collections of users IDs
     * @throws Exception
     */
    public void setSchoolUserAdmins(int schoolId, Collection<Integer> userIds) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SUsers.setSchoolUserAdmins");
        params.put("param1", "" + schoolId);
        params.put("param2", gson.toJson(userIds));

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);

        if (!pr.success) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    public Collection<CDUser> getUsers(CDUser.Type type) throws Exception {
        Collection<CDUser> lrt = new ArrayList<>();

        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SUsers.getUsers");
        params.put("param1", "" + type);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        CDUser[] ar = gson.fromJson(rt, CDUser[].class);
        lrt.addAll(Arrays.asList(ar));
        return lrt;
    }
}
