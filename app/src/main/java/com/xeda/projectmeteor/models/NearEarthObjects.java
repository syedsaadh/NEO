
package com.xeda.projectmeteor.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NearEarthObjects {

    @SerializedName("meteor-objects")
    @Expose
    private String date;
    @SerializedName("meteor-objects")
    @Expose
    private List<MeteorObjects> meteorObjects = null;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<MeteorObjects> getMeteorObjects() {
        return meteorObjects;
    }

    public void setMeteorObjects(List<MeteorObjects> meteorObjects) {
        this.meteorObjects = meteorObjects;
    }

}
