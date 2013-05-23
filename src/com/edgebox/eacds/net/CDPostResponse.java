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

import com.edgebox.eacds.data.CDBaseData;

/**
 *
 * @author Augusto Campos <a-campos at critical-links.com>
 */
public class CDPostResponse extends CDBaseData {

    /**
     * Action Success status.
     */
    public Boolean success;

    /**
     * Error message
     * <p>Only filled on case <success> is false.
     */
    public String message;

    /**
     * More Error Information
     * <p>Only filled on case <success> is false.
     */
    public String errorTrace;

    /**
     * Object Returned data
     * <p>Only filled on case <success> is true.
     */
    public Object data;

    /**
     * Constructor.
     */
    public CDPostResponse() {
    }

    /**
     * Error log information
     *
     * @return <String> "message - errorTrace"
     */
    public String log() {
        return String.format("%s - %s", message, errorTrace);
    }

}
