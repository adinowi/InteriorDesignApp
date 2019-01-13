package pl.lodz.p.interiordesignapp.model;

import android.view.Display;

import java.util.ArrayList;
import java.util.List;

public class SkinModel implements DesignModel{
    private DesignModel mainModel;
    private List<DesignModel> subModels;

    public SkinModel() {
        mainModel = new ModelInfo();
        subModels = new ArrayList<>();
    }

    public DesignModel getMainModel() {
        return mainModel;
    }

    public void setMainModel(DesignModel mainModel) {
        this.mainModel = mainModel;
    }

    public List<DesignModel> getSubModels() {
        return subModels;
    }

    public void setSubModels(List<DesignModel> subModels) {
        this.subModels = subModels;
    }

    @Override
    public String getImagePath() {
        return mainModel.getImagePath();
    }

    @Override
    public String getSfbPath() {
        return mainModel.getSfbPath();
    }

    @Override
    public boolean isDownloaded() {
        return mainModel.isDownloaded();
    }

    @Override
    public String getMainDir() {
        return mainModel.getMainDir();
    }

    @Override
    public String getColor() {
        return mainModel.getColor();
    }
}
