package pl.lodz.p.interiordesignapp.utils;

import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelperUtil {
    public static List<String> getModelsNames(Resources resources, String category) {
        List<String> result;
        try {
            AssetManager assetManager = resources.getAssets();
            result = new ArrayList<String>(Arrays.asList(assetManager.list(AppConst.MODELS_DIR + "/" + category)));
        } catch (IOException e) {
            result = new ArrayList<>();
        }

        return  result;
    }
}
