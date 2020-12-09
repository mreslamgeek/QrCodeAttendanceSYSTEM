package com.eslam.qrcodeattendancesystem.models;

public class SessionModel {
    private static final SessionModel ourInstance = new SessionModel();
    public static SessionModel getInstance() {
        return ourInstance;
    }



    String user_id, session_key_id, session_title, session_description, session_image, session_date;

    public SessionModel(String user_id, String session_key_id, String session_title, String session_description, String session_image, String session_date) {
        this.user_id = user_id;
        this.session_title = session_title;
        this.session_key_id = session_key_id;
        this.session_description = session_description;
        this.session_image = session_image;
        this.session_date = session_date;
    }

    public SessionModel() {
    }

    public String getSession_key_id() {
        return session_key_id;
    }

    public void setSession_key_id(String session_key_id) {
        this.session_key_id = session_key_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSession_title() {
        return session_title;
    }

    public void setSession_title(String session_title) {
        this.session_title = session_title;
    }

    public String getSession_description() {
        return session_description;
    }

    public void setSession_description(String session_description) {
        this.session_description = session_description;
    }

    public String getSession_image() {
        return session_image;
    }

    public void setSession_image(String session_image) {
        this.session_image = session_image;
    }

    public String getSession_date() {
        return session_date;
    }

    public void setSession_date(String session_date) {
        this.session_date = session_date;
    }
}
