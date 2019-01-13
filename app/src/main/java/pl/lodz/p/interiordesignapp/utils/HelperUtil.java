package pl.lodz.p.interiordesignapp.utils;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import android.view.Display;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.lodz.p.interiordesignapp.model.DesignModel;
import pl.lodz.p.interiordesignapp.model.ModelInfo;
import pl.lodz.p.interiordesignapp.model.SkinModel;

public class HelperUtil {
    public static List<DesignModel> getModelsInfo(Resources resources, String category, File filePath) {
        List<DesignModel> result;
        List<DesignModel> internalModels = getInternalModelsInfo(resources, category);
        List<DesignModel> downloadedModels = getDownloadedModelsInfo(new File(filePath + "/" + AppConst.MODELS_DIR + "/" + category), category);
        result = internalModels;
        result.addAll(downloadedModels);

        return  result;
    }

    private static List<DesignModel> getInternalModelsInfo(Resources resources, String category) {
        List<DesignModel> result;
        try {
            AssetManager assetManager = resources.getAssets();
            ArrayList<String> names = new ArrayList<String>(Arrays.asList(assetManager.list(AppConst.MODELS_DIR + "/" + category)));
            result = new ArrayList<>();
            for(String name : names) {
                ModelInfo modelInfo = new ModelInfo((name.split("[.]"))[0] ,false, false, category);
                modelInfo.setSfbName(name);
                modelInfo.setImageName((name.split("[.]"))[0]);
                result.add(modelInfo);
                //result.add(new ModelInfo(name, AppConst.MODELS_DIR + "/" + category + "/", (name.split("[.]"))[0],false));
            }


        } catch (IOException e) {
            result = new ArrayList<>();
        }

        return  result;
    }

    private static List<DesignModel> getDownloadedModelsInfo(File parentDir, String category) {
        ArrayList<DesignModel> downloadedModels = new ArrayList<>();
        if (parentDir.exists()) {
            File[] files = parentDir.listFiles();
            for (File file : files) {
                if(file.isDirectory()) {
                    SkinModel skinModel = new SkinModel();
                    String name = file.getName();
                    for(File model : file.listFiles()) {
                        if(model.isDirectory() && model.getName().equals("sub")) {
                            List<DesignModel> subModels = new ArrayList<>();
                            for (File subModel : model.listFiles()) {
                                subModels.add(createDownloadedModelInfo(true, subModel, name, category));
                            }
                            skinModel.setSubModels(subModels);
                        } else {
                            skinModel.setMainModel(createDownloadedModelInfo(false, model, name, category));
                        }
                    }
                    downloadedModels.add(skinModel);
                }
                /*if (!file.isDirectory() && file.getName().endsWith(".sfb")) {
                    for (File fileImage : files) {
                        String[] name = fileImage.getName().split("[.]");
                        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(name[1]);
                        if(type != null && type.matches("image\\/.*") && file.getName().matches(name[0] + ".*")) {
                            downloadedModels.add(new ModelInfo(file.getName(), AppConst.FILE_DIR + AppConst.MODELS_DIR + "/" + category + "/", fileImage.getName(), true));
                        }
                    }
                }*/
            }
        }
        return downloadedModels;
    }

    public static void deleteFile(Uri uri) {
        File file = new File(uri.getPath());
        if (file.exists()) {
            file.delete();
        }
    }

    private static ModelInfo createDownloadedModelInfo(boolean isSub, File file, String name, String category) {
        ModelInfo modelInfo = new ModelInfo(name, true, false, category);
        String color = file.getName();
        String sfbName = "";
        String imageName = "";
        for(File fileDetail : file.listFiles()) {
            if(!fileDetail.isDirectory() && fileDetail.getName().endsWith(".sfb")){
                sfbName = fileDetail.getName();
            } else {
                imageName = fileDetail.getName();
            }
        }
        modelInfo.setColor(color);
        modelInfo.setImageName(imageName);
        modelInfo.setSfbName(sfbName);
        modelInfo.setDownloaded(true);
        modelInfo.setSub(isSub);
        return modelInfo;
    }
}
