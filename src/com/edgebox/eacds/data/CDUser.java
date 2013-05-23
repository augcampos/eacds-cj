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
public class CDUser extends CDBaseData {

    /**
     * Types of users.
     */
    public static class Type {

        /**
         * Gest user.
         */
        public static int Gest = 0;

        /**
         * Normal user.
         */
        public static int Normal = 1;

        /**
         * School Administrator user.
         */
        public static int SchoolAdministrator = 2;

        /**
         * Administrator users.
         */
        public static int Administrator = 3;
    }

    /**
     * User Name.
     */
    public String name;

    /**
     * User Login
     */
    public String login;

    /**
     * Type of user.
     *
     * @see CDUser.Type
     */
    public int type;

    /**
     * User password holder.
     * <p>Can be a hash of the password</p>
     * <p>Can be a password </p>
     */
    public String password;

    /**
     * User ID
     */
    public int id;

    /**
     * User creation GMT stamp
     */
    public long creation;

    /**
     * Users las access GMT stamp
     */
    public long last;

    public CDUser() {
    }

    public CDUser(String name, String login, int type, String password) {
        this.name = name;
        this.login = login;
        this.type = type;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("%s Creation(D)=%s, Last(D)=%s",
                super.toString(),
                sdfDate.format(this.toLocalDate(creation)),
                sdfDate.format(this.toLocalDate(last)));
    }

}
