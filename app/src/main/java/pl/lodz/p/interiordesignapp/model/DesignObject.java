package pl.lodz.p.interiordesignapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DesignObject implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("category")
    private String category;
    @SerializedName("image")
    private String imageURL;
    @SerializedName("sfb")
    private String sfbURL;

    public DesignObject(String name, String category, String imageURL, String sfbURL) {
        this.name = name;
        this.category = category;
        this.imageURL = imageURL;
        this.sfbURL = sfbURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSfbURL() {
        return sfbURL;
    }

    public void setSfbURL(String sfbURL) {
        this.sfbURL = sfbURL;
    }
}