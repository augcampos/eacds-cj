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

import java.security.cert.CertificateException;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Augusto Campos <a-campos at critical-links.com>
 */
class NullTrustManager implements X509TrustManager {

    /**
     * Method to Check Client Trusted.
     *
     * @param xcs
     * @param string
     * @throws CertificateException
     */
    @Override
    public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String string) throws java.security.cert.CertificateException {
    }

    /**
     * Method to Check Server Trusted.
     *
     * @param xcs
     * @param string
     * @throws CertificateException
     */
    @Override
    public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String string) throws java.security.cert.CertificateException {
    }

    /**
     * Method for getting the Accepted Certificates Issuers.
     *
     * @return
     * <p><b>Note: </b> Accept all certificates
     */
    @Override
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null; // SECURITY ALERT
    }

}
