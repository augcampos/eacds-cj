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

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 *
 * @author Augusto Campos <a-campos at critical-links.com>
 *
 */
class NullHostnameVerifier implements HostnameVerifier {

    /**
     * Method to verify the connected host.
     *
     * @param hostname
     * @param session
     * @return <true>/<false>
     */
    @Override
    public boolean verify(String hostname, SSLSession session) {
        //http://www.jroller.com/hasant/entry/no_subject_alternative_names_matching
        return true;
        //return hostname.equals("192.168.90.49");
    }

}
