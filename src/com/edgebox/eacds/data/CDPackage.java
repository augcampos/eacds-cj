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
public class CDPackage extends CDBaseData {

    public static class Type {

        public static final int Public = 0;
        public static final int Private = 1;

    }

    public static class Status {

        public static final int Normal = 0;
        public static final int InMaintenance = 1;
        public static final int Updating = 2;
        public static final int Undefined = -1;

    }
    public int id;
    public int type;
    public String name;
    public String description;
    public int status;
    public int ownerId;
    public String ownerName;
    public long creationDate;
    public double itemsSize;
    public int itemsCount;
    public String keywords;
    
    /* only filled int SContent.listSchoolPackagesSubscribed */
    public int sortPriority = -1;

    public CDPackage() {
    }

}
