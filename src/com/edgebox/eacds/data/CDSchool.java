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
public class CDSchool extends CDBaseData {

    public int id;
    public int port;
    public int groupId;
    public String ip;
    public String url;
    public String name;
    public String address;
    public String location;
    public String secretKey;
    public long lastDate;
    public long bandwidth;
    public String actionPeriod;
    public long creationDate;
    public int connectionState;
    public int synchronizeState;
    public String licenseId;
    public long pollingInterval;
    public boolean forceSync;
    public int userId;
    public long diskTotal;
    public long diskFree;
    public long lastContact;
    public String message;
    public long messageTimestamp;
    
    public CDSchool() {
    }

}
