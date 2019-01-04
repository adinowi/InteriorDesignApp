package pl.lodz.p.interiordesignapp.utils;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import pl.lodz.p.interiordesignapp.model.ModelInfo;

public class HelperUtil {
    public static List<ModelInfo> getModelsInfo(Resources resources, String category, File filePath) {
        List<ModelInfo> result;
        List<ModelInfo> internalModels = getInternalModelsInfo(resources, category);
        List<ModelInfo> downloadedModels = getDownloadedModelsInfo(new File(filePath + "/" + AppConst.MODELS_DIR + "/" + category), category);
        result = internalModels;
        result.addAll(downloadedModels);

        return  result;
    }

    private static List<ModelInfo> getInternalModelsInfo(Resources resources, String category) {
        List<ModelInfo> result;
        try {
            AssetManager assetManager = resources.getAssets();
            ArrayList<String> names = new ArrayList<String>(Arrays.asList(assetManager.list(AppConst.MODELS_DIR + "/" + category)));
            result = new ArrayList<>();
            for(String name : names) {
                result.add(new ModelInfo(name, AppConst.MODELS_DIR + "/" + category + "/", (name.split("[.]"))[0],false));
            }


        } catch (IOException e) {
            result = new ArrayList<>();
        }

        return  result;
    }

    private static List<ModelInfo> getDownloadedModelsInfo(File parentDir, String category) {
        ArrayList<ModelInfo> downloadedModels = new ArrayList<>();
        if (parentDir.exists()) {
            File[] files = parentDir.listFiles();
            for (File file : files) {
                if (!file.isDirectory() && file.getName().endsWith(".sfb")) {
                    for (File fileImage : files) {
                        String[] name = fileImage.getName().split("[.]");
                        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(name[1]);
                        if(type != null && type.matches("image\\/.*") && file.getName().matches(name[0] + ".*")) {
                            downloadedModels.add(new ModelInfo(file.getName(), AppConst.FILE_DIR + AppConst.MODELS_DIR + "/" + category + "/", fileImage.getName(), true));
                        }
                    }
                }
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
}
