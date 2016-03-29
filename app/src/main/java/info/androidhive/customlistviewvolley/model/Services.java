package info.androidhive.customlistviewvolley.model;

import android.util.Log;

public class Services {
    private int app_flow, role_id;
    private String role_name, image;

    public Services () {
    }

    public Services (int app_flow, int role_id, String role_name, String image) {
        this.app_flow = app_flow;
        this.role_id = role_id;
        this.role_name = role_name;
        this.image = image;
    }

    public int getApp_flow () {
        return app_flow;
    }

    public void setApp_flow (int app_flow) {
        this.app_flow = app_flow;
        Log.d("app_flow", "" + app_flow);
    }

    public int getRole_id () {
        return role_id;
    }

    public void setRole_id (int role_id) {
        this.role_id = role_id;
        Log.d ("role_id", "" + role_id);
    }

    public String getRole_name () {
        return role_name;
    }

    public void setRole_name (String role_name) {
        this.role_name = role_name;
        Log.d ("role_name", role_name);
    }

    public String getImage () {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
        Log.d ("image", image);
    }
    

}
