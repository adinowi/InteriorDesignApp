package pl.lodz.p.interiordesignapp.model;

import android.content.Context;
import android.net.Uri;
import android.view.Gravity;
import android.widget.Toast;

import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import pl.lodz.p.interiordesignapp.utils.AppConst;

public class ArFragmentManager {
    private static ArFragmentManager manager;
    private ArFragment arFragment;
    private ModelRenderable modelRenderable;
    private Context context;
    private String name = "drawer.sfb";


    private ArFragmentManager(Context context) {
        this.context = context;
    }

    public static ArFragmentManager getInstance( Context context) {
        if(manager == null) {
            manager = new ArFragmentManager(context);
        }
        return manager;
    }

    public ArFragment getArFragment() {
        return arFragment;
    }

    public ModelRenderable getModelRenderable() {
        return modelRenderable;
    }

    public void setModelRenderable(String modelName) {
        ModelRenderable.builder()
                .setSource(context, Uri.parse(modelName))
                .build()
                .thenAccept(renderable -> modelRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(context, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
