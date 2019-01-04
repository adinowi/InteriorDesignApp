package pl.lodz.p.interiordesignapp.model;

public class ModelInfo {
    private String name;
    private String dir;
    private String imagePath;
    private boolean isDownloaded;

    public ModelInfo(String name, String dir, String imagePath,  boolean isDownloaded) {
        this.name = name;
        this.dir = dir;
        this.imagePath = imagePath;
        this.isDownloaded = isDownloaded;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return dir + name;
    }
}
