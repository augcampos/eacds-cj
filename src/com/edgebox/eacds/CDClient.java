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
import com.google.gson.Gson;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Augusto Campos <a-campos at critical-links.com>
 */
public final class CDClient {

    private final String Server;
    private final int Port;
    private final String ServerLink;
    private final String ServerJavaScriptInterface;

    private final SServer sserver;
    private final SUser ssuser;
    private final SContent scontents;
    private final SSchool sschool;
    private final SDistribution sdistribution;

    private String Username;
    private boolean isLoggedIn;

    private final Gson gson = new Gson();

    /**
     * Constructor for a client
     *
     * @param server
     * @param port
     */
    public CDClient(String server, int port) {
        this.Server = server;
        this.Port = port;

        StringBuilder sb = new StringBuilder();
        if (!this.Server.startsWith("https://")) {
            sb.append("https://");
        }
        sb.append(this.Server);
        sb.append(":");
        sb.append(this.Port);
        sb.append("/");

        this.ServerLink = sb.toString();

        sb.append("JavaScriptInterface");
        this.ServerJavaScriptInterface = sb.toString();

        this.sserver = new SServer(this.ServerJavaScriptInterface);
        this.ssuser = new SUser(this.ServerJavaScriptInterface);
        this.scontents = new SContent(this.ServerJavaScriptInterface);
        this.sschool = new SSchool(this.ServerJavaScriptInterface);
        this.sdistribution = new SDistribution(this.ServerJavaScriptInterface);

    }

    /**
     * Constructor for a client and perform a login action with supplied user
     *
     * <p><b>Note:</b> this will fail if connect or login fails.
     *
     * @param server
     * @param port
     * @param username
     * @param password
     * @throws java.lang.Exception
     *
     *
     */
    public CDClient(String server, int port, String username, String password) throws Exception {
        this(server, port);
        this.login(username, password);
    }

    /**
     * Property to alow access to SServer Module
     *
     * @return SServer
     */
    public SServer getSServer() {
        return this.sserver;
    }

    /**
     * Property to alow access to SUser Module
     *
     * @return SUser
     */
    public SUser getSUser() {
        return this.ssuser;
    }

    /**
     * Property to alow access to SContent Module
     *
     * @return SContent
     */
    public SContent getSContent() {
        return this.scontents;
    }

    /**
     * Property to alow access to SSchool Module
     *
     * @return SSchool
     */
    public SSchool getSSchool() {
        return this.sschool;
    }

    /**
     * Property to alow access to SDistribution Module
     *
     * @return SSchool
     */
    public SDistribution getSDistribution() {
        return this.sdistribution;
    }

    /**
     * Login in the Content Server
     *
     * @param username
     * @param password
     * @return true if login OK
     * @throws java.lang.Exception
     */
    public boolean login(String username, String password) throws Exception {

        this.Username = username;
        Logger.getLogger(this.getClass().getName()).log(Level.FINE, "LOGIN: User {0} on server {1}",
                new Object[]{this.Username, this.ServerLink});

        String tt = CDConnection.Get(String.format("%slogin?user=%s&pass=%s", this.ServerLink, this.Username, password));

        this.isLoggedIn = !tt.contains("Invalid user name or password...");
        if (!this.isLoggedIn) {
            throw new Exception("Invalid user name or password...");
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "LOGIN: OK");
        }

        return this.isLoggedIn;

    }

    /**
     * Logged State
     *
     * @return
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Logout the current user from the Content Server
     *
     * @throws java.io.IOException
     */
    public void logout() throws IOException {

        Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                "LOGOUT: User {0} from server {1}",
                new Object[]{this.Username, this.ServerLink});

        this.isLoggedIn = false;
        this.Username = null;
        CDConnection.Get(String.format("%slogout", this.ServerLink));

        Logger.getLogger(this.getClass().getName()).log(Level.FINE, "LOGOUT: OK");

    }

}
