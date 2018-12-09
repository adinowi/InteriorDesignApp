package pl.lodz.p.interiordesignapp.model;

import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class ArFragmentManager {
    private static ArFragmentManager manager;
    private ArFragment arFragment;
    private ModelRenderable modelRenderable;


    private ArFragmentManager(ArFragment arFragment) {
        this.arFragment = arFragment;
    }

    public static ArFragmentManager getInstance(ArFragment arFragment) {
        if(manager == null) {
            manager = new ArFragmentManager(arFragment);
        }

        return manager;
    }

    public ArFragment getArFragment() {
        return arFragment;
    }

    public ModelRenderable getModelRenderable() {
        return modelRenderable;
    }

    public void setModelRenderable(ModelRenderable modelRenderable) {
        this.modelRenderable = modelRenderable;
    }
}
