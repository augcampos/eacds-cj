/*
 *
 * Copyright (c) Critical Links S.A., All Rights Reserved.
 * (www.critical-links.com)
 *
 * This software is the proprietary information of Critical Links S.A. Use is
 * subject to license terms.
 *
 */
package com.edgebox.eacds.data;

/**
 *
 * @author Augusto Campos <a-campos at critical-links.com>
 */
public class CDJob extends CDBaseData {

    public long eta;
    public double reate;
    public int jobState;
    public long totalSize;
    public long transferSize;
    public String message;
    public String schoolName;
    public String packageName;

    public CDJob() {
    }

}
