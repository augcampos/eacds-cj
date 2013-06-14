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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Augusto Campos <a-campos at critical-links.com>
 */
public abstract class CDBaseData {

    protected static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected static final TimeZone tzgmt = TimeZone.getTimeZone("GMT");
    protected static final TimeZone tzlocal = TimeZone.getDefault();

    /**
     * Clone all data from similar object.
     *
     * @param from data to clone from.
     * <p><b>Note: </b> The Object will be the same the data will be cloned.
     */
    public void cloneData(CDBaseData from) {
        Map<String, Field> of = from.getFields();
        Map<String, Field> tf = this.getFields();

        for (Map.Entry<String, Field> entry : of.entrySet()) {
            String k = entry.getKey();
            Field f = entry.getValue();
            try {
                ((Field) tf.get(k)).set(this, f.get(from));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(CDBaseData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(": ");
        for (Field f : this.getClass().getDeclaredFields()) {
            if (Modifier.isTransient(f.getModifiers())) {
                continue;
            }
            sb.append(f.getName());
            sb.append("=");
            try {
                if (f.getType() == String.class) {
                    sb.append("\"").append(f.get(this)).append("\"");
                } else if (Collection.class.isAssignableFrom(f.getType())) {
                    Collection<Object> c = (Collection<Object>) f.get(this);
                    sb.append("(").append(c.size()).append(")").append(c).append("\"");
                } else {
                    sb.append(f.get(this));
                }
            } catch (IllegalAccessException | IllegalArgumentException ex) {
                sb.append("<Error>");
                //sb.append("<Error:").append(ex.getMessage()).append(">");
            }
            sb.append(", ");
        }
        return sb.toString().replaceAll("\\n", "\\n");
    }

    protected Date toLocalDate(long gsmStamp) {
        Calendar cal = Calendar.getInstance(tzlocal);
        cal.setTimeInMillis(gsmStamp * 1000);
        return cal.getTime();
    }

    protected Date toGMTDate(long gsmStamp) {
        Calendar cal = Calendar.getInstance(tzgmt);
        cal.setTimeInMillis(gsmStamp * 1000);
        return cal.getTime();
    }

    /**
     * Map with all fields from this class.
     *
     * @return
     */
    protected Map<String, Field> getFields() {
        Map<String, Field> of = new HashMap<>();
        for (Field f : this.getClass().getDeclaredFields()) {
            of.put(f.getName(), f);
        }
        return of;
    }

}
