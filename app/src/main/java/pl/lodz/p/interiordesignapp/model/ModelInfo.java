package pl.lodz.p.interiordesignapp.model;

import pl.lodz.p.interiordesignapp.utils.AppConst;

public class ModelInfo implements DesignModel{
    private String name;
    private String sfbName;
    private String imageName;
    private boolean isDownloaded;
    private String color;
    private boolean isSub;
    private String category;

    public ModelInfo() {
        name = "";
        sfbName = "";
        imageName = "";
        isDownloaded = false;
        color = "";
        isSub = false;
    }

    public ModelInfo(String name, boolean isDownloaded, boolean isSub, String category) {
        this.name = name;
        this.isDownloaded = isDownloaded;
        this.isSub = isSub;
        this.category = category;
    }

    public ModelInfo(String name, String sfbName, String imageName, boolean isDownloaded, String color, boolean isSub, String category) {
        this.name = name;
        this.sfbName = sfbName;
        this.imageName = imageName;
        this.isDownloaded = isDownloaded;
        this.color = color;
        this.isSub = isSub;
        this.category = category;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSfbName() {
        return sfbName;
    }

    public void setSfbName(String sfbName) {
        this.sfbName = sfbName;
    }

    @Override
    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }
    @Override
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isSub() {
        return isSub;
    }

    public void setSub(boolean sub) {
        isSub = sub;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getImagePath() {
        if(isDownloaded) {
            return AppConst.FILE_DIR + AppConst.MODELS_DIR + "/" + category + "/" + name + "/" + color + "/" + imageName ;
        }
        return imageName;
    }

    @Override
    public String getSfbPath() {
        if(isDownloaded) {
            if(isSub) {
                return AppConst.FILE_DIR + AppConst.MODELS_DIR + "/" + category + "/" + name + "/" + AppConst.SUB_MODELS_DIR + "/" + color + "/" + sfbName ;
            }
            return AppConst.FILE_DIR + AppConst.MODELS_DIR + "/" + category + "/" + name + "/" + color + "/" + sfbName ;
        }
        return "//android_asset/" + AppConst.MODELS_DIR + "/" + category + "/" + sfbName;
    }

    @Override
    public String toString() {
        return sfbName + name;
    }

    @Override
    public String getMainDir() {
        if(isDownloaded) {
            return  AppConst.FILE_DIR + AppConst.MODELS_DIR + "/" + category + "/" + name;
        }
        return "";
    }
}
