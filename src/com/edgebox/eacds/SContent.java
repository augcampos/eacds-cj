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

import com.edgebox.eacds.data.CDPackage;
import com.edgebox.eacds.data.CDPackageContent;
import com.edgebox.eacds.data.CDPackageNode;
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
 * Module to handle Content related actions
 *
 * @author Augusto Campos <a-campos at critical-links.com>
 */
public class SContent extends SBaseModule {

    SContent(String serverJavaScriptInterface) {
        super(serverJavaScriptInterface);
    }

    /**
     * Subscribe Packages to a school
     *
     * @param schoolId school Id
     * @param packages Collection of packagesIds to Subscribe
     * @throws Exception
     */
    public void subscribePackage(int schoolId, Collection<Integer> packages) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SContents.subscribePackage");
        params.put("param1", "" + schoolId);
        params.put("param2", gson.toJson(packages));

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (!pr.success) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * Unsubscribe Packages from a school
     *
     * @param schoolId school Id
     * @param packages Collection of packagesIds to unSubscribe
     * @throws Exception
     */
    public void unsubscribePackage(int schoolId, Collection<Integer> packages) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SContents.unsubscribePackage");
        params.put("param1", "" + schoolId);
        params.put("param2", gson.toJson(packages));

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (!pr.success) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * List all packages subscribed from a school
     *
     * @param schoolId
     * @return
     * @throws java.lang.Exception
     */
    public Collection<CDPackage> listSchoolPackagesSubscribed(int schoolId) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SContents.getPackagesSubscribe");
        params.put("param1", "" + schoolId);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        CDPackage[] ar = gson.fromJson(rt, CDPackage[].class);
        return new ArrayList<>(Arrays.asList(ar));
    }

    /**
     * List all Packages from the Content Server
     *
     * @param text - ?? Always empty
     * @param offset- start offset
     * @param limit - limit returned records
     * @return Collection<CDPackage>
     * @throws java.lang.Exception
     * @see CDPackage
     */
    public Collection<CDPackage> listPackages(String text, int offset, int limit) throws Exception {
        return this.listPackages("SContents.listPackages", text, offset, limit);
    }

    /**
     * List Packages for subscription from the Content Server
     *
     * @param text ?? Always empty
     * @param offset start offset
     * @param limit limit returned records
     * @return Collection<CDPackage>
     * @throws Exception
     */
    public Collection<CDPackage> listPackagesForSubscription(String text, int offset, int limit) throws Exception {
        return this.listPackages("SContents.listPackagesForSubscription", text, offset, limit);
    }

    private Collection<CDPackage> listPackages(String action, String text, int offset, int limit) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", action);
        params.put("param1", "\"" + text + "\"");
        params.put("param2", "" + offset);
        params.put("param3", "" + limit);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPackage[] ar = gson.fromJson(rt, CDPackage[].class);
        return new ArrayList<>(Arrays.asList(ar));

    }

    /**
     * List Package Content (files and directories)
     *
     * @param pakageId - Package id to list content
     * @param offset- start offset
     * @param limit - limit returned records
     * @return Collection<CDPackageContent>
     * @throws java.lang.Exception
     * @see* CDPackageContent
     */
    public Collection<CDPackageNode> listPackageNodes(int pakageId, int offset, int limit) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SContents.listPackage");
        params.put("param1", "" + pakageId);
        params.put("param2", "" + offset);
        params.put("param3", "" + limit);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPackageNode[] ar = gson.fromJson(rt, CDPackageNode[].class);
        return new ArrayList<>(Arrays.asList(ar));

    }

    /**
     * Create a new Package
     *
     * @param pakage package info
     * @throws Exception
     */
    public void createPackage(CDPackage pakage) throws Exception {
        this.setPackage("SContents.createPackage", pakage);
    }

    /**
     * Update Package Info
     *
     * @param pakage
     * @throws Exception
     */
    public void updatePackage(CDPackage pakage) throws Exception {
        this.setPackage("SContents.updatePackage", pakage);
    }

    private void setPackage(String action, CDPackage pakage) throws Exception {
        if (pakage == null) {
            throw new Exception("Invalid pakage information!");
        }
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", action);
        params.put("param1", gson.toJson(pakage));

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (pr.success) {
            // new fields add to pakage class
            String pk_jason = pr.data.toString();
            CDPackage pk = gson.fromJson(pk_jason, CDPackage.class);
            pakage.cloneData(pk);
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * Remove a existing package
     *
     * @param packageId
     * @throws Exception
     */
    public void removePackage(int packageId) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SContents.removePackage");
        params.put("param1", "" + packageId);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);

        if (!pr.success) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * List Schools that have the package subscribed
     *
     * @param packageId package id
     * @return
     * @throws Exception
     */
    public Collection<CDSchool> getSchoolsWithPackageSubscribe(int packageId) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SContents.getSchoolsWithPackageSubscribe");
        params.put("param1", "" + packageId);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        CDSchool[] ar = gson.fromJson(rt, CDSchool[].class);
        return new ArrayList<>(Arrays.asList(ar));
    }

    /**
     * Get info for a Node(path) of a package
     *
     * @param packageId package id
     * @param path node path
     * @return <CDPackageContent>
     * @throws Exception
     */
    public CDPackageContent getContentInfo(int packageId, String path) throws Exception {
        //TODO: Check if this function is works on server
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("method", "SContents.getContentInfo");
            params.put("param1", "" + packageId);
            params.put("param2", "\"" + path + "\"");

            String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
            return gson.fromJson(rt, CDPackageContent.class);

        } catch (CDConnectionException ex) {
            if (ex.getStatusCode() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                return null; // NOTE recive a 400 error on a not invalid id
            }
            throw ex;
        }
    }

    /**
     * synchronize, subscribe and/or unsubscribe packages to a school.
     *
     * @param schoolId school id
     * @param subsPackageIds packages ids to be subscribe for the school
     * @param unsubsPackageIds packages ids to be unsubscribe for the school
     * @throws Exception
     */
    public void synchronizeSubscribePackage(int schoolId, Collection<Integer> subsPackageIds, Collection<Integer> unsubsPackageIds)
            throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SContents.synchronizeSubscribePackage");
        params.put("param1", "" + schoolId);
        params.put("param2", gson.toJson(subsPackageIds));
        params.put("param3", gson.toJson(unsubsPackageIds));

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (!pr.success) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * Create a directory in a package
     *
     * @param packageId package id
     * @param path path to create
     * @return <CDPackageNode>
     * @throws Exception
     */
    public CDPackageNode createDirectory(int packageId, String path) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SContents.createDirectory");
        params.put("param1", "" + packageId);
        params.put("param2", "\"" + path + "\"");

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (pr.success) {
            // new fields add to pakage class
            String pk_jason = pr.data.toString();
            CDPackageNode pk = gson.fromJson(pk_jason, CDPackageNode.class);
            return pk;
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * List all nodes in the package
     *
     * @param packageId Package Id
     * @param offset
     * @param limit
     * @return Collection<CDPackageNode>
     * @throws Exception
     */
    public Collection<CDPackageNode> listPackage(int packageId, int offset, int limit) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SContents.listPackage");
        params.put("param1", "" + packageId);
        params.put("param2", "" + offset);
        params.put("param3", "" + limit);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
        CDPackageNode[] ar = gson.fromJson(rt, CDPackageNode[].class);
        return new ArrayList<>(Arrays.asList(ar));
    }

    /**
     * Get a package
     *
     * @param packageId package Id
     * @return
     * @throws Exception
     */
    public CDPackage getPackage(int packageId) throws Exception {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("method", "SContents.getPackage");
            params.put("param1", "" + packageId);

            String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);
            return gson.fromJson(rt, CDPackage.class);

        } catch (CDConnectionException ex) {
            if (ex.getStatusCode() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                return null; // NOTE recive a 400 error on a not invalid id user
            }
            throw ex;
        }
    }

    /**
     * Move a Node inside a package
     *
     * @param packageId package id
     * @param srcPath source path
     * @param destPath destination path
     * @throws Exception
     */
    public void move(int packageId, String srcPath, String destPath) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SContents.move");
        params.put("param1", "" + packageId);
        params.put("param2", "\"" + srcPath + "\"");
        params.put("param3", "\"" + destPath + "\"");

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (!pr.success) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * Remove a node in package
     *
     * @param packageId
     * @param path
     * @throws Exception
     */
    public void remove(int packageId, String path) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SContents.remove");
        params.put("param1", "" + packageId);
        params.put("param2", "\"" + path + "\"");

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (!pr.success) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

}
