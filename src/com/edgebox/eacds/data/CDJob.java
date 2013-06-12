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

    public static class State {

        public static final int WAITING = 0;
        public static final int RUNNING = 1;
        public static final int CANCEL = 2;
        public static final int ERROR = 3;
        public static final int FINISHED = 4;
        public static final int EXPIRED = 5;
    };

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
