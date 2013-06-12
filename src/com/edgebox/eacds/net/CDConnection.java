/*
 *
 * Copyright (c) Critical Links S.A., All Rights Reserved.
 * (www.critical-links.com)
 *
 * This software is the proprietary information of Critical Links S.A. Use is
 * subject to license terms.
 *
 */
package com.edgebox.eacds.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/**
 *
 * @author Augusto Campos <a-campos at critical-links.com>
 */
public class CDConnection {

    private static final int CONNECTION_TIMEOUT = 30000;
    private static final CookieManager cookieManager = new CookieManager();

    static {
        try {

            // configure the SSLContext with a TrustManager
            SSLContext sslcontext = SSLContext.getInstance("SSL");
            TrustManager[] trustAllCerts = new TrustManager[]{new NullTrustManager()};
            sslcontext.init(new KeyManager[0], trustAllCerts, new SecureRandom());
            //SSLContext.setDefault(sslcontext);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());

            /* disabling hostnamechecking for all https connections statically */
            HttpsURLConnection.setDefaultHostnameVerifier(new NullHostnameVerifier());

            // store jar storage
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(cookieManager);

        } catch (KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(CDConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * HTTP Get action to the URL
     *
     * @param urlStr URL to get
     * @return <String> data returned from the actions get
     * @throws IOException
     */
    public static String Get(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        if (conn.getResponseCode() >= 400) {
            throw new IOException(conn.getResponseMessage());
        }
        String rt = readConnectionContent(conn.getInputStream());
        conn.disconnect();
        return rt;
    }

    /**
     * HTTP Post actions to the URL
     *
     * @param urlStr URL to get
     * @param params <Map> Key value map for post parameters
     * @return <String> data returned from the actions get
     * @throws CDConnectionException
     * @throws Exception
     */
    public static String Post(String urlStr, Map<String, String> params) throws CDConnectionException, Exception {
        Logger.getLogger("CDHTTPConnection").log(Level.FINEST, params.toString());

        URL url = new URL(urlStr);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(CONNECTION_TIMEOUT);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setAllowUserInteraction(false);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        try (OutputStream out = conn.getOutputStream(); Writer writer = new OutputStreamWriter(out, "UTF-8")) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                writer.write(entry.getKey());
                writer.write("=");
                writer.write(entry.getValue());
                writer.write("&");
            }
        }

        if (conn.getResponseCode() >= HttpsURLConnection.HTTP_BAD_REQUEST) {
            CDConnectionException e = new CDConnectionException(conn.getResponseCode(), conn.getResponseMessage());
            throw e;
        }

        InputStream rtsteam = null;
        if ("gzip".equals(conn.getContentEncoding())) {
            rtsteam = decompressStream(conn.getInputStream());
        }

        if (rtsteam == null) {
            rtsteam = conn.getInputStream();
        }

        // Buffer the result into a string
        String rt = readConnectionContent(rtsteam);
        Logger.getLogger(CDConnection.class.getName()).log(Level.FINEST, rt);

        conn.disconnect();
        return rt;

    }

    private static String readConnectionContent(InputStream istream) throws IOException {
        StringBuilder sb;
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(istream, "UTF-8"))) {
            sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private static InputStream decompressStream(InputStream input) throws IOException {
        java.io.PushbackInputStream pb = new java.io.PushbackInputStream(input, 2); //we need a pushbackstream to look ahead
        byte[] signature = new byte[2];
        pb.read(signature); //read the signature
        pb.unread(signature); //push back the signature to the stream
        if (signature[ 0] == (byte) 0x1f && signature[ 1] == (byte) 0x8b) //check if matches standard gzip maguc number
        {
            return new GZIPInputStream(pb);
        } else {
            return pb;
        }
    }

}
