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
public class CDPackageSyncState extends CDBaseData {

    public int packageId;
    public int subscribedSchools;
    public int synchronizedSchools;

    public CDPackageSyncState() {
    }

}
