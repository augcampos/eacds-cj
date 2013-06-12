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

import com.edgebox.eacds.data.CDJob;
import com.edgebox.eacds.data.CDPackageSyncState;
import com.edgebox.eacds.data.CDSchoolSyncState;
import com.edgebox.eacds.data.CDStat;
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
public class SDistribution extends SBaseModule {

    SDistribution(String serverJavaScriptInterface) {
        super(serverJavaScriptInterface);
    }

    /**
     * Request school synchronization
     *
     * @param schoolId school id
     * @throws Exception
     */
    public void requestSchoolSynchronization(int schoolId) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SDistribution.requestSchoolSynchronization");
        params.put("param1", "" + schoolId);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (!pr.success) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * Request school package synchronization
     *
     * @param schoolId school id
     * @param packageId package id
     * @throws Exception
     */
    public void requestSchoolPackageSynchronization(int schoolId, int packageId) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SDistribution.requestSchoolPackageSynchronization");
        params.put("param1", "" + schoolId);
        params.put("param2", "" + packageId);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (!pr.success) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * Request a package synchronization
     *
     * @param packageId package id
     * @throws Exception
     */
    public void requestPackageSynchronization(int packageId) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SDistribution.requestPackageSynchronization");
        params.put("param1", "" + packageId);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (!pr.success) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * Get school synchronizationState information
     *
     * @param schoolId school id
     * @return <CDSchoolSyncState>
     * @throws Exception
     */
    public CDSchoolSyncState getSchoolSynchronizationState(int schoolId) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SDistribution.getSchoolSynchronizationState");
        params.put("param1", "" + schoolId);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (pr.success) {
            String pk_jason = pr.data.toString();
            CDSchoolSyncState pk = gson.fromJson(pk_jason, CDSchoolSyncState.class);
            return pk;
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * Get school packages synchronization information
     *
     * @param schoolId school id
     * @param packageId package id
     * @return <CDSchoolSyncState>
     * @throws Exception
     */
    public CDSchoolSyncState getSchoolPackageSynchronizationState(int schoolId, int packageId) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SDistribution.getSchoolPackageSynchronizationState");
        params.put("param1", "" + schoolId);
        params.put("param2", "" + packageId);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (pr.success) {
            String pk_jason = pr.data.toString();
            CDSchoolSyncState pk = gson.fromJson(pk_jason, CDSchoolSyncState.class);
            return pk;
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * Get package synchronization information
     *
     * @param packageId package id
     * @return <CDPackageSyncState>
     * @throws Exception
     */
    public CDPackageSyncState getPackageSynchronizationState(int packageId) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SDistribution.getPackageSynchronizationState");
        params.put("param1", "" + packageId);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (pr.success) {
            String pk_jason = pr.data.toString();
            CDPackageSyncState pk = gson.fromJson(pk_jason, CDPackageSyncState.class);
            return pk;
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
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

    /**
     * Clear all finished Jobs
     *
     * @throws Exception
     */
    public void clearDoneJobs() throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SDistribution.clearDoneJobs");

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        CDPostResponse pr = gson.fromJson(rt, CDPostResponse.class);
        if (!pr.success) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, pr.log());
            throw new Exception(pr.message);
        }
    }

    /**
     * List statistics from Content Distribution Server
     *
     * @param offset initial offset
     * @param limit limit records return
     * @return Collection<CDStat>
     * @throws Exception
     */
    public Collection<CDStat> listStats(int offset, int limit) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", "SDistribution.listStats");
        params.put("param1", "" + offset);
        params.put("param2", "" + limit);

        String rt = CDConnection.Post(this.ServerJavaScriptInterface, params);

        Collection<CDStat> lrt = new ArrayList<>();
        CDStat[] ar = gson.fromJson(rt, CDStat[].class);
        lrt.addAll(Arrays.asList(ar));

        return lrt;
    }

}
