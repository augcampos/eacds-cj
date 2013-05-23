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

/**
 *
 * @author Augusto Campos <a-campos at critical-links.com>
 */
public class CDConnectionException extends Exception {

    private final int statusCode;

    /**
     * Constructor.
     *
     * @param statusCode
     * @param message
     */
    public CDConnectionException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * Gets the HTTP status code.
     *
     * @return HTTP status code
     *
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     *
     * @return Formated log string
     */
    public String log() {
        return String.format("%s: %d - %s", this.getClass().getName(), this.getStatusCode(), this.getMessage());
    }

}
