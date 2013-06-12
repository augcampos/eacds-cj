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
public class CDSchoolSyncState extends CDBaseData {

    public int eta;
    public int schoolId;
    public int packageId;
    public int transferSize;
    public float transferRate;
    public int totalFiles;
    public int totalDirectories;
    public int remainingFiles;
    public int remainingDirectories;
    public String message;
    public int jobState;
    public long totalSize;
    public long remainingSize;

    public CDSchoolSyncState() {
    }

}
